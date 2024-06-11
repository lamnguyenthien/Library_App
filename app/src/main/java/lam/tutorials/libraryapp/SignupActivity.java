package lam.tutorials.libraryapp;

import static lam.tutorials.libraryapp.service.UserService.checkCode;
import static lam.tutorials.libraryapp.service.UserService.checkEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.ActivitySignupBinding;
import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.User;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = binding.signupStucode.getText().toString();
                String name = binding.signupFullname.getText().toString();
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();
                String role = "Student";
                if(email.equals("")||password.equals("")||confirmPassword.equals("")
                ||name.equals("")||code.equals(""))
                    Toast.makeText(SignupActivity.this, "Vui lòng điền đầy dủ thông tin", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(confirmPassword)) {
                        if(checkEmail(email, SignupActivity.this)) {
                            Toast.makeText(SignupActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                        }else{
                            if(checkCode(code,SignupActivity.this)) {
                                Toast.makeText(SignupActivity.this, "Mã sinh viên đã đăng ký", Toast.LENGTH_SHORT).show();
                            }else{
                                User nUser = new User(code, email, name, role, password,1);
                                Log.d("Signup","Btn");
                                UserDAO userDAO = new UserDAO(getApplicationContext());
                                BookDAO bookDAO = new BookDAO(getApplicationContext());
                                nUser = new User("2021604260", "tranduchuy@gmail.com", "Trần Đức Huy", "Student", "123456",1);
                                userDAO.insertUser(nUser);
                                nUser = new User("2021604261", "dohavi@gmail.com", "Đỗ Hà Vi", "Student", "123456",1);
                                userDAO.insertUser(nUser);
                                nUser = new User("2021604263", "nguyenminhhieu@gmail.com", "Nguyễn Minh Hiếu", "Student", "123456",1);
                                userDAO.insertUser(nUser);
                                nUser = new User("2021604264", "trinhgialoc@gmail.com", "Trịnh Gia Lộc", "Student", "123456",1);
                                userDAO.insertUser(nUser);
                                nUser = new User("GV_01", "hoangquanghuy@gmail.com", "Hoàng Quang Huy", "Teacher", "123456",1);
                                userDAO.insertUser(nUser);
                                nUser = new User("GV_02", "vuthiduong@gmail.com", "Vũ Thị Dương", "Teacher", "123456",1);
                                userDAO.insertUser(nUser);
                                Book nBook = new Book("Nguyên lý hệ điều hành", "Nguyễn Bá Nghiễn", "2020", "Kim Đồng", 20, 2, "Khoa học", "Giáo trình", "CNTT",20000,1);
                                bookDAO.insertBook(nBook);
                                nBook = new Book("Phát triển ứng dụng di động", "Nguyễn Bá Nghiễn", "2022", "Thống kê", 22, 2, "Khoa học", "Giáo trình", "CNTT",20000,1);
                                bookDAO.insertBook(nBook);
                                nBook = new Book("Giàu từ chứng khoán", "John Boik", "2016", "Lao động", 22, 2, "Kinh tế", "Sách", "Không",79000,1);
                                bookDAO.insertBook(nBook);
                                if(userDAO.insertUser(nUser) < 0) {
                                    Toast.makeText(SignupActivity.this,"Đăng ký không thành công",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(SignupActivity.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    }else{
                        Toast.makeText(SignupActivity.this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}