package lam.tutorials.libraryapp;

import static lam.tutorials.libraryapp.service.UserService.checkAccount;
import static lam.tutorials.libraryapp.service.UserService.isStudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.ActivityLoginBinding;
import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.User;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UserDAO userDAO = new UserDAO(this);
        BookDAO bookDAO = new BookDAO(this);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.loginCode.getText().toString();
                String password = binding.loginPassword.getText().toString();
                if(code.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                else{
                    if(checkAccount(code,password, LoginActivity.this)) {
                        if(isStudent(code,password, LoginActivity.this)) {
                            int id = userDAO.getIdByAccount(code, password);
                            User user = userDAO.getUserByID(id);
                            String name = user.getFullname();
                            Intent intent  = new Intent(getApplicationContext(), StudentMainActivity.class);
                            intent.putExtra("id_user", id);
                            intent.putExtra("name_user", name);
                            startActivity(intent);
                        }else{
                            int id = userDAO.getIdByAccount(code, password);
                            User user = userDAO.getUserByID(id);
                            String name = user.getFullname();
                            Intent intent  = new Intent(getApplicationContext(), TeacherMainActivity.class);
                            intent.putExtra("id_user", id);
                            intent.putExtra("name_user", name);
                            Log.d("Login_test","Manager");
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}