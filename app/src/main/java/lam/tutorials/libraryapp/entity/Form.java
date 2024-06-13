package lam.tutorials.libraryapp.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "form")
public class Form {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_book;
    private int id_user;
    private String code;
    private String type;
    private String status;
    private String regis_date;
    private String return_date;
    private int quantity;
    private long total;

    public Form(int id, int id_book, int id_user, String code, String type, String status, String regis_date, String return_date, int quantity, long total) {
        this.id = id;
        this.id_book = id_book;
        this.id_user = id_user;
        this.code = code;
        this.type = type;
        this.status = status;
        this.regis_date = regis_date;
        this.return_date = return_date;
        this.quantity = quantity;
        this.total = total;
    }

    public Form(int id_book, int id_user, String code, String type, String status, String regis_date, String return_date, int quantity, long total) {
        this.id_book = id_book;
        this.id_user = id_user;
        this.code = code;
        this.type = type;
        this.status = status;
        this.regis_date = regis_date;
        this.return_date = return_date;
        this.quantity = quantity;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegis_date() {
        return regis_date;
    }

    public void setRegis_date(String regis_date) {
        this.regis_date = regis_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
