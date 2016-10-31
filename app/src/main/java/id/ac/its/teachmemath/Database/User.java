package id.ac.its.teachmemath.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Dzaky on 30/10/2016.
 */
@Table(name = "User")
public class User extends Model {
    @Column(name = "nama")
    public String nama;
    @Column(name = "type")
    public int type;
    @Column(name = "username")
    public String username;
    @Column(name = "password")
    public String password;
    public User(){
        super();
    }
}
