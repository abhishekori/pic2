package turing.com.picstatus;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText tv;
    ImageView img;
    Button btn;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (EditText) findViewById(R.id.textm);
        img= (ImageView) findViewById(R.id.imageView);
        btn= (Button) findViewById(R.id.button);
        tv1= (TextView) findViewById(R.id.textView);

        tv.setDrawingCacheEnabled(true);
        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        tv.buildDrawingCache(true);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText(tv.getText().toString());
                Bitmap res = getBitmapFromView(tv1);
               img.setImageBitmap(res);
img.buildDrawingCache();
                Bitmap bm=img.getDrawingCache();

                OutputStream fOut = null;
                Uri outputFileUri;
                try {
                    File root = new File(Environment.getExternalStorageDirectory()
                            + File.separator + "folder_name" + File.separator);
                    root.mkdirs();
                    File sdImageMainDirectory = new File(root, "myPicName.jpg");
                    outputFileUri = Uri.fromFile(sdImageMainDirectory);
                    fOut = new FileOutputStream(sdImageMainDirectory);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error occured. Please try again later.",
                            Toast.LENGTH_SHORT).show();
                }

                try {
                    bm.compress(Bitmap.CompressFormat.PNG, 0, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (Exception e) {
                }

              // cimage(img);




//                Bitmap res = getBitmapFromView(tv);
//                img.setImageBitmap(res);
//                //storeImage(res);
//                String path = Environment.getExternalStorageDirectory().toString();
//                OutputStream fOut = null;
//                File file = new File(path, "FitnessGir.jpg"); // the File to save to
//                try {
//                    fOut = new FileOutputStream(file);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                Bitmap pictureBitmap = res; // obtaining the Bitmap
//                pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 0, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
//                try {
//                    fOut.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    fOut.close(); // do not forget to close the stream
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        });

    }


public void cimage(View view){
    Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
    view.setDrawingCacheEnabled(false);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

    File f = new File(Environment.getExternalStorageDirectory() + File.separator + "v2i.jpg");
    try {
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
    } catch (Exception e) {
    }
    //finish();

}
    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);

        return returnedBitmap;
    }
}
