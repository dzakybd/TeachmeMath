package id.ac.its.teachmemath.View;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.pixplicity.easyprefs.library.Prefs;

import id.ac.its.teachmemath.Database.Laporan;
import id.ac.its.teachmemath.Database.Materi;
import id.ac.its.teachmemath.Database.Soal;
import id.ac.its.teachmemath.Database.Tanyajawab;
import id.ac.its.teachmemath.Database.User;
import id.ac.its.teachmemath.R;


/**
 * Created by Dzaky on 26/10/2016.
 */

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pageplash);
        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("teachmemath.db").create();
        ActiveAndroid.initialize(dbConfiguration);
        firstinstall();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                Intent i = new Intent(Splash.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }

    public void firstinstall() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("teachmemath")
                .setUseDefaultSharedPreference(true)
                .build();
        boolean firstinstall = Prefs.getBoolean("firstinstall", true);
        if (firstinstall) {
            User murid;
            User guru;
            User ortu;
            Materi materi;
            Soal soal;
            Tanyajawab tanyajawab;
            Laporan laporan;

            murid = new User();
            murid.type=1;
            murid.nama="zaki";
            murid.username="zaki123";
            murid.password="cobacoba";
            murid.save();

            guru = new User();
            guru.type=2;
            guru.nama="Pak Andi";
            guru.username="andi123";
            guru.password="cobacoba";
            guru.save();

            ortu = new User();
            ortu.type=3;
            ortu.nama="Bu Lina";
            ortu.username="lina123";
            ortu.password="cobacoba";
            ortu.save();

            materi = new Materi();
            materi.nama="Pertambahan";
            materi.guru=guru;
            materi.penjelasan="Pertambahan adalah bla bla bla";
            materi.save();

            soal = new Soal();
            soal.materi=materi;
            soal.soal="5+6+3+2 = ?";
            soal.jawaban="16";
            soal.save();


            tanyajawab = new Tanyajawab();
            tanyajawab.materi=materi;
            tanyajawab.user=murid;
            tanyajawab.teks="ohhhh yayaya";
            tanyajawab.save();

            laporan = new Laporan();
            laporan.soal=soal;
            laporan.murid=murid;
            String a ="16";
            laporan.jawaban=a;
            if(a==soal.jawaban) laporan.status=true;
            else laporan.status=false;
            laporan.save();

            Prefs.putBoolean("firstinstall", false);
        }
    }
}
