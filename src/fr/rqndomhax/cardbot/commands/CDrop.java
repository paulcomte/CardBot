package fr.rqndomhax.cardbot.commands;

import fr.rqndomhax.cardbot.database.MUser;
import fr.rqndomhax.cardbot.utils.Drop;
import fr.rqndomhax.cardbot.utils.ImageMaker;
import fr.rqndomhax.cardbot.utils.Setup;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CDrop extends ListenerAdapter {

    private final Setup setup;
    public CDrop(Setup setup) {
        this.setup = setup;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        String[] args = e.getMessage().getContentRaw().split("\\s+");
        MessageBuilder mB;

        if (!args[0].equalsIgnoreCase(setup.getPrefix() + "drop") && !args[0].equalsIgnoreCase(setup.getPrefix() + "d"))
            return;
        MUser user = this.setup.getmUserManager().getUser(e.getAuthor().getId());
        if (user != null && !user.canDrop()) {
            e.getChannel().sendMessage("Vous ne pouvez pas obtenir de nouvelles cartes, vous devez patienter encore " + user.getNextDrop()).queue();
            return;
        }
        Drop drop = new Drop(setup.getCardManager(), e.getAuthor().getId());
        try {
            new ImageMaker(setup).createDropImage(drop.getDrops());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        mB = new MessageBuilder();
        mB.append(Objects.requireNonNull(e.getMember()).getAsMention()).append(" vient d'obtenir 3 cartes !");
        e.getChannel().sendMessage(mB.build()).addFile(new File("card.png")).queue();
        this.setup.getmUserManager().updateLastDrop(e.getAuthor().getId(), "0");
    }
}