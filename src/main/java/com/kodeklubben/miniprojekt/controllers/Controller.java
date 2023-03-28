package com.kodeklubben.miniprojekt.controllers;

import com.kodeklubben.miniprojekt.models.userModel;
import com.kodeklubben.miniprojekt.repositories.WishListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    WishListRepository wishListRepository = new WishListRepository();

    @GetMapping("/{id}")
    public ResponseEntity<ArrayList<String>> getWishLists(@PathVariable String id) {
        return new ResponseEntity<>(wishListRepository.getWishLists(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/")
    public String getAllSuperheroes() {
        return "homePage";
    }

                // Login

    @GetMapping("/login")
        public String login(Model model) {
            userModel userModel = new userModel();
            model.addAttribute("usermodel", userModel);
            return "loginPage";
        }

    @PostMapping("/login")
        public String submitLogin(@ModelAttribute("userModel") userModel userModel) {
        wishListRepository.userLogin(userModel);
        return "";
    }


                // Create User

    @GetMapping("/createuser")
    public String createUser(Model model) {
        userModel userModel = new userModel();
        model.addAttribute("usermodel", userModel);
        return "createUser";
    }

    @PostMapping("/createUser")
    public String submitUser(@ModelAttribute("userModel") userModel userModel) {
        wishListRepository.userLogin(userModel);
        return "";
    }

}
