package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getCompanyWithSpecificName(String companyName) {
        return companyRepository.findAllByCompanyName(companyName);
    }

    public List<Employee> getEmployeesInCompany(Integer companyId) {
        Company companyWithSpecificId =  companyRepository.findById(companyId).orElse(null);
        if (companyWithSpecificId == null) {
            return null;
        }
        return companyWithSpecificId.getEmployeeList();
    }

    public List<Company> createNewCompany(Company company) {
        companyRepository.saveAndFlush(company);
        return companyRepository.findAll();
    }

    public Company updateCompany(Integer companyId, Company company) {
        Company oldCompany = companyRepository.findById(companyId).orElse(null);
        if (oldCompany != null) {
            oldCompany.setEmployeeList(company.getEmployeeList());
            oldCompany.setCompanyName(company.getCompanyName());
            oldCompany.setEmployeesNumber(company.getEmployeesNumber());
        }
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Company> deleteCompany(Integer companyId) {
        companyRepository.deleteById(companyId);
        return companyRepository.findAll();
    }

    public List<Company> returnCompanyListWithSpecificSize(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company getCompanyWithSpecificId(Integer companyId) {
        Company companyWithSpecificId = companyRepository.findById(companyId).orElse(null);
        if (companyWithSpecificId == null) {
            return null;
        }
        return companyWithSpecificId;
    }
}
