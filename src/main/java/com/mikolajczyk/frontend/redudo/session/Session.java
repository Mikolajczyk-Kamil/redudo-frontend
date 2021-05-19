package com.mikolajczyk.frontend.redudo.session;

import com.mikolajczyk.frontend.redudo.domain.User;
import com.vaadin.flow.component.html.Div;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Session {

    private Div searchHistory;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        return new User(
                oidcUser.getAttribute("subject"),
                oidcUser.getAttribute("given_name"),
                oidcUser.getAttribute("family_name"),
                oidcUser.getAttribute("email"),
                oidcUser.getAttribute("picture")
        );
    }

    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        return oidcUser.getIdToken().getTokenValue();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null;
    }

    public Div getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(Div searchHistory) {
        this.searchHistory = searchHistory;
    }
}
