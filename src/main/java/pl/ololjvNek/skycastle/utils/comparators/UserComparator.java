package pl.ololjvNek.skycastle.utils.comparators;

import pl.ololjvNek.skycastle.data.User;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        Integer p1 = o1.getPoints();
        Integer p2 = o2.getPoints();
        return p2.compareTo(p1);
    }
}
