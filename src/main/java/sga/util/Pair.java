package sga.util;

public record Pair<X, Y> (X first, Y second) {
    public X x() { return first; }
    public Y y() { return second; }
}