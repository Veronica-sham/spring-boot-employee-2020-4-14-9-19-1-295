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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies() {
        return service.getAllCompanies();
    }

    @GetMapping(params = "companyName")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompanyWithSpecificName(@RequestParam(required = false) String companyName) {
        return service.getCompanyWithSpecificName(companyName);
    }

    @GetMapping(path = "{companyName}/employee")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesInCompany(@PathVariable String companyName) {
        return service.getEmployeesInCompany(companyName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Company> createNewCompany(@RequestBody Company company) {
        return service.createNewCompany(company);
    }

    @PutMapping("/{companyName}")
    public Company updateCompanies(@PathVariable String companyName, @RequestBody Company company) {
        return service.updateCompany(companyName , company);
    }

    @DeleteMapping("/{companyName}")
    public List<Company> deleteAllEmployees(@PathVariable String companyName) {
        return service.deleteAllEmployees(companyName);
    }

    @GetMapping(params = {"page", "pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Company> returnCompanyListOfSpecificSize(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return service.returnCompanyListWithSpecificSize(page, pageSize);
    }
}
