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

        UserDAO userDAO = new UserDAO(getApplicationContext());
        BookDAO bookDAO = new BookDAO(getApplicationContext());

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
                                if(userDAO.insertUser(nUser) < 0) {
                                    Toast.makeText(SignupActivity.this,"Đăng ký không thành công",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(SignupActivity.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        }
                    }else{
                        Toast.makeText(SignupActivity.this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}