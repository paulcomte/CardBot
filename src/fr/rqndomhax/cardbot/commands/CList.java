package fr.rqndomhax.cardbot.commands;

import fr.rqndomhax.cardbot.cards.monsters.CardMonster;
import fr.rqndomhax.cardbot.utils.Setup;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class CList extends ListenerAdapter {

    private final Setup setup;

    public CList(Setup setup) {
        this.setup = setup;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String[] args = e.getMessage().getContentRaw().split("\\s+");

        if (!args[0].equalsIgnoreCase(setup.getPrefix() + "list") && !args[0].equalsIgnoreCase(setup.getPrefix() + "l"))
            return;
        if (args.length != 2) {
            e.getMessage().getChannel().sendMessage(new MessageBuilder().append("Veuillez entrer le nom d'une catégorie : `monsters` etc... " + Objects.requireNonNull(e.getMessage().getMember()).getAsMention()).build()).queue();
            return;
        }
        if (args[1].equalsIgnoreCase("monsters")) {
            sendMonstersInfo(e);
            return;
        }
    }

    private void sendMonstersInfo(GuildMessageReceivedEvent e) {
        List<CardMonster> cards = this.setup.getCardManager().getMonstersByOwnerId(e.getAuthor().getId());
        if (cards.size() == 0) {
            e.getChannel().sendMessage(new MessageBuilder().append("Désolé " + e.getAuthor().getAsMention() + " mais tu ne possèdes aucun monstre !").build()).queue();
            return;
        }
        EmbedBuilder em = new EmbedBuilder();
        em.setColor(new Color(8440453));
        em.setAuthor(e.getAuthor().getName(), "https://discrodapp.com", e.getAuthor().getAvatarUrl());
        showMonstersInfo(e, em, cards, 1);
        Message m = new MessageBuilder()
                .append("<:sacdevoyage:772082937948405800> ")
                .append(e.getAuthor().getAsMention())
                .append(", voici ta (magnifique) collection !")
                .setEmbed(em.build())
                .build();
        e.getChannel().sendMessage(m).queue(mes -> {
            mes.addReaction("⬅️").queue();
            mes.addReaction("➡️").queue();
        });
    }
    private void showMonstersInfo(GuildMessageReceivedEvent e, EmbedBuilder em, List<CardMonster> cards, int index) {
        if (cards.size() <= 9)
            em.setFooter("Affichage des cartes " + (index * 9 - 8) + "-" + cards.size() + " sur " + cards.size(), "https://cdn.discordapp.com/icons/770938678964650015/71a2d4bf1907666b886f6783e03c6255.webp?size=1024");
        else
            em.setFooter("Affichage des cartes " + (index * 9 - 8) + "-" + 9 * index + " sur " + cards.size(), "https://cdn.discordapp.com/icons/770938678964650015/71a2d4bf1907666b886f6783e03c6255.webp?size=1024");
         em.addField("Collection de " + e.getAuthor().getName() + ":", "ID - RARETE - ATT - DEF - NOM fade\n", false);
         monstersCardInfo(cards, (index * 9) - 9, 9 * index, cards.size(), em);
    }
    private void monstersCardInfo(List<CardMonster> cards, int start, int end, int total, EmbedBuilder em) {
        StringBuffer sb = new StringBuffer();
        for (int i = start ; i < end && i < total; i++) {
            sb.append('`' + cards.get(i).getId() + '`');
            sb.append(" - ");
            sb.append("**" + cards.get(i).getMonsters().getRarity().getName() + "**");
            sb.append(" - ");
            sb.append(cards.get(i).getAttack());
            sb.append(" - ");
            sb.append(cards.get(i).getDefense());
            sb.append(" - ");
            sb.append("**" + cards.get(i).getMonsters().getName() + "**");
            sb.append("\n");
            em.addField("", sb.toString(), false);
            sb = new StringBuffer();
        }
    }
}