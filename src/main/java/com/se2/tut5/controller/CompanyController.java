package com.se2.tut5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.se2.tut5.model.Company;
import com.se2.tut5.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private static final String COMPANIES = "companies";
    private static final String COMPANY = "company";

    private CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @RequestMapping("/list")
    public String getAllCompany(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute(COMPANIES, companies);
        return "companyList";
    }

    @RequestMapping("/{id}")
    public String getCompanyById(
            @PathVariable("id") Long id, Model model) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            model.addAttribute(COMPANY, company.get());
            model.addAttribute("employees", company.get().getEmployees());
            return "companyDetail";
        } else {
            return "404";
        }
    }

    @GetMapping("/add")
    public String addCompany(Model model) {
        Company company = new Company();
        model.addAttribute(COMPANY, company);
        return "companyAdd";
    }

    @GetMapping("/update/{id}")
    public String updateCompany(
            @PathVariable("id") Long id, Model model) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            model.addAttribute(COMPANY, company.get());
            return "companyUpdate";
        } else {
            return "404";
        }
    }

    @PostMapping("/save")
    public String saveUpdate(Company company) {
        companyRepository.save(company);
        return "redirect:/company/" + company.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        if (companyRepository.findById(id).isPresent()) {
            Optional<Company> company = companyRepository.findById(id);
            if (company.isPresent()) {
                companyRepository.delete(company.get());
                return "redirect:/company/list";
            }
        }
        return "404";
    }
}
