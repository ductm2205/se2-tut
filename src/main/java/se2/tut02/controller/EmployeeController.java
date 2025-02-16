package se2.tut02.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import se2.tut02.model.Employee;
import se2.tut02.repository.EmployeeRepository;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(value = "/")
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();

        model.addAttribute("employees", employees);
        return "employeeList";
    }

    @RequestMapping(value = "/detail/{id}")
    public String getEmployeeById(@PathVariable(value = "id") Long id, Model model) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            model.addAttribute("employee", optionalEmployee.get()); // Unwrap Optional
        } else {
            return "error"; // Handle missing employee case
        }

        return "employeeDetails";
    }

}
