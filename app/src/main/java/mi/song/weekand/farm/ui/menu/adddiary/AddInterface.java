package mi.song.weekand.farm.ui.menu.adddiary;

import mi.song.weekand.farm.base.BaseInterface;
import mi.song.weekand.farm.model.Photo;

public interface AddInterface {

    interface View extends BaseInterface.View {
        void updateImages();
        void clear();
        void showProgressBar();
        void closeProgressBar();
    }

    interface Presenter {
        void saveDiary(String title, String contents, Photo photo);
    }
}
