package ru.labs.lab3;

public class Branch implements Location{
    static final Color color=Color.GREEN;
    Tree tree;
    public Branch(Tree tree){
        tree.branches.add(this);
        this.tree = tree;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Branch branch = (Branch) o;
        return this.tree.equals(branch.tree) && this.tree.branches.indexOf(this) == this.tree.branches.indexOf(branch);
    }

    @Override
    public String toString() {
        return color + " ветки дерева \""+tree.toString()+"\"";
    }
    @Override
    public int hashCode() {
        return super.hashCode()+tree.hashCode();
    }
}
