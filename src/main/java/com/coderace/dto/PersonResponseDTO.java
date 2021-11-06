package com.coderace.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PersonResponseDTO {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public PersonResponseDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public PersonResponseDTO setAge(Integer age) {
        this.age = age;
        return this;
    }
}
