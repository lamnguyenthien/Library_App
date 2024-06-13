package lam.tutorials.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.database.FormDAO;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.ActivityBuyBookBinding;
import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.Form;
import lam.tutorials.libraryapp.entity.User;

public class BuyBookActivity extends AppCompatActivity {

    ActivityBuyBookBinding binding;

    UserDAO userDAO;
    BookDAO bookDAO;
    FormDAO formDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDAO = new UserDAO(getApplicationContext());
        bookDAO = new BookDAO(getApplicationContext());
        formDAO = new FormDAO(getApplicationContext());

        Intent intent = getIntent();
        int id_book = intent.getIntExtra("id_book",0);
        Book book = bookDAO.getBookByID(id_book);

        binding.tvtBName.setText("Tên sách: " + book.getName());
        binding.tvtBAuthor.setText("Tác giả: " + book.getAuthor());
        binding.tvtBPublishComp.setText("NXB: " + book.getPublish_comp());
        binding.tvtBPublishYear.setText("Năm: " + book.getPublish_date());
        binding.tvtBCategory.setText("Thể loại: " + book.getCategory());
        binding.tvtBType.setText("Loại: " + book.getType());
        binding.tvtBFaculty.setText("Ngành: " + book.getFaculty());
        binding.tvtBookPrice.setText("Giá: " + book.getPrice() + " VND");

        binding.editQuality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int quantity = Integer.parseInt(binding.editQuality.getText().toString());
                    if(quantity > book.getAvailableForSale()) {
                        Toast.makeText(getApplicationContext(), "Thư viện không đủ số lượng sách bán", Toast.LENGTH_SHORT).show();
                    }else{
                        long total = quantity * book.getPrice();
                        binding.editTotal.setText(String.valueOf(total));
                    }
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số lượng hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnBuyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editQuality.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số lượng hợp lệ", Toast.LENGTH_SHORT).show();
                }else{
                    int quantity = Integer.parseInt(binding.editQuality.getText().toString());
                    Book cbook = bookDAO.getBookByID(id_book);
                    if(quantity > cbook.getAvailableForSale()) {
                        Toast.makeText(getApplicationContext(), "Thư viện không đủ số lượng sách bn", Toast.LENGTH_SHORT).show();
                    }else{
                        long total = quantity * cbook.getPrice();
                        //Calendar calendar = Calendar.getInstance();
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String currentDate = dateFormat.format(date);
                        Form buyform = new Form(id_book, -1,"" , "BuyForm","Đã bán" , currentDate,null, quantity, total);
                        int stock = cbook.getAvailableForSale() - quantity;
                        cbook.setAvailableForSale(stock);
                        bookDAO.updateBook(cbook);
                        long new_id = formDAO.insertForm(buyform);
                        if(new_id < 0) {
                            Toast.makeText(getApplicationContext(), "Mua không thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Form newForm = formDAO.getFormByID(new_id);
                            newForm.setCode(newForm.getType()+"_"+newForm.getId());
                            formDAO.updateForm(newForm);
                            Toast.makeText(getApplicationContext(), "Mua thành công", Toast.LENGTH_SHORT).show();
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