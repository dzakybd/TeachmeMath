package id.ac.its.teachmemath.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Dzaky on 30/10/2016.
 */
@Table(name = "Laporan")
public class Laporan extends Model {
    @Column(name = "soal", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Soal soal;
    @Column(name = "murid", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public User murid;
    @Column(name = "jawaban")
    public String jawaban;
    @Column(name = "status")
    public boolean status;

    public Laporan() {
        super();
    }

    public List<Soal> soal() {
        return getMany(Soal.class, "Laporan");
    }
    public List<User> murid() {
        return getMany(User.class, "Laporan");
    }
}
