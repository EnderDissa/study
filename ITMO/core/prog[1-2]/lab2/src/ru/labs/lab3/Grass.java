package ru.labs.lab3;

import java.util.ArrayList;

public class Grass implements Admirable,Location{
    static ArrayList<Flower> flowers= new ArrayList<>();
    public static void dazzle(Flower flower){
        System.out.println("в траве пестрел "+flower.toString()+ " ");
    }
    @Override
    public void admire(Person person) {
        person.changeMood(1);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (super.equals(o)) {
            return true;
        }
        return false;
    }
    @Override
    public String toString(){
        return super.toString()+flowers.toString();
    }
    @Override
    public int hashCode() {
        return super.hashCode()+flowers.hashCode();
    }
}
