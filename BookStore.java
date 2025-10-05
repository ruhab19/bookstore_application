/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.util.ArrayList;
import java.io.*; // Imports all classes for file handling (FileReader, FileWriter, IOException)

public class BookStore {
    private ArrayList<Book> books; // hold list of Book objects in the bookstore
    private ArrayList<Customer> customers; // holds list of Customer objects in the bookstore
    private Owner owner = Owner.getInstance();
    private static BookStore instance;

    public BookStore() {                                
        // initialize lists
        this.books = new ArrayList<>();                  
        this.customers = new ArrayList<>();
        this.owner = Owner.getInstance(); 
    }
    
    

public static BookStore getInstance() {
    if (instance == null) {
        instance = new BookStore();
    }
    return instance;
}

    
    public void writeBooks() throws IOException {
        // to automatically manage resource closing
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("books.txt"))) {
            // loops over each Book object in the books list
            for (Book book : books) {
                // write the book's title and price separated by a comma, then add a newline
                bw.write(book.getTitle() + "," + book.getPrice());
                bw.newLine();              
            }
        }
            catch (IOException e) {
                    e.printStackTrace();
        }
    }

    // current list of customers to "customers.txt"
    public void writeCustomers() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("customers.txt"))) {
            // loops over each Customer in the customers list
            for (Customer customer : customers) {
                // writes customer's username, password, and points separated by commas
                bw.write(customer.getUsername() + "," + customer.getPassword() + "," + customer.getPoints());
                bw.newLine();          
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // read books from "books.txt" into a list
    public ArrayList<Book> readBooks() throws IOException {
        ArrayList<Book> booksFromFile = new ArrayList<>(); // // ArrayList to hold books read from file
        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            String line; // stores each line from the file
            // reads each line until end-of-file is reached
            while ((line = br.readLine()) != null) {
                // splits the line into parts using comma
                String[] parts = line.split(",");
                // makes sure at least two parts exist: title and price
                if (parts.length >= 2) {
                    Book book = new Book(); // creates a new Book object
                    book.setTitle(parts[0]); // set the book's title using the first part
                    book.setPrice(parts[1]); // set the book's price using the second part (setter converts string to double)
                    booksFromFile.add(book); // adds the book to the list of books read from file
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return booksFromFile; // returns the list of books read from the file
    }

    // read customers from "customers.txt" into a list
    public ArrayList<Customer> readCustomers() throws IOException{
        ArrayList<Customer> customersFromFile = new ArrayList<>(); // stores customers read from file
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;       
            while ((line = br.readLine()) != null) { // reads each line until end-of-file is reached
                String[] parts = line.split(","); // splits line
                // check that there are at least three parts: username, password, and points
                if (parts.length >= 3) {
                    // create a new Customer using the split parts (convert points from String to int)
                    Customer customer = new Customer(parts[0], parts[1], Integer.parseInt(parts[2]));
                    customersFromFile.add(customer); // add the customer to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customersFromFile;
    }

    public ArrayList<Book> getBooks() {
        return books; // returns the list containing Book objects.
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    
    public Owner getOwner() {
        return owner;
    }
    
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
    
    public void setCustomers(ArrayList<Customer> customers) {
    this.customers = customers;
}

}