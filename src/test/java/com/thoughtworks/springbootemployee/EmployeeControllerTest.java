package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {


    @Autowired
    private EmployeeController employeeController;

    @MockBean
    EmployeeService employeeService;
    EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    Employee employee;

    @Test
    public void shouldFindEmployeeById() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employee.get(0).getId());
        Assert.assertEquals("Paul", employee.get(0).getName());
    }

    @Test
    public void shouldCreateNewEmployee() {

        Employee newEmployee = new Employee(19, "Wendy", 30, "Female", 9000);
        //Mockito.when(employeeRepository.createNewEmployee(newEmployee)).thenReturn(employeeRepository.getAllEmployee());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post("/employees");

        Assert.assertEquals(201, response.getStatusCode());
        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Employee addedEmployee = employee.stream().filter(staff -> staff.getId() == 19).findFirst().get();

        Assert.assertEquals(newEmployee.getId(), addedEmployee.getId());
        Assert.assertEquals(newEmployee.getName(), addedEmployee.getName());
    }

    @Test
    public void shouldDeleteSpecificEmployee() {
        Integer employeeID = 15;
        Boolean containThisEmployee = true;
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/15");

        Assert.assertEquals(200, response.getStatusCode());
        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        if(employee.stream().noneMatch(staff -> staff.getId() == employeeID)){
            containThisEmployee = false;
        }

        Assert.assertEquals(false, containThisEmployee);
    }

    @Test
    public void shouldUpdateEmployeeData(){
        Employee newEmployee = new Employee(3, "Wendy", 30, "Female", 9000);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .put("/employees/3");

        Assert.assertEquals(200, response.getStatusCode());
        Employee updatedEmployee = response.getBody().as(Employee.class);
        Assert.assertNotNull(updatedEmployee);
        Assert.assertEquals(newEmployee.getName() , updatedEmployee.getName());

    }

    @Test
    public void shouldFindEmployeeByGender() {
        EmployeeController employeeController = new EmployeeController();
        Boolean containsFemales = true;
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender","Female")
                .when()
                .get("/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        if(employee.stream().noneMatch(staff -> staff.getGender().equals("Female"))){
           containsFemales = false;
        }

        Assert.assertEquals(false , containsFemales);

    }



}
