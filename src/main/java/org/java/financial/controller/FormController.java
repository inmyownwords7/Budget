package org.java.financial.controller;

import org.java.financial.entity.Role;
import org.java.financial.repository.RoleRepository;
import org.java.financial.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
public class FormController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public FormController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,
                               @RequestParam(required = false) String roleName, RedirectAttributes redirectAttributes) {

//    public ResponseEntity<Map<String, String>> registerUser(@RequestParam String username,
//                                                            @RequestParam String password,
//                                                            @RequestParam(required = false) String roleName) {

//        String assignedRole = (roleName != null && roleName.equalsIgnoreCase("ADMIN")) ? "ROLE_ADMIN" : "ROLE_USER";

        Role role = roleRepository.findByRoleName("ROLE_USER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setRoleName("ROLE_USER");
            return roleRepository.save(newRole);
        });

        try {
            userService.registerUser(username, password, role);
            redirectAttributes.addFlashAttribute("success", "User registered successfully");
            return "redirect:/login";
//            User newUser = userService.registerUser(username, password, role);
//            return ResponseEntity.ok(Map.of("message", "User registered successfully!", "username", newUser.getUsername(), "role", assignedRole));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
//            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
