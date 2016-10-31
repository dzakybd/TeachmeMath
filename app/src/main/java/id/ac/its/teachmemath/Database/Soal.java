package id.ac.its.teachmemath.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Dzaky on 30/10/2016.
 */

@Table(name = "Soal")
public class Soal extends Model{
    @Column(name = "soal")
    public String soal;
    @Column(name = "jawaban")
    public String jawaban;
    @Column(name = "materi", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Materi materi;

    public Soal() {
        super();
    }

    public List<Materi> materi() {
        return getMany(Materi.class, "Soal");
    }
}
