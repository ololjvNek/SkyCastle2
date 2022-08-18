package pl.ololjvNek.skycastle.utils;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class DiscoUtil {

    public static void sendPacket(Player player, Packet packet){
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }
}
