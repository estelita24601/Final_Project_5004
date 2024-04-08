package model.scheduling;

public enum Department {
    ENGINEERING, BRIDGE, SECURITY, SCIENCE, MEDICAL_BAY;

    @Override
    public String toString() {
        String output = null;
        switch (this) {
            case ENGINEERING -> {
                output = "Engineering";
            }
            case BRIDGE -> {
                output = "Bridge";
            }
            case SECURITY -> {
                output = "Security";
            }
            case SCIENCE -> {
                output = "Science Department";
            }
            case MEDICAL_BAY -> {
                output = "Medical Bay";
            }default -> {
                throw new IllegalStateException("Invalid value for department enum");
            }
        }

        return output;
    }


}
