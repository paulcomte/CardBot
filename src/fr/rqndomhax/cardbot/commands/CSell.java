package fr.rqndomhax.cardbot.commands;

import fr.rqndomhax.cardbot.database.Card;
import fr.rqndomhax.cardbot.database.MAuction;
import fr.rqndomhax.cardbot.database.requests.AuctionRequest;
import fr.rqndomhax.cardbot.utils.Setup;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CSell extends ListenerAdapter {
    private final Setup setup;

    public CSell(Setup setup) {
        this.setup = setup;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String[] args = e.getMessage().getContentRaw().split("\\s+");

        if (!args[0].equalsIgnoreCase(setup.getPrefix() + "sell") && !args[0].equalsIgnoreCase(setup.getPrefix() + "s"))
            return;
        if (args.length != 4) {
            e.getMessage().getChannel().sendMessage("Veuillez respecter la syntaxe suivante : `msell <card_id> <starting_value> <min_length>` " + e.getAuthor().getAsMention()).queue();
            return;
        }
        checkCorrectRequest(e, args);
    }

    private void checkCorrectRequest(GuildMessageReceivedEvent e, String[] args) {
        Card card = this.setup.getCardManager().getById(args[1]);

        if (!isCorrectCard(card, e))
            return;
        double price;
        int time;
        try {
            price = Double.parseDouble(args[2]);
            time = Integer.parseInt(args[3]);
        } catch (NumberFormatException et) {
            e.getMessage().getChannel().sendMessage("Veuillez respecter la syntaxe suivante : `msell <card_id> <starting_value> <min_length>` " + e.getAuthor().getAsMention()).queue();
            return;
        }
        MAuction auctions = this.setup.getmAuctionManager().getAuctions(card.getOwnerId());
        if (auctions.getAuctions().size() >= 5) {
            e.getMessage().getChannel().sendMessage("Vous avez déjà 5 enchère en attente ! " + e.getAuthor().getAsMention()).queue();
            return;
        }
        showRequest(card, price, time, e);
    }

    private boolean isCorrectCard(Card card, GuildMessageReceivedEvent e) {
        if (card == null) {
            e.getMessage().getChannel().sendMessage("La carte n'existe pas ! " + e.getAuthor().getAsMention()).queue();
            return false;
        }
        if (card.getOwnerId().equalsIgnoreCase("-1")) {
            e.getMessage().getChannel().sendMessage("Cette carte est actuellement en enchère ! " + e.getAuthor().getAsMention()).queue();
            return false;
        }
        if (!card.getOwnerId().equalsIgnoreCase(e.getAuthor().getId())) {
            e.getMessage().getChannel().sendMessage("Vous n'êtes pas le propriétaire de cette carte ! " + e.getAuthor().getAsMention()).queue();
            return false;
        }
        return true;
    }

    private void showRequest(Card card, double price, int time, GuildMessageReceivedEvent e) {
        e.getMessage().getChannel()
                .sendMessage("ATTENTION: UNE FOIS LA CARTE AJOUTE AUX ENCHERES, ELLE NE VOUS APPARTIENDRAS PLUS ! Souhaitez vous ajouter la carte : " + card.getId() + " pour un prix de départ à " + price + " avec une durée d'enchère de " + time + " mins aux enchères ? " + e.getAuthor().getAsMention())
                .queue(mes -> {
                    AuctionRequest request = new AuctionRequest(mes.getId(), card, price, time);
                    this.setup.getmUserManager().updateRequest(card.getOwnerId(), request);
                    mes.addReaction("❌").queue();
                    mes.addReaction("✅").queue();
                });
    }
}
