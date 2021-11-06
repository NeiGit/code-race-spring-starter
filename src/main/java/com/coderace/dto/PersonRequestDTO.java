package com.coderace.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PersonRequestDTO {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public PersonRequestDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public PersonRequestDTO setAge(Integer age) {
        this.age = age;
        return this;
    }
}
