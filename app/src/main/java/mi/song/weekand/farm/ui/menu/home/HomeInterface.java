package mi.song.weekand.farm.ui.menu.home;

import java.util.ArrayList;
import java.util.Map;

import mi.song.weekand.farm.base.BaseInterface;

public interface HomeInterface {

    interface View extends BaseInterface.View {
        void setCorpItemData(ArrayList<Map<String, Object>> dataList);
    }

    interface Presenter{
        void getCorpItemList(Long time);
    }
}
