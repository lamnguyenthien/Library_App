package lam.tutorials.libraryapp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import lam.tutorials.libraryapp.LoginActivity;
import lam.tutorials.libraryapp.SignupActivity;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.DialogChangePasswordBinding;
import lam.tutorials.libraryapp.databinding.FragmentAccountBinding;
import lam.tutorials.libraryapp.entity.User;


public class AccountFragment extends Fragment {
    private int id_user;
    FragmentAccountBinding binding;
    UserDAO userDAO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            id_user = getArguments().getInt("id_user",0);
        }
        userDAO = new UserDAO(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnUpdateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User ouser = userDAO.getUserByID(id_user);
                String fullname = binding.editFullname.getText().toString();
                String email = binding.editEmail.getText().toString();
                ouser.setEmail(email);
                ouser.setFullname(fullname);
                userDAO.updateUser(ouser);
                Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        binding.btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog(id_user);
            }
        });
    }

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        User u = userDAO.getUserByID(id_user);
        String role = u.getRole();

        if(role.equals("Student")) {
            binding.btnSignup.setVisibility(View.GONE);
        }
        binding.tvtCode.setText(u.getCode());
        binding.editFullname.setText(u.getFullname());
        binding.editEmail.setText(u.getEmail());
        binding.tvtRole.setText(u.getRole());
        return binding.getRoot();
    }

    private void showChangePasswordDialog(int id_user) {
        final Dialog dialog = new Dialog(getContext());
        DialogChangePasswordBinding dialogbinding = DialogChangePasswordBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogbinding.getRoot());

        User ouser = userDAO.getUserByID(id_user);




        dialogbinding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentpass = dialogbinding.editCurrentPass.getText().toString();
                String newpass = dialogbinding.editNewPass.getText().toString();
                String confirmpass = dialogbinding.editConfirmPass.getText().toString();

                if(ouser.getPassword().equals(currentpass)) {
                    if(newpass.equals(confirmpass)) {
                        ouser.setPassword(newpass);
                        userDAO.updateUser(ouser);
                        Toast.makeText(getContext(), "Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogbinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}