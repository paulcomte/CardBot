package fr.rqndomhax.cardbot.events;

import fr.rqndomhax.cardbot.database.MUser;
import fr.rqndomhax.cardbot.database.requests.BurnRequest;
import fr.rqndomhax.cardbot.database.requests.Requests;
import fr.rqndomhax.cardbot.utils.Setup;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;
import java.util.Optional;

public class EWipe extends ListenerAdapter {

    private final Setup setup;

    public EWipe(Setup setup) {
        this.setup = setup;
    }

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getReaction().isSelf()) return;
        MUser user = this.setup.getmUserManager().getUser(Objects.requireNonNull(e.getUser()).getId());
        Optional<BurnRequest> request = user.getRequests().stream().filter(i -> i.getRequestType() == Requests.BURN).findFirst().map(a -> ((BurnRequest) a));
        if (!request.isPresent())
            return;
        if (!request.get().getBurnMessage().equalsIgnoreCase(e.getMessageId())) return;
        if (e.getReaction().getReactionEmote().getName().equalsIgnoreCase("❌")) {
            e.getChannel().sendMessage("wipe canceled").queue();
            this.setup.getmUserManager().removeRequest(user.getId(), request.get());
            return;
        }
        if (!e.getReaction().getReactionEmote().getName().equalsIgnoreCase("✅")) return;
        if (request.get().getBurnCard() == null) {
            e.getChannel().sendMessage("card not available").queue();
            this.setup.getmUserManager().removeRequest(user.getId(), request.get());
            return;
        }
        this.setup.getmUserManager().updateCoins(user.getId(), user.getCoins() + request.get().getBurnCard().getCoins());
        e.getChannel().sendMessage(("card has been burned, you earned " + request.get().getBurnCard().getCoins() + " coins")).queue();
        this.setup.getCardManager().deleteById(request.get().getBurnCard().getId());
        this.setup.getmUserManager().removeRequest(user.getId(), request.get());
    }
}
