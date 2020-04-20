package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
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
public class CompanyControllerTest {

    public Employee employee = new Employee();

    // @Autowired
    // private EmployeeController employeeController;

    @Mock
    CompanyService service;

    private List<Company> companies = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Employee> employees2 = new ArrayList<>();
    private List<Employee> newEmployees = new ArrayList<>();
    Company newCompany;
    Company updateCompany = new Company("ABC", new ArrayList<>(), 2);

    Employee newEmployee = new Employee(19, "Wendy", 30, "Female", 9000, 1);
    Employee updateEmployee = new Employee(3, "Wendy", 30, "Female", 9000, 2);

    @Before
    public void setUp() {
        CompanyController companyController = new CompanyController(service);
        RestAssuredMockMvc.standaloneSetup(companyController);
        employees.add(new Employee(1, "Paul", 18, "Male", 4000, 1));
        employees.add(new Employee(2, "Amy", 20, "Female", 8000, 1));
        employees.add(new Employee(3, "May", 23, "Female", 9000, 1));
        employees.add(new Employee(4, "King", 18, "Male", 7000, 1));
        employees.add(new Employee(5, "Rory", 18, "Male", 7000, 1));
        employees.add(new Employee(6, "Kelvin", 18, "Male", 7000, 1));
        employees.add(new Employee(7, "Keith", 18, "Male", 7000, 1));
        employees2.add(new Employee(1, "Holly", 45, "Female", 8000, 1));
        employees2.add(new Employee(2, "Kel", 67, "Male", 8000, 1));
        companies.add(new Company("OOCL",employees,7));
        companies.add(new Company("ABC", employees2, 2));
        newEmployees.add(new Employee(19, "Wendy", 30, "Female", 9000, 1));
        newEmployees.add(new Employee(39, "fgj", 36, "Male", 9000, 1));
        newCompany = new Company("DDD", newEmployees, 2);


    }


    @Test
    public void shouldGetCompanyWithSpecificID() {
        doReturn(companies.get(0)).when(service).getCompanyWithSpecificId(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies?companyId=1");

        Assert.assertEquals(200, response.getStatusCode());

        Company companyWithSpecificId = response.getBody().as(Company.class);
        Assert.assertEquals(7,  companyWithSpecificId.getEmployeesNumber());
        Assert.assertEquals("Paul", companyWithSpecificId.getEmployeeList().get(0).getName());
    }

    @Test
    public void shouldCreateNewCompany() {
        companies.add(newCompany);
        doReturn(companies).when(service).createNewCompany(any());

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when()
                .post("/companies");

        Assert.assertEquals(201, response.getStatusCode());
        List<Company> companyList = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Company newCompani = companyList.stream().filter(com -> com.getCompanyName().equals("DDD")).findFirst().orElse(null);

        Assert.assertNotNull(newCompani);

    }

    @Test
    public void shouldDeleteCompany() {
        companies.remove(0);
        doReturn(companies).when(service).createNewCompany(any());

        Boolean containCompany = true;
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());
        List<Company> companyList = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        if(companyList.stream().noneMatch(com -> com.getId()==1)){
            containCompany = false;
        }

        Assert.assertEquals(false, containCompany);
    }

    @Test
    public void shouldUpdateCompanyData(){
        doReturn(updateCompany).when(service).updateCompany(any(), any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(updateCompany)
                .when()
                .put("/companies/2");

        Assert.assertEquals(200, response.getStatusCode());

        Company updatedCompany = response.getBody().as(Company.class);

        Assert.assertNotNull(updatedCompany);
        Assert.assertEquals(updateCompany.getCompanyName() , updatedCompany.getCompanyName());

    }



    @Test
    public void shouldGetWholeEmployeeListInCompany(){
        doReturn(companies.get(0).getEmployeeList()).when(service).getEmployeesInCompany(any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1/employee");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employeeListList = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(7, employeeListList.size());

    }

    @Test
    public void shouldReturnEmployeeListWithSpecificSize(){
        doReturn(companies.subList(0,2)).when(service).returnCompanyListWithSpecificSize(any(),any());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies?page=1&pageSize=5");

        Assert.assertEquals(200, response.getStatusCode());

        List<Company> companyList = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        System.out.println(companyList);
        Assert.assertEquals(2, companyList.size());

    }

}
