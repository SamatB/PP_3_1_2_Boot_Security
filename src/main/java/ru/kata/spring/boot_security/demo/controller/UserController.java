package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping()
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/admin/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("users") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/updatePage")
    public String updatePage(Model model, @RequestParam("id") Long id) {
        model.addAttribute("userUpdate", userService.getUserById(id));
        return "updateUser";
    }

    @PostMapping("/admin/update")
    public String updateUser(@ModelAttribute("userUpdate") User user, @RequestParam("id") Long id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/profile")
    public String userProfile(Model model, @RequestParam Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "userProfile";
    }

    @GetMapping("/user/usersProfile")
    public String usersProfile(Model model, Principal principal) {
        User userByName = userService.getUserByName(principal.getName());
        model.addAttribute("user", userByName);
        return "usersProfile";
    }

    @GetMapping("/admin/adminProfile")
    public String adminProfile(Model model, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "adminProfile";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }

}
