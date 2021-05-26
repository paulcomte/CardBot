package fr.rqndomhax.cardbot.commands;

import fr.rqndomhax.cardbot.database.Card;
import fr.rqndomhax.cardbot.database.requests.BurnRequest;
import fr.rqndomhax.cardbot.utils.Setup;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CWipe extends ListenerAdapter {

    private final Setup setup;

    public CWipe(Setup setup) {
        this.setup = setup;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String[] args = e.getMessage().getContentRaw().split("\\s+");

        if (!args[0].equalsIgnoreCase(setup.getPrefix() + "wipe") && !args[0].equalsIgnoreCase(setup.getPrefix() + "w"))
            return;

        if (args.length != 2) {
            e.getChannel().sendMessage("Veuillez spécifier une carte à détruire !").queue();
            return;
        }
        String cardId = args[1];

        Card card = this.setup.getCardManager().getById(cardId);
        if (card == null) {
            e.getChannel().sendMessage("Le carte demandée n'a pas été trouvée :(").queue();
            return;
        }
        if (!card.getOwnerId().equals(e.getAuthor().getId())) {
            e.getChannel().sendMessage("La carte demandée ne vous appartient pas !").queue();
            return;
        }
        showCard(card, e);
    }

    private void showCard(Card card, GuildMessageReceivedEvent e) {
        Message message;
        message = new MessageBuilder()
                .append(e.getAuthor().getAsMention())
                .append(" souhaites-tu vraiment détruire cette carte ? **Cela te rapportera ")
                .append(String.valueOf(card.getCoins()))
                .append(" jetons.**")
                .build();
        e.getChannel().sendMessage(message).queue(mes -> {
            BurnRequest request = new BurnRequest(card, mes.getId());
            this.setup.getmUserManager().updateRequest(e.getAuthor().getId(), request);
            mes.addReaction("❌").queue();
            mes.addReaction("✅").queue();
        });
    }
}