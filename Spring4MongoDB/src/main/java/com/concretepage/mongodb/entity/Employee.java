package com.concretepage.mongodb.entity;

import org.springframework.data.annotation.Id;
public class Employee { 
	@Id
	public Integer id;
	public String name;
    public Integer age;
	public Employee(Integer id, String name, Integer age){
		this.id = id;
		this.name=name;
		this.age=age;
	}
} 
