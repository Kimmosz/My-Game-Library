package com.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Register")
@PageTitle("Register")
@CssImport("./styles/styles.css")
public class Register extends VerticalLayout {

    public Register() {
        if (SessionAttributes.getLoggedUser() == null) {
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addClassName("register");
            add(Header.addMenuBar());

            H2 pageTitle = new H2("Register");
            
            FormLayout registerLayout = new FormLayout();
            
            TextField usernameField = new TextField("Username"); 
            usernameField.setRequired(true);
            usernameField.setErrorMessage("Enter a username");

            EmailField emailField = new EmailField("Email");
            emailField.setRequiredIndicatorVisible(true);
            emailField.setErrorMessage("Enter a valid email address");

            PasswordField passwordField = new PasswordField("Password");
            passwordField.setRequired(true);
            passwordField.setErrorMessage("Enter a password");

            PasswordField passwordCheckField = new PasswordField("Password check");
            passwordCheckField.setRequired(true);
            passwordCheckField.setErrorMessage("Verify password");

            Span errorMessage = new Span();
            errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
            errorMessage.getStyle().set("padding", "15px 0");

            Button registerButton = new Button("Register");
            registerButton.addClickListener(event -> registerCheck(usernameField.getValue(), emailField.getValue(), passwordField.getValue(), passwordCheckField.getValue(), errorMessage));
            registerButton.setEnabled(false);
            
            Button cancelButton = new Button("Cancel");
            cancelButton.addClickListener(event -> cancelButton.getUI().ifPresent(ui -> ui.navigate("")));

            usernameField.addValueChangeListener(event -> {
                if (usernameField.isEmpty() || emailField.isEmpty() || emailField.isInvalid() || passwordField.isEmpty() || passwordCheckField.isEmpty()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            });

            emailField.addValueChangeListener(event -> {
                if (usernameField.isEmpty() || emailField.isEmpty() || emailField.isInvalid() || passwordField.isEmpty() || passwordCheckField.isEmpty()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            });

            passwordField.addValueChangeListener(event -> {
                if (usernameField.isEmpty() || emailField.isEmpty() || emailField.isInvalid() || passwordField.isEmpty() || passwordCheckField.isEmpty()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            });

            passwordCheckField.addValueChangeListener(event -> {
                if (usernameField.isEmpty() || emailField.isEmpty() || emailField.isInvalid() || passwordField.isEmpty() || passwordCheckField.isEmpty()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            });

            registerLayout.add(pageTitle, usernameField, emailField, passwordField, passwordCheckField, errorMessage, registerButton, cancelButton);
            registerLayout.setMaxWidth("500px");
            registerLayout.getStyle().set("margin","0 auto");
            registerLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
            registerLayout.setColspan(pageTitle, 2);
            registerLayout.setColspan(errorMessage, 2);
            add(registerLayout);
        } else {
            UI.getCurrent().navigate("Library");
            UI.getCurrent().getPage().reload();
        }
    }

    private void registerCheck(String username, String email, String password, String passwordCheck, Span errorMessage) {
        boolean usernameExists = Database.usernameExists(username);
        boolean emailExists = Database.emailExists(email);

        if (usernameExists) {
            errorMessage.setText("Username already exists"); 
        } else if (emailExists) {
            errorMessage.setText("Email already exists");
        } else if (!password.equals(passwordCheck)) {
            errorMessage.setText("Passwords do not match");
        } else {
            Database.registerUser(username, email, password);
            Notification.show("Succesfully registered!");
            UI.getCurrent().navigate("Login");
        }
    }
}