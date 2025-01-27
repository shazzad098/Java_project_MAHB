package com.librarysystem.service;

import com.librarysystem.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users;

    public UserService() {
        this.users = new ArrayList<>();
        registerInitialUsers();
    }

    private void registerInitialUsers() {
        users.add(new User(1, "Shazzad", "123-456-7890", "shazzad@gmail.com", "Dhaka,Bangladesh"));
        users.add(new User(2, "Polok", "987-654-3210", "polok@gmail.com", "Dhaka,Bangladesh"));
        users.add(new User(3, "Tamim", "555-123-4567", "tamim@gmail.com", "New York,USA"));
        users.add(new User(4, "Rabby", "111-222-3333", "rabby@gmail.com", "Delhi, India"));
        users.add(new User(5, "Ahiq", "444-555-6666", "ashiq@gmail.com", "Zenova, Switzerland"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User findUserById(int userId) throws UserNotFoundException {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        throw new UserNotFoundException("User with ID " + userId + " not found.");
    }
}