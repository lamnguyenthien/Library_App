package lam.tutorials.libraryapp;

import static lam.tutorials.libraryapp.service.BookService.checkBookIsExist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.databinding.ActivityUploadBookBinding;
import lam.tutorials.libraryapp.entity.Book;

public class UploadBookActivity extends AppCompatActivity {

    ActivityUploadBookBinding binding;

    BookDAO bookDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityUploadBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bookDAO = new BookDAO(this);

        binding.btnUploadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.nbookName.getText().toString();
                String author = binding.nbookAuthor.getText().toString();
                String publish_comp = binding.nbookPubishComp.getText().toString();
                String publish_date = binding.nbookPublishYear.getText().toString();
                String type = binding.nbookType.getText().toString();
                String category = binding.nbookCategory.getText().toString();
                String faculty = binding.nbookFaculty.getText().toString();
                String avail_sale = binding.nbookAvailSale.getText().toString();
                String avail_loan = binding.nbookAvailLoan.getText().toString();
                String quantity_borrow = binding.nbookQuantityBorrow.getText().toString();
                String price_string = binding.nbookPrice.getText().toString();
                long price = Long.parseLong(price_string);
                int sale = Integer.parseInt(avail_sale);
                int loan = Integer.parseInt(avail_loan);
                int quantity = Integer.parseInt(quantity_borrow);

                if(name.equals("")||author.equals("")||publish_comp.equals("")||publish_date.equals("")||price_string.equals("")
                    ||type.equals("")||category.equals("")||faculty.equals("")||avail_sale.equals("")||avail_loan.equals("")||quantity_borrow.equals("")) {

                    Toast.makeText(UploadBookActivity.this,"Nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }else{
                    if(checkBookIsExist(name,publish_date,author,publish_comp,UploadBookActivity.this)) {
                        Toast.makeText(UploadBookActivity.this,"Sách đã tồn tại",Toast.LENGTH_LONG).show();
                    }else{
                        Book nBook = new Book(name, author, publish_date, publish_comp, sale, loan,quantity, category, type, faculty,price,1);
                        if(bookDAO.insertBook(nBook) < 0) {
                            Toast.makeText(UploadBookActivity.this,"Thêm không thành công",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(UploadBookActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}