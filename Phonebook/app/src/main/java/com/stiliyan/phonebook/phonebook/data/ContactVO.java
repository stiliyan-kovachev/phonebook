package com.stiliyan.phonebook.phonebook.data;

public class ContactVO {
    public int id;
    public  String name;
    public CountryVO country = new CountryVO();
    public String phone;
    public String email;
    public String gender;

    public ContactVO() {}
}
