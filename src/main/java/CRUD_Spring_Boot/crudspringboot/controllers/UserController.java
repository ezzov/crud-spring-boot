package CRUD_Spring_Boot.crudspringboot.controllers;

import CRUD_Spring_Boot.crudspringboot.service.RoleService;
import CRUD_Spring_Boot.crudspringboot.service.UserService;
import CRUD_Spring_Boot.crudspringboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/users")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "allusers";
    }

    @GetMapping("/user")
    public String getUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "user";
    }

    @GetMapping("/admin/new")
    public String newPerson(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/admin/users")
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
        userService.save(user, checkBoxRoles);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit/{userId}")
    public String edit(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("user", userService.getUser(userId));
        model.addAttribute("roles", roleService.getAllRoles());
        return "update";
    }

    @PatchMapping("/admin/edit/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
        userService.update(user, checkBoxRoles);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/remove/{userId}")
    public String delete(@PathVariable("userId") int userId) {
        userService.delete(userId);
        return "redirect:/admin/users";
    }
}
