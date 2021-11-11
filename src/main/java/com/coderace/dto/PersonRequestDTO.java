package com.coderace.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PersonRequestDTO {
    private String name;
    private Integer age;
    private String country;

    public PersonRequestDTO() {

    }

    public PersonRequestDTO(String name, Integer age, String country) {
        this.name = name;
        this.age = age;
        this.country = country;
    }

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

    public String getCountry() {
        return country;
    }

    public PersonRequestDTO setCountry(String country) {
        this.country = country;
        return this;
    }
}
