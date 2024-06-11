package lam.tutorials.libraryapp.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String email;
    private String fullname;
    private String role;
    private String password;
    private int enable;

    public User(int id, String code, String email, String fullname, String role, String password, int enable) {
        this.id = id;
        this.code = code;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.password = password;
        this.enable = enable;
    }

    public User(String code, String email, String fullname, String role, String password, int enable) {
        this.code = code;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.password = password;
        this.enable = enable;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
