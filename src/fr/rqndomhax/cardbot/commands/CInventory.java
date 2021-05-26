package fr.rqndomhax.cardbot.commands;

import fr.rqndomhax.cardbot.database.Card;
import fr.rqndomhax.cardbot.utils.Setup;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class CInventory extends ListenerAdapter {

    private final Setup setup;

    public CInventory(Setup setup) {
        this.setup = setup;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String[] args = e.getMessage().getContentRaw().split("\\s+");

        if (!args[0].equalsIgnoreCase(setup.getPrefix() + "i") && !args[0].equalsIgnoreCase(setup.getPrefix() + "inventory"))
            return;
        List<Card> cards = this.setup.getCardManager().getByOwnerId(e.getAuthor().getId());
        e.getChannel().sendMessage(new MessageBuilder()
                .append("<:havresac:772082970537492510> " + e.getAuthor().getAsMention() + ", voici le contenu de ton Havresac :")
                .setEmbed(new EmbedBuilder()
                        .setColor(new Color(8440453))
                        .setFooter("Bientôt plus de place ? il faudra songer à acheter un nouvel havresac !", "https://cdn.discordapp.com/icons/770938678964650015/71a2d4bf1907666b886f6783e03c6255.webp?size=1024")
                        .setAuthor(e.getAuthor().getName(), "https://discordapp.com", e.getAuthor().getAvatarUrl())
                        .addField("Ton inventaire :", "<:havresac:772082970537492510> `=` " + cards.size() + "\n<:jeton:772109273580175391> `=` " + this.setup.getmUserManager().getUser(e.getAuthor().getId()).getCoins(), false)
                        .build())
                .build()).queue();
    }
}
