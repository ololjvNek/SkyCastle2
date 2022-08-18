package pl.ololjvNek.skycastle.managers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.enums.ChatStatus;
import pl.ololjvNek.skycastle.utils.Util;

public class ChatManager {

    @Getter @Setter
    private static ChatStatus status = ChatStatus.ON;


    public static boolean asyncChat(Player executer, ChatStatus status){
        if(getStatus() == status){
            return Util.sendMessage(executer, "&cSKYCASTLE &8>> &7Chat is actually " + (getStatus() == ChatStatus.ON ? "&aon" : "&coff"));
        }
        setStatus(status);
        switch (status){
            case ON:
                for(int i = 0; i < 101; i++){
                    Util.sendMessage(Bukkit.getOnlinePlayers(), "");
                }
                return Util.sendMessage(Bukkit.getOnlinePlayers(), "&8&m==============&7 ( &cSKYCASTLE &7) &8&m==============\n" +
                        "&8>> &7Chat was &aenabled\n" +
                        "&8>> &7Administrator: &c" + executer.getName() + "\n" +
                        "&8&m==============&7 ( &6SKYCASTLE &7) &8&m==============");
            case OFF:
                for(int i = 0; i < 101; i++){
                    Util.sendMessage(Bukkit.getOnlinePlayers(), "");
                }
                return Util.sendMessage(Bukkit.getOnlinePlayers(), "&8&m==============&7 ( &cSKYCASTLE &7) &8&m==============\n" +
                        "&8>> &7Chat was &cdisabled\n" +
                        "&8>> &7Administrator: &c" + executer.getName() + "\n" +
                        "&8&m==============&7 ( &6SKYCASTLE &7) &8&m==============");
            case PREMIUM:
                for(int i = 0; i < 101; i++){
                    Util.sendMessage(Bukkit.getOnlinePlayers(), "");
                }
                return Util.sendMessage(Bukkit.getOnlinePlayers(), "&8&m==============&7 ( &cSKYCASTLE &7) &8&m==============\n" +
                        "&8>> &7Chat was &aenabled &7for players with &6PREMIUM &7ranks\n" +
                        "&8>> &7Administrator: &c" + executer.getName() + "\n" +
                        "&8&m==============&7 ( &6SKYCASTLE &7) &8&m==============");
            case CLEAR:
                for(int i = 0; i < 101; i++){
                    Util.sendMessage(Bukkit.getOnlinePlayers(), "");
                }
                return Util.sendMessage(Bukkit.getOnlinePlayers(), "&8&m==============&7 ( &cSKYCASTLE &7) &8&m==============\n" +
                        "&8>> &7Chat was &9cleared\n" +
                        "&8>> &7Administrator: &c" + executer.getName() + "\n" +
                        "&8&m==============&7 ( &6SKYCASTLE &7) &8&m==============");
        }
        return false;
    }
}
