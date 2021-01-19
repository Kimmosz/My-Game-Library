package com.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Login")
@PageTitle("Login")
@CssImport("./styles/styles.css")
public class Login extends VerticalLayout {

    public Login() {
        if (SessionAttributes.getLoggedUser() == null) {
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addClassName("login");
            add(Header.addMenuBar());

            H2 pageTitle = new H2("Login");
            
            FormLayout loginLayout = new FormLayout();
            
            TextField usernameField = new TextField("Username"); 
            usernameField.setRequired(true);
            usernameField.setErrorMessage("Enter a username");

            PasswordField passwordField = new PasswordField("Password");
            passwordField.setRequired(true);
            passwordField.setErrorMessage("Enter a password");

            Span errorMessage = new Span();
            errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
            errorMessage.getStyle().set("padding", "15px 0");

            Button loginButton = new Button("Login");
            loginButton.addClickListener(event -> userLogin(usernameField.getValue(), passwordField.getValue(), errorMessage));
            loginButton.setEnabled(false);
            
            Button cancelButton = new Button("Cancel");
            cancelButton.addClickListener(event -> cancelButton.getUI().ifPresent(ui -> ui.navigate("")));

            usernameField.addValueChangeListener(event -> {
                if (usernameField.isEmpty() || passwordField.isEmpty()) {
                    loginButton.setEnabled(false);
                } else {
                    loginButton.setEnabled(true);
                }
            });

            passwordField.addValueChangeListener(event -> {
                if (usernameField.isEmpty() || passwordField.isEmpty()) {
                    loginButton.setEnabled(false);
                } else {
                    loginButton.setEnabled(true);
                }
            });

            loginLayout.add(pageTitle, usernameField, passwordField, errorMessage, loginButton, cancelButton);
            loginLayout.setMaxWidth("500px");
            loginLayout.getStyle().set("margin","0 auto");
            loginLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
            loginLayout.setColspan(pageTitle, 2);
            loginLayout.setColspan(errorMessage, 2);
            add(loginLayout);
        } else {
            UI.getCurrent().navigate("Library");
            UI.getCurrent().getPage().reload();
        }
    }

    private void userLogin(String username, String password, Span errorMessage) {
        boolean correctCredentials = Database.correctLoginCredentials(username, password);

        if (!correctCredentials) {
            errorMessage.setText("Username and password do not match"); 
        } else {
            Notification.show("Welcome "+username+"!");
            SessionAttributes.login(username);
            UI.getCurrent().navigate("Library");
        }
    }
}