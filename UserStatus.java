/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author mmmujahi
 */
public abstract class UserStatus {
    public abstract String getStatusName();
    public abstract double buyAndRedeem(Customer customer, double totalPrice);
}