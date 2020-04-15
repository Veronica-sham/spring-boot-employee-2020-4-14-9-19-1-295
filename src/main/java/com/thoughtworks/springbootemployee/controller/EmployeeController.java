package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sun.font.TrueTypeFont;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Paul", 18, "Male", 4000));
        employees.add(new Employee(2, "Amy", 20, "Female",8000));
        employees.add(new Employee(3,"May",23,"Female",9000));
        employees.add(new Employee(4,"King",18,"Male",7000));
        employees.add(new Employee(5,"Rory",18,"Male",7000));
        employees.add(new Employee(6,"Kelvin",18,"Male",7000));
        employees.add(new Employee(7,"Keith",18,"Male",7000));
        employees.add(new Employee(8,"Chris",18,"Male",7000));
        employees.add(new Employee(9,"Warren",18,"Male",7000));
        employees.add(new Employee(10,"King",18,"Male",7000));
        employees.add(new Employee(11,"Rory",18,"Male",7000));
        employees.add(new Employee(12,"Kelvin",18,"Male",7000));
        employees.add(new Employee(13,"Keith",18,"Male",7000));
        employees.add(new Employee(14,"Chris",18,"Male",7000));
        employees.add(new Employee(15,"Warren",18,"Male",7000));


        return employees;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createNewEmployee(@RequestBody Employee employee){
        employee.add(employee);
        return employee;
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesWithSpecificID(@PathVariable int id) {

        List<Employee> employees = getAllEmployees();
        List<Employee> employeeWithSpecificID = employees.stream().filter(employee -> employee.getId() == id).collect(Collectors.toList());
        return employeeWithSpecificID;
    }

    @GetMapping(path = "?page={page}&pageSize={pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> paging(@PathVariable Integer page, Integer pageSize) {
        List<Employee> employees = getAllEmployees();
        if (page == null || pageSize ==null){
            return employees;
        }
        Integer leftBound = (page -1)*pageSize;
        Integer rightBound = (page-1)*pageSize +pageSize;
        leftBound = leftBound>employees.size() - 1?0:leftBound;
        rightBound = rightBound>employees.size() - 1?employees.size():rightBound;

        return employees.subList(leftBound, rightBound);
    }

    @GetMapping(params = "gender")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesWithSpecificGender(@RequestParam(defaultValue = "Female") String gender) {
        List<Employee> employees = getAllEmployees();
        List<Employee> employeeWithSpecificGender = employees.stream().filter(employee -> employee.getGender()==gender).collect(Collectors.toList());
        return employeeWithSpecificGender;
    }






}
