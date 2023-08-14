package fpoly.shopbe.controller;


import fpoly.shopbe.DTO.AccountLoginDto;
import fpoly.shopbe.DTO.JwtResponseDto;
import fpoly.shopbe.domain.CustomUserDetails;
import fpoly.shopbe.jwt.JwtService;
import fpoly.shopbe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountService accountService;

    @PostMapping("login")
    public ResponseEntity<?> loginService(@Valid @RequestBody AccountLoginDto dto, BindingResult result) {
        try {
            // Xác thực đăng nhập với mật khẩu không mã hóa
            if (dto.getUsername() != null && dto.getPassword() != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
                authentication = authenticationManager.authenticate(authentication);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                String jwt = jwtService.accountToken(customUserDetails);

                JwtResponseDto token = new JwtResponseDto(jwt, customUserDetails.getUsername(), customUserDetails.getAuthorities().toString());

                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing username or password");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }
}
