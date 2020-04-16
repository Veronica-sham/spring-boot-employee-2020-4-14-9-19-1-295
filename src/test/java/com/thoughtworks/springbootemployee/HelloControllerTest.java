package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.HelloController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest {
    @Autowired
    private HelloController helloController;

    @Before
    public void setUp() throws Exception{
        RestAssuredMockMvc.standaloneSetup(helloController);
    }

    @Test
    public void shouldReturnHelloWorld() {
        MockMvcResponse response = given()
                .when()
                .get("/hello");

        Assert.assertEquals(200, response.getStatusCode());

        String helloWorld = response.getBody().asString();
        Assert.assertEquals("hello! ", helloWorld);
    }


}


