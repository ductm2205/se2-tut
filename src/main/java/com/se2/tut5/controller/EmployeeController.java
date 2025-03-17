package com.se2.tut5.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.se2.tut5.model.Company;
import com.se2.tut5.model.Employee;
import com.se2.tut5.repository.CompanyRepository;
import com.se2.tut5.repository.EmployeeRepository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private static final String COMPANIES = "companies";
    private static final String EMPLOYEE = "employee";

    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;

    public EmployeeController(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @RequestMapping("/")
    public String getAllEmployee(
            @RequestParam(value = "company", required = false, defaultValue = "0") Long comId,
            @RequestParam(value = "sort", required = false, defaultValue = "0") int sortMode,
            Model model) {
        Sort.Direction sortOrder = Sort.Direction.DESC;
        String sortColumn = "id";
        if (sortMode == 1 || sortMode == 2) {
            sortOrder = Sort.Direction.ASC;
        }
        if (sortMode == 2 || sortMode == 3) {
            sortColumn = "name";
        }
        List<Employee> employees = null;
        if (comId != 0) {
            Optional<Company> comp = companyRepository.findById(comId);
            if (comp.isPresent()) {
                employees = employeeRepository.findByCompany(
                        comp.get(),
                        Sort.by(sortOrder, sortColumn));
            }
        }
        if (employees == null) {
            // failed to filter by Company
            employees = employeeRepository.findAll(
                    Sort.by(sortOrder, sortColumn));
        }
        model.addAttribute("employees", employees);
        List<Company> companies = companyRepository.findAll();
        model.addAttribute(COMPANIES, companies);
        model.addAttribute("comId", comId);
        model.addAttribute("sortMode", sortMode);
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
