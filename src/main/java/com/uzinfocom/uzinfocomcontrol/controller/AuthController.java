package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.Company;
import com.uzinfocom.uzinfocomcontrol.model.DTO.AuthResponse;
import com.uzinfocom.uzinfocomcontrol.model.DTO.CompanyDTO;
import com.uzinfocom.uzinfocomcontrol.model.DTO.LoginRequest;
import com.uzinfocom.uzinfocomcontrol.model.DTO.RegisterRequest;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.repository.UserRepository;
import com.uzinfocom.uzinfocomcontrol.service.CompanyService;
import com.uzinfocom.uzinfocomcontrol.service.DepartmentService;
import com.uzinfocom.uzinfocomcontrol.service.UserService;
import com.uzinfocom.uzinfocomcontrol.untils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
//        if (userRepository.findByUserName(request.getUsername()).isPresent()) {
//            return ResponseEntity.badRequest().body("User already exists");
//        }
//        User user = new User();
//        user.setUserName(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        userRepository.save(user);
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("Uzinfocom");
        Company company = companyService.addCompany(companyDTO);
        CompanyDTO companyDTO2 = new CompanyDTO();
        companyDTO2.setName("Uzinfocom");
        Company company2 = companyService.addCompany(companyDTO2);
        userService.saveMockDate(departmentService.addDepartment("",company),departmentService.addDepartment("",company2));
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
