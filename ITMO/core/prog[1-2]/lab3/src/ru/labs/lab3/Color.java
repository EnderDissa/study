package ru.labs.lab3;

public enum Color {
    GREEN("зелёные"),
    PINK("розового"),
    BLUE("нежно-голубого");
    private final String string;
    Color(String title){string=title;}
    @Override
    public String toString(){
        return string;
    }
}
