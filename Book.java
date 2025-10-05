/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import javafx.beans.property.BooleanProperty; // Import BooleanProperty from JavaFX for property binding
import javafx.beans.property.SimpleBooleanProperty; // Import SimpleBooleanProperty for instantiating a BooleanProperty


public class Book {
    // initialize variables
    private String title; // title of the book
    private double price; // price of the book
    private BooleanProperty isSelected; // selected status (true/false) as a property, part of fx
    
     public Book() {                                      
        // initializes the isSelected property to false by default using SimpleBooleanProperty
        this.title = "";
        this.price = 0.0;
        this.isSelected = new SimpleBooleanProperty(false);  
    }
    public Book(String title, double price) {                                      
        // initializes the isSelected property to false by default using SimpleBooleanProperty
        this.title = title;
        this.price = price;
        this.isSelected = new SimpleBooleanProperty(false);  
    }
    
    // getter and setter for the title of the book
    public String getTitle() {                           
        return title; // current title
    }
    
    public void setTitle(String title) {                
        this.title = title;
    }
    
    // getter and setter for the price of the book
    public void setPrice(String price) {                
        // converts the price input (string) to a double and assigns it as the price 
        this.price = Double.parseDouble(price);        
    }
    
    public double getPrice() {                          
        return price; // returns the current price as a double
    }
    
    // getter and setter for isSelected
    public void setIsSelected(boolean value) {          
        this.isSelected.set(value); // sets isSelected to the given boolean
    }
    
    public boolean getSelected() {                      
        return this.isSelected.get(); // returns current boolean value of isSelected
    }
    
    // method to access the BooleanProperty itself for GUI binding
    public BooleanProperty selectedProperty() {       
        return this.isSelected; // returns the isSelected property so it can be bound to GUI components
    }
    
    @Override
    public String toString() {                          
        // returns a string with the title and price of the book
        return "Book Title:" + title + ", Price $" + price + "]";  
    }
}