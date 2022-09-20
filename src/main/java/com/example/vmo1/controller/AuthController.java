package com.example.vmo1.controller;

import com.example.vmo1.model.entity.RefreshToken;
import com.example.vmo1.model.request.*;
import com.example.vmo1.model.response.AccountInforResponse;
import com.example.vmo1.model.response.JWTAuthResponse;
import com.example.vmo1.security.jwt.JwtTokenProvider;
import com.example.vmo1.security.service.CustomUserDetails;
import com.example.vmo1.service.AccountService;
import com.example.vmo1.service.ForgotPasswordService;
import com.example.vmo1.service.RefeshTokenService;
import com.example.vmo1.service.RegistrationService;
import com.example.vmo1.validation.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ForgotPasswordService forgotPasswordService;
    @Autowired
    private RefeshTokenService refeshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("Login request: " + loginRequest.getUsernameOrEmail());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        RefreshToken refreshToken = refeshTokenService.createRefreshToken(customUserDetails.getId());
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token, refreshToken.getToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request){
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken refreshToken = refeshTokenService.findByToken(requestRefreshToken).get();
        refeshTokenService.verifyExpiration(refreshToken);

        String token = tokenProvider.generateTokenFromUsername(refreshToken.getAccount().getUsername());
        return ResponseEntity.ok(new JWTAuthResponse(token, requestRefreshToken));

    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(registrationService.register(request));
    }

    @GetMapping(path = "/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping(path = "/forgot-password/confirm")
    public String confirmTokenToChangePassword(@RequestParam("token") String token) {
        return forgotPasswordService.confirmTokenResetPassword(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendTokenToChangePassword(@RequestBody PasswordResetLinkRequest request) {
        return ResponseEntity.ok(forgotPasswordService.sendTokenToChangePassword(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetRequest request){
        return ResponseEntity.ok(forgotPasswordService.changePassword(request));
    }


}
