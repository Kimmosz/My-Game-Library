package com.application;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PageTitle("Homepage")
@PWA(name = "My Game Library", shortName = "Game Library", description = "This is my game library.", enableInstallPrompt = false)
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    public MainView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("homepage");
        MenuBar.addMenuBar();
    }
}