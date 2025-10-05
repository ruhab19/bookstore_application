/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package coe528.project;

import javafx.scene.control.Button;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author mmmujahi
 */
public class BookStoreApp extends Application {
    
    private BookStore bookStore;
    private Customer currentCustomer;
    Button loginButton = new Button("Login");
    Button booksButton = new Button("Books");
    Button customersButton = new Button("Customers");
    Button logoutButton = new Button ("Logout");
    Button backScreenButton = new Button ("Back");
    Button buyButton = new Button ("Buy");
    Button buyAndRedeemButton = new Button ("Redeem points and Buy");
    TextField userName = new TextField();
    PasswordField password = new PasswordField();
    Label invalidLoginLabel = new Label();
    Label noBooksSelectedLabel = new Label();
    
    TableView<Customer> customersTable = new TableView<>();
    TableView<Book> booksTable = new TableView<>();
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BookStore App"); // Setting window/stage name.
        bookStore = bookStore.getInstance();
        primaryStage.setScene(new Scene(loginScreen(), 600, 500)); // Creating and setting first scene, login screen.
        invalidLoginLabel.setVisible(false);
        noBooksSelectedLabel.setVisible(false);
        primaryStage.show();
        
        try { // Loading books and customers data from files.
            bookStore.setBooks(bookStore.readBooks());
            bookStore.setCustomers(bookStore.readCustomers());
        }
        catch (IOException e) {
            System.out.println("Error reading from file.");
        }
        
        loginButton.setOnAction((ActionEvent e) -> {
            if (bookStore.getOwner().authenticate(userName.getText(), password.getText())) {
                primaryStage.setScene(new Scene(ownerStartScreen(), 600, 500));
            }
            for (Customer c : bookStore.getCustomers()) {
                if (c.authenticate(userName.getText(), password.getText())) {
                    currentCustomer = c;
                    primaryStage.setScene(new Scene(customerStartScreen(), 600, 500));                  
                }
            }
            invalidLoginLabel.setText("Incorrect login information entered.");
            invalidLoginLabel.setVisible(true);
        });
        
        logoutButton.setOnAction((ActionEvent e) -> {
            primaryStage.setScene(new Scene(loginScreen(), 600, 500));
            userName.clear();
            password.clear();
            invalidLoginLabel.setVisible(false);
        });
        
        booksButton.setOnAction((ActionEvent e) -> {
            primaryStage.setScene(new Scene(ownerBooksScreen(), 600, 500));
        });
        
        customersButton.setOnAction((ActionEvent e) -> {
            primaryStage.setScene(new Scene(ownerCustomersScreen(), 600, 500));
        });
        
        backScreenButton.setOnAction((ActionEvent e) -> {
            primaryStage.setScene(new Scene(ownerStartScreen(), 600, 500));
        });
        
        buyButton.setOnAction((ActionEvent e) -> {
            boolean bookSelected = false;
            for (Book b : bookStore.getBooks()) {
                if(b.getSelected()) {
                    bookSelected = true;
                }
            }
            if (!bookSelected) {
                noBooksSelectedLabel.setText("Please select a book you would like to purchase.");
                noBooksSelectedLabel.setVisible(true);
            } else {
                noBooksSelectedLabel.setVisible(false);
                primaryStage.setScene(new Scene(customerCostScreen(false), 600, 500));
            }
        });
        
        buyAndRedeemButton.setOnAction ((ActionEvent e) -> {
            boolean bookSelected = false;
            for (Book b : bookStore.getBooks()) {
                if(b.getSelected()) {
                    bookSelected = true;
                }
            }
            if (!bookSelected) {
                noBooksSelectedLabel.setText("Please select a book you would like to purchase.");
                noBooksSelectedLabel.setVisible(true);
            } else {
                noBooksSelectedLabel.setVisible(false);
                primaryStage.setScene(new Scene(customerCostScreen(true), 600, 500));
            }
        });
        
        primaryStage.setOnCloseRequest((WindowEvent e) -> {
            try {
                bookStore.writeBooks();
                bookStore.writeCustomers();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });
    }
    
