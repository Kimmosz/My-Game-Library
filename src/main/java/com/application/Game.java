package com.application;

public class Game {
    private int Id;
    private String Title;
    private String Console;
    private String Genre;
    private String Type;

    public Game (int id, String title, String console, String genre, String type) {
        this.Id = id;
        this.Title = title;
        this.Console = console;
        this.Genre = genre;
        this.Type = type;
    }

    public int getID() {
        return this.Id;
    }

    public String getTitle() {
        return this.Title;
    } 
    public void setTitle(String value) {
        this.Title = value;
    }

    public String getConsole() {
        return this.Console;
    }
    public void setConsole(String value) {
        this.Console = value;
    }

    public String getGenre() {
        return this.Genre;
    }
    public void setGenre(String value) {
        this.Genre = value;
    }

    public String getType() {
        return this.Type;
    }
    public void setType(String value) {
        this.Type = value;
    }
}


