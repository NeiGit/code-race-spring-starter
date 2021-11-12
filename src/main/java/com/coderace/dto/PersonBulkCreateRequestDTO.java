package com.coderace.dto;

import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class PersonBulkCreateRequestDTO {
    private Integer age;
    private List<PersonBulkCreateDTO> persons;

    public Integer getAge() {
        return age;
    }

    public PersonBulkCreateRequestDTO setAge(Integer age) {
        this.age = age;
        return this;
    }

    public List<PersonBulkCreateDTO> getPersons() {
        return persons;
    }

    public PersonBulkCreateRequestDTO setPersons(List<PersonBulkCreateDTO> persons) {
        this.persons = persons;
        return this;
    }

    @EqualsAndHashCode
    public static class PersonBulkCreateDTO {
        private String name;
        private String country;

        public String getName() {
            return name;
        }

        public PersonBulkCreateDTO setName(String name) {
            this.name = name;
            return this;
        }

        public String getCountry() {
            return country;
        }

        public PersonBulkCreateDTO setCountry(String country) {
            this.country = country;
            return this;
        }
    }
}


