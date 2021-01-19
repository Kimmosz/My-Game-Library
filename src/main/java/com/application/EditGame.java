package com.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("EditGame")
@PageTitle("Edit Game")
@CssImport("./styles/styles.css")
public class EditGame extends VerticalLayout {

    public EditGame() {
        if (SessionAttributes.getLoggedUser() != null && SessionAttributes.getSelectedGame() != 0) {
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addClassName("game");
            add(Header.addMenuBar());

            H2 pageTitle = new H2("Edit Game");
            
            FormLayout editGameLayout = new FormLayout();
            
            TextField titleField = new TextField("Title");
            titleField.setRequired(true);
            titleField.setErrorMessage("Enter a title");
            
            ComboBox<String> consoleField = new ComboBox<>();
            consoleField.setItems("PC", "Playstation 1", "Playstation 2", "Playstation 3", "Playstation 4", "Playstation 5", "Playstation Portable", "Playstation Vita", "Xbox", "Xbox 360", "Xbox One", "Xbox X", "Nintendo Game Boy", "Nintendo DS", "Nintendo 3DS", "Nintendo Wii", "Nintendo WiiU", "Nintendo Switch", "Nintendo Entertainment System", "Super Nintendo Entertainment System", "Nintendo 64", "Nintendo GameCube");
            consoleField.setLabel("Console");
            consoleField.setRequired(true);
            consoleField.setErrorMessage("Choose a console");

            ComboBox<String> genreField = new ComboBox<>();
            genreField.setItems("Action", "Adventure", "RPG", "MMORPG", "MMO", "Simulation", "Strategy", "Sports", "Other");
            genreField.setLabel("Genre");
            genreField.setRequired(true);
            genreField.setErrorMessage("Choose a genre");

            RadioButtonGroup<String> typeField = new RadioButtonGroup<>();
            typeField.setLabel("Type");
            typeField.setItems("Digital", "Physical");
            typeField.setRequired(true);
            typeField.setErrorMessage("Choose a type");

            Button submitButton = new Button("Edit game in library");
            submitButton.addClickListener(event -> editGame(titleField.getValue(), consoleField.getValue(), genreField.getValue(), typeField.getValue()));
            submitButton.setEnabled(false);

            Button resetButton = new Button("Reset");
            resetButton.addClickListener(event -> { 
                Database.loadGame(titleField, consoleField, genreField, typeField);
                submitButton.setEnabled(false);
            });

            Database.loadGame(titleField, consoleField, genreField, typeField);

            titleField.addValueChangeListener(event -> {
                if (titleField.isEmpty() || consoleField.isEmpty() || genreField.isEmpty() || typeField.isEmpty()) {
                    submitButton.setEnabled(false);
                } else {
                    submitButton.setEnabled(true);
                }
            });
            consoleField.addValueChangeListener(event -> {
                if (titleField.isEmpty() || consoleField.isEmpty() || genreField.isEmpty() || typeField.isEmpty()) {
                    submitButton.setEnabled(false);
                } else {
                    submitButton.setEnabled(true);
                }
            });
            genreField.addValueChangeListener(event -> {
                if (titleField.isEmpty() || consoleField.isEmpty() || genreField.isEmpty() || typeField.isEmpty()) {
                    submitButton.setEnabled(false);
                } else {
                    submitButton.setEnabled(true);
                }
            });
            typeField.addValueChangeListener(event -> {
                if (titleField.isEmpty() || consoleField.isEmpty() || genreField.isEmpty() || typeField.isEmpty()) {
                    submitButton.setEnabled(false);
                } else {
                    submitButton.setEnabled(true);
                }
            });

            editGameLayout.add(pageTitle, titleField, consoleField, genreField, typeField, submitButton, resetButton);
            editGameLayout.setMaxWidth("500px");
            editGameLayout.getStyle().set("margin","0 auto");
            editGameLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
            editGameLayout.setColspan(pageTitle, 2);
            add(editGameLayout);
        } else {
            UI.getCurrent().navigate("Login");
            UI.getCurrent().getPage().reload();
        }
    }

    private void editGame(String title, String console, String genre, String type) {
        if (Database.editGame(title, console, genre, type)) {
            Notification.show("Succesfully edited game in library!");
            UI.getCurrent().navigate("Library");
        } else {
            Notification.show("Something went wrong while connecting to the database, please try again.");
        }
    }
}