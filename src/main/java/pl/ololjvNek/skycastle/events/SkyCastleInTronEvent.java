package pl.ololjvNek.skycastle.events;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.ololjvNek.skycastle.data.SkyCastle;

import java.util.List;

@Data
public class SkyCastleInTronEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;
    private Player player;
    private SkyCastle skyCastle;
    private List<Player> restIn;
    private String team;

    public SkyCastleInTronEvent(Player player, SkyCastle skyCastle, List<Player> restIn, String team){
        this.isCancelled = false;
        this.player = player;
        this.skyCastle = skyCastle;
        this.restIn = restIn;
        this.team = team;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean arg0) {
        this.isCancelled = arg0;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
