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

import java.util.ArrayList;
import java.util.List;

import lam.tutorials.libraryapp.adapter.BookAdapter;
import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.FragmentFindBookBinding;
import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.User;


public class FindBookFragment extends Fragment {

    FragmentFindBookBinding binding;
    RecyclerView recyclerView;
    List<Book> bookListEnable;
    SearchView searchView;
    private int id_user;
    BookAdapter adapter;
    BookDAO bookDAO;
    UserDAO userDAO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_user = getArguments().getInt("id_user", 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        bookDAO = new BookDAO(getContext());
        userDAO = new UserDAO(getContext());
        User u = userDAO.getUserByID(id_user);
        String role = u.getRole();
        bookListEnable = new ArrayList<>();
        bookListEnable = bookDAO.getAllBooksEnable(1);
        adapter = new BookAdapter(getContext(), bookListEnable, id_user, role);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFindBookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.search.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recyclerView.setLayoutManager(gridLayoutManager);

        bookDAO = new BookDAO(getContext());
        userDAO = new UserDAO(getContext());
        User u = userDAO.getUserByID(id_user);
        String role = u.getRole();
        bookListEnable = new ArrayList<>();
        bookListEnable = bookDAO.getAllBooksEnable(1);
        adapter = new BookAdapter(getContext(), bookListEnable, id_user, role);
        binding.recyclerView.setAdapter(adapter);

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

    }

    public void searchList(String text) {
        ArrayList<Book> searchList = new ArrayList<>();
        for (Book book : bookListEnable) {
            if (book.getName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(book);
            }
        }
        adapter.serachDataList(searchList);
    }
}
