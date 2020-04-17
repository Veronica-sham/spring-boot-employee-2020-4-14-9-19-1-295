package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createNewEmployee(@RequestBody Employee employee) {
        return service.createNewEmployee(employee);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeesWithSpecificID(@PathVariable int id) {
        return service.findEmployeebyId(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> returnSpecificNumberOfEmployees(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return service.returnSpecificNumberOfEmployees(page, pageSize);
    }

    @GetMapping(params = "gender")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeesWithSpecificGender(@RequestParam(required = false) String gender) {
        return service.findEmployeeByGender(gender);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Integer employeeId, @RequestBody Employee employee) {
        service.updateEmployee(employeeId, employee);
    }


    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer employeeId) {
        service.deleteEmployee(employeeId);
    }


}
