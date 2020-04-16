package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

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
        Employee newEmployee = new Employee(15, "Wendy", 30, "Female", 9000);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .put("/employees/15");

        Assert.assertEquals(200, response.getStatusCode());
        Employee updatedEmployee = response.getBody().as(Employee.class);
        Assert.assertEquals(newEmployee.getName() , updatedEmployee.getName());

    }

    @Test
    public void shouldFindEmployeeByGender() {
        Boolean containsFemales = true;
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender","Male")
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
