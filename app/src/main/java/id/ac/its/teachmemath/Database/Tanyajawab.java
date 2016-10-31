package id.ac.its.teachmemath.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Dzaky on 30/10/2016.
 */

@Table(name = "Tanyajawab")
public class Tanyajawab extends Model{
    @Column(name = "teks")
    public String teks;
    @Column(name = "user", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public User user;
    @Column(name = "materi", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Materi materi;

    public Tanyajawab() {
        super();
    }

    public List<Materi> materi() {
        return getMany(Materi.class, "Tanyajawab");
    }

    public List<User> user() {
        return getMany(User.class, "Tanyajawab");
    }


}
