public enum Department {
    ENGINEERING("Engineering"),
    BRIDGE("Bridge"),
    SECURITY("Security"),
    SCIENCE("Science"),
    MEDICAL_BAY("Medical Bay");

    public final String name;

    Department(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
