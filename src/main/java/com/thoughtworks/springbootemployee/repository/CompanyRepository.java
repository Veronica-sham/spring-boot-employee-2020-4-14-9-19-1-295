package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        List<Employee> employeeList1 = new ArrayList<>();
        List<Employee> employeeList2 = new ArrayList<>();
        companies.add(new Company("OOCL", employeeList1, 4));
        employeeList1.add(new Employee(1, "Amy", 20, "Female", 8000));
        employeeList1.add(new Employee(2, "Hsa", 23, "Male", 5000));
        employeeList1.add(new Employee(3, "Anwars", 22, "Female", 9400));
        employeeList1.add(new Employee(4, "Uio", 25, "Male", 9900));
        companies.add(new Company("ABC", employeeList2, 5));
        employeeList2.add(new Employee(1, "dcfs", 67, "Female", 9000));
        employeeList2.add(new Employee(2, "dvc", 34, "Male", 9000));
        employeeList2.add(new Employee(3, "xv", 23, "Female", 9200));
        employeeList2.add(new Employee(4, "svd", 55, "Male", 9900));
        employeeList2.add(new Employee(5, "rtw", 35, "Male", 7900));
    }


    public List<Company> getAllCompany() {
        return companies;
    }


    public List<Company> getCompanyWithSpecificName(String companyName) {
        return companies.stream().filter(company -> company.getCompanyName().equals(companyName)).collect(Collectors.toList());
    }

    public List<Employee> getEmployeesInCompany(String companyName) {
        Company companyWithSpecificName = companies.stream().filter(company -> company.getCompanyName().equals(companyName)).findFirst().orElse(null);
        return companyWithSpecificName.getEmployeeList();
    }

    public List<Company> createNewCompany(Company company) {
        companies.add(company);
        return companies;
    }

    public Company updateCompany(String companyName, Company company) {
        Company oldCompany = companies.stream().filter(thatCompany -> thatCompany.getCompanyName().equals(companyName)).findFirst().get();
        int oldCompanyIndex = companies.indexOf(oldCompany);
        companies.get(oldCompanyIndex).update(company);
        return companies.get(oldCompanyIndex);
    }


    public List<Company> deleteAllEmployees(String companyName) {
        Company oldCompany = companies.stream().filter(staff -> staff.getCompanyName().equals(companyName)).findFirst().get();
        int oldCompanyIndex = companies.indexOf(oldCompany);
        companies.get(oldCompanyIndex).setEmployeeList(null);
        companies.get(oldCompanyIndex).setEmployeesNumber(0);
        return companies;
    }


    public List<Company> returnCompanyListWithSpecificSize(Integer page, Integer pageSize) {
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min((startIndex + pageSize), companies.size());
        return companies.subList(startIndex, endIndex);
    }
}
