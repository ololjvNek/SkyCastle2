package pl.ololjvNek.skycastle.utils.comparators;

import pl.ololjvNek.skycastle.data.User;

import java.util.Comparator;

public class StarsComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        Integer s1 = o1.getStars();
        Integer s2 = o2.getStars();
        return s2.compareTo(s1);
    }
}
