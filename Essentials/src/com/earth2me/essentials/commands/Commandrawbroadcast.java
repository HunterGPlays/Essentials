package com.earth2me.essentials.commands;

import org.bukkit.Server;

import com.earth2me.essentials.CommandSource;
import com.earth2me.essentials.User;
import com.earth2me.essentials.utils.FormatUtil;


public class Commandrawbroadcast extends EssentialsCommand {
    public Commandrawbroadcast() {
        super("rawbroadcast");
    }

    @Override
    public void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception {
        sendBroadcast(user.getDisplayName(), args);
    }

    @Override
    public void run(final Server server, final CommandSource sender, final String commandLabel, final String[] args) throws Exception {
        sendBroadcast(sender.getSender().getName(), args);
    }

    private void sendBroadcast(final String name, final String[] args) throws NotEnoughArgumentsException {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException();
        }

        ess.broadcastMessage(FormatUtil.replaceFormat(getFinalArg(args, 0)).replace("\\n", "\n"));
    }
}
