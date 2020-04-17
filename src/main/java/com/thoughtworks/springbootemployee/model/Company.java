package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    private int employeesNumber;

    @OneToMany(targetEntity = Employee.class, mappedBy = "companyId", fetch = FetchType.EAGER)
    private List<Employee> employeeList;

    public Company(String companyName, List<Employee> employeeList, int employeesNumber) {
        this.employeesNumber = employeesNumber;
        this.employeeList = employeeList;
        this.companyName = companyName;
    }


}
