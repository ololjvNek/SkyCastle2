package pl.ololjvNek.skycastle.combats;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CombatManager {

    @Getter private static List<Combat> combats = new ArrayList<>();

    public static void createCombat(Player started, Player gotHitted){
        Combat combat = new Combat(started, gotHitted);
        combats.add(combat);
    }

    public static Combat getCombat(Player player){
        for(Combat combat : combats){
            if(combat.getLastPlayerHit().getUniqueId().toString().equals(player.getUniqueId().toString())){
                return combat;
            }else if(combat.getWhoHitted().getUniqueId().toString().equals(player.getUniqueId().toString())){
                return combat;
            }
        }
        return null;
    }
}
