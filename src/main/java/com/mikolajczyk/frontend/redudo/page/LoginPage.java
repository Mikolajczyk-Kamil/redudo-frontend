package com.mikolajczyk.frontend.redudo.page;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

@Route("login")
@PageTitle("Login")
@CssImport(value = "./styles/login-page-styles.css")
class LoginPage extends AppLayout {

    private static final String GOOGLE_URL = "/oauth2/authorization/google";

    @Value("spring.security.oauth2.client.registration.google.client-id")
    private String clientKey;

    @PostConstruct
    public void InitView() {
        if (clientKey == null || clientKey.isEmpty() || clientKey.length() < 16) {
            Paragraph paragraph = new Paragraph("Error in server configuration. Please contact to us.");
            setContent(paragraph);
        } else {
            Div div = new Div();
            div.setClassName("loginDiv");
            Anchor loginButton = new Anchor(GOOGLE_URL, "Login with Google");
            loginButton.setClassName("loginButton");
            div.add(loginButton);
            setContent(div);
        }
    }
}
