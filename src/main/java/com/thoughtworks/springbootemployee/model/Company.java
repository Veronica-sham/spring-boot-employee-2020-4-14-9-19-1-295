package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {

    List<Employee> employeeList;
    private String companyName;
    private int employeesNumber;


    public Company(String companyName, List<Employee> employeeList, int employeesNumber) {
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
        this.employeeList = employeeList;
    }

    public List<Employee> getEmployeeList() {
        return this.employeeList;
    }

    public void setEmployeeList(List<Employee> employees) {
        this.employeeList = employees;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeesNumber() {
        return this.employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public void update(Company company) {
        this.companyName = company.getCompanyName();
        this.employeeList = company.getEmployeeList();
        this.employeesNumber = company.getEmployeeList().size();
    }
}
