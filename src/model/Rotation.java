package model;

public enum Rotation {
    ALPHA("Alpha model.Shift"), BETA("Beta model.Shift"), GAMMA("Gamma model.Shift"), DELTA("Delta model.Shift");

    public final String name;

    Rotation(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}