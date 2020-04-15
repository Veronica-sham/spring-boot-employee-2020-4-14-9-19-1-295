package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@RequestMapping("/companies")

public class CompanyController {

    EmployeeController employeeController = new EmployeeController();

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("OOCL",employeeController.getAllEmployees()));
        return companies;
    }

    @GetMapping(params = "companyName")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompanyWithSpecificName(@RequestParam(required = false) String companyName) {
        List<Company> companies = getAllCompanies();
        List<Company> companyWithSpecificName = companies.stream().filter(company -> company.getCompanyName().equals(companyName)).collect(Collectors.toList());
        return companyWithSpecificName;
    }

    @GetMapping(path = "{companyName}/employee")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesInCompany(@PathVariable String companyName) {
        List<Company> companies = getAllCompanies();
        Company companyWithSpecificName = companies.stream().filter(company -> company.getCompanyName().equals(companyName)).findFirst().get();
        List<Employee> employeeList = companyWithSpecificName.getEmployeeList();
        return employeeList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Company> createNewEmployee(@RequestBody Company company) {
        List<Company> companies = getAllCompanies();
        companies.add(company);
        return companies;
    }

    @PutMapping("/{companyName}")
    public Company updateCompanies(@PathVariable String companyName, @RequestBody Company company) {
        List<Company> companies = getAllCompanies();
        Company oldCompany = companies.stream().filter(thatCompany -> thatCompany.getCompanyName().equals(companyName)).findFirst().get();
        int oldCompanyIndex = companies.indexOf(oldCompany);
        companies.get(oldCompanyIndex).update(company);
        return companies.get(oldCompanyIndex);
    }

    @DeleteMapping("/{companyName}")
    public List<Company> deleteAllEmployees(@PathVariable String companyName) {
        List<Company> companies = getAllCompanies();
        Company oldCompany = companies.stream().filter(staff -> staff.getCompanyName().equals(companyName)).findFirst().get();
        int oldCompanyIndex = companies.indexOf(oldCompany);
        companies.get(oldCompanyIndex).setEmployeeList(null);
        companies.get(oldCompanyIndex).setEmployeesNumber(0);
        return companies;
    }

}
