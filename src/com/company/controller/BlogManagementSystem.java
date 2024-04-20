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
        int loggedInUserId = -1; // Initialize to an invalid user ID


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
                        // Handle main menu options after login
                        while (true) {
                            System.out.println("\nMain Menu");
                            System.out.println("1. Add category");
                            System.out.println("2. Show categories");
                            System.out.println("3. Display all blogs");
                            System.out.println("4. Display my blogs");
                            System.out.println("5. Add blog");
                            System.out.println("6. Edit blog");
                            System.out.println("7. Search blog");
                            System.out.println("8. Delete blog");
                            System.out.println("9. Logout");
                            System.out.print("Enter your choice: ");
                            int menuChoice = scanner.nextInt();

                            switch (menuChoice) {
                                case 1:
                                    // Add category
                                    System.out.print("Enter category title: ");
                                    String categoryTitle = scanner.next();
                                    System.out.print("Enter category description: ");
                                    String categoryDescription = scanner.next();
                                    bms.addCategory(categoryTitle, categoryDescription);
                                    break;
                                case 2:
                                    // Show categories
                                    bms.showCategories();
                                    break;
                                case 3:
                                    // Display all blogs
                                    bms.displayAllBlogs();
                                    break;
                                case 4:
                                    // Display user's blogs
                                    bms.displayUserBlogs(loggedInUserId);
                                    break;
                                case 5:
                                    // Add blog
                                    System.out.print("Enter blog title: ");
                                    String blogTitle = scanner.next();
                                    System.out.print("Enter blog content: ");
                                    String blogContent = scanner.next();
                                    System.out.print("Enter category ID: ");
                                    int categoryId = scanner.nextInt();
                                    bms.addBlog(blogTitle, blogContent, loggedInUserId, categoryId);
                                    break;
                                case 6:
                                    // Edit blog
                                    System.out.print("Enter blog id: ");
                                    int blogId = scanner.nextInt();
                                    System.out.print("Enter new blog title: ");
                                    String newTitle = scanner.next();
                                    System.out.print("Enter new blog content: ");
                                    String newblogContent = scanner.next();
                                    System.out.print("Enter new category ID: ");
                                    int newcategoryId = scanner.nextInt();
                                    bms.editBlog(blogId,newTitle,newblogContent,newcategoryId);
                                    break;
                                case 7:
                                    // Search blog
                                    System.out.print("Enter new category ID: ");
                                    String searchText = scanner.next();
                                    // Search blog functionality
                                    bms.searchBlog(searchText);
                                    break;
                                case 8:
                                    // Delete blog
                                    // Delete blog functionality
                                    System.out.print("Enter blog ID of blog to be deleted: ");
                                    int blogIddel = scanner.nextInt();
                                    bms.deleteBlog(blogIddel);
                                    break;
                                case 9:
                                    // Logout
                                    loggedInUserId = -1;
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }

                            if (menuChoice == 9) {
                                break; // Break out of the inner while loop and go back to the login menu
                            }
                        }
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

