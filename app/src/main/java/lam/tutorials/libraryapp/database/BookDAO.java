package lam.tutorials.libraryapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.User;

public class BookDAO {
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;
    private Context context;

    public static final String SQL_TAO_BANG_BOOK =
            "CREATE TABLE book (id integer primary key autoincrement," +
                    "name text, author text, publish_date text," +
                    "publish_comp text, availableForSale integer," +
                    "availableForLoan integer,borrowedQuantity integer, category text, type text," +
                    "faculty text, price integer, enable integer)";

    public static final String TABLE_NAME = "book";

    public BookDAO(Context context) {
        this.context = context;
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int insertBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("name",book.getName());
        values.put("author",book.getAuthor());
        values.put("publish_date",book.getPublish_date());
        values.put("publish_comp",book.getPublish_comp());
        values.put("availableForSale",book.getAvailableForSale());
        values.put("availableForLoan",book.getAvailableForLoan());
        values.put("borrowedQuantity",book.getBorrowedQuantity());
        values.put("category",book.getCategory());
        values.put("type",book.getType());
        values.put("faculty",book.getFaculty());
        values.put("price",book.getPrice());
        values.put("enable",book.getEnable());
        if(db.insert(TABLE_NAME,null, values)<0) {
            return -1; //insert không thành công
        }else {
            return 1; //insert thành công
        }
    }

    //update
    public void updateBook (Book book) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",book.getName());
        values.put("author",book.getAuthor());
        values.put("publish_date",book.getPublish_date());
        values.put("publish_comp",book.getPublish_comp());
        values.put("availableForSale",book.getAvailableForSale());
        values.put("availableForLoan",book.getAvailableForLoan());
        values.put("borrowedQuantity",book.getBorrowedQuantity());
        values.put("category",book.getCategory());
        values.put("type",book.getType());
        values.put("faculty",book.getFaculty());
        values.put("price",book.getPrice());
        values.put("enable",book.getEnable());

        db.update(TABLE_NAME,values,"id = ?", new String[]{String.valueOf(book.getId())});
        //db.close();
    }

    //delete
    public void deleteBook(int bookId) {
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME,"id = ?",new String[]{String.valueOf(bookId)});
        //db.close();
    }

    //getBookById
    public Book getBookByID(int bookID) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,  "id = ?",new String[] {String.valueOf(bookID)},null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        Book book = new Book(cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2),  cursor.getString(3),
                            cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),
                            cursor.getString(8), cursor.getString(9), cursor.getString(10),
                            cursor.getInt(11),cursor.getInt(12));
        cursor.close();
        //db.close();
        return book;
    }

    //getAllBooks
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            Book book = new Book(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2),  cursor.getString(3),
                    cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),
                    cursor.getString(8), cursor.getString(9), cursor.getString(10),
                    cursor.getInt(11),cursor.getInt(12));
            bookList.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        //db.close();
        return bookList;
    }

    //getListBookEnable
    public List<Book> getAllBooksEnable(int enable) {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE enable = ?";

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[] {String.valueOf(enable)});
        if(cursor!=null)
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false) {
            Book book = new Book(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2),  cursor.getString(3),
                    cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),
                    cursor.getString(8), cursor.getString(9), cursor.getString(10),
                    cursor.getInt(11),cursor.getInt(12));
            bookList.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        //db.close();
        return bookList;
    }


}
