package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<>();
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

        return employees;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> createNewEmployee(@RequestBody Employee employee) {
        List<Employee> employees = getAllEmployees();
        employees.add(employee);
        return employees;
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesWithSpecificID(@PathVariable int id) {
        List<Employee> employees = getAllEmployees();
        List<Employee> employeeWithSpecificID = employees.stream().filter(employee -> employee.getId() == id).collect(Collectors.toList());
        return employeeWithSpecificID;
    }

    @GetMapping(params = {"page", "pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> paging(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        //check null-->return all
        List<Employee> employees = getAllEmployees();
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min((startIndex + pageSize), employees.size());
        return employees.subList(startIndex, endIndex);
    }

    @GetMapping(params = "gender")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesWithSpecificGender(@RequestParam(required = false) String gender) {
        List<Employee> employees = getAllEmployees();
        List<Employee> employeeWithSpecificGender = employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
        return employeeWithSpecificGender;
    }

    @PutMapping("/{employeeId}")
    public Employee update(@PathVariable Integer employeeId, @RequestBody Employee employee) {
        List<Employee> employees = getAllEmployees();
        Employee oldEmployee = employees.stream().filter(staff -> staff.getId() == employeeId).findFirst().get(); //orElse , if return null
        int oldEmployeeIndex = employees.indexOf(oldEmployee);
        employees.get(oldEmployeeIndex).update(employeeId, employee.getName(), employee.getGender(), employee.getAge(), employee.getSalary());
        return employees.get(oldEmployeeIndex);
    }


    @DeleteMapping("/{employeeId}")
    public List<Employee> delete(@PathVariable Integer employeeId) {
        List<Employee> employees = getAllEmployees();
        Employee oldEmployee = employees.stream().filter(staff -> staff.getId() == employeeId).findFirst().get();
        employees.remove(oldEmployee);
        return employees;
    }


}
