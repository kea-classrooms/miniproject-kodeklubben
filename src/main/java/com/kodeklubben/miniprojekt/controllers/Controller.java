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
import java.util.Collection;
import java.util.Map;

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

    //wish list
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

    //go to profile


    //login with email and password
    @GetMapping("/credentials")
    public String submitLogin(@RequestParam String id, Model model) {
        //http://localhost:8080/credentials?id=frederikbehrens90@gmail.com;123
        String email = id.split(";")[0];
        String password = id.split(";")[1];
        int userId = wishListRepository.getIdFromAuthentication(email, password);
        System.out.println("id: " + userId);
        if (userId != -1) {
            UserModel userModel = wishListRepository.getUser(userId);
            ArrayList<WishListModel> wishLists = wishListRepository.getWishLists(userId);
            model.addAttribute("user", userModel);
            model.addAttribute("wishLists", wishLists);
            return "profile";
        } else {
            return "login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        System.out.println(model);
        UserModel userModel = new UserModel("", "", "");
        model.addAttribute("userModel", userModel);
        return "register";
    }

    // Create User
    @PostMapping("/register")
    public String createUser(@ModelAttribute("userModel") UserModel userModel, Model model) {
        System.out.println(userModel);
        wishListRepository.insertNewUser(userModel.getName(), userModel.getEmail(), userModel.getPassword());
        submitLogin(userModel.getEmail() + ";" + userModel.getPassword(), model);
        return "profile";
    }

    // About & Contact
    @GetMapping("/about")
    public String aboutPage() {
        return "About";
    }
    @GetMapping("/contact")
    public String contactPage() {
        return "Contact";
    }



}
