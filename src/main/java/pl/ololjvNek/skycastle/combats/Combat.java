package pl.ololjvNek.skycastle.combats;

import lombok.Data;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.commands.Command;
import pl.ololjvNek.skycastle.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

@Data
public class Combat {

    private Player lastPlayerHit, whoHitted;
    private List<Player> assisted;
    private long logout;

    public Combat(Player p1, Player p2){
        lastPlayerHit = p1;
        whoHitted = p2;
        assisted = new ArrayList<>();
        logout = System.currentTimeMillis()+ TimeUtil.SECOND.getTime(15);
    }

    public boolean isInAssisted(Player player){
        for(Player assists : assisted){
            if(assists.getUniqueId().toString().equals(player.getUniqueId().toString())){
                return true;
            }
        }
        return false;
    }


}
