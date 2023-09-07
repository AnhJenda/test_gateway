package com.example.authservice.controller;

import com.example.authservice.entities.LoginRequest;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    @author: Dinh Quang Anh
    Date   : 9/6/2023
    Project: auth-service
*/
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(authService.login(loginRequest));
    }
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        // Step 1: Hash the password
//        String hashedPassword = encoder.encode(signUpRequest.getPassword());
//
//// Step 2: Create the User object with username, email, and hashed password
//        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), hashedPassword);
//
//// Step 3: Set the telephone number on the User object
//        user.setTel(signUpRequest.getTel());
//
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName("USER")
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role.toLowerCase(Locale.ROOT)) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName("ADMIN")
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "manager":
//                        Role modRole = roleRepository.findByName("MANAGER")
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName("USER")
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles((Set<Role>) roles);
//        user = userRepository.save(user);
//        emailVerificationService.sendVerificationEmail(user);
//
//
////        emailVerificationService.sendVerificationEmail(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
//
//    @GetMapping("/verify-email")
//    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
//        EmailVerificationToken verificationToken = emailVerificationTokenRepository.findByToken(token);
//
//        if (verificationToken == null) {
//            // Mã xác nhận không hợp lệ
//            return ResponseEntity.badRequest().body(new MessageResponse("Invalid verification token"));
//        }
//
//        User user = verificationToken.getUser();
//        user.setEmailVerified(true); // Đánh dấu email là đã xác nhận
//        userRepository.save(user);
//
//        // Xóa mã xác nhận khỏi cơ sở dữ liệu
//        emailVerificationTokenRepository.delete(verificationToken);
//
//        return ResponseEntity.ok(new MessageResponse("Email verified successfully!"));
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
//        String token = extractAuthToken(request);
//        if (token != null) {
//            jwtUtils.expireJwtToken(token);
//        }
//        return ResponseEntity.ok(new MessageResponse("User logged out successfully!"));
//    }
//
//    private String extractAuthToken(HttpServletRequest request) {
//        String header = request.getHeader("Authorization");
//        if (header != null && header.startsWith("Bearer ")) {
//            return header.substring(7); // Lấy token từ header
//        }
//
//        return null;
//    }





//    private final AuthService authService;
//
//    @Autowired
//    public AuthController(final AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping(value = "/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
//        return ResponseEntity.ok(authService.register(authRequest));
//    }

}
