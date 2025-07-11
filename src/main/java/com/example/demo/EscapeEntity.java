package com.example.demo;

import java.util.Objects;

public class EscapeEntity {
    int year;
    String name;
    boolean gameIsOut;

    public EscapeEntity(int val, String name, boolean bool){
        this.year = val;
        this.name = name;
        gameIsOut = bool;
    }

    public boolean isCorrectValues(){
        if(year == 2024 && Objects.equals(name, "Franci") && gameIsOut) {
            return true;
        }else
            return false;
    }
}
