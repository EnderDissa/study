import ru.labs.lab3.*;

public class Story {

    Tree tree = new Tree();
    Bird bird = new Bird();
    Person skuperfield = new Person("Скуперфильд");
    Grass grass = new Grass();
    Flower flower = new Flower();
    Branch branch = new Branch(tree);

    public void run() {
        skuperfield.watch(tree);
        Wind.blow(tree);
        bird.move(branch);
        Grass.dazzle(flower);
        bird.peek();
    for (int i=0;i<5;i++){
        skuperfield.look(new Flower());
        }

        bird.move(skuperfield);

    }
}