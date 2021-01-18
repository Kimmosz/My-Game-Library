package com.application;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

@CssImport("./styles/styles.css")
public class Header {

    public static VerticalLayout addMenuBar() {
        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        H1 header = new H1("My Game Library");
        header.getElement().getThemeList();
        
        MenuBar menuBar = new MenuBar();
        menuBar.addClassName("menubar");
        menuBar.addThemeVariants(MenuBarVariant.LUMO_CONTRAST);
        menuBar.setOpenOnHover(true);

        MenuItem homepage = menuBar.addItem("Home");
        homepage.addComponentAsFirst(new Icon(VaadinIcon.HOME));
        homepage.addClickListener(event -> homepage.getUI().ifPresent(ui -> ui.navigate("")));

        // If logged in
        MenuItem library = menuBar.addItem("Library");
        library.addComponentAsFirst(new Icon(VaadinIcon.FOLDER_OPEN));
        library.addClickListener(event -> library.getUI().ifPresent(ui -> ui.navigate("library")));
        
        SubMenu librarySubMenu = library.getSubMenu();
        MenuItem addGame = librarySubMenu.addItem("Add game to library");
        addGame.addClickListener(event ->  addGame.getUI().ifPresent(ui -> ui.navigate("AddGame")));
        
        // If not logged in
        MenuItem login = menuBar.addItem("Login");
        login.addComponentAsFirst(new Icon(VaadinIcon.USER));
        login.addClickListener(event -> library.getUI().ifPresent(ui -> ui.navigate("Login")));
        
        SubMenu loginSubMenu = login.getSubMenu();
        MenuItem register = loginSubMenu.addItem("Register");
        register.addClickListener(event ->  register.getUI().ifPresent(ui -> ui.navigate("Register")));

        // If logged in
        MenuItem logout = menuBar.addItem("Logout");
        logout.addComponentAsFirst(new Icon(VaadinIcon.USER));
        logout.addClickListener(event -> library.getUI().ifPresent(ui -> {
            ui.navigate("");
            // Logout
            Notification.show("Succesfully logged out");
        }));
        
        layout.add(header, menuBar);

        return layout;
    }
}
