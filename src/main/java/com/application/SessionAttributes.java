package com.application;

import com.vaadin.flow.server.VaadinService;

public class SessionAttributes {
    public static void login(String loggedUser) {
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedUser", loggedUser);  
    }

    public static void logout() {
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggedUser", null);
    }

    public static String getLoggedUser() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedUser") == null) {
            return null;
        } else {
            return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("loggedUser").toString();
        }
    }

    public static void selectGame(int selectedID) {
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("selectedGame", selectedID);  
    }

    public static void deselectGame() {
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("selectedGame", null);  
    }

    public static int getSelectedGame() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("selectedGame") == null) {
            return 0;
        } else {
            return Integer.parseInt(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("selectedGame").toString());
        }
    }
}
