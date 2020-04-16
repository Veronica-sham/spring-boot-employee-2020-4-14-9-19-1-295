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

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    public Employee employee = new Employee();

   // @Autowired
  //  private EmployeeController employeeController;

    @Mock
    EmployeeService service;

    private List<Employee> employees = new ArrayList<>();
    Employee newEmployee = new Employee(19, "Wendy", 30, "Female", 9000);
    Employee updateEmployee = new Employee(3, "Wendy", 30, "Female", 9000);

    @Before
    public void setUp() {
        EmployeeController employeeController = new EmployeeController(service);
        RestAssuredMockMvc.standaloneSetup(employeeController);
        employees.add(new Employee(1, "Paul", 18, "Male", 4000));
        employees.add(new Employee(2, "Amy", 20, "Female", 8000));
        employees.add(new Employee(3, "May", 23, "Female", 9000));
        employees.add(new Employee(4, "King", 18, "Male", 7000));
        employees.add(new Employee(5, "Rory", 18, "Male", 7000));
        employees.add(new Employee(6, "Kelvin", 18, "Male", 7000));
        employees.add(new Employee(7, "Keith", 18, "Male", 7000));

    }


    @Test
    public void shouldFindEmployeeById() {
        doReturn(employees).when(service).findEmployeeByID(any());
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
        employees.add(newEmployee);
        doReturn(employees).when(service).createNewEmployee(any());

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
        Integer employeeID = 7;
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

        if(employee.stream().noneMatch(staff -> staff.getId() == employeeID)){
            containThisEmployee = false;
        }

        Assert.assertEquals(false, containThisEmployee);
    }

    @Test
    public void shouldUpdateEmployeeData(){
        doReturn(updateEmployee).when(service).updateEmployee(any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(updateEmployee)
                .when()
                .put("/employees/3");

        Assert.assertEquals(200, response.getStatusCode());

        Employee updatedEmployee = response.getBody().as(Employee.class);

        Assert.assertNotNull(updatedEmployee);
        Assert.assertEquals(updateEmployee.getName() , updatedEmployee.getName());

    }

    @Test
    public void shouldFindEmployeeByGender() {
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


    @Test
    public void shouldGetWholeEmployeeList(){
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
        Assert.assertEquals(7, employee.size());

    }

    @Test
    public void shouldReturnEmployeeListWithSpecificSize(){
        doReturn(employees).when(service).returnSpecificNumberOfEmployees(any(),any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees?page=1&pageSize=5");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employee = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        System.out.println(employee);
        Assert.assertEquals(7, employee.size());

    }

}
