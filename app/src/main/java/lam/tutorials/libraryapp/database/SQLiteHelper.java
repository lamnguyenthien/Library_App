package lam.tutorials.libraryapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "qlythuvien";
    public static final int VERSION = 1;

    //Tạo database
    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    //Tạo bảng dữ liệu
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.SQL_TAO_BANG_USER);
        db.execSQL(FormDAO.SQL_TAO_BANG_FORM);
        db.execSQL(BookDAO.SQL_TAO_BANG_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FormDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BookDAO.TABLE_NAME);

    }
}
