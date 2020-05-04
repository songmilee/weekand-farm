package mi.song.weekand.farm.util;

import android.content.Context;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public File createTempFile(Context context) throws IOException{
        String fileName = String.valueOf(System.currentTimeMillis());

        File storageDir = context.getFilesDir();
        File imagFile = File.createTempFile(fileName, ".jpg", storageDir);

        return imagFile;
    }

}
