/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author mmmujahi
 */
public class Gold extends UserStatus{
    @Override
    public String getStatusName(){
        return "Gold";
    }
    
    @Override
    public double buyAndRedeem(Customer customer, double totalPrice){
        double discount = Math.min(customer.getPoints() / 100.0, totalPrice);
        customer.subtractPoints(discount * 100);
        double finalPrice = totalPrice - discount;
        customer.updatePoints(finalPrice);
        customer.updateStatus();
        return finalPrice;
    }
}
