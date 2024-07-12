package lam.tutorials.libraryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.ActivityTeacherBookDetailBinding;
import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.User;

public class TeacherBookDetailActivity extends AppCompatActivity {
    ActivityTeacherBookDetailBinding binding;

    UserDAO userDAO;
    BookDAO bookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherBookDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDAO = new UserDAO(this);
        bookDAO = new BookDAO(this);

        Intent intent = getIntent();
        int id_book = intent.getIntExtra("id_book", 0);
        Book cBook = bookDAO.getBookByID(id_book);
        binding.editBookName.setText(cBook.getName());
        binding.editBookYear.setText(cBook.getPublish_date());
        binding.editBookAuthor.setText(cBook.getAuthor());
        binding.editPublishComp.setText(cBook.getPublish_comp());
        binding.editBookType.setText(cBook.getType());
        binding.editBookCategory.setText(cBook.getCategory());
        binding.editBookFaculty.setText(cBook.getFaculty());
        binding.editAvaiForSale.setText(String.valueOf(cBook.getAvailableForSale()));
        binding.editAvaiForLoan.setText(String.valueOf(cBook.getAvailableForLoan()));
        binding.editQuantityBorrow.setText(String.valueOf(cBook.getBorrowedQuantity()));
        binding.editBookPrice.setText(String.valueOf(cBook.getPrice()));

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherBookDetailActivity.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");

                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Book dBook = bookDAO.getBookByID(id_book);;
                        dBook.setEnable(0);
                        bookDAO.updateBook(dBook);
                        Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng dialog
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        binding.btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book oBook = bookDAO.getBookByID(id_book);
                String book_name = binding.editBookName.getText().toString();
                String book_date = binding.editBookYear.getText().toString();
                String book_author = binding.editBookAuthor.getText().toString();
                String book_comp = binding.editPublishComp.getText().toString();
                String book_type = binding.editBookType.getText().toString();
                String book_category = binding.editBookCategory.getText().toString();
                String book_faculty = binding.editBookFaculty.getText().toString();
                String quantity_borrow = binding.editQuantityBorrow.getText().toString();
                String avail_for_sale = binding.editAvaiForSale.getText().toString();
                String avail_for_loan = binding.editAvaiForLoan.getText().toString();
                String price = binding.editBookPrice.getText().toString();
                if(book_name.equals("")||book_date.equals("")||book_author.equals("")||price.equals("")||
                   book_comp.equals("")||book_type.equals("")||book_category.equals("")||
                   book_faculty.equals("")||quantity_borrow.equals("")||avail_for_sale.equals("")||avail_for_loan.equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    int availsale = Integer.parseInt(avail_for_sale);
                    int availloan = Integer.parseInt(avail_for_loan);
                    int quantityborrow = Integer.parseInt(quantity_borrow);
                    long price_book = Long.parseLong(price);
                    oBook.setName(book_name);
                    oBook.setAuthor(book_author);
                    oBook.setPublish_comp(book_comp);
                    oBook.setPublish_date(book_date);
                    oBook.setType(book_type);
                    oBook.setCategory(book_category);
                    oBook.setFaculty(book_faculty);
                    oBook.setAvailableForSale(availsale);
                    oBook.setAvailableForLoan(availloan);
                    oBook.setBorrowedQuantity(quantityborrow);
                    oBook.setPrice(price_book);
                    bookDAO.updateBook(oBook);
                    Toast.makeText(getApplicationContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}