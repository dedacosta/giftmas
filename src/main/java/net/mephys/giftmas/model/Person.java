package net.mephys.giftmas.model;

import java.util.Objects;

public class Person {
    private String name;
    private String email;
    private boolean sent;

    public Person(String name, String email, boolean sent) {
        this.name = name;
        this.email = email;
        this.sent=sent;
    }

    public Person() {
    }

    public Person(String name, String email) {
       this(name, email, false);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean isSent() {
        return sent;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", sent='" + sent + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(name, person.name) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}