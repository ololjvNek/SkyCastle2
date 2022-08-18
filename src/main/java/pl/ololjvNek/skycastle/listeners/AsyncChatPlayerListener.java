package pl.ololjvNek.skycastle.listeners;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.query.QueryMode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.enums.ChatStatus;
import pl.ololjvNek.skycastle.managers.ChatManager;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.LPUtil;
import pl.ololjvNek.skycastle.utils.Util;

import java.util.Collection;

public class AsyncChatPlayerListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        final Player p = e.getPlayer();
        final User u = UserManager.getUser(p);
        final String group = LPUtil.getPlayerGroup(p);
        if(u.getSkyCastle() != null){
            e.setCancelled(true);
            if(e.getMessage().startsWith("!")){
                switch (u.getSkyCastle().getTeamIn(p)){
                    case "RED":
                        Util.sendMessage(u.getSkyCastle().getRedTeam(), Main.getMessagesConfig().getConfig().getString("messages.chat.prefixes.teamchat").replace("{PLAYER}", p.getName()).replace("{MESSAGE}", e.getMessage().replaceFirst("!", "")));
                        break;
                    case "BLUE":
                        Util.sendMessage(u.getSkyCastle().getBlueTeam(), Main.getMessagesConfig().getConfig().getString("messages.chat.prefixes.teamchat").replace("{PLAYER}", p.getName()).replace("{MESSAGE}", e.getMessage().replaceFirst("!", "")));
                        break;
                    case "":
                        Util.sendMessage(p, "&4Error: You're not in any team");
                        break;
                }
                return;
            }
            Util.sendMessage(u.getSkyCastle().getPlayers(), "&8(&6" + u.getStars() + " &e&l✪&8) " + (u.getSkyCastle().getTeamIn(p).equals("RED") ? "&c[RED]" : (u.getSkyCastle().getTeamIn(p).equals("BLUE") ? "&9[BLUE]" : "&7[Lobby]")) + " &7" + p.getName() + " &8>> &f" + e.getMessage());
            return;
        }
        if(ChatManager.getStatus() == ChatStatus.OFF){
            e.setCancelled(true);
            Util.sendMessage(p, "&4Error: Chat is off");
            return;
        }
        if(ChatManager.getStatus() == ChatStatus.PREMIUM && !p.hasPermission("chat.premium")){
            e.setCancelled(true);
            Util.sendMessage(p, "&4Error: Chat is actually turned for PREMIUM ranks");
            return;
        }
        if(u.getStars() < 1){
            e.setCancelled(true);
            Util.sendMessage(p, "&4Error: To be able chatting you must get &61&e&l✪ &estar");
            return;
        }
        for(String gr : Main.getMessagesConfig().getConfig().getConfigurationSection("messages.chat.formating").getKeys(false)){
            if(group == null){
                e.setFormat(Util.fixColors("&8" + p.getName() + "&7: &f" + e.getMessage()));
                break;
            }
            if(group.equalsIgnoreCase(gr)){
                e.setFormat(Util.fixColors(Main.getMessagesConfig().getConfig().getString("messages.chat.formating." + gr).replace("{PLAYER}", p.getName()).replace("{MESSAGE}", e.getMessage()).replace("{POINTS}", ""+u.getPoints()).replace("{STARS}", ""+u.getStars())));
                break;
            }
        }
    }
}
