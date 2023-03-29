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

    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    // Login
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    //login with email and password
    @GetMapping("/wishList")
    public String getWishList(@RequestParam String id, Model model) {
        //localhost:8080/wishList?id=1;;1
        int userId = Integer.parseInt(id.split(";;")[0]);
        int wishListId = Integer.parseInt(id.split(";;")[1]);
        WishListModel wishList = wishListRepository.getWishList(userId, wishListId);
        if (wishList != null) {
            UserModel user = wishListRepository.getUser(userId);
            if (user != null) {
                model.addAttribute("user", user);
            }
            model.addAttribute("wishList", wishList);
            return "wishList";
        } else {
            return "createWishList";
        }
    }

    //login with email and password
    @GetMapping("/credentials")
    public String submitLogin(@RequestParam String id, Model model) {
        //localhost:8080/credentials?id=Adam@kea.dk;;Adam Hagepassword
        String email = id.split(";;")[0];
        String password = id.split(";;")[1];
        int userId = wishListRepository.getIdFromAuthentication(email, password);
        System.out.println("id: " + userId);
        if (userId != -1) {
            UserModel userModel = wishListRepository.getUser(userId);
            model.addAttribute("user", userModel);
            return "profile";
        } else {
            return "login";
        }
    }


    // Create User
    @GetMapping("/createuser")
    public String createUser(Model model) {
        model.addAttribute("usermodel");
        return "register";
    }

    @PostMapping("/createUser")
    public String submitUser(@ModelAttribute("userModel") UserModel userModel) throws SQLException {
        wishListRepository.insertNewUser(userModel.getName(), userModel.getEmail(), userModel.getPassword());
        return "profile";
    }





}
