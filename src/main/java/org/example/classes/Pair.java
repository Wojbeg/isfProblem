package org.example.classes;

public class Pair<T, U> {
    public final T l;
    public final U r;

    public Pair(T l, U r) {
        this.l= l;
        this.r= r;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "l=" + l +
                ", r=" + r +
                '}';
    }


}