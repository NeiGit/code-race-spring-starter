package com.coderace.entity;


import com.coderace.entity.enums.Country;

import javax.persistence.*;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private Country country;

    public Person() {
    }

    public Person(String name, int age, Country country) {
        this.name = name;
        this.age = age;
        this.country = country;
    }

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Name: " + name + " - Age: " + age;
    }

    public static class Builder {
        private String name;
        private int age;
        private Country country;

        public Person build() {
            return new Person(name, age, country);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setCountry(Country country) {
            this.country = country;
            return this;
        }
    }
}
