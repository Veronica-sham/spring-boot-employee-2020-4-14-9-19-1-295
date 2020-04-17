package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/companies")

public class CompanyController {


    @Autowired
    private CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies() {
        return service.getAllCompanies();
    }

    @GetMapping(params = "companyId")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyWithSpecificId(@RequestParam(required = false) Integer companyId) {
        return service.getCompanyWithSpecificId(companyId);
    }

    @GetMapping(path = "{companyId}/employee")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesInCompany(@PathVariable Integer companyId) {
        return service.getEmployeesInCompany(companyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Company> createNewCompany(@RequestBody Company company) {
        return service.createNewCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompanies(@PathVariable Integer companyId, @RequestBody Company company) {
        return service.updateCompany(companyId , company);
    }

    @DeleteMapping("/{companyId}")
    public List<Company> deleteCompany(@PathVariable Integer companyId) {
        return service.deleteCompany(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Company> returnCompanyListOfSpecificSize(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return service.returnCompanyListWithSpecificSize(page, pageSize);
    }
}
