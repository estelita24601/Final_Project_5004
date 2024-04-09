package model.personnel;

public enum Species {
HUMAN, VULCAN, KLINGON, ROMULAN, BAJORAN, ANDORIAN, FERENGI, TRILL, BETAZOID;

    @Override
    public String toString() {
        switch (this) {
            case HUMAN:return "Human";
            case VULCAN:return "Vulcan";
            case KLINGON:return "Klingon";
            case ROMULAN:return "Romulan";
            case BAJORAN:return "Bajoran";
            case ANDORIAN:return "Andorian";
            case FERENGI:return "Ferengi";
            case TRILL:return "Trill";
            case BETAZOID:return "Betazoid";
            default: return "Unknown Species";
        }
    }
}
