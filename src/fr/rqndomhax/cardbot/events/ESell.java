package fr.rqndomhax.cardbot.events;

import fr.rqndomhax.cardbot.database.MUser;
import fr.rqndomhax.cardbot.database.requests.AuctionRequest;
import fr.rqndomhax.cardbot.database.requests.Requests;
import fr.rqndomhax.cardbot.utils.Auction;
import fr.rqndomhax.cardbot.utils.DateManager;
import fr.rqndomhax.cardbot.utils.Setup;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;
import java.util.Optional;

public class ESell extends ListenerAdapter {

    private final Setup setup;

    public ESell(Setup setup) {
        this.setup = setup;
    }

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getReaction().isSelf()) return;
        MUser user = this.setup.getmUserManager().getUser(Objects.requireNonNull(e.getUser()).getId());
        Optional<AuctionRequest> request = user.getRequests().stream().filter(i -> i.getRequestType() == Requests.AUCTION).findFirst().map(a -> ((AuctionRequest) a));
        if (!request.isPresent())
            return;
        if (!request.get().getMessageId().equalsIgnoreCase(e.getMessageId())) return;
        if (e.getReaction().getReactionEmote().getName().equalsIgnoreCase("❌")) {
            e.getChannel().sendMessage("auction canceled").queue();
            this.setup.getmUserManager().removeRequest(user.getId(), request.get());
            return;
        }
        if (!e.getReaction().getReactionEmote().getName().equalsIgnoreCase("✅")) return;
        if (request.get().getCard() == null) {
            e.getChannel().sendMessage("card not available").queue();
            this.setup.getmUserManager().removeRequest(user.getId(), request.get());
            return;
        }
        request.get().getCard().setOwnerId("-1");
        Auction auction = new Auction(0, request.get().getCard(), request.get().getPrice(), "0", String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis() + ((request.get().getTime() * 60) * 1000)));
        this.setup.getmAuctionManager().addAuction(e.getUserId(), auction);
        this.setup.getmUserManager().removeRequest(e.getUserId(), request.get());
        this.setup.getCardManager().updateCard(request.get().getCard());
        e.getChannel().sendMessage("la carte vient d'être ajouté à l'hotel des ventes pendant " + new DateManager((long) (request.get().getTime() * 60)).getDuration()).queue();
    }

}
