package fr.rqndomhax.cardbot.cards.monsters;

import fr.rqndomhax.cardbot.cards.Rarity;
import fr.rqndomhax.cardbot.cards.Resistance;
import fr.rqndomhax.cardbot.cards.Strengths;

public enum EventMonsters {
    ABOMINABLE_YITI_DES_NEIGES("Abominable yiti des neiges", Rarity.UNCOMMON, Strengths.LOW, Resistance.LOW, Type.NOWEL, "https://static.ankama.com/dofus/www/game/monsters/200/839.w200h.png"),
    BALLOTIN_LE_BOUFTOU("Ballotin le bouftou", Rarity.COMMON, Strengths.WEAK, Resistance.WEAK, Type.BALLOTIN, "https://static.ankama.com/dofus/www/game/monsters/200/1232.w200h.png"),
    BEBE_CADOB("Bébé cadob", Rarity.COMMON, Strengths.VERY_WEAK, Resistance.VERY_WEAK, Type.NOWEL, "https://static.ankama.com/dofus/www/game/monsters/200/890.w200h.png"),
    BONHOMME_DE_NEIGE("Bonhomme de neige", Rarity.COMMON, Strengths.AVERAGE, Resistance.AVERAGE, Type.NOWEL, "https://static.ankama.com/dofus/www/game/monsters/200/845.w200h.png"),
    BOUBOULE_DE_NEIGE("Bouboule de neige", Rarity.COMMON, Strengths.AVERAGE, Resistance.AVERAGE, Type.NOWEL, "https://static.ankama.com/dofus/www/game/monsters/200/867.w200h.png"),
    ;



    private final String name;
    private final Rarity rarity;
    private final Strengths strength;
    private final Resistance resistance;
    private final Type type;
    private final String imageURL;

    EventMonsters(String name, Rarity rarity, Strengths strength, Resistance resistance, Type type, String imageURL) {
        this.name = name;
        this.rarity = rarity;
        this.strength = strength;
        this.resistance = resistance;
        this.type = type;
        this.imageURL = imageURL;
    }

    public Resistance getResistance() {
        return resistance;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Strengths getStrength() {
        return strength;
    }

    public Type getType() {
        return type;
    }

    public String getImageURL() {
        return imageURL;
    }

    public enum Type {
        HALLOUINE("Hallouine"),
        NOWEL("Nowel"),
        BALLOTIN("Ballotin");

        private final String eventName;

        Type(String eventName) {
            this.eventName = eventName;
        }

        public String getEventName() {
            return eventName;
        }
    }
}