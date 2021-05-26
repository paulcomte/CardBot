package fr.rqndomhax.cardbot.cards.monsters;

import fr.rqndomhax.cardbot.cards.Rarity;

public enum Dofus {
    DOFAWA("Dofawa", Rarity.COMMON, Type.ANOTHER_DOFUS),
    ABYSALL("Dofus Abysal", Rarity.MAIN_DOFUS, Type.MAIN_DOFUS),
    CAWOTTE("Dofus Cawotte", Rarity.UNCOMMON, Type.ANOTHER_DOFUS),
    ARGENTE("Dofus Argenté", Rarity.UNCOMMON, Type.ANOTHER_DOFUS),
    ;

    private final String name;
    private final Rarity rarity;
    private final Type type;

    Dofus(String name, Rarity rarity, Type type) {
        this.name = name;
        this.rarity = rarity;
        this.type = type;
    }

    public enum Type {
        ANOTHER_DOFUS,
        MAIN_DOFUS;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
