package pl.ololjvNek.skycastle.utils;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.PermissionHolder;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.group.GroupManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Collection;

public class LPUtil {

    public static String getPlayerGroup(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());

        for (Group group : inheritedGroups) {
            if (player.hasPermission("group." + group.getName())) {
                return group.getName();
            }
        }
        return null;
    }

    public static void addPlayerPermission(Player player, String permission){
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        LuckPermsProvider.get().getUserManager().modifyUser(player.getUniqueId(), u -> {
           u.data().add(Node.builder(permission).build());
        });
    }

    public static void addPlayerGroup(Player player, String group){
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        LuckPermsProvider.get().getUserManager().modifyUser(player.getUniqueId(), user1 -> {
            user1.data().add(Node.builder("group." + group).expiry(Duration.ofDays(3)).build());
        });
    }
}
