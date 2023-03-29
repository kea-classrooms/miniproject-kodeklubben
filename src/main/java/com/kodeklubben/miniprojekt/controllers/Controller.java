package com.kodeklubben.miniprojekt.controllers;

import com.kodeklubben.miniprojekt.models.UserModel;
import com.kodeklubben.miniprojekt.models.WishListModel;
import com.kodeklubben.miniprojekt.repositories.WishListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    WishListRepository wishListRepository = new WishListRepository();

    @GetMapping("/{id}")
    public ResponseEntity<ArrayList<WishListModel>> getWishLists(@PathVariable String id) {
        return new ResponseEntity<>(wishListRepository.getWishLists(Integer.parseInt(id)), HttpStatus.OK);
    }

    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    // Login
    @GetMapping("/login")
    public String login(Model model) {
        UserModel userModel = new UserModel();
        model.addAttribute("usermodel", userModel);
        return "LoginPage";
    }

    @PostMapping("/login")
    public String submitLogin(@ModelAttribute("userModel") UserModel userModel) {
        wishListRepository.getIdFromAuthentication(userModel.getEmail(), userModel.getPassword());
        return "userPage";
    }


    // Create User

    @GetMapping("/createuser")
    public String createUser(Model model) {
        UserModel userModel = new UserModel();
        model.addAttribute("usermodel", userModel);
        return "RegistrationPage";
    }

    @PostMapping("/createUser")
    public String submitUser(@ModelAttribute("userModel") UserModel userModel) throws SQLException {
        wishListRepository.insertNewUser(userModel.getName(), userModel.getEmail(), userModel.getPassword());
        return "userPage";
    }
}
