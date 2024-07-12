package lam.tutorials.libraryapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lam.tutorials.libraryapp.R;
import lam.tutorials.libraryapp.adapter.FormAdapter;
import lam.tutorials.libraryapp.database.FormDAO;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.FragmentRegisFormBinding;
import lam.tutorials.libraryapp.entity.Form;
import lam.tutorials.libraryapp.entity.User;


public class RegisFormFragment extends Fragment {
    FragmentRegisFormBinding binding;
    RecyclerView recyclerView;
    List<Form> formList;
    List<Form> borrowFormList;
    List<Form> buyFormList;
    FormAdapter adapter;
    private int id_user;
    ArrayAdapter arrayAdapter = null;
    UserDAO userDAO;
    FormDAO formDAO;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            id_user = getArguments().getInt("id_user",0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        binding.recyclerView.setLayoutManager(gridLayoutManager);

        userDAO = new UserDAO(getContext());
        formDAO = new FormDAO(getContext());

        User u = userDAO.getUserByID(id_user);
        String role = u.getRole();

        formList = new ArrayList<>();
        buyFormList = new ArrayList<>();
        borrowFormList = new ArrayList<>();
        formList = formDAO.getAllFormsByIdUser(id_user);
        //buyFormList = formDAO.getAllFormsByIdUserType(id_user,"BuyForm");
        //borrowFormList = formDAO.getAllFormsByIdUserType(id_user,"BorrowForm");
        Collections.reverse(formList);
        //Collections.reverse(buyFormList);
        //Collections.reverse(borrowFormList);

        adapter = new FormAdapter(getContext(),formList,id_user,role);
        binding.recyclerView.setAdapter(adapter);

        arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.formstatusfilter, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinStatus.setAdapter(arrayAdapter);



        binding.spinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = (String) parent.getItemAtPosition(position);
                if(status.equals("Tất cả")) {
                    adapter.changDataList(formList);
                }else{
                    filterListStatus(status);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.searchForm.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchListForm(newText);
                return true;
            }
        });
    }

    //Phương thức tìm kiếm đơn theo mã
    private void searchListForm(String text) {
        ArrayList<Form> searchList = new ArrayList<>();
        for(Form form:formList) {
            if(form.getCode().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(form);
            }
        }
        adapter.changDataList(searchList);
    }

    //Phương thức thay đổi list data theo trạng thái
    public void filterListStatus(String status) {
        ArrayList<Form> filterListStatus = new ArrayList<>();
        for(Form form:formList) {
            if(form.getStatus().toLowerCase().equals(status.toLowerCase())) {
                filterListStatus.add(form);
            }
        }
        adapter.changDataList(filterListStatus);
    }
}