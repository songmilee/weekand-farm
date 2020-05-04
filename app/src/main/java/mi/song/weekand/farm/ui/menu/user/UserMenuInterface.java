package mi.song.weekand.farm.ui.menu.user;

import mi.song.weekand.farm.base.BaseInterface;

public interface UserMenuInterface {
    interface View extends BaseInterface.View{
        void setEditable(boolean setEdit);
        void showEditMenu(boolean isShow);
        void showPhotoDialog();
    }

    interface Presenter {
        boolean validateEmailForm(String email);
        boolean validateDisplayName(String name);
        void updateEmail(String email);
        void updateProfile(String userName, String photoUri);
    }
}
