package ru.labs.lab3;

import java.util.ArrayList;

public class Tree implements Admirable, Visible{

    private ArrayList<Person> observers= new ArrayList<>();
    protected ArrayList<Branch> branches= new ArrayList<>();
    @Override
    public void admire(Person person) {
        person.changeMood(2);
    }

    @Override
    public void beWatched(Person person) {
        if (observers.indexOf(person) == -1) {
            observers.add(person);
        }
    }

    @Override
    public void beNoMoreWatched(Person person) {
        int index=observers.indexOf(person);
        if(index!=-1) {
            observers.remove(index);
        }
    }
    void wave(){
        System.out.println("и шевелил на нём листочки");
        for (Person person: observers){
            person.changeMood(10);
        }
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tree tree = (Tree) o;
        return this.branches.equals(tree.branches) && this.observers.equals(tree.observers);
    }
    @Override
    public String toString(){
        return "берёза";
    }
    @Override
    public int hashCode() {
        return super.hashCode()+ 145;
    }
}