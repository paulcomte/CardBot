package fr.rqndomhax.cardbot.events;

import fr.rqndomhax.cardbot.database.MUser;
import fr.rqndomhax.cardbot.database.requests.Requests;
import fr.rqndomhax.cardbot.database.requests.TradeRequest;
import fr.rqndomhax.cardbot.database.trades.States;
import fr.rqndomhax.cardbot.utils.Setup;
import fr.rqndomhax.cardbot.utils.TradeMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Optional;

public class ETrade extends ListenerAdapter {

    private final Setup setup;

    public ETrade(Setup setup) {
        this.setup = setup;
    }

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getReaction().isSelf()) return;
        MUser owner = this.setup.getmUserManager().getTrade(e.getMessageId());
        if (owner == null)
            return;
        Optional<TradeRequest> request = owner.getRequests().stream().filter(i -> i.getRequestType() == Requests.TRADE).findFirst().map(a -> ((TradeRequest) a));
        if (!request.isPresent())
            return;
        if (!request.get().getUser2Inventory().getInventoryOwnerId().equalsIgnoreCase(e.getUserId())
                && !request.get().getUser1Inventory().getInventoryOwnerId().equalsIgnoreCase(e.getUserId()))
            return;
        if (e.getReaction().getReactionEmote().getName().equalsIgnoreCase("❌")) {
            showCanceled(e, request.get());
            return;
        }
        if (!e.getReaction().getReactionEmote().getName().equalsIgnoreCase("✅")) return;
        checkReaction(request.get(), e);
    }

    private void checkReaction(TradeRequest request, MessageReactionAddEvent e) {
        if (request.getState() == States.OFFER && !e.getUserId().equalsIgnoreCase(request.getUser1Inventory().getInventoryOwnerId())) {
            request.setState(States.WAITING);
            new TradeMessage().updateMessage(request, e);
            this.setup.getmUserManager().updateRequest(request.getUser1Inventory().getInventoryOwnerId(), request);
            return;
        }
    }

    private void showCanceled(MessageReactionAddEvent e, TradeRequest request) {
        e.getChannel().editMessageById(request.getMessageId(),
                new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Échange annulé")
                        .addField("", "La demande d'échange a été refusée", true)
                        .build()).queue();
        this.setup.getmUserManager().removeRequest(request.getUser1Inventory().getInventoryOwnerId(), request);
    }

}
