package fr.rqndomhax.cardbot.events;

import fr.rqndomhax.cardbot.database.MUser;
import fr.rqndomhax.cardbot.database.requests.Requests;
import fr.rqndomhax.cardbot.database.requests.TradeRequest;
import fr.rqndomhax.cardbot.database.trades.States;
import fr.rqndomhax.cardbot.utils.Setup;
import fr.rqndomhax.cardbot.utils.TradeMessage;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Optional;

public class ETradeMessage extends ListenerAdapter {

    private final Setup setup;

    public ETradeMessage(Setup setup) {
        this.setup = setup;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String[] args = e.getMessage().getContentRaw().split("\\s+");

        MUser owner = this.setup.getmUserManager().getUserTrade(e.getAuthor().getId());
        MUser current = this.setup.getmUserManager().getUser(e.getAuthor().getId());
        if (owner == null)
            return;
        if (current == null)
            return;
        Optional<TradeRequest> request = owner.getRequests().stream().filter(i -> i.getRequestType() == Requests.TRADE).findFirst().map(a -> ((TradeRequest) a));
        if (!request.isPresent())
            return;
        if (request.get().getState() != States.WAITING)
            return;
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("coins")) {
                checkCoins(args, e, request.get(), current);
                return;
            }
        }
    }

    private void checkCoins(String[] args, GuildMessageReceivedEvent e, TradeRequest request, MUser current) {
        double coins;
        try {
            coins = Double.parseDouble(args[0]);
        } catch (NumberFormatException exception) {
            e.getChannel().sendMessage("Vous avez entré un nombre de `coins` incorrect !" + e.getAuthor().getAsMention()).queue();
            return;
        }
        if (coins < 0) {
            e.getChannel().sendMessage("Vous avez entré un nombre de `coins` incorrect !" + e.getAuthor().getAsMention()).queue();
            return;
        }
        if (current.getCoins() < coins) {
            e.getChannel().sendMessage("Vous ne possédez pas `" + coins + "` `coins`").queue();
            return;
        }
        request.getUserInventoryFromUser(current).setCoins(coins);
        new TradeMessage().updateMessage(request, e);
        this.setup.getmUserManager().updateRequest(request.getUser1Inventory().getInventoryOwnerId(), request);
    }

}