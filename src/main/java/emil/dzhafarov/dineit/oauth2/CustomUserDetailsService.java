package emil.dzhafarov.dineit.oauth2;

import emil.dzhafarov.dineit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return this.userService.findByUsername(email);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}