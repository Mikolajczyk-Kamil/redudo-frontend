package com.mikolajczyk.frontend.redudo.session;

import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.domain.User;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Session {

    private VerticalLayout searchHistory;
    private Book book;
    private boolean darkMode = false;
    private boolean signedIn = false;

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

    public VerticalLayout getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(VerticalLayout searchHistory) {
        this.searchHistory = searchHistory;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public boolean isSignedIn() {
        return signedIn;
    }

    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }
}
