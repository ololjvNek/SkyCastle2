package pl.ololjvNek.skycastle.variables;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.Main;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.RankingManager;
import pl.ololjvNek.skycastle.utils.Util;

public class TopVariable extends Variable {

    private int i;

    public TopVariable(final String name, final int integ) {
        super(name);
        this.i = integ;
    }

    @Override
    public String getReplacement(final Player player) {
        String str = "";
        if(Main.isShowTopPoints()){
            if(RankingManager.getUserRankings().size() < this.i){
                return str;
            }
            final User u = RankingManager.getUserRankings().get(this.i - 1);
            str = Util.fixColors(this.i + ". " + (u.isOnline() ? "&c&l" + u.getLastName() : "&c" + u.getLastName()) + " &6" + u.getPoints());
        }else{
            if(RankingManager.getStarsRankings().size() < this.i){
                return str;
            }
            final User u = RankingManager.getStarsRankings().get(this.i - 1);
            str = Util.fixColors(this.i + ". " + (u.isOnline() ? "&c&l" + u.getLastName() : "&c" + u.getLastName()) + " &6" + u.getStars() + " &e&l✪");
        }
        return str;
    }
}
