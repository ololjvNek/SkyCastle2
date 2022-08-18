package pl.ololjvNek.skycastle.variables;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.ololjvNek.skycastle.data.Team;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.managers.RankingManager;
import pl.ololjvNek.skycastle.utils.Util;

public class TTopVariable extends Variable {

    private int i;

    public TTopVariable(final String name, final int integ) {
        super(name);
        this.i = integ;
    }

    @Override
    public String getReplacement(final Player player) {
        String str = this.i + ". &cBrak";
        str = Util.fixColors(str);
        if(RankingManager.getTeamRankings().size() < this.i){
            return str;
        }
        final Team u = RankingManager.getTeamRankings().get(this.i - 1);
        if(u.getMemberList().size() < 10){
            str = this.i + ". &cBrak";
            return Util.fixColors(str);
        }
        str = Util.fixColors(this.i + ". &9" + u.getTeamTag() + " &6" + u.getPointsCalculated() + " &e&l✪");
        return str;
    }
}
