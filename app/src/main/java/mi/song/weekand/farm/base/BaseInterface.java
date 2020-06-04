package mi.song.weekand.farm.base;

import android.content.Context;

public interface BaseInterface {
    interface View{
        Context getContext();
        void sendMessage(String msg);
    }
}
