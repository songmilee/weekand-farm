package mi.song.weekand.farm.ui.menu.adddiary;

import mi.song.weekand.farm.base.BaseInterface;

public interface AddInterface {

    interface View extends BaseInterface.View {
        void updateImages();
        void clear();
    }

    interface Presenter {
        void saveDiary(String title, String contents);
    }
}
