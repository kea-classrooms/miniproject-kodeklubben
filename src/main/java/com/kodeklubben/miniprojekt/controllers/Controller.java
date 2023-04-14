package com.kodeklubben.miniprojekt.controllers;

import com.kodeklubben.miniprojekt.models.UserModel;
import com.kodeklubben.miniprojekt.models.WishListModel;
import com.kodeklubben.miniprojekt.models.WishModel;
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
        UserModel userModel = new UserModel("", "", "", -1);
        model.addAttribute("userModel", userModel);
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
    @GetMapping("/wishList")
    public String getWishList(Model model, @RequestParam String id) {
        //localhost:8080/wishList?id=1;
        int wishListId = Integer.parseInt(id);
        WishListModel wishList = wishListRepository.getWishList(wishListId);
        if (wishList == null) {
            ArrayList<WishModel> wishes = new ArrayList<>();
            wishList = new WishListModel("Ukendt liste", wishes, -1);
        }
        model.addAttribute("wishModel", new WishModel(-1,"",""));
        model.addAttribute("wishList", wishList);
        model.addAttribute(Integer.parseInt(id));
        return "wishList";
    }
    
    //chatgpt fix


        // Create wishList
    @PostMapping("/profile")
    public String submitCreateWishlist(@ModelAttribute("wishListModel") WishListModel wishListModel, Model model) {
        System.out.println(wishListModel);
        System.out.println(userModel);
        System.out.println(model);
        int userId = wishListRepository.getIdFromAuthentication(userModel.getEmail(), userModel.getPassword());
        wishListRepository.insertNewWishList(wishListModel.getListName(), userId);
        ArrayList<WishListModel> wishLists = wishListRepository.getWishLists(userId);
        model.addAttribute("wishLists", wishLists);
        model.addAttribute("userModel", userModel);
        return "profile";
    }

    @RequestMapping("/profile")
    public String getProfile(@ModelAttribute("wishListModel") WishListModel wishListModel ,Model model) {
        ArrayList<WishListModel> wishLists = wishListRepository.getWishLists(userModel.getId());
        model.addAttribute("userModel", userModel);
        System.out.println(userModel.getName());
        model.addAttribute("wishLists", wishLists);
        return "profile";
    }

    // Create wish
    @PostMapping("/wishList")
    public String submitCreateWish(@ModelAttribute("wishModel") WishModel wishModel, Model model) {
        System.out.println(wishModel);
        System.out.println(userModel);
        System.out.println("model = " + model.toString());
        int wishListID = (int) model.getAttribute("id");
        System.out.println("kig her " + wishListID);
        System.out.println(model.getAttribute("id"));
        wishListRepository.insertNewWish(wishModel.getName(),wishModel.getLink(), wishModel.getId());
        model.addAttribute("wishModel", wishModel);
        model.addAttribute("userModel", userModel);
        return "wishList";
    }

    @RequestMapping("/wishList")
    public String getWishProfile(@ModelAttribute("wishListModel") WishModel wishModel ,Model model) {
        ArrayList<WishListModel> wishLists = wishListRepository.getWishLists(userModel.getId());
        model.addAttribute("userModel", userModel);
        System.out.println(userModel.getName());
        model.addAttribute("wishModel", wishModel);
        return "wishList";
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
        UserModel userModel = new UserModel("", "", "", -1);
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
