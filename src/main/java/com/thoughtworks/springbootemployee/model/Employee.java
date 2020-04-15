package com.thoughtworks.springbootemployee.model;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class Employee {
    private int id;
    private String name;
    private int age;
    private String gender;
    private int salary;

    public Employee(int id, String name,int age, String gender, int salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }


    public int getId(){
        return this.id;
    }

    public void setId(int newID){
        this.id = newID;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getGender(){
        return this.gender;
    }

    public void setGender(String newGender){
        this.gender = gender;
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int newAge){
        this.age = newAge;
    }

    public int getSalary(){
        return this.salary;
    }

    public void setSalary(int salary){
        this.salary = salary;
    }

    public void update(int employeeID,String name,String gender, int age, int salary) {
        this.id = employeeID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }



}
