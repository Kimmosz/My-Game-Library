package com.application;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
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

        if (SessionAttributes.getLoggedUser() == null || SessionAttributes.getLoggedUser() == "") {
            MenuItem login = menuBar.addItem("Login");
            login.addComponentAsFirst(new Icon(VaadinIcon.USER));
            login.addClickListener(event -> login.getUI().ifPresent(ui -> ui.navigate("Login")));
            
            SubMenu loginSubMenu = login.getSubMenu();
            MenuItem register = loginSubMenu.addItem("Register");
            register.addClickListener(event ->  register.getUI().ifPresent(ui -> ui.navigate("Register")));
        } else {
            MenuItem library = menuBar.addItem("Library");
            library.addComponentAsFirst(new Icon(VaadinIcon.FOLDER_OPEN));
            library.addClickListener(event -> library.getUI().ifPresent(ui -> ui.navigate("Library")));
            
            SubMenu librarySubMenu = library.getSubMenu();
            MenuItem addGame = librarySubMenu.addItem("Add game to library");
            addGame.addClickListener(event ->  addGame.getUI().ifPresent(ui -> ui.navigate("AddGame")));

            MenuItem logout = menuBar.addItem("Logout");
            logout.addComponentAsFirst(new Icon(VaadinIcon.USER));
            logout.addClickListener(event -> logout.getUI().ifPresent(ui -> {
                SessionAttributes.logout();
                UI.getCurrent().navigate("");
                UI.getCurrent().getPage().reload();
            }));
        }

        layout.add(header, menuBar);

        return layout;
    }
}
