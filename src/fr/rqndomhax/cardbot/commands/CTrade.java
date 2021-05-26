package fr.rqndomhax.cardbot.commands;

import fr.rqndomhax.cardbot.database.requests.TradeRequest;
import fr.rqndomhax.cardbot.database.trades.States;
import fr.rqndomhax.cardbot.utils.Inventory;
import fr.rqndomhax.cardbot.utils.Setup;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.HashSet;

public class CTrade extends ListenerAdapter {

    private final Setup setup;

    public CTrade(Setup setup) {
        this.setup = setup;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String[] args = e.getMessage().getContentRaw().split("\\s+");

        if (!args[0].equalsIgnoreCase(setup.getPrefix() + "trade") && !args[0].equalsIgnoreCase(setup.getPrefix() + "t"))
            return;
        if (args.length != 2) {
            e.getChannel().sendMessage("Veuillez spécifier un utilisateur avec lequel faire une demande d'échange !").queue();
            return;
        }
        String userId = args[1];
        userId = userId.replace("<", "").replace(">","").replace("!", "").replace("@", "");
        Member member;

        try {
            member = e.getGuild().getMemberById(userId);
        } catch (NumberFormatException et) {
            e.getChannel().sendMessage("Cette utilisateur n'est pas présent sur ce serveur discord !").queue();
            return;
        }

        if (e.getJDA().getSelfUser().getId().equalsIgnoreCase(userId)) {
            e.getChannel().sendMessage("Vous ne pouvez pas faire de demande d'échange avec moi !").queue();
            return;
        }
        if (member == null) {
            e.getChannel().sendMessage("Cette utilisateur n'est pas présent sur ce serveur discord !").queue();
            return;
        }
        if (member.getId().equalsIgnoreCase(e.getAuthor().getId())) {
            e.getChannel().sendMessage("Vous ne pouvez pas échanger avec vous-même !").queue();
            return;
        }
        EmbedBuilder eB = new EmbedBuilder()
                .setTitle("Demande d'échange")
                .addField("", member.getAsMention() + ", veuillez accepter ou refuser la demande d'échange avec " + e.getAuthor().getAsMention() + " pour poursuivre.", false)
                .setColor(Color.WHITE);
        MessageBuilder mB = new MessageBuilder()
                .setContent(member.getAsMention() + ", voulez vous échanger avec " + e.getAuthor().getAsMention() + "?");
        e.getChannel().sendMessage(mB.setEmbed(eB.build()).build()).queue(mes -> {
            TradeRequest request = new TradeRequest(mes.getId(), new Inventory(e.getAuthor().getId(), new HashSet<>(), 0), new Inventory(member.getId(), new HashSet<>(), 0), States.OFFER);
            this.setup.getmUserManager().updateRequest(e.getAuthor().getId(), request);
            mes.addReaction("❌").queue();
            mes.addReaction("✅").queue();
        });
    }
}