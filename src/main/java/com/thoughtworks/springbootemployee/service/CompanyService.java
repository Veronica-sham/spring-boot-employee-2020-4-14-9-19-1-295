package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;


    public List<Company> getAllCompanies() {
        return companyRepository.getAllCompany();
    }

    public List<Company> getCompanyWithSpecificName(String companyName) {
        return companyRepository.getCompanyWithSpecificName(companyName);

    }

    public List<Employee> getEmployeesInCompany(String company) {
        return companyRepository.getEmployeesInCompany(company);
    }

    public List<Company> createNewCompany(Company company) {
        return companyRepository.createNewCompany(company);
    }

    public Company updateCompany(String companyName, Company company) {
        return companyRepository.updateCompany(companyName, company);
    }

    public List<Company> deleteAllEmployees(String companyName) {
        return companyRepository.deleteAllEmployees(companyName);
    }

    public List<Company> returnCompanyListWithSpecificSize(Integer page, Integer pageSize) {
        return companyRepository.returnCompanyListWithSpecificSize(page, pageSize);
    }
}
