/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author mmmujahi
 */
public class Silver extends UserStatus{
    @Override
    public String getStatusName(){
        return "Silver";
    }
    
    /* @Override
    public void buyAndRedeem(Customer customer, double totalPrice){
        //calculating the points earned dpeending on price of book, then updating the users status
        customer.updatePoints(totalPrice);
        
        //calculates and applies discounts to prices, updates customer status
        double discount = Math.min(customer.getPoints() / 100, totalPrice);
        customer.updatePoints(-(discount * 100));
        double finalPrice = totalPrice - discount;
        
        System.out.println("Final Price: " + finalPrice);
        customer.updateStatus();
    }*/
    @Override
    public double buyAndRedeem(Customer customer, double totalPrice) {
        // Calculate discount based on current points (1 CAD for every 100 points)
        double discount = Math.min(customer.getPoints() / 100.0, totalPrice);
        // Deduct the points corresponding to the discount
        customer.subtractPoints(discount * 100);
        // Final price after discount
        double finalPrice = totalPrice - discount;
        // Award points for the final money spent (10 points per CAD)
        customer.updatePoints(finalPrice);
        customer.updateStatus();
        return finalPrice;
    }
}
