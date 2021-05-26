package fr.rqndomhax.cardbot.cards;

public enum CardType {
    MONSTER("Monstre"),
    HERO("Héro"),
    DOFUS("Dofus"),
    ITEM("Objet");

    private final String typeName;

    CardType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
