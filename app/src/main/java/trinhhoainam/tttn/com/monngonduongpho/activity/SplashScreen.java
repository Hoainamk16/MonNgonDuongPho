package trinhhoainam.tttn.com.monngonduongpho.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import trinhhoainam.tttn.com.monngonduongpho.R;

public class SplashScreen extends AppCompatActivity {
    public static final String DATA_NAME = "MON_NGON.sqlite";
    public static final String DATA_PATH = "/databases/";

    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        coppy();
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent next = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(next);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

    }

    private void coppy() {
        File in = getDatabasePath(DATA_NAME);

        if (!in.exists()) {
            try {
                coppyFromAssets();
                Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, e + "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void coppyFromAssets() {
        try {
            InputStream iput;
            iput = getAssets().open(DATA_NAME);

            String outFile = getDataBasePath();
            File f = new File(getApplicationInfo().dataDir + DATA_PATH);
            if (!f.exists()) {
                f.mkdir();
            }

            OutputStream outPut = new FileOutputStream(outFile);

            byte[] buff = new byte[1024];
            int lenght;
            while ((lenght = iput.read(buff)) > 0) {
                outPut.write(buff, 0, lenght);
            }
            outPut.flush();
            outPut.close();
            iput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getDataBasePath() {
        return getApplicationInfo().dataDir + DATA_PATH + DATA_NAME;
    }
}
