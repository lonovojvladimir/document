package ru.com.document.application.security;

import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.support.ConnectionUsernameProvider;
import org.springframework.stereotype.Component;
import ru.com.document.application.service.UserService;

@Component
@AllArgsConstructor
public class SecurityContextHolderUserProvider implements ConnectionUsernameProvider {

    private final UserService userService;

    @Override
    public String getUserName() {
        return userService.getCustomUserDetails().getUsername();
    }

}
