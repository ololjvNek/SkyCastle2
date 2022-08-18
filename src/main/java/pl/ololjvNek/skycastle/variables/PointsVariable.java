package pl.ololjvNek.skycastle.variables;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.UserManager;

public class PointsVariable extends Variable {

    public PointsVariable() {
        super("points");
    }

    @Override
    public String getReplacement(Player player) {
        User u = UserManager.getUser(player);
        assert u != null;
        return String.valueOf(u.getPoints());
    }
}
