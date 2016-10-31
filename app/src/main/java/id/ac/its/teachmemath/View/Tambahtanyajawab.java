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
import id.ac.its.teachmemath.Database.Tanyajawab;
import id.ac.its.teachmemath.Database.User;
import id.ac.its.teachmemath.R;

public class Tambahtanyajawab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formtambahtanyajawab);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("teachmemath")
                .setUseDefaultSharedPreference(true)
                .build();
        final EditText teks =(EditText) findViewById(R.id.ttj_detail);
        Button tambah =(Button) findViewById(R.id.ttj_tambah);
        final Long b = getIntent().getExtras().getLong("materiid",0);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Materi materi = new Select()
                        .from ( Materi.class )
                        .where("id = ?", b)
                        .executeSingle();
                User user = new Select()
                        .from ( User.class )
                        .where("id = ?", Prefs.getLong("iduser",0))
                        .executeSingle();
                Tanyajawab tj = new Tanyajawab();
                tj.teks=teks.getText().toString();
                tj.user=user;
                tj.materi=materi;
                tj.save();
                finish();
            }
        });
    }
}
