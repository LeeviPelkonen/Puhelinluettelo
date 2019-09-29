package com.example.puhelinluettelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Contact implements Parcelable{
    private String firstName;
    private String lastName;
    private List<String> numbers;
    private Boolean firstLetter = false;

    public Contact(String firstName, String lastName, List<String> numbers){
        this.firstName = firstName;
        this.lastName = lastName;
        this.numbers = numbers;
    }

    protected Contact(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        numbers = in.createStringArrayList();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public Boolean getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(Boolean firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName){
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

    public void setNumbers(List<String> numberList){
        numbers = numberList;
    }

    public void addNumber(String number){
        this.numbers.add(number);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeStringList(numbers);
    }
}
