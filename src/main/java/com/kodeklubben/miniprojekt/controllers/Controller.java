package com.kodeklubben.miniprojekt.controllers;

import com.kodeklubben.miniprojekt.models.UserModel;
import com.kodeklubben.miniprojekt.models.WishListModel;
import com.kodeklubben.miniprojekt.repositories.WishListRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        UserModel userModel = new UserModel("", "", "");
        model.addAttribute("userModel", userModel);
        int userId = wishListRepository.getIdFromAuthentication(userModel.getEmail(), userModel.getPassword());
        ArrayList<WishListModel> wishLists = wishListRepository.getWishLists(userId);
        model.addAttribute("wishLists", wishLists);
        return "login";
    }

    // Create User
    @PostMapping("/login")
    public String submitLogin(@ModelAttribute("userModel") UserModel userModel, Model model) {
        submitLogin(userModel.getEmail() + ";" + userModel.getPassword(), model, true);
        return "profile";
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

    @PostMapping("/createWishList")
    public String submitCreateWishlist(@ModelAttribute("wishListModel") WishListModel wishListModel, UserModel userModel, Model model) {
        System.out.println(wishListModel);
        System.out.println(userModel);
        System.out.println(model);
        wishListRepository.insertNewWishList(wishListModel.getListName(), wishListRepository.getIdFromAuthentication(userModel.getEmail(), userModel.getPassword()));
        int userId = wishListRepository.getIdFromAuthentication(userModel.getEmail(), userModel.getPassword());
        ArrayList<WishListModel> wishLists = wishListRepository.getWishLists(userId);
        model.addAttribute("wishLists", wishLists);
        model.addAttribute("userModel", userModel);
        return "profile";
    }

    //login with email and password
    @GetMapping("/credentials")
    public String submitLogin(@RequestParam String id, Model model, boolean isLogin) {
        //http://localhost:8080/credentials?id=frederikbehrens90@gmail.com;123
        String email = id.split(";")[0];
        String password = id.split(";")[1];
        int userId = wishListRepository.getIdFromAuthentication(email, password);
        if (userId != -1) {
            UserModel userModel = wishListRepository.getUser(userId);
            ArrayList<WishListModel> wishLists = wishListRepository.getWishLists(userId);
            model.addAttribute("user", userModel);
            System.out.println(userModel.getName());
            model.addAttribute("wishLists", wishLists);
            System.out.println(wishLists.size());
            return "profile";
        } else {
            if (isLogin) {
                return "login";
            } else {
                return "register";
            }
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
        submitLogin(userModel.getEmail() + ";" + userModel.getPassword(), model, false);
        return "";
    }

    // About & Contact
    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "Contact";
    }

}
