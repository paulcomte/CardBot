package fr.rqndomhax.cardbot.commands;

import fr.rqndomhax.cardbot.cards.monsters.CardMonster;
import fr.rqndomhax.cardbot.cards.monsters.Monsters;
import fr.rqndomhax.cardbot.utils.Setup;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.Objects;

public class CView extends ListenerAdapter {

    private final Setup setup;

    public CView(Setup setup) {
        this.setup = setup;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String[] args = e.getMessage().getContentRaw().split("\\s+");
        EmbedBuilder eB;
        CardMonster cardMonster;

        if (!args[0].equalsIgnoreCase(setup.getPrefix() + "view") && !args[0].equalsIgnoreCase(setup.getPrefix() + "v"))
            return;
        if (args.length != 2) {
            e.getMessage().getChannel().sendMessage(new MessageBuilder().append("Veuillez entrer l'identifiant d'une carte " + Objects.requireNonNull(e.getMessage().getMember()).getAsMention()).build()).queue();
            return;
        }
        eB = new EmbedBuilder();
        cardMonster = this.setup.getCardManager().getMonsterById(args[1]);
        if (cardMonster != null) {
            eB.setTitle("Beta", "https://twitter.com/Med_Korp");
            eB.setColor(cardMonster.getMonsters().getRarity().getColor());
            eB.setFooter("twitter.com/Med_Korp", "https://cdn.discordapp.com/icons/770938678964650015/71a2d4bf1907666b886f6783e03c6255.webp?size=1024");
            eB.addField("Nom", cardMonster.getMonsters().getName(), false);
            eB.addField("Type", "Monstre", false);
            eB.addField("Rareté", cardMonster.getMonsters().getRarity().getName(), false);
            eB.addField("Attaque",  String.valueOf(cardMonster.getAttack()), true);
            eB.addField("Défense", String.valueOf(cardMonster.getDefense()), true);
            eB.setImage(cardMonster.getMonsters().getImageURL());
            e.getChannel().sendMessage(new MessageBuilder().setEmbed(eB.build()).build()).queue();
            return;
        }

        for (Monsters monsters : Monsters.values()) {
            if (!monsters.getName().equalsIgnoreCase(Arrays.toString(args).replaceAll(",","").replaceAll("]","").substring(7)))
                continue;
            eB.setTitle("Beta", "https://twitter.com/Med_Korp");
            eB.setColor(monsters.getRarity().getColor());
            eB.setFooter("twitter.com/Med_Korp", "https://cdn.discordapp.com/icons/770938678964650015/71a2d4bf1907666b886f6783e03c6255.webp?size=1024");
            eB.addField("Nom", monsters.getName(), false);
            eB.addField("Type", "Monstre", false);
            eB.addField("Rareté", monsters.getRarity().getName(), false);
            eB.setImage(monsters.getImageURL());
            e.getChannel().sendMessage(new MessageBuilder().setEmbed(eB.build()).build()).queue();
            return;
        }
        e.getChannel().sendMessage("Le carte demandée n'a pas été trouvée :(").queue();
    }
}