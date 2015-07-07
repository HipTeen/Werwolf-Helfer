package thorsten.yahya.werwolf;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;


public class CameraActivity extends Activity {

    private static final String LOG_TAG = CameraActivity.class.getSimpleName();

    private boolean done = true;
    File sdDir;

    protected static final String PHOTO_TAKEN = "photo_taken";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            File root = new File(Environment.getExternalStorageDirectory() + File.separator + "FolderName" + File.separator);
            root.mkdirs();
            sdDir = new File(root, "image.jpg");
            Log.d(LOG_TAG, "Creating image storage file: " + sdDir.getPath());
            startCameraActivity();
        } catch (Exception e) {
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap uri = BitmapFactory.decodeFile(sdDir.getPath());
        Intent intent = new Intent(this, ShowPictureActivity.class);
        intent.putExtra("image", uri);
        startActivity(intent);
        finish();
    }

    protected void startCameraActivity() {
        Uri outputFileUri = Uri.fromFile(sdDir);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.getBoolean(CameraActivity.PHOTO_TAKEN)) {
            done = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(CameraActivity.PHOTO_TAKEN,  done);
    }


}
