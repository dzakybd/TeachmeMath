package id.ac.its.teachmemath.View;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.query.Select;
import com.pixplicity.easyprefs.library.Prefs;

import id.ac.its.teachmemath.Database.Materi;
import id.ac.its.teachmemath.Database.User;
import id.ac.its.teachmemath.R;

public class Tambahmateri extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formtambahmateri);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("teachmemath")
                .setUseDefaultSharedPreference(true)
                .build();
        final EditText judulmateri =(EditText) findViewById(R.id.tm_judul);
        final EditText penjelasan =(EditText) findViewById(R.id.tm_detail);
        Button tambah =(Button) findViewById(R.id.tm_tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User guru = new Select()
                        .from ( User.class )
                        .where("id = ?", Prefs.getLong("iduser",0))
                        .executeSingle();
                Materi materi = new Materi();
                materi.guru=guru;
                materi.penjelasan=penjelasan.getText().toString();
                materi.nama=judulmateri.getText().toString();
                materi.save();
                finish();
            }
        });
    }
}
