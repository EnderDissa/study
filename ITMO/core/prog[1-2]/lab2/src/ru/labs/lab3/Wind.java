package ru.labs.lab3;

public class Wind {
    public static void blow(Tree tree) {
        System.out.println("ветер налетал порывами");
        tree.wave();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
    @Override
    public String toString(){
        return super.toString()+"\nwind";
    }
    @Override
    public int hashCode() {
        return super.hashCode()+11;
    }
}
