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

    //login with email and password
    @GetMapping("/credentials")
    public String submitLogin(@RequestParam String id) {
        //localhost:8080/credentials?id=Adam@kea.dk;Adam Hagepassword
        String email = id.split(";")[0];
        String password = id.split(";")[1];
        int userId = wishListRepository.getIdFromAuthentication(email, password);
        System.out.println("id: " + userId);
        if (userId != -1) {
            Model model = new Model() {
                @Override
                public Model addAttribute(String attributeName, Object attributeValue) {
                    return null;
                }

                @Override
                public Model addAttribute(Object attributeValue) {
                    return null;
                }

                @Override
                public Model addAllAttributes(Collection<?> attributeValues) {
                    return null;
                }

                @Override
                public Model addAllAttributes(Map<String, ?> attributes) {
                    return null;
                }

                @Override
                public Model mergeAttributes(Map<String, ?> attributes) {
                    return null;
                }

                @Override
                public boolean containsAttribute(String attributeName) {
                    return false;
                }

                @Override
                public Object getAttribute(String attributeName) {
                    return null;
                }
                @Override
                public Map<String, Object> asMap() {
                    return null;
                }
            };
            UserModel userModel = wishListRepository.getUser(userId);
            model.addAttribute("user", userModel);
            return "profile";
        } else {
            return "login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserModel userModel = new UserModel("", "", "");
        model.addAttribute("userModel", userModel);
        return "register";
    }

    // Create User
    @PostMapping("/register")
    public String createUser(@ModelAttribute("userModel") UserModel userModel) {
        System.out.println(userModel);
        wishListRepository.insertNewUser(userModel.getName(), userModel.getEmail(), userModel.getPassword());
        submitLogin(userModel.getEmail() + ";" + userModel.getPassword());
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
