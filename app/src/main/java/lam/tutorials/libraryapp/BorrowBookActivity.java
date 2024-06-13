package lam.tutorials.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.database.FormDAO;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.ActivityBorrowBookBinding;
import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.Form;
import lam.tutorials.libraryapp.entity.User;

public class BorrowBookActivity extends AppCompatActivity {

    ActivityBorrowBookBinding binding;

    String selectedDate;
    String returnDate = "";
    UserDAO userDAO;
    BookDAO bookDAO;
    FormDAO formDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBorrowBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDAO = new UserDAO(getApplicationContext());
        bookDAO = new BookDAO(getApplicationContext());
        formDAO = new FormDAO(getApplicationContext());

        Intent intent = getIntent();
        int id_student = intent.getIntExtra("id_student", 0);
        int id_book = intent.getIntExtra("id_book",0);
        User student = userDAO.getUserByID(id_student);
        Book book = bookDAO.getBookByID(id_book);

        binding.tvtStuName.setText("Họ tên: " + student.getFullname());
        binding.tvtStuCode.setText("MSV: " + student.getCode());
        binding.tvtBName.setText("Tên sách: " + book.getName());
        binding.tvtBAuthor.setText("Tác giả: " + book.getAuthor());
        binding.tvtBPublishComp.setText("NXB: " + book.getPublish_comp());
        binding.tvtBPublishYear.setText("Năm: " + book.getPublish_date());
        //binding.tvtBCategory.setText("Thể loại: " + book.getCategory());
        //binding.tvtBType.setText("Loại: " + book.getType());
        //binding.tvtBFaculty.setText("Ngành: " + book.getFaculty());
        binding.tvtBookPrice.setText("Giá: " + book.getPrice() + " VND");

        binding.btnReturnDate.setOnClickListener( v -> {
            Calendar calender = Calendar.getInstance();
            int year = calender.get(Calendar.YEAR);
            int month = calender.get(Calendar.MONTH);
            int dayOfMonth = calender.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear,selectedMonth,selectedDayOfMonth);
                        int day_of_week = selectedDate.get(Calendar.DAY_OF_WEEK);

                        if(selectedDate.getTimeInMillis() >= calender.getTimeInMillis()) {
                            if(day_of_week == 7 || day_of_week == 1) {
                                Toast.makeText(getApplicationContext(), "Chọn ngày trả trong tuần",Toast.LENGTH_LONG).show();
                            }else{
                                returnDate = String.format("%02d-%02d-%d",selectedDayOfMonth , selectedMonth + 1, selectedYear);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Chọn ngày trong tương lai",Toast.LENGTH_LONG).show();
                        }
                    },
                    year, month, dayOfMonth
            );
            datePickerDialog.show();
        });


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
                    int quality = Integer.parseInt(binding.editQuality.getText().toString());
                    if(quality > book.getAvailableForLoan()) {
                        Toast.makeText(getApplicationContext(), "Thư viện không đủ số lượng sách", Toast.LENGTH_SHORT).show();
                    }else{
                        long total = quality * book.getPrice();
                        binding.editTotal.setText(String.valueOf(total));
                    }
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số lượng hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnBorrowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editQuality.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số lượng hợp lệ", Toast.LENGTH_SHORT).show();
                }else{
                    if(returnDate.equals("")) {
                        Toast.makeText(getApplicationContext(), "Vui lòng chọn ngày trả sách", Toast.LENGTH_SHORT).show();
                    }else{
                        int quantity = Integer.parseInt(binding.editQuality.getText().toString());
                        Book cbook = bookDAO.getBookByID(id_book);
                        if(quantity > cbook.getAvailableForLoan()) {
                            Toast.makeText(getApplicationContext(), "Thư viện không đủ sách mượn", Toast.LENGTH_SHORT).show();
                        }else{
                            long total = quantity * cbook.getPrice();
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String currentDate = dateFormat.format(date);

                            Form borrowform = new Form(id_book, id_student,"", "BorrowForm", "Chờ nhận", currentDate, returnDate, quantity,total);
                            int stock = cbook.getAvailableForLoan() - quantity;
                            int borrow = cbook.getBorrowedQuantity() + quantity;
                            cbook.setAvailableForLoan(stock);
                            cbook.setBorrowedQuantity(borrow);
                            bookDAO.updateBook(cbook);
                            long new_id = formDAO.insertForm(borrowform);
                            if(new_id < 0) {
                                Toast.makeText(getApplicationContext(), "Đăng ký mượn không thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Form newForm = formDAO.getFormByID(new_id);
                                newForm.setCode(newForm.getType()+"_"+newForm.getId());
                                formDAO.updateForm(newForm);
                                Toast.makeText(getApplicationContext(), "Đăng ký mượn thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }

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