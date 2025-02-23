package se2.tut02.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import se2.tut02.model.Company;
import se2.tut02.repository.CompanyRepository;

@Controller
@RequestMapping(value = "/company")
public class CompanyController {

    private CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping(value = "/")
    public String getAll(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        return "company/companyList";
    }

    @GetMapping(value = "/{id}")
    public String getCompanyById(@PathVariable(value = "id") Long id, Model model) {
        Company company = companyRepository.findById(id).get();

        model.addAttribute("company", company);
        model.addAttribute("employees", company.getEmployees());

        return "company/companyDetails";
    }

    @GetMapping(value = "/add")
    public String addCompany(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "company/companyAdd";
    }

    @GetMapping(value = "/update/{id}")
    public String updateCompany(@PathVariable(value = "id") Long id, Model model) {
        Company company = companyRepository.findById(id).get();
        model.addAttribute("company", company);
        return "company/companyUpdate";
    }

    @PostMapping(value = "/save")
    public String save(Company company) {
        companyRepository.save(company);
        return "redirect:/company/" + company.getId();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCompany(@PathVariable(value = "id") Long id) {
        Company company = companyRepository.findById(id).get();
        if (company != null) {
            companyRepository.delete(company);
        }
        return "redirect:/company/";
    }

}
