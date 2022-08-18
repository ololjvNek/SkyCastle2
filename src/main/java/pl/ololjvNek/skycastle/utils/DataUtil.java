package pl.ololjvNek.skycastle.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {
	    private static final SimpleDateFormat dateFormat;
	    private static final SimpleDateFormat timeFormat;
	    private static final LinkedHashMap<Integer, String> values;

	    public static String secondsToString(long l) {
	        int seconds = (int)((l - System.currentTimeMillis())/ 1000);
	        final StringBuilder sb = new StringBuilder();
	        for (final Map.Entry<Integer, String> e : DataUtil.values.entrySet()) {
	            final int iDiv = seconds / e.getKey();
	            if (iDiv >= 1) {
	                final int x = (int) Math.floor(iDiv);
	                sb.append(x + e.getValue()).append("");
	                seconds -= x * e.getKey();
	            }
	        }
	        return sb.toString();
	    }

	    public static String getDate(final long time) {
	        return DataUtil.dateFormat.format(new Date(time));
	    }

	    public static String getTime(final long time) {
	        return DataUtil.timeFormat.format(new Date(time));
	    }

	    public static int convertStrToInt(String str){
	    	String[] split = str.split("s");
	    	if(split.length > 0){
	    		return Integer.parseInt(split[0]);
			}
	    	return Integer.parseInt(str.replace("s", ""));
		}

	    public static long parseDateDiff(final String time, final boolean future) {
	        try {
	            final Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
	            final Matcher m = timePattern.matcher(time);
	            int years = 0;
	            int months = 0;
	            int weeks = 0;
	            int days = 0;
	            int hours = 0;
	            int minutes = 0;
	            int seconds = 0;
	            boolean found = false;
	            while (m.find()) {
	                if (m.group() != null && !m.group().isEmpty()) {
	                    for (int i = 0; i < m.groupCount(); ++i) {
	                        if (m.group(i) != null && !m.group(i).isEmpty()) {
	                            found = true;
	                            break;
	                        }
	                    }
	                    if (!found) {
	                        continue;
	                    }
	                    if (m.group(1) != null && !m.group(1).isEmpty()) {
	                        years = Integer.parseInt(m.group(1));
	                    }
	                    if (m.group(2) != null && !m.group(2).isEmpty()) {
	                        months = Integer.parseInt(m.group(2));
	                    }
	                    if (m.group(3) != null && !m.group(3).isEmpty()) {
	                        weeks = Integer.parseInt(m.group(3));
	                    }
	                    if (m.group(4) != null && !m.group(4).isEmpty()) {
	                        days = Integer.parseInt(m.group(4));
	                    }
	                    if (m.group(5) != null && !m.group(5).isEmpty()) {
	                        hours = Integer.parseInt(m.group(5));
	                    }
	                    if (m.group(6) != null && !m.group(6).isEmpty()) {
	                        minutes = Integer.parseInt(m.group(6));
	                    }
	                    if (m.group(7) == null) {
	                        break;
	                    }
	                    if (m.group(7).isEmpty()) {
	                        break;
	                    }
	                    seconds = Integer.parseInt(m.group(7));
	                    break;
	                }
	            }
	            if (!found) {
	                return -1L;
	            }
	            final Calendar c = new GregorianCalendar();
	            if (years > 0) {
	                c.add(1, years * (future ? 1 : -1));
	            }
	            if (months > 0) {
	                c.add(2, months * (future ? 1 : -1));
	            }
	            if (weeks > 0) {
	                c.add(3, weeks * (future ? 1 : -1));
	            }
	            if (days > 0) {
	                c.add(5, days * (future ? 1 : -1));
	            }
	            if (hours > 0) {
	                c.add(11, hours * (future ? 1 : -1));
	            }
	            if (minutes > 0) {
	                c.add(12, minutes * (future ? 1 : -1));
	            }
	            if (seconds > 0) {
	                c.add(13, seconds * (future ? 1 : -1));
	            }
	            final Calendar max = new GregorianCalendar();
	            max.add(1, 10);
	            if (c.after(max)) {
	                return max.getTimeInMillis();
	            }
	            return c.getTimeInMillis();
	        } catch (Exception e) {
	            return -1L;
	        }
	    }

	    static {
	        dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
	        timeFormat = new SimpleDateFormat("HH:mm:ss");
	        (values = new LinkedHashMap<Integer, String>(6)).put(31104000, "y");
	        DataUtil.values.put(2592000, "msc");
	        DataUtil.values.put(86400, "d");
	        DataUtil.values.put(3600, "h");
	        DataUtil.values.put(60, "min");
	        DataUtil.values.put(1, "s");
	    }
}

