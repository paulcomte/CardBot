package fr.rqndomhax.cardbot.cards.monsters;

import fr.rqndomhax.cardbot.cards.Rarity;
import fr.rqndomhax.cardbot.cards.Resistance;
import fr.rqndomhax.cardbot.cards.Strengths;

public enum UniqueMonsters {
    ADAGRA_FISTE("Adagra fiste", Rarity.RARE, Strengths.GOOD, Resistance.GOOD,"https://static.ankama.com/dofus/www/game/monsters/200/5007.w200h.png"),
    AERMYNE__BRACO__SCALPTARAS("Aermyne 'braco' scalptaras", Rarity.RARE, Strengths.VERY_LOW, Resistance.AVERAGE, "https://static.ankama.com/dofus/www/game/monsters/200/446.w200h.png"),
    AFREZ("Afrèz", Rarity.RARE, Strengths.VERY_LOW, Resistance.WEAK,"https://static.ankama.com/dofus/www/game/monsters/200/4895.w200h.png"),
    AGONIE_LA_DETERREE("Agonie la déterrée", Rarity.EPIC, Strengths.VERY_LOW, Resistance.VERY_GOOD,"https://static.ankama.com/dofus/www/game/monsters/200/5684.w200h.png"),
    ALI_BABAOROM("Ali babaorom", Rarity.RARE, Strengths.LOW, Resistance.LOW,"https://static.ankama.com/dofus/www/game/monsters/200/4667.w200h.png"),
    ALI_GROTHOR("Ali grothor", Rarity.RARE, Strengths.AVERAGE, Resistance.AVERAGE, "https://static.ankama.com/dofus/www/game/monsters/200/3524.w200h.png"),
    AMBI_GUMAN("Ambi guman", Rarity.UNCOMMON, Strengths.VERY_LOW, Resistance.VERY_LOW,  "https://static.ankama.com/dofus/www/game/monsters/200/3525.w200h.png"),
    AMY_L_EMPOISONNEUSE("Amy l'empoisonneuse", Rarity.RARE, Strengths.AVERAGE, Resistance.LOW, "https://static.ankama.com/dofus/www/game/monsters/200/3528.w200h.png"),
    ANATAK_DISKEDOR("Anatak diskedor", Rarity.RARE, Strengths.GOOD, Resistance.AVERAGE,"https://static.ankama.com/dofus/www/game/monsters/200/4017.w200h.png"),
    BILL_IKID("Bill ikid", Rarity.RARE, Strengths.LOW, Resistance.LOW, "https://static.ankama.com/dofus/www/game/monsters/200/4669.w200h.png"),
    BOSKO_THO("Bosko tho", Rarity.EPIC, Strengths.VERY_STRONG, Resistance.STRONG, "https://static.ankama.com/dofus/www/game/monsters/200/3539.w200h.png"),
    BOT("Bot", Rarity.COMMON, Strengths.VERY_WEAK, Resistance.VERY_WEAK, "https://static.ankama.com/dofus/www/game/monsters/200/5930.w200h.png"),
    ;







    private final String name;
    private final Rarity rarity;
    private final Strengths strength;
    private final Resistance resistance;
    private final String imageURL;

    UniqueMonsters(String name, Rarity rarity, Strengths strength, Resistance resistance, String imageURL) {
        this.name = name;
        this.rarity = rarity;
        this.strength = strength;
        this.resistance = resistance;
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

    public String getImageURL() {
        return imageURL;
    }



}