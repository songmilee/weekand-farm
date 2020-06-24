package mi.song.weekand.farm.ui.menu.home;

import java.util.Map;

import mi.song.weekand.farm.base.BaseInterface;

public interface HomeInterface {

    interface View extends BaseInterface.View {
        void setCorpItemData(Map<String, Object> data);
    }

    interface Presenter{
        void getCorpItemList();
    }
}
