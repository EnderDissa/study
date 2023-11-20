package ru.labs.lab3;

public class Person extends Entity implements Location {
    //public Person(){};
    public Person(String name) {
        this.name = name;
        this.gender = Gender.MALE;
    }

    public Person(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    final String name;
    final Gender gender;
    double admiration = 0;
    boolean isMotionless = true;
    Visible lookingAt = null;

    public void watch(Visible object){
        System.out.println(name+" смотрит на "+object.toString());
        if (this.lookingAt != null) {
            lookingAt.beNoMoreWatched(this);
        }
        if (object != null) {
            object.beWatched(this);
        }
    }

    public void look(Admirable object) {
        System.out.println(name+" взглянул на "+object.toString());

        object.admire(this);
    }

    @Override
    void move(Location location) {
        if (location.getClass().getSimpleName() != "Person") {
            if (location != this.location) {
                this.location = location;
            }
            this.isMotionless = false;
        }
    }

    void freeze() {
        this.isMotionless = true;
    }

    void cry() {
        if (this.gender == Gender.MALE) {
            System.out.println("\n"+name + " заплакал..."+"\n");
        } else {
            System.out.println(name + " заплакала...");
        }
    }

    void changeMood(double changes) {
        this.admiration += changes;
        System.out.println("это умилило "+name);
        if (this.admiration < 0) {
            this.admiration = 0;
        }
        if (this.admiration >= 100) {
            this.cry();
            this.admiration = 0;

        }
    }

    @Override
    public void beTouched() {
        changeMood(70);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return this.name.equals(person.name) && this.admiration == person.admiration && this.isMotionless == person.isMotionless && this.lookingAt.equals(person.lookingAt);
    }
    @Override
    public String toString(){
        return name;
    }
    @Override
    public int hashCode() {
        return super.hashCode()+name.hashCode()+gender.hashCode()+((int)admiration)+((Boolean)isMotionless).hashCode()+lookingAt.hashCode();
    }
}