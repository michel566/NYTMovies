package com.michelbarbosa.nytmovies.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataUtil {

    private static void SaveImage(final Context context, final String MyUrl){
        final ProgressDialog progress = new ProgressDialog(context);
        class SaveThisImage extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setTitle("Processing");
                progress.setMessage("Please Wait...");
                progress.setCancelable(false);
                progress.show();
            }
            @Override
            protected Void doInBackground(Void... arg0) {
                try{

                    File sdCard = Environment.getExternalStorageDirectory();
                    @SuppressLint("DefaultLocale") String fileName = String.format("%d.jpg", System.currentTimeMillis());
                    File dir = new File(sdCard.getAbsolutePath() + "/savedImageName");
                    dir.mkdirs();
                    final File myImageFile = new File(dir, fileName); // Create image file
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(myImageFile);
                        Bitmap bitmap = Picasso.get().load(MyUrl).get();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(myImageFile));
                        context.sendBroadcast(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    Log.e("DataUtil()", "doInBackground Exception");
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                if(progress.isShowing()){
                    progress.dismiss();
                }
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            }
        }
        SaveThisImage shareimg = new SaveThisImage();
        shareimg.execute();
    }
}
