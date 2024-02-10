package pl.ololjvNek.skycastle.commands.sc;

import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.commands.PlayerCommand;
import pl.ololjvNek.skycastle.enums.ChatStatus;
import pl.ololjvNek.skycastle.managers.ChatManager;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.Arrays;

public class ChatCommand extends PlayerCommand {

    public ChatCommand() {
        super("chat", "Chat command", "/chat <on/off/premium/clear>", "core.chat");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if(args.length == 0){
            return Util.sendMessage(p, "&4Error &8>> &7Correct usage: &c" + getUsage());
        }
        switch (args[0]){
            case "on":
                ChatManager.asyncChat(p, ChatStatus.ON);
                break;
            case "off":
                ChatManager.asyncChat(p, ChatStatus.OFF);
                break;
            case "premium":
                ChatManager.asyncChat(p, ChatStatus.PREMIUM);
                break;
            case "clear":
                ChatManager.asyncChat(p, ChatStatus.CLEAR);
                break;
        }
        return false;
    }
}
