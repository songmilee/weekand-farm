package mi.song.weekand.farm.util;

import java.text.SimpleDateFormat;

public class TimeUtils {
    public static String parseLongTime(Long timeMillis){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timeMillis);
    }
}
