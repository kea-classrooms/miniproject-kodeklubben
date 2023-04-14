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
    UserModel userModel = new UserModel();

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

    @PostMapping("/login")
    public String submitLogin(@ModelAttribute("userModel") UserModel userModel, Model model) {
        submitLogin(userModel.getEmail() + ";" + userModel.getPassword(), model, true);
        WishListModel wishListModel = new WishListModel();
        model.addAttribute("wishListModel", wishListModel);
        return "profile";
    }

    //wish list
    @GetMapping("/wishList/{id}")
    public String getWishList(Model model, @PathVariable String id) {
        //localhost:8080/wishList?id=1;
        int wishListId = Integer.parseInt(id);
        WishListModel wishList = wishListRepository.getWishList(wishListId);
        model.addAttribute("wishList", wishList);
        return "wishList";
    }

    /*
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


     */
    /*
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
        model.addAttribute("wishListModel", wishListModel); // Add this line
        return "profile";
    }
     */
    //chatgpt fix
    @PostMapping("/createWishList")
    public String submitCreateWishlist(@ModelAttribute("wishListModel") WishListModel wishListModel, Model model) {
        System.out.println(wishListModel);
        System.out.println(userModel);
        System.out.println(model);
        int userId = wishListRepository.getIdFromAuthentication(userModel.getEmail(), userModel.getPassword());
        wishListRepository.insertNewWishList(wishListModel.getListName(), userId);
        ArrayList<WishListModel> wishLists = wishListRepository.getWishLists(userId);
        System.out.println(wishLists.get(0).getWishListID());
        model.addAttribute("wishLists", wishLists);
        model.addAttribute("userModel", userModel);
        model.addAttribute("wishListModel", wishListModel); // Add this line
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
            userModel = wishListRepository.getUser(userId);
            ArrayList<WishListModel> wishLists = wishListRepository.getWishLists(userId);
            model.addAttribute("userModel", userModel);
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

    @GetMapping("/error")
    public String errorPage() {
        return "Error";
    }
}
