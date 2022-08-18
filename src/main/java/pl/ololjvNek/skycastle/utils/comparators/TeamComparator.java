package pl.ololjvNek.skycastle.utils.comparators;

import pl.ololjvNek.skycastle.data.Team;

import java.util.Comparator;

public class TeamComparator implements Comparator<Team> {
    @Override
    public int compare(Team o1, Team o2) {
        Integer p1 = o1.getPointsCalculated();
        Integer p2 = o2.getPointsCalculated();
        return p2.compareTo(p1);
    }
}
