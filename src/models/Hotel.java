/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author ASUS
 */
public class Hotel {
    private String id;
    private String name;
    private int available_room;
    private String address;
    private String phone;
    private String rating;

    public Hotel() {
    }

    public Hotel(String id, String name, int available_room, String address, String phone, String rating) {
        this.id = id;
        this.name = name;
        this.available_room = available_room;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailable_room() {
        return available_room;
    }

    public void setAvailable_room(int available_room) {
        this.available_room = available_room;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return  id + "," + name + ", " + available_room + ", " + address + "," + phone + ", " + rating ;
    }
    
    
}
