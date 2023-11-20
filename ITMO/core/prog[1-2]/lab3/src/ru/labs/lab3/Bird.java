package ru.labs.lab3;

public class Bird extends Entity implements Admirable{

    @Override
    public void move(Location location){
        super.move(location);
        if(Math.random()<0.5){
            System.out.println("птичка задела "+location.toString()+" краем крыла");
            location.beTouched();
        }
    }
    public void peek(){
        System.out.println("птички наполняли воздух чириканьем");
    }

    @Override
    public void admire(Person person) {
        person.changeMood(5);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }
    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode()+1;
    }
}
