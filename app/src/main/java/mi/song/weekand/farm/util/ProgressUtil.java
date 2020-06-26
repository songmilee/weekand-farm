package mi.song.weekand.farm.util;

import android.app.AlertDialog;
import android.content.Context;

import mi.song.weekand.farm.R;

public class ProgressUtil {
    private static ProgressUtil progressUtil = null;

    private AlertDialog progressDialog;

    public static ProgressUtil getInstance(Context context){
        if (progressUtil == null){
            progressUtil = new ProgressUtil(context);
        }

        return progressUtil;
    }

    private ProgressUtil(Context context){
        progressDialog = new AlertDialog.Builder(context)
                            .setView(R.layout.progress)
                            .setCancelable(false)
                            .create();
    }

    public void showDialog(){
        progressDialog.show();
    }

    public void dismissDialog(){
        progressDialog.dismiss();
    }

}
