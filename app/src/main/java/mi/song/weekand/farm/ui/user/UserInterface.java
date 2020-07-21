package mi.song.weekand.farm.ui.user;

import mi.song.weekand.farm.base.BaseInterface;

public interface UserInterface {
    interface View extends BaseInterface.View{
        void setEditable(boolean setEdit);
        void showEditMenu(boolean isShow);
        void showPhotoDialog();
        void showProgress();
        void dismissProgress();
    }

    interface Presenter {
        boolean validateEmailForm(String email);
        boolean validateDisplayName(String name);
        void updateEmail(String email);
        void updateProfile(String userName, String photoUri);
        boolean updateInfo(String userName, String photoUri, String email);
    }
}
