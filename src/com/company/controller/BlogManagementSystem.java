package com.company.controller;

import com.company.entity.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BlogManagementSystem {
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Blog> blogs = new HashMap<>();
    private Map<Integer, Category> categories = new HashMap<>();
    private int userIdCounter = 1;
    private int blogIdCounter = 1;
    private int categoryIdCounter = 1;

    // Method to handle user registration
    public void registerUser(String fullName, String email, String password, String phoneNo) {
        // Create user object and add it to the users map
        User user = new User(userIdCounter++, fullName, email, password, phoneNo, new Date());
        users.put(user.getId(), user);
        // Optionally, store user data in a text file
    }

    // Method to handle user login
    public boolean loginUser(String email, String password) {
        // Check if user exists and password matches
        for (User user : users.values()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Method to add a new category
    public void addCategory(String title, String description) {
        // Create category object and add it to the categories map
        Category category = new Category(categoryIdCounter++, title, description);
        categories.put(category.getId(), category);
        // Optionally, store category data in a text file
    }

    // Method to display all categories
    public void showCategories() {
        // Print all categories
        for (Category category : categories.values()) {
            System.out.println(category.getId() + ". " + category.getTitle() + ": " + category.getDescription());
        }
    }

    // Method to add a new blog
    public void addBlog(String title, String content, int userId, int categoryId) {
        // Create blog object and add it to the blogs map
        Blog blog = new Blog(blogIdCounter++, title, content, new Date(), userId, categoryId);
        blogs.put(blog.getId(), blog);
        // Store blog data in a text file
        try {
            String fileName = "content/" + title + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(content);
            writer.close();
            System.out.println("Blog added successfully. Blog file saved as " + fileName);
        } catch (IOException e) {
            System.out.println("Error: Unable to save blog content.");
            e.printStackTrace();
        }

    }

    // Method to edit an existing blog
    public void editBlog(int blogId, String newTitle, String newContent, int newCategoryId) {
        Blog blog = blogs.get(blogId);
        if (blog != null) {
            blog.setTitle(newTitle);
            blog.setContent(newContent);
            blog.setCategoryId(newCategoryId);
            System.out.println("Blog updated successfully.");
        } else {
            System.out.println("Blog not found.");
        }
    }

    // Method to search blogs by title or content
    public void searchBlog(String searchText) {
        System.out.println("Results:");
        for (Blog blog : blogs.values()) {
            if (blog.getTitle().contains(searchText) || blog.getContent().contains(searchText)) {
                System.out.println(blog.getId() + ". " + blog.getTitle());
            }
        }
    }

    // Method to display all blogs
    public void displayAllBlogs() {
        System.out.println("All blogs:");
        for (Blog blog : blogs.values()) {
            System.out.println(blog.getId() + ". " + blog.getTitle());
        }
    }

    // Method to display user-specific blogs
    public void displayUserBlogs(int userId) {
        System.out.println("User's blogs:");
        for (Blog blog : blogs.values()) {
            if (blog.getUserId() == userId) {
                System.out.println(blog.getId() + ". " + blog.getTitle());
            }
        }
    }

    // Method to delete a blog
    public void deleteBlog(int blogId) {
        Blog blog = blogs.remove(blogId);
        if (blog != null) {
            System.out.println("Blog deleted successfully.");
        } else {
            System.out.println("Blog not found.");
        }
    }

    public static void main(String[] args) {
        // Main method to handle user interactions
        Scanner scanner = new Scanner(System.in);
        BlogManagementSystem bms = new BlogManagementSystem();

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Handle login
                    System.out.println("Please Login to Continue");
                    System.out.print("Email: ");
                    String email = scanner.next();
                    System.out.print("Password: ");
                    String password = scanner.next();
                    if (bms.loginUser(email, password)) {
                        // Display main menu
                        System.out.println("Welcome " + email);
                        // Handle main menu options
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                    break;
                case 2:
                    // Handle registration
                    System.out.println("Please enter your details");
                    System.out.print("Full Name: ");
                    String fullName = scanner.next();
                    System.out.print("Email: ");
                    email = scanner.next();
                    System.out.print("Password: ");
                    password = scanner.next();
                    System.out.print("Phone no: ");
                    String phoneNo = scanner.next();
                    bms.registerUser(fullName, email, password, phoneNo);
                    break;
                case 0:
                    // Exit the application
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

