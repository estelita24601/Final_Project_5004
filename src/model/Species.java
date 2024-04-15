package model;

public enum Species {
    HUMAN("Human"),
    VULCAN("Vulcan"),
    KLINGON("Klingon"),
    ROMULAN("Romulan"),
    BAJORAN("Bajoran"),
    ANDORIAN("Andorian"),
    FERENGI("Ferengi"),
    TRILL("Trill"),
    BETAZOID("Betazoid"),
    BOLIAN("Bolian"),
    HOLOGRAM("Hologram"),
    OCAMPAN("Ocampan"),
    BORG("Borg");

    public final String name;

    Species(String str) {
        this.name = str;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
