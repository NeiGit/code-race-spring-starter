package com.coderace.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PersonResponseDTO {
    private String name;
    private int age;
    private String country;
    private String bornDate;

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

    public String getCountry() {
        return country;
    }

    public PersonResponseDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getBornDate() {
        return bornDate;
    }

    public PersonResponseDTO setBornDate(String bornDate) {
        this.bornDate = bornDate;
        return this;
    }
}
