package com.earth2me.essentials.commands;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.earth2me.essentials.CommandSource;
import com.earth2me.essentials.User;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import static com.earth2me.essentials.I18n.tl;

// This command can be used to echo messages to the users screen, mostly useless but also an #EasterEgg
public class Commandping extends EssentialsCommand {
    public Commandping() {
        super("ping");
    }

    @Override
    public void run(final Server server, final CommandSource sender, final String commandLabel, final String[] args) throws Exception {
        if (args.length < 1) {
            if (!sender.isPlayer()) {
                sender.sendMessage("Pong!");
                return;
            }
            int ping = getPing(sender.getPlayer(), server);
            sender.sendMessage(tl("ping", "" + getPingColor(ping) + ping));
        } else {
            final User user = getPlayer(server, args, 0, true, true);
            int ping = getPing((Player) user, server);
            sender.sendMessage(tl("pingOfPlayer", user.getDisplayName(), "" + getPingColor(ping) + ping));
        }
    }

    public int getPing(final Player player, final Server server) throws Exception {
        // REFLECTION
        final String serverVersion = server.getClass().getPackage().getName().split("\\.")[3];
        final Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit."+serverVersion+".entity.CraftPlayer");
        final Object converted = craftPlayer.cast(player);
        final Method handle = converted.getClass().getMethod("getHandle");
        final Object entityPlayer = handle.invoke(converted);
        Field pingField = entityPlayer.getClass().getField("ping");
        return pingField.getInt(entityPlayer);
    }

    public ChatColor getPingColor(int ping) {
        ChatColor color;
        if (ping <= 200) {
            color = ChatColor.GREEN;
        } else if (ping <= 500) {
            color = ChatColor.YELLOW;
        } else {
            color = ChatColor.RED;
        }
        return color;
    }
}
