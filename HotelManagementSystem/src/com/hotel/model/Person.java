package com.hotel.model;

/** Abstraction: common data for any person in the system. */
public abstract class Person {
    private final int id;
    private String name;
    private String phone;

    protected Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }

    public abstract String getRole();

    @Override
    public String toString() {
        return String.format("[%s #%d] %s | %s", getRole(), id, name, phone);
    }
}
