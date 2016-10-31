package id.ac.its.teachmemath.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Dzaky on 30/10/2016.
 */

@Table(name = "Materi")
public class Materi extends Model {
    @Column(name = "nama")
    public String nama;
    @Column(name = "guru", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public User guru;
    @Column(name = "penjelasan")
    public String penjelasan;

    public List<User> guru() {
        return getMany(User.class, "Materi");
    }

    public Materi() {
        super();
    }
}
