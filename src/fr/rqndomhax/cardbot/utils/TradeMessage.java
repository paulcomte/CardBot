package fr.rqndomhax.cardbot.utils;

import fr.rqndomhax.cardbot.database.requests.TradeRequest;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class TradeMessage {
    public void updateMessage(TradeRequest request, MessageReactionAddEvent e) {
        ArrayList<String> user1 = new ArrayList<>();
        ArrayList<String> user2 = new ArrayList<>();
        user1.add(showCoins(request.getUser1Inventory()));
        user2.add(showCoins(request.getUser2Inventory()));
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.yellow);
        embedBuilder.addField(Objects.requireNonNull(e.getGuild().getMemberById(request.getUser1Inventory().getInventoryOwnerId())).getEffectiveName(),
                user1.toString().replaceAll(",","\n").replaceAll("]","").substring(1), true);
        embedBuilder.addField(Objects.requireNonNull(e.getGuild().getMemberById(request.getUser2Inventory().getInventoryOwnerId())).getEffectiveName(),
                user2.toString().replaceAll(",","\n").replaceAll("]","").substring(1), true);
        e.getChannel().editMessageById(request.getMessageId(), embedBuilder.build()).queue();
    }

    public void updateMessage(TradeRequest request, GuildMessageReceivedEvent e) {
        ArrayList<String> user1 = new ArrayList<>();
        ArrayList<String> user2 = new ArrayList<>();
        user1.add(showCoins(request.getUser1Inventory()));
        user2.add(showCoins(request.getUser2Inventory()));
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.yellow);
        embedBuilder.addField(Objects.requireNonNull(e.getGuild().getMemberById(request.getUser1Inventory().getInventoryOwnerId())).getEffectiveName(),
                user1.toString().replaceAll(",","\n").replaceAll("]","").substring(1), true);
        embedBuilder.addField(Objects.requireNonNull(e.getGuild().getMemberById(request.getUser2Inventory().getInventoryOwnerId())).getEffectiveName(),
                user2.toString().replaceAll(",","\n").replaceAll("]","").substring(1), true);
        e.getChannel().editMessageById(request.getMessageId(), embedBuilder.build()).queue();
    }

    private String showCoins(Inventory inventory) {
        if (inventory.getCoins() != 0)
            return inventory.getCoins() + " coins";
        return "";
    }
}
