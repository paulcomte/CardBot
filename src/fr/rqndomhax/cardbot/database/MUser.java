package fr.rqndomhax.cardbot.database;

import fr.rqndomhax.cardbot.database.requests.Request;
import fr.rqndomhax.cardbot.utils.DateManager;

import java.util.Set;

public class MUser {

    private final String id;
    private double coins;
    private String lastDrop;
    private String createdDate;
    private final Set<Request> requests;

    public MUser(String id, double coins, String lastDrop, String createdDate, Set<Request> requests) {
        this.id = id;
        this.coins = coins;
        this.lastDrop = lastDrop;
        this.createdDate = createdDate;
        this.requests = requests;
    }

    public String getId() {
        return id;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public String getLastDrop() {
        return lastDrop;
    }

    public void setLastDrop(String lastDrop) {
        this.lastDrop = lastDrop;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public boolean canDrop() {
        return (Long.parseLong(lastDrop) + 30*60*1000) < System.currentTimeMillis();
    }

    public String getNextDrop() {
        return new DateManager(Long.parseLong(lastDrop) + 30*60*1000).getTimeLeft();
    }
}