/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author mmmujahi
 */
public class Customer extends User {
    private int points;
    private UserStatus status;
    
    public Customer(String username, String password){
        this.username = username;
        this.password = password;
        this.points = 0;
        this.status = new Silver();   
    }
    
    public Customer(String username, String password, int points){
        this.username = username;
        this.password = password;
        this.points = points;
        updateStatus();
        
    }
    /* public void updatePoints(double totalPrice){
        this.points += totalPrice * 10;
        updateStatus();
    }*/
    public void updatePoints(double value) {
        if (value >= 0) {
            this.points += (int) Math.round(value * 10);
        } else {
            this.points += (int) value; // Negative values are used as-is
        }
        updateStatus();
    }
    //this
    public void subtractPoints(double pointsToSubtract) {
        this.points -= (int) pointsToSubtract;
        updateStatus();
    }
    
    public int getPoints(){
        return points;
    }
    
    public void updateStatus(){
        if(points >= 1000){
            status = new Gold();
        } else {
            status = new Silver();
        }
    }
    

    @Override
    public boolean authenticate(String username, String password){
       return this.username.equals(username) && this.password.equals(password);
    }
    
    @Override
    public String toString(){
        return "Welcome " +username+ ". You have " + points + " points. Your status is " + status.getStatusName() + ".";
    }
    
    public UserStatus getStatus(){
        return status;
    }
}