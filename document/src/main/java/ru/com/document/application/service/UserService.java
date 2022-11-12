package ru.com.document.application.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.com.document.application.security.CustomUserDetails;
import ru.com.document.application.security.jwt.JwtUtil;

@Service
@AllArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;

    public CustomUserDetails getCustomUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        } else {
            return null;
        }
    }


}
