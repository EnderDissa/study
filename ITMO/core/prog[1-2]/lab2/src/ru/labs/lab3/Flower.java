package ru.labs.lab3;

public class Flower implements Admirable{
    final Color color;
    public Flower() {
        if (Math.random()<0.5) {
            this.color = Color.PINK;
        } else {
            this.color = Color.BLUE;
        }
        Grass.flowers.add(this);
    }
    public Flower(Color color) {
        this.color = color;
        Grass.flowers.add(this);
    }

    @Override
    public void admire(Person person) {
        person.changeMood(10);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        return this.color==((Flower) o).color;
    }
    @Override
    public String toString(){
        return "цветок "+color.toString()+" цвета";
    }
    @Override
    public int hashCode() {
        return super.hashCode()+color.hashCode();
    }

    }
