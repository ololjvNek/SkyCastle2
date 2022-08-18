package pl.ololjvNek.skycastle.managers;

import lombok.Getter;
import pl.ololjvNek.skycastle.data.Team;
import pl.ololjvNek.skycastle.data.User;
import pl.ololjvNek.skycastle.utils.comparators.StarsComparator;
import pl.ololjvNek.skycastle.utils.comparators.TeamComparator;
import pl.ololjvNek.skycastle.utils.comparators.UserComparator;

import java.util.ArrayList;
import java.util.List;

public class RankingManager {

    @Getter private static List<User> userRankings = new ArrayList<>();
    @Getter private static List<Team> teamRankings = new ArrayList<>();
    @Getter private static List<User> starsRankings = new ArrayList<>();

    public static void sortUserRankings(){
        userRankings.sort(new UserComparator());
    }

    public static void sortTeamRankings(){
        teamRankings.sort(new TeamComparator());
    }

    public static void sortStarsRankings() { starsRankings.sort(new StarsComparator()); }
}