    public VBox loginScreen () {
        VBox main = new VBox(10);
        main.setAlignment(Pos.CENTER);
        
        Label welcomeMessage = new Label("Welcome to the Bookstore App!");
        Label userNameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.add(userNameLabel, 0, 0);
        grid.add(userName, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(password, 1, 1);
        main.getChildren().addAll(welcomeMessage, grid, loginButton, invalidLoginLabel);
        
        return main;
    }
    
    public VBox ownerStartScreen() {
        VBox main = new VBox(15);
        main.setAlignment(Pos.CENTER);
        
        main.getChildren().addAll(booksButton, customersButton, logoutButton);
        
        return main;
    }
    
    public VBox ownerBooksScreen() {
        VBox main = new VBox(10);
        main.setAlignment(Pos.CENTER);
        ObservableList<Book> books = FXCollections.observableArrayList(bookStore.getBooks());
        
        booksTable.getColumns().clear();
        booksTable.getItems().clear();
        TableColumn bookNameColumn = new TableColumn<Book, String>("Book Name");
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        TableColumn bookPriceColumn = new TableColumn<Book, Double>("Book Price");
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<Book, Double>("price"));
        booksTable.getColumns().addAll(bookNameColumn, bookPriceColumn);
        booksTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        booksTable.setItems(books);
        
        TextField bookName = new TextField();
        bookName.setPromptText("Book Name");
        TextField bookPrice = new TextField();
        bookPrice.setPromptText("Book Price");
        Button addButton = new Button("Add");
        addButton.setPrefWidth(100);
        Button deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(80);
        Label bookExists = new Label("The book already exists in the table.");
        bookExists.setVisible(false);
        
        HBox middleRow = new HBox(5);
        middleRow.setAlignment(Pos.CENTER);
        middleRow.setPadding(new Insets(0, 5, 0, 5));
        middleRow.getChildren().addAll(bookName, bookPrice, addButton);
        HBox bottomRow = new HBox(5);
        bottomRow.setAlignment(Pos.BOTTOM_RIGHT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomRow.setPadding(new Insets(0, 5, 5, 5));
        bottomRow.getChildren().addAll(backScreenButton, spacer, deleteButton);
        
        addButton.setOnAction((ActionEvent e) -> {
            Book book = new Book (bookName.getText(), Double.parseDouble(bookPrice.getText()));
            boolean exists = false;
            
            for (Book b : bookStore.getBooks()) {
                if (book.getTitle().equals(b.getTitle())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                bookExists.setVisible(true);
            } else {
                bookExists.setVisible(false);
                booksTable.getItems().add(book);
                bookStore.getOwner().addBook(book);
                bookName.clear();
                bookPrice.clear();
            }
        });
        
        deleteButton.setOnAction((ActionEvent e) -> {
            int selectedRow = booksTable.getSelectionModel().getSelectedIndex();
            booksTable.getItems().remove(selectedRow);
            bookStore.getOwner().deleteBook(selectedRow);
        });
        
        main.getChildren().addAll(booksTable, middleRow, bookExists, bottomRow);
        return main;
    }
    
    public VBox ownerCustomersScreen() {
        VBox main = new VBox(10);
        main.setAlignment(Pos.CENTER);
        ObservableList<Customer> customers = FXCollections.observableArrayList(bookStore.getCustomers());
        
        customersTable.getColumns().clear();
        customersTable.getItems().clear();
        TableColumn userNameColumn = new TableColumn<Customer, String>("Username");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("username"));
        TableColumn passwordColumn = new TableColumn<Customer, String>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("password"));
        TableColumn pointsColumn = new TableColumn<Customer, Integer>("Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("points"));
        customersTable.getColumns().addAll(userNameColumn, passwordColumn, pointsColumn);
        customersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        customersTable.setItems(customers);
        
        TextField userName = new TextField();
        userName.setPromptText("Username");
        TextField password = new TextField();
        password.setPromptText("Password");
        Button addButton = new Button("Add");
        addButton.setPrefWidth(100);
        Button deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(80);
        Label customerExists = new Label("The customer is already registered.");
        customerExists.setVisible(false);
        
        HBox middleRow = new HBox(5);
        middleRow.setAlignment(Pos.CENTER);
        middleRow.setPadding(new Insets(0, 5, 0, 5));
        middleRow.getChildren().addAll(userName, password, addButton);
        HBox bottomRow = new HBox(5);
        bottomRow.setAlignment(Pos.BOTTOM_RIGHT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomRow.setPadding(new Insets(0, 5, 5, 5));
        bottomRow.getChildren().addAll(backScreenButton, spacer, deleteButton);
        
        addButton.setOnAction((ActionEvent e) -> {
            Customer customer = new Customer(userName.getText(), password.getText(), 0);
            boolean exists = false;
            
            for (Customer c : bookStore.getCustomers()) {
                if (customer.getUsername().equals(c.getUsername()) && customer.getPassword().equals(c.getPassword())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                customerExists.setVisible(true);
            } else {
                customerExists.setVisible(false);
                customersTable.getItems().add(customer);
                bookStore.getOwner().addCustomer(customer);
                userName.clear();
                password.clear();
            }
        });
        
        deleteButton.setOnAction((ActionEvent e) -> {
            int rowSelected = customersTable.getSelectionModel().getSelectedIndex();
            customersTable.getItems().remove(rowSelected);
            bookStore.getOwner().deleteCustomer(rowSelected);
        });
        
        main.getChildren().addAll(customersTable, middleRow, customerExists, bottomRow);
        return main;
    }
    
    public VBox customerStartScreen() {
        VBox main = new VBox(10);
        main.setPadding(new Insets(5, 0, 0, 0));
        main.setAlignment(Pos.CENTER);
        Label welcomeMessage = new Label("Welcome " +currentCustomer.getUsername()+ ". You have " +currentCustomer.getPoints()+ " points. Your status is " +currentCustomer.getStatus().getStatusName()+ ".");
        
        booksTable.getColumns().clear();
        booksTable.getItems().clear();
        booksTable.setEditable(true);
        TableColumn bookNameColumn = new TableColumn<Book, String>("Book Name");
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        TableColumn bookPriceColumn = new TableColumn<Book, Double>("Book Price");
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<Book, Double>("price"));
        TableColumn<Book, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setEditable(true);
        booksTable.getColumns().addAll(bookNameColumn, bookPriceColumn, selectColumn);
        booksTable.setItems(FXCollections.observableArrayList(bookStore.getBooks()));
        booksTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        HBox bottomRow = new HBox(5);
        bottomRow.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomRow.setPadding(new Insets(0, 5, 5, 5));
        bottomRow.getChildren().addAll(buyButton, buyAndRedeemButton, spacer, logoutButton);
        
        main.getChildren().addAll(welcomeMessage, booksTable, noBooksSelectedLabel, bottomRow);
        return main;
    }
    
    /** public VBox customerCostScreen(boolean pointsRedeemed) {
        VBox main = new VBox(10);
        main.setAlignment(Pos.CENTER); //this
        main.setPadding(new Insets(20)); //this
        
        Label totalCostLabel = new Label("Total Cost: ");
        Label customerInfo = new Label("Points: " +currentCustomer.getPoints()+ ", Status: " +currentCustomer.getStatus());
        
        if(pointsRedeemed) {
            currentCustomer.getStatus().buyAndRedeem(currentCustomer, 0);
        }
        main.getChildren().addAll(totalCostLabel, customerInfo, logoutButton);
        return main;
    } */
    public VBox customerCostScreen(boolean pointsRedeemed) {
        VBox main = new VBox(10);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(20));  // Better formatting

    // Calculate total price of selected books
    double totalPrice = 0;
    for (Book b : bookStore.getBooks()) {
        if (b.getSelected()) {
            totalPrice += b.getPrice();
        }
    }
    
    Label totalCostLabel = new Label("Total Cost: $" + String.format("%.2f", totalPrice));
    Label customerInfo = new Label("Points: " + currentCustomer.getPoints() 
                                   + ", Status: " + currentCustomer.getStatus().getStatusName());
    
    if (pointsRedeemed) {
        double finalPrice = currentCustomer.getStatus().buyAndRedeem(currentCustomer, totalPrice);
        Label finalPriceLabel = new Label("Final Price: $" + String.format("%.2f", finalPrice));
        main.getChildren().addAll(totalCostLabel, finalPriceLabel, customerInfo, logoutButton);
    } else {
        currentCustomer.updatePoints(totalPrice);
        currentCustomer.updateStatus();
        main.getChildren().addAll(totalCostLabel, customerInfo, logoutButton);
    }
    
    return main;
}


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args); 
    }
}