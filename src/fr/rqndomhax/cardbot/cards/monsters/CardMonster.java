package fr.rqndomhax.cardbot.cards.monsters;

import fr.rqndomhax.cardbot.database.Card;

public class CardMonster extends Card {

    private final Monsters monsters;
    private int attack;
    private int defense;

    public CardMonster(Card card, Monsters monster, int attack, int defense) {
        super(card.getCardType(), monster.getName(), card.getCardBackground(), card.getOwnerId(), card.getId(), card.getCoins(), card.getTimestamp());
        this.monsters = monster;
        this.attack = attack;
        this.defense = defense;
    }

    public Monsters getMonsters() {
        return monsters;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
