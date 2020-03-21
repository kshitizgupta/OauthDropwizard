package com.howtodoinjava.model;

public class Employee {
    private int id;
    private String name;

    public Employee(final int i, final String kshitiz) {
        id = i;
        name = kshitiz;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
