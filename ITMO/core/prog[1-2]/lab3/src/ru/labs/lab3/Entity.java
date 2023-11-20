package ru.labs.lab3;

public abstract class Entity {
    Location location;
    void move(Location location){
        this.location=location;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        return this.location.equals(((Entity) o).location);
    }
    @Override
    public String toString(){
        return super.toString()+"\nlocation:"+location.toString();
    }
    @Override
    public int hashCode() {
        return super.hashCode()+location.hashCode();
    }
}
