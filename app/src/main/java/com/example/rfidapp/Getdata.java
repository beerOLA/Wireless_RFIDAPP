package com.example.rfidapp;

public class Getdata {

    public Getdata() {
    }

    public Getdata(String phone, String ID, String name, String password, String pic, String role) {
        this.phone = phone;
        this.ID = ID;
        Name = name;
        this.password = password;
        this.pic = pic;
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String phone;
    private String ID;
    private String Name;
    private String password;
    private String pic;
    private String role;
}
