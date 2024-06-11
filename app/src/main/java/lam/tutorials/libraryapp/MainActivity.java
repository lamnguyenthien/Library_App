package lam.tutorials.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import lam.tutorials.libraryapp.databinding.ActivityMainBinding;
import lam.tutorials.libraryapp.entity.Form;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private List<Form> listForm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }


}