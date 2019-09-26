package com.example.puhelinluettelo;

import java.util.List;

public class Contact {
    private String firstName;
    private String lastName;
    private List<String> numbers;

    public Contact(String firstName, String lastName, List<String> numbers){
        this.firstName = firstName;
        this.lastName = lastName;
        this.numbers = numbers;
    }

    public String getFirstName() {
        return firstName;
    }

    public void changeFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void changeLastName(String lastName){
        this.lastName = lastName;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void changeNumber(String number, int id){
        this.numbers.set(id, number);
    }

    public void removeNumber(int id){
        this.numbers.remove(id);
    }

    public void addNumber(String number){
        this.numbers.add(number);
    }
}
