package se2.tut02.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();

        model.addAttribute("employees", employees);
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
