package lam.tutorials.libraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import lam.tutorials.libraryapp.databinding.ActivityStudentMainBinding;
import lam.tutorials.libraryapp.fragment.AccountFragment;
import lam.tutorials.libraryapp.fragment.FindBookFragment;
import lam.tutorials.libraryapp.fragment.ManageBookFragment;
import lam.tutorials.libraryapp.fragment.ManageFormFragment;
import lam.tutorials.libraryapp.fragment.RegisFormFragment;

public class StudentMainActivity extends AppCompatActivity {

    ActivityStudentMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        int id_user = extras.getInt("id_user", 0); // Đặt giá trị mặc định nếu không có
        String name_user = extras.getString("name_user");
        binding.toolbarGreet.setTitle("Xin chào, " + name_user);

        Bundle bundle_create = new Bundle();
        bundle_create.putInt("id_user", id_user);
        RegisFormFragment regisFormFragment_create = new RegisFormFragment();
        regisFormFragment_create.setArguments(bundle_create);
        replaceFragment(regisFormFragment_create);
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            Bundle bundle = new Bundle();
            bundle.putInt("id_user", id_user);
            if (item.getItemId() == R.id.formregis ) {
                RegisFormFragment regisFormFragment = new RegisFormFragment();
                regisFormFragment.setArguments(bundle);
                replaceFragment(regisFormFragment);
            } else if (item.getItemId() == R.id.findbook) {
                FindBookFragment findBookFragment = new FindBookFragment();
                findBookFragment.setArguments(bundle);
                replaceFragment(findBookFragment);
            } else if (item.getItemId() == R.id.account) {
                AccountFragment accountFragment = new AccountFragment();
                accountFragment.setArguments(bundle);
                replaceFragment(accountFragment);
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}