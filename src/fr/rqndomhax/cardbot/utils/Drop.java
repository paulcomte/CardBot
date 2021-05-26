package fr.rqndomhax.cardbot.utils;

import fr.rqndomhax.cardbot.cards.Backgrounds;
import fr.rqndomhax.cardbot.cards.CardType;
import fr.rqndomhax.cardbot.cards.Rarity;
import fr.rqndomhax.cardbot.cards.monsters.CardMonster;
import fr.rqndomhax.cardbot.cards.monsters.Monsters;
import fr.rqndomhax.cardbot.database.Card;
import fr.rqndomhax.cardbot.database.CardManager;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class Drop {

    public final HashSet<CardMonster> drops = new HashSet<>();
    private final CardManager cardManager;
    private final String ownerId;


    public Drop(CardManager cardManager, String ownerId) {
        this.cardManager = cardManager;
        this.ownerId = ownerId;
        for (int i = 0 ; i < 3 ; i++)
            newMonster();
    }

    public double getMessage() {

        return 0;
    }

    public double getAttack() {
        // TODO - Random attack
        return 0;
    }

    public double getDefense() {
        // TODO - Random defense
        return 0;
    }

    private void newMonster() {
        /*Backgrounds cardBackground = Backgrounds.ENUTROF;
        Rarity ra = getRarity();
        Monsters monster = getMonster(ra);
        int attack = getAttack(monster);
        int defense = getDefense(monster);
        double coins = getCoins(monster, attack, defense);*/
        CardMonster cardMonster = new CardMonster(new Card(CardType.MONSTER, Monsters.AKAKWA.getName(), Backgrounds.ECAFLIP, ownerId, UUID.randomUUID().toString().substring(0, 8),30000, String.valueOf(System.currentTimeMillis())), Monsters.AKAKWA,  20, 20);
        drops.add(cardMonster);
        cardManager.initMonster(cardMonster);
    }

    private Rarity getRarity() {
        int ra = (int) (Math.random() * 1000);
        Rarity rarity = null;
        for (Rarity raritys : Rarity.values()) {
            if (raritys.getDropRate() * 1000 > ra)
                continue;
            rarity = raritys;
        }
        if (rarity == null)
            return Rarity.COMMON;
        return rarity;
    }

    private Monsters getMonster(Rarity rarity) {
        Random r = new Random();
        int ra = r.nextInt(Monsters.values().length);
        for (int i = ra ; i > 0 ; i--) {
            if (Monsters.values()[i].getRarity() == rarity)
                return (Monsters.values()[i]);
        }
        for (int i = ra ; i < Monsters.values().length ; i++) {
            if (Monsters.values()[i].getRarity() == rarity)
                return (Monsters.values()[i]);
        }
        return Monsters.ALHYENE;
    }

    private int getAttack(Monsters monsters) {
        Random r = new Random();
        return r.nextInt(monsters.getStrength().getMax() - monsters.getStrength().getMin() + 1) + monsters.getStrength().getMin();
    }

    private int getDefense(Monsters monsters) {
        Random r = new Random();
        return r.nextInt(monsters.getResistance().getMax() - monsters.getResistance().getMin() + 1) + monsters.getResistance().getMin();
    }

    private double getCoins(Monsters monsters, int attack, int defense) {
        return 2;
    }

    public HashSet<CardMonster> getDrops() {
        return drops;
    }
}
