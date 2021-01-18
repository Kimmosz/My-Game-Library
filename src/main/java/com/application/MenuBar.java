package com.application;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/styles.css")
public class MenuBar extends VerticalLayout {
    public static void addMenuBar() {
        H1 header = new H1("My Game Library");
        header.getElement().getThemeList();
    }
}
