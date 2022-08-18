package pl.ololjvNek.skycastle.runnable;


import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;
import pl.ololjvNek.skycastle.utils.DiscoUtil;
import pl.ololjvNek.skycastle.utils.ItemBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

public class DiscoArmorRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){
            User u = UserManager.getUser(player);
            if(u.getDiscoArmor().isSmooth()){
                final Color color = Color.fromBGR(u.getDiscoArmor().getSmoothInt(), u.getDiscoArmor().getSmoothInt(), u.getDiscoArmor().getSmoothInt());
                ItemStack is = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(color).toItemStack();
                for (final Player target : Bukkit.getOnlinePlayers()) {
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 1, CraftItemStack.asNMSCopy(is)));
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 2, CraftItemStack.asNMSCopy(is)));
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 3, CraftItemStack.asNMSCopy(is)));
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 4, CraftItemStack.asNMSCopy(is)));
                }
                u.getDiscoArmor().addSmoothInt(1);
                if(u.getDiscoArmor().getSmoothInt() > 255){
                    u.getDiscoArmor().setSmoothInt(0);
                }
            }
            if(u.getDiscoArmor().isRandom()){
                ItemStack helmet = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(Color.fromBGR(ThreadLocalRandom.current().nextInt(256), ThreadLocalRandom.current().nextInt(256), ThreadLocalRandom.current().nextInt(256))).toItemStack();
                ItemStack chest = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(Color.fromBGR(ThreadLocalRandom.current().nextInt(256), ThreadLocalRandom.current().nextInt(256), ThreadLocalRandom.current().nextInt(256))).toItemStack();
                ItemStack leg = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(Color.fromBGR(ThreadLocalRandom.current().nextInt(256), ThreadLocalRandom.current().nextInt(256), ThreadLocalRandom.current().nextInt(256))).toItemStack();
                ItemStack boo = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(Color.fromBGR(ThreadLocalRandom.current().nextInt(256), ThreadLocalRandom.current().nextInt(256), ThreadLocalRandom.current().nextInt(256))).toItemStack();
                for (final Player target : Bukkit.getOnlinePlayers()) {
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 1, CraftItemStack.asNMSCopy(helmet)));
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 2, CraftItemStack.asNMSCopy(chest)));
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 3, CraftItemStack.asNMSCopy(leg)));
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 4, CraftItemStack.asNMSCopy(boo)));
                }
            }
            if(u.getDiscoArmor().isSpeed()){
                int color = ThreadLocalRandom.current().nextInt(256);
                ItemStack chest = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).setLeatherArmorColor(Color.fromRGB(color,color,color)).toItemStack();
                for (final Player target : Bukkit.getOnlinePlayers()) {
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 1, CraftItemStack.asNMSCopy(chest)));
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 2, CraftItemStack.asNMSCopy(chest)));
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 3, CraftItemStack.asNMSCopy(chest)));
                    DiscoUtil.sendPacket(target, new PacketPlayOutEntityEquipment(player.getEntityId(), 4, CraftItemStack.asNMSCopy(chest)));
                }
            }
        }
    }
}
