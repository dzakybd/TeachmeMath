package id.ac.its.teachmemath.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.query.Select;

import id.ac.its.teachmemath.Database.Materi;
import id.ac.its.teachmemath.Database.Soal;
import id.ac.its.teachmemath.R;

public class Tambahsoal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formtambahsoal);

        final EditText soalteks =(EditText) findViewById(R.id.ts_soal);
        final EditText jawaban =(EditText) findViewById(R.id.ts_jawaban);
        Button tambah =(Button) findViewById(R.id.ts_tambah);
        final Long b = getIntent().getExtras().getLong("materiid",0);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Materi materi = new Select()
                        .from ( Materi.class )
                        .where("id = ?", b)
                        .executeSingle();
                Soal soal = new Soal();
                soal.materi=materi;
                soal.jawaban=jawaban.getText().toString();
                soal.soal=soalteks.getText().toString();
                soal.save();
                finish();
            }
        });
    }
}
