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
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    WishListRepository wishListRepository = new WishListRepository();

    @GetMapping("/{id}")
    public ResponseEntity<ArrayList<WishListModel>> getWishLists(@PathVariable String id) {
        return new ResponseEntity<>(wishListRepository.getWishLists(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/")
    public String getAllSuperheroes() {
        return "homePage";
    }

                // Login

    @GetMapping("/login")
        public String login(Model model) {
            UserModel userModel = new UserModel();
            model.addAttribute("usermodel", userModel);
            return "loginPage";
        }

    @PostMapping("/login")
        public String submitLogin(@ModelAttribute("userModel") UserModel userModel) {
        wishListRepository.userLogin(userModel);
        return "";
    }


                // Create User

    @GetMapping("/createuser")
    public String createUser(Model model) {
        UserModel userModel = new UserModel();
        model.addAttribute("usermodel", userModel);
        return "createUser";
    }

    @PostMapping("/createUser")
    public String submitUser(@ModelAttribute("userModel") UserModel userModel) throws SQLException {
        wishListRepository.addUserToDatabase(userModel);
        return "";
    }

}
