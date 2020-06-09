package lt.vcs.carapp;

import java.util.Comparator;

public class DateComparator implements Comparator<DateObject> {

    @Override
    public int compare(DateObject o1, DateObject o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}