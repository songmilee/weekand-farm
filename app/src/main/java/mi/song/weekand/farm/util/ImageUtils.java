package mi.song.weekand.farm.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;

import mi.song.weekand.farm.BuildConfig;
import mi.song.weekand.farm.R;
import mi.song.weekand.farm.model.Photo;

public class ImageUtils {
    private final String TAG = ImageUtils.class.getSimpleName();
    private Fragment fragment;
    private Context mContext;

    public ImageUtils(Fragment fragment){
        this.fragment = fragment;
        mContext = fragment.getContext();
    }

    public void showPhotoSelectDialog(Photo photo){
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.user_menu_img_title)
                .setItems(R.array.user_menu_img_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                getImageByPhoto();
                                break;
                            case 1:
                                getImageByCamera(photo);
                                break;
                        }
                    }
                })
                .create();

        dialog.show();
    }

    private void getImageByPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);

        fragment.startActivityForResult(intent, RequestCode.REQ_PHOTO_IMG);
    }

    private void getImageByCamera(Photo photo){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(mContext.getPackageManager()) != null){
            File photoFile = null;
            FileUtils fileUtils = new FileUtils();
            try{
                photoFile = fileUtils.createTempFile(mContext);

                if(photoFile != null){
                    Uri pUri = null;

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        pUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID, photoFile);
                    } else {
                        pUri = Uri.fromFile(photoFile);
                    }

                    photo.setUri(pUri.toString());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pUri);
                    fragment.startActivityForResult(intent, RequestCode.REQ_CAMERA_IMG);
                }
            } catch (Exception e) {
                Log.d(TAG, "error occured : " + e.getMessage());
            }
        }
    }
}
