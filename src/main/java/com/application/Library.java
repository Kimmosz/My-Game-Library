package com.application;

import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Library")
@PageTitle("Library")
@CssImport("./styles/styles.css")
public class Library extends VerticalLayout {

    public Library() {
        if (SessionAttributes.getLoggedUser() != null) {
            SessionAttributes.deselectGame();
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            setSizeFull();
            addClassName("library");
            add(Header.addMenuBar());

            H2 pageTitle = new H2("Library");   
            add(pageTitle);
            fillLibrary();
        } else {
            UI.getCurrent().navigate("Login");
            UI.getCurrent().getPage().reload();
        }
    }

    private void fillLibrary() {
        List<Game> gamesList = Database.getGames();
        Grid<Game> gamesGrid = new Grid<>(Game.class);
        if (gamesList.isEmpty()) {

        } else {
            gamesGrid.setItems(gamesList);
            gamesGrid.setColumns("ID","title","console","genre","type");
            gamesGrid.removeColumnByKey("ID");

           Button editButton = new Button("Edit");
           Button deleteButton = new Button("Delete");
           

            gamesGrid.asSingleSelect().addValueChangeListener(select -> {
                SessionAttributes.selectGame(select.getValue().getID());
                if (select.getValue() != null) {
                    editButton.addClickListener(edit -> {
                        editButton.getUI().ifPresent(ui -> ui.navigate("EditGame"));
                    });
                    deleteButton.addClickListener(delete -> deleteFromLibrary(gamesGrid, editButton, deleteButton));
                    add(editButton, deleteButton);
                } else {
                    remove(editButton, deleteButton);
                }

            });
            
            add(gamesGrid);
        }
    }

    private void deleteFromLibrary(Grid<Game> games, Button edit, Button delete) {
        if (Database.deleteGame()) {
            Notification.show("Succesfully deleted");
            remove(games, edit, delete);
            fillLibrary();
        } else {
            Notification.show("Something went wrong while connecting to the database, please try again.");
        }
    }
}