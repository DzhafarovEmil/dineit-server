package emil.dzhafarov.dineit.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, CUSTOMER, BUSINESS, FRIDGE;

    @Override
    public String getAuthority() {
        return name();
    }
}
