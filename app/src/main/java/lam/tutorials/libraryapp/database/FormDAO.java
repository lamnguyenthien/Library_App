package lam.tutorials.libraryapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.Form;

public class FormDAO {
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;
    private Context context;
    public static final String SQL_TAO_BANG_FORM =
            "CREATE TABLE form (id integer primary key autoincrement," +
                    "id_book integer, id_user integer, code text, type text, " +
                    "status text, regis_date text," +
                    "return_date text, quantity integer, total integer)";

    public static final String TABLE_NAME = "form";

    public FormDAO(Context context) {
        this.context = context;
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public long insertForm(Form form) {
        ContentValues values = new ContentValues();
        values.put("id_book",form.getId_book());
        values.put("id_user",form.getId_user());
        values.put("code",form.getCode());
        values.put("type",form.getType());
        values.put("status",form.getStatus());
        values.put("regis_date",form.getRegis_date());
        values.put("return_date",form.getReturn_date());
        values.put("quantity",form.getQuantity());
        values.put("total",form.getTotal());

        long id = db.insert(TABLE_NAME,null, values);
        if(id <0) {
            return -1; //insert không thành công
        }else {
            return id; //insert thành công
        }
    }

    //update
    public void updateForm(Form form) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_book",form.getId_book());
        values.put("id_user",form.getId_user());
        values.put("code",form.getCode());
        values.put("type",form.getType());
        values.put("status",form.getStatus());
        values.put("regis_date",form.getRegis_date());
        values.put("return_date",form.getReturn_date());
        values.put("quantity",form.getQuantity());
        values.put("total",form.getTotal());

        db.update(TABLE_NAME,values,"id = ?", new String[]{String.valueOf(form.getId())});
        //db.close();
    }

    //delete
    public void deleteForm(int formId) {
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME,"id = ?",new String[]{String.valueOf(formId)});
        //db.close();
    }

    //getFormById
    public Form getFormByID(long formID) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,  "id = ?",new String[] {String.valueOf(formID)},null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        Form form = new Form( cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7),
                cursor.getInt(8), cursor.getInt(9));
        cursor.close();
        //db.close();
        return form;
    }

    //getAllForms
    public List<Form> getAllForms() {
        List<Form> formList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            Form form = new Form( cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7),
                    cursor.getInt(8), cursor.getInt(9));
            formList.add(form);
            cursor.moveToNext();
        }
        cursor.close();
        //db.close();
        return formList;
    }

    //getAllFormsByType
    public List<Form> getAllFormsByType(String type) {
        List<Form> formList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE type = ?";

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[] {type});
        if(cursor!=null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            Form form = new Form( cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7),
                    cursor.getInt(8), cursor.getInt(9));
            formList.add(form);
            cursor.moveToNext();
        }
        cursor.close();
        //db.close();
        return formList;
    }

    //getAllFormsByType
    public List<Form> getAllFormsByType(String type, String status) {
        List<Form> formList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE type = ? AND status = ?";

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[] {type, status});
        if(cursor!=null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            Form form = new Form( cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7),
                    cursor.getInt(8), cursor.getInt(9));
            formList.add(form);
            cursor.moveToNext();
        }
        cursor.close();
        //db.close();
        return formList;
    }

    //getAllFormsByIdUser
    public List<Form> getAllFormsByIdUser(int id_user) {
        List<Form> formList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_user = ?";

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[] {String.valueOf(id_user)});
        if(cursor!=null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            Form form = new Form( cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7),
                    cursor.getInt(8), cursor.getInt(9));
            formList.add(form);
            cursor.moveToNext();
        }
        cursor.close();
        //db.close();
        return formList;
    }

    //getAllFormsByIdUserType
    public List<Form> getAllFormsByIdUserType(int id_user, String type) {
        List<Form> formList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id_user = ? AND type = ?";

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[] {String.valueOf(id_user), type});
        if(cursor!=null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            Form form = new Form( cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7),
                    cursor.getInt(8), cursor.getInt(9));
            formList.add(form);
            cursor.moveToNext();
        }
        cursor.close();
        //db.close();
        return formList;
    }
}
