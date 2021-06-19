package main.java;

public class Var{
    String name;
    Object value;

    public Var(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Var{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
