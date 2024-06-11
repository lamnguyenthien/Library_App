package lam.tutorials.libraryapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.apache.xmlbeans.impl.store.Cur;

import java.util.ArrayList;
import java.util.List;

import lam.tutorials.libraryapp.entity.User;

public class UserDAO {
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;
    private Context context;
    public static final String SQL_TAO_BANG_USER =
            "CREATE TABLE user (id integer primary key autoincrement, code text," +
                    "email text, fullname text, role text, password text,enable integer)";

    public static final String TABLE_NAME = "user";

    public UserDAO(Context context) {
        this.context = context;
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("code", user.getCode());
        values.put("email", user.getEmail());
        values.put("fullname", user.getFullname());
        values.put("role", user.getRole());
        values.put("password", user.getPassword());
        values.put("enable", user.getEnable());
        if(db.insert(TABLE_NAME,null, values)<0) {
            return -1; //insert không thành công
        }else {
            return 1; //insert thành công
        }
    }

    //update
    public void updateUser(User user) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", user.getCode());
        values.put("email", user.getEmail());
        values.put("fullname", user.getFullname());
        values.put("role", user.getRole());
        values.put("password", user.getPassword());
        values.put("enable", user.getEnable());

        db.update(TABLE_NAME,values,"id = ?", new String[]{String.valueOf(user.getId())});
        //db.close();
    }

    //delete
    public void deleteUser(int userId) {
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME,"id = ?",new String[]{String.valueOf(userId)});
        //db.close();
    }


    //getUserById
    public User getUserByID(int userID) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,  "id = ?",new String[] {String.valueOf(userID)},null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));
        cursor.close();
        //db.close();
        return user;
    }
    //getAllUsers
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor != null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                               cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));

            userList.add(user);
            cursor.moveToNext();
        };
        cursor.close();
       //db.close();
        return userList;
    }

    //getAllUsersByCode
    public List<User> getAllUserByCode(String code) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE code = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[] {code});
        if(cursor != null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));

            userList.add(user);
            cursor.moveToNext();
        };
        cursor.close();
        //db.close();
        return userList;
    }

    //getAllUserByAccount
    public List<User> getAllUserByAccount(String code, String pass) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE code = ? AND password = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[] {code, pass});
        if(cursor != null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));

            userList.add(user);
            cursor.moveToNext();
        };
        cursor.close();
        db.close();
        return userList;
    }

    //getIdByAccount
    public int getIdByAccount(String code, String pass) {
        String query = "SELECT id FROM " + TABLE_NAME + " WHERE code = ? AND password = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[] {code, pass});
        int id = -1;
        if(cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        Log.d("getIdByAccount",String.valueOf(id));
        return id;
    }
}
