package lam.tutorials.libraryapp.service;

import android.content.Context;

import java.util.List;

import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.entity.Book;

public class BookService {

    public static boolean checkBookIsExist(String name, String date, String author, String comp, Context context) {
        BookDAO bookDAO = new BookDAO(context);
        List<Book> bookList = bookDAO.getAllBooks();
        for(Book book : bookList) {
            if(book.getName().equals(name) && book.getPublish_date().equals(date)
            && book.getPublish_comp().equals(comp) && book.getAuthor().equals(author)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameBookById(int id_book, Context context) {
        BookDAO bookDAO = new BookDAO(context);
        Book book = bookDAO.getBookByID(id_book);
        return book.getName();
    }
}
