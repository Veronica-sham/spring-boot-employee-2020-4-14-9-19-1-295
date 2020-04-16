package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository(){
        employees.add(new Employee(1, "Paul", 18, "Male", 4000));
        employees.add(new Employee(2, "Amy", 20, "Female", 8000));
        employees.add(new Employee(3, "May", 23, "Female", 9000));
        employees.add(new Employee(4, "King", 18, "Male", 7000));
        employees.add(new Employee(5, "Rory", 18, "Male", 7000));
        employees.add(new Employee(6, "Kelvin", 18, "Male", 7000));
        employees.add(new Employee(7, "Keith", 18, "Male", 7000));
        employees.add(new Employee(8, "Chris", 18, "Male", 7000));
        employees.add(new Employee(9, "Warren", 18, "Male", 7000));
        employees.add(new Employee(10, "King", 18, "Male", 7000));
        employees.add(new Employee(11, "Rory", 18, "Male", 7000));
        employees.add(new Employee(12, "Kelvin", 18, "Male", 7000));
        employees.add(new Employee(13, "Keith", 18, "Male", 7000));
        employees.add(new Employee(14, "Chris", 18, "Male", 7000));
        employees.add(new Employee(15, "Warren", 18, "Male", 7000));
    }

    public List<Employee> getAllEmployee(){
        return employees;
    }

    public List<Employee> createNewEmployee(Employee employee) {
        employees.add(employee);
        return employees;
    }

    public List<Employee> findEmployeeByID(Integer employeeID) {
        return employees.stream().filter(employee -> employee.getId() == employeeID).collect(Collectors.toList());
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }


    public Employee updateEmployee(Integer employeeID, Employee employee) {
        Employee oldEmployee = employees.stream().filter(staff -> staff.getId() == employeeID).findFirst().orElse(null);
        System.out.println(oldEmployee.getName());
        int oldEmployeeIndex = employees.indexOf(oldEmployee);
        employees.get(oldEmployeeIndex).update(employeeID, employee.getName(), employee.getGender(), employee.getAge(), employee.getSalary());
        System.out.println("\n"+oldEmployee.getName());
        return employees.get(oldEmployeeIndex);
    }

    public List<Employee> deleteEmployee(Integer employeeID) {
        Employee oldEmployee = employees.stream().filter(staff -> staff.getId() == employeeID).findFirst().orElse(null);
        employees.remove(oldEmployee);
        return employees;
    }

    public List<Employee> returnSpecificNumberOfEmployees(int startIndex, int endIndex) {
        return employees.subList(startIndex, endIndex);
    }
}
