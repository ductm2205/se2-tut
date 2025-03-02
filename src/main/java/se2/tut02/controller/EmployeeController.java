package se2.tut02.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import se2.tut02.model.Company;
import se2.tut02.model.Employee;
import se2.tut02.repository.CompanyRepository;
import se2.tut02.repository.EmployeeRepository;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @RequestMapping(value = "/")
    public String getAllEmployees(
            Model model,
            @RequestParam(value = "company", required = false, defaultValue = "0") Long companyId,
            @RequestParam(value = "sort", required = false, defaultValue = "0") int sortMode) {
        List<Company> companies = companyRepository.findAll();
        Sort sort;
        String sortColumn = "id";
        Sort.Direction sortOrder = Sort.Direction.DESC;
        if (sortMode == 1 || sortMode == 2) {
            sortOrder = Sort.Direction.ASC;
        }
        if (sortMode == 2 || sortMode == 3) {
            sortColumn = "name";
        }

        sort = Sort.by(sortOrder, sortColumn);
        List<Employee> employees = null;

        if (companyId != 0) {
            Optional<Company> comp = companyRepository.findById(companyId);
            if (comp.isPresent()) {
                employees = employeeRepository.findByCompany(
                        comp.get(),
                        sort);
            }
        }
        if (employees == null) {
            employees = employeeRepository.findAll(sort);
        }

        model.addAttribute("companies", companies);
        model.addAttribute("employees", employees);
        model.addAttribute("companyId", companyId);
        model.addAttribute("sortMode", sortMode);
        return "employee/employeeList";
    }

    @RequestMapping(value = "/detail/{id}")
    public String getEmployeeById(@PathVariable(value = "id") Long id, Model model) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            model.addAttribute("employee", optionalEmployee.get()); // Unwrap Optional
        } else {
            return "error"; // Handle missing employee case
        }

        return "employee/employeeDetails";
    }

    @GetMapping(value = "/update/{id}")
    public String updateEmployee(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id).get();
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("employee", employee);
        model.addAttribute("companies", companies);
        return "employee/employeeUpdate";
    }

    @PostMapping(value = "/save")
    public String saveUpdate(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employee/detail/" + employee.getId();
    }

    @GetMapping(value = "/add")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("employee", employee);
        model.addAttribute("companies", companies);
        return "employee/employeeAdd";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        if (employeeRepository.findById(id).isPresent()) {
            Employee employee = employeeRepository.findById(id).get();
            employeeRepository.delete(employee);
        }
        return "redirect:/employee/";
    }

}
