/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.util.ArrayList;

/**
 *
 * @author mmmujahi
 */
public class Owner extends User{
    private static Owner instance;
    private Owner(){
        this.username = "admin";
        this.password = "admin";
    }
    
    public static Owner getInstance(){
        if (instance == null){
            instance = new Owner();
        }
        return instance;
    }
    
    public void addBook(Book b){
        BookStore.getInstance().getBooks().add(b);
    }
    
    public void deleteBook(int index){
        BookStore.getInstance().getBooks().remove(index);
    }
    
    public void addCustomer(Customer c){
        BookStore.getInstance().getCustomers().add(c);
    }
    
    public void deleteCustomer(int index){
        BookStore.getInstance().getCustomers().remove(index);
    }
    
    @Override
    public boolean authenticate(String username, String password){
       return this.username.equals(username) && this.password.equals(password);
    }
    
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }
    
}