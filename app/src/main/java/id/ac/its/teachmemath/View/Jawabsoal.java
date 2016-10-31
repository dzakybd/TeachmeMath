package id.ac.its.teachmemath.View;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.pixplicity.easyprefs.library.Prefs;

import id.ac.its.teachmemath.Database.Laporan;
import id.ac.its.teachmemath.Database.Soal;
import id.ac.its.teachmemath.Database.User;
import id.ac.its.teachmemath.R;

public class Jawabsoal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("teachmemath")
                .setUseDefaultSharedPreference(true)
                .build();
        Long id = getIntent().getExtras().getLong("soal",0);
        final Soal soal = new Select()
                .from ( Soal.class )
                .where("id = ?", id)
                .executeSingle();
        final User murid = new Select()
                .from ( User.class )
                .where("id = ?", Prefs.getLong("iduser",0))
                .executeSingle();
        setContentView(R.layout.formjawabsoal);
        TextView judul = (TextView) findViewById(R.id.judul);
        TextView soalteks = (TextView) findViewById(R.id.soal);
        final EditText jawaban = (EditText) findViewById(R.id.jawab);
        judul.setText("Soal "+soal.getId());
        soalteks.setText(soal.soal);
        Button masukan = (Button) findViewById(R.id.masuk);
        masukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Laporan laporan = new Laporan();
                laporan.soal=soal;
                laporan.murid=murid;
                String a = jawaban.getText().toString();
                laporan.jawaban=a;
                if(a.equals(soal.jawaban)) laporan.status=true;
                else laporan.status=false;
                laporan.save();
                finish();
            }
        });
    }
}
