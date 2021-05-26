package fr.rqndomhax.cardbot.utils;

import fr.rqndomhax.cardbot.Token;
import fr.rqndomhax.cardbot.commands.*;
import fr.rqndomhax.cardbot.database.*;
import fr.rqndomhax.cardbot.events.ESell;
import fr.rqndomhax.cardbot.events.ETrade;
import fr.rqndomhax.cardbot.events.ETradeMessage;
import fr.rqndomhax.cardbot.events.EWipe;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class Setup {

    private JDA jda;
    private final String prefix = "m";
    private MongoDB mongoDB;
    private CardManager cardManager;
    private MUserManager mUserManager;
    private MAuctionManager mAuctionManager;

    // Register Events
    private void getEvents() {
        // Discord.jda
        jda.addEventListener(new EWipe(this));
        jda.addEventListener(new ESell(this));
        jda.addEventListener(new ETrade(this));
        jda.addEventListener(new ETradeMessage(this));
    }

    //Register Commands
    private void getCommands() {

        // TODO - Système d'avis /idée - /advice [msg]
        // TODO - Help system

        jda.addEventListener(new CDrop(this));
        jda.addEventListener(new CWipe(this));
        jda.addEventListener(new CView(this));
        jda.addEventListener(new CInventory(this));
        jda.addEventListener(new CList(this));
        jda.addEventListener(new CSell(this));
        jda.addEventListener(new CTrade(this));
    }

    // Custom status
    private Activity getCustomStatus() {
        return Activity.playing("mhelp for cmds");
    }

    // Register all
    public void setup() throws LoginException, InterruptedException {
        System.out.println("---> Initializing database <---");
        mongoDB = new MongoDB("mongodb://dev-test:U60ffvaHMwFWfA41@cluster0-shard-00-00.gzimr.mongodb.net:27017,cluster0-shard-00-01.gzimr.mongodb.net:27017,cluster0-shard-00-02.gzimr.mongodb.net:27017/bot?ssl=true&replicaSet=atlas-134dfz-shard-0&authSource=admin&retryWrites=true&w=majority&readPreference=primary&appname=MongoDB%20Compass&retryWrites=true&ssl=true");
        cardManager = new CardManager(this);
        mUserManager = new MUserManager(this);
        mAuctionManager = new MAuctionManager(this);
        System.out.println("Database = OK");
        System.out.println("---> Intializing connection <---");
        jda = JDABuilder.create(new Token().getToken(), EnumSet.allOf(GatewayIntent.class))
                .setActivity(getCustomStatus())
                .build();
        System.out.println("Connection = OK");
        System.out.println("Initializing Commands");
        getCommands();
        getEvents();
        System.out.println("Commands = OK");
        System.out.println("Finalizing last tasks");
        jda.awaitReady();
        System.out.println("Last tasks = OK");
        System.out.println("MedKorpDrop[ON]");
    }

    public MongoDB getMongoDB() {
        return mongoDB;
    }

    public String getPrefix() {
        return prefix;
    }

    public CardManager getCardManager() {
        return cardManager;
    }

    public MUserManager getmUserManager() {
        return mUserManager;
    }

    public MAuctionManager getmAuctionManager() {
        return mAuctionManager;
    }
}