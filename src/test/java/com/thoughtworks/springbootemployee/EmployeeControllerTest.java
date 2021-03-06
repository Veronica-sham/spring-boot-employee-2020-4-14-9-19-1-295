package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    public Employee employee = new Employee();

    // @Autowired
    // private EmployeeController employeeController;

    @Mock
    EmployeeService service;

    private List<Employee> employees = new ArrayList<>();
    Employee newEmployee = new Employee(19, "Wendy", 30, "Female", 9000, 1);
    Employee updateEmployee = new Employee(3, "Wendy", 30, "Female", 9000, 2);

    @Before
    public void setUp() {
        EmployeeController employeeController = new EmployeeController(service);
        RestAssuredMockMvc.standaloneSetup(employeeController);
        employees.add(new Employee(1, "Paul", 18, "Male", 4000, 1));
        employees.add(new Employee(2, "Amy", 20, "Female", 8000, 1));
        employees.add(new Employee(3, "May", 23, "Female", 9000, 1));
        employees.add(new Employee(4, "King", 18, "Male", 7000, 1));
        employees.add(new Employee(5, "Rory", 18, "Male", 7000, 1));
        employees.add(new Employee(6, "Kelvin", 18, "Male", 7000, 1));
        employees.add(new Employee(7, "Keith", 18, "Male", 7000, 1));

    }


    @Test
    public void shouldFindEmployeeById() {
        doReturn(employees.get(0)).when(service).findEmployeeById(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());

        Employee employeeWithSpecificId = response.getBody().as(Employee.class);
        Assert.assertNotNull(employeeWithSpecificId);
        Assert.assertEquals("Paul", employeeWithSpecificId.getName());
    }

    @Test
    public void shouldCreateNewEmployee() {
        employees.add(newEmployee);
        doReturn(employees.get(employees.indexOf(newEmployee))).when(service).createNewEmployee(any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newEmployee)
                .when()
                .post("/employees");

        Assert.assertEquals(201, response.getStatusCode());
        Employee addedEmployee = response.getBody().as(Employee.class);

        Assert.assertNotNull(addedEmployee);
        Assert.assertEquals(newEmployee.getId(), addedEmployee.getId());
        Assert.assertEquals(newEmployee.getName(), addedEmployee.getName());
    }

    @Test
    public void shouldDeleteSpecificEmployee() {
        employees.remove(6);
        doReturn(employees).when(service).deleteEmployee(any());
        int employeeID = 7;
        Boolean containThisEmployee = true;
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/7");

        Assert.assertEquals(200, response.getStatusCode());
        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(6, employee.size());

        if (employee.stream().noneMatch(staff -> staff.getId() == employeeID)) {
            containThisEmployee = false;
        }

        Assert.assertEquals(false, containThisEmployee);
    }

    @Test
    public void shouldUpdateEmployeeData() {
        doReturn(updateEmployee).when(service).updateEmployee(any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(updateEmployee)
                .when()
                .put("/employees/3");

        Assert.assertEquals(200, response.getStatusCode());

        Employee updatedEmployee = response.getBody().as(Employee.class);

        Assert.assertNotNull(updatedEmployee);
        Assert.assertEquals(updateEmployee.getName(), updatedEmployee.getName());

    }

    @Test
    public void shouldFindEmployeeByGender() {
        List<Employee> femaleEmployees = employees.stream().filter(employee -> !employee.getGender().equals("Female")).collect(Collectors.toList());
        doReturn(femaleEmployees).when(service).findEmployeeByGender(any());
        Boolean containsFemales = true;
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender", "Female")
                .when()
                .get("/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(5, employee.size());

        if (employee.stream().noneMatch(staff -> staff.getGender().equals("Female"))) {
            containsFemales = false;
        }
        Assert.assertEquals(false, containsFemales);
    }


    @Test
    public void shouldGetWholeEmployeeList() {
        doReturn(employees).when(service).getAllEmployees();
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertNotNull(employees);
        Assert.assertEquals(7, employee.size());

    }

    @Test
    public void shouldReturnEmployeeListWithSpecificSize() {
        doReturn(employees.subList(0, 5)).when(service).returnSpecificNumberOfEmployees(any(), any()); //return the same employees list
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees?page=1&pageSize=5");

        Assert.assertEquals(200, response.getStatusCode()); //no meaning to test below

        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertNotNull(employee);
        Assert.assertEquals(5, employee.size());

    }

}
