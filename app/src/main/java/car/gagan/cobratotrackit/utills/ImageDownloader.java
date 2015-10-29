package car.gagan.cobratotrackit.utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by gagandeep on 29/10/15.
 */
public class ImageDownloader extends AsyncTask<Void, Void, Void>
{

    String URl;

    CallBackWebService callback;

    private File file;
    private Context con;

    public ImageDownloader(String URl, CallBackWebService callback, Context con)
    {
        this.URl = URl;
        this.callback = callback;
        this.con = con;
    }

    private void downloadImagesToSdCard(String downloadUrl)
    {
        try
        {

            if (downloadUrl.isEmpty()) return;


            URL url = new URL(downloadUrl);

            File myDir = new File("/sdcard" + "/" + "CobraCar");

            if (!myDir.exists())
            {
                myDir.mkdir();

            }

            String fname = "CarImage.png";
            file = new File(myDir, fname);


            SharedPreferences shrdPref = con.getSharedPreferences(Global_Constants.shared_pref_name, Context.MODE_PRIVATE);


            if (file.exists())
            {


                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                if (bitmap != null && shrdPref.getString("carimage", "").equals(downloadUrl))
                {
                    return;
                }


            }


            SharedPreferences.Editor editor = shrdPref.edit();
            editor.putString("carimage", downloadUrl);
            editor.apply();







             /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();
            InputStream inputStream = null;
            HttpURLConnection httpConn = (HttpURLConnection) ucon;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = httpConn.getInputStream();
            }

            FileOutputStream fos = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0)
            {
                fos.write(buffer, 0, bufferLength);

            }

            fos.close();

        }
        catch (Exception | Error io)
        {

            if (file != null)
            {
                file.delete();
            }
            io.printStackTrace();
        }
    }


    @Override
    protected Void doInBackground(Void... voids)
    {
        downloadImagesToSdCard(URl);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {

        super.onPostExecute(aVoid);

        callback.webOnFinish("");


    }
}
