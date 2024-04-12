public enum Rotation {
    ALPHA("Alpha Shift"), BETA("Beta Shift"), GAMMA("Gamma Shift"), DELTA("Delta Shift");

    public final String name;

    Rotation(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}