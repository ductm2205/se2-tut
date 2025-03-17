package com.se2.tut5.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.se2.tut5.model.Company;
import com.se2.tut5.model.Employee;
import com.se2.tut5.repository.CompanyRepository;
import com.se2.tut5.repository.EmployeeDao;
import com.se2.tut5.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private static final String COMPANIES = "companies";
    private static final String EMPLOYEE = "employee";

    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;
    private EmployeeDao employeeDao;

    public EmployeeController(EmployeeRepository employeeRepository, CompanyRepository companyRepository,
            EmployeeDao employeeDao) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.employeeDao = employeeDao;
    }

    @RequestMapping("/")
    public String getAllEmployee(
            @RequestParam(value = "company", required = false, defaultValue = "0") Long comId,
            @RequestParam(value = "gender", required = false, defaultValue = "0") int gender,
            @RequestParam(value = "sort", required = false, defaultValue = "0") int sortMode,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model) {
        final int pageSize = 1; // You can change this value to adjust page size
        List<Company> companies = companyRepository.findAll();
        model.addAttribute(COMPANIES, companies);
        
        Page<Employee> employees = employeeDao.filterAndSortEmployees(
                comId, gender, sortMode,
                PageRequest.of(page, pageSize));
        
        model.addAttribute("employees", employees.getContent());
        model.addAttribute("comId", comId);
        model.addAttribute("gender", gender);
        model.addAttribute("sortMode", sortMode);
        model.addAttribute("page", page);
        model.addAttribute("pages", employees.getTotalPages());
        
        return "employeeList";
    }

    @RequestMapping("/detail/{id}")
    public String getEmployeeById(@PathVariable(value = "id") Long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            model.addAttribute(EMPLOYEE, employee.get());
            return "employeeDetail";
        }
        return "404";
    }

    @GetMapping("/add")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        List<Company> companies = companyRepository.findAll();
        model.addAttribute(EMPLOYEE, employee);
        model.addAttribute(COMPANIES, companies);
        return "employeeAdd";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(
            @PathVariable(value = "id") Long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            model.addAttribute(EMPLOYEE, employee.get());
            model.addAttribute(COMPANIES, companyRepository.findAll());
            return "employeeUpdate";
        }
        return "404";
    }

    @PostMapping("/save")
    public String saveUpdate(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employee/detail/" + employee.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        if (employeeRepository.findById(id).isPresent()) {
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isPresent()) {
                employeeRepository.delete(employee.get());
                return "redirect:/employee/";
            }
        }
        return "404";
    }
}
