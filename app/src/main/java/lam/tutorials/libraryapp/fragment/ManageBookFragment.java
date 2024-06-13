package lam.tutorials.libraryapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lam.tutorials.libraryapp.UploadBookActivity;
import lam.tutorials.libraryapp.adapter.BookAdapter;
import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.FragmentManageBookBinding;
import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.User;


public class ManageBookFragment extends Fragment {

    FragmentManageBookBinding binding;
    RecyclerView recyclerView;
    List<Book> bookList;
    List<Book> bookListEnable;
    SearchView searchView;
    private int id_user;
    BookAdapter adapter;
    private static final int REQUEST_CODE = 123;

    BookDAO bookDAO;
    UserDAO userDAO;
    @Override
    public void onResume() {
        super.onResume();
        bookDAO = new BookDAO(getContext());
        userDAO = new UserDAO(getContext());
        User u = userDAO.getUserByID(id_user);
        String role = u.getRole();
        bookListEnable = new ArrayList<>();
        bookListEnable = bookDAO.getAllBooksEnable(1);
        adapter = new BookAdapter(getContext(),bookListEnable,id_user, role);
        binding.recyclerView.setAdapter(adapter);
    }

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
        // Inflate the layout for this fragment
        binding = FragmentManageBookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.search.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        binding.recyclerView.setLayoutManager(gridLayoutManager);

        bookDAO = new BookDAO(getContext());
        userDAO = new UserDAO(getContext());

        User u = userDAO.getUserByID(id_user);
        String role = u.getRole();
        bookListEnable = new ArrayList<>();
        bookListEnable = bookDAO.getAllBooksEnable(1);
        adapter = new BookAdapter(getContext(),bookListEnable,id_user, role);
        binding.recyclerView.setAdapter(adapter);



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), UploadBookActivity.class);
                startActivity(intent);
            }
        });

        binding.search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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

        binding.btnExportFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                } else {
                    Log.d("export_file", "Yêu cầu xong");
                    // Tiếp tục với quá trình xuất dữ liệu
                    exportToExcel(bookList);
                }*/
                bookList = bookDAO.getAllBooks();
                exportToExcel(bookList);

            }
        });
    }

    private void searchList(String text) {
        ArrayList<Book> searchList = new ArrayList<>();
        for(Book book:bookListEnable) {
            if(book.getName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(book);
            }
        }
        adapter.serachDataList(searchList);
    }

    private void exportToExcel(List<Book> dataList) {
        //Lấy thời điểm hiện tại
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        String currentDate = dateFormat.format(date);

        //Tạo file
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("BookList_" + currentDate);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Id", "Tên sách", "Tác giả", "NXB", "Năm xuất bản", "Thể loại", "Loại", "Ngành", "Số sách bán", "Số sách mượn","Đang mượn","Trạng thái"};
        for (int i = 0; i < headers.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
        }

        for(int i = 0; i < dataList.size();i++) {
            Book book = dataList.get(i);
            Row dataRow = sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(book.getId());
            dataRow.createCell(1).setCellValue(book.getName());
            dataRow.createCell(2).setCellValue(book.getAuthor());
            dataRow.createCell(3).setCellValue(book.getPublish_comp());
            dataRow.createCell(4).setCellValue(book.getPublish_date());
            dataRow.createCell(5).setCellValue(book.getCategory());
            dataRow.createCell(6).setCellValue(book.getType());
            dataRow.createCell(7).setCellValue(book.getFaculty());
            dataRow.createCell(8).setCellValue(book.getAvailableForSale());
            dataRow.createCell(9).setCellValue(book.getAvailableForLoan());
            dataRow.createCell(10).setCellValue(book.getBorrowedQuantity());
            dataRow.createCell(11).setCellValue(book.getEnable());

        }

        try{
            File file = new File(getContext().getExternalFilesDir(null), "BookList_" + currentDate + ".xlsx");

            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
                FileOutputStream outputStream = new FileOutputStream(file);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
                Log.d("export_file", file.getAbsolutePath());
                Toast.makeText(getContext(), "Xuất file thành công", Toast.LENGTH_LONG).show();
        }catch (IOException e) {
            e.printStackTrace();
            Log.d("export_file", e.toString());

            Toast.makeText(getContext(), "Xuất file thất bại" + e, Toast.LENGTH_LONG).show();
        }

    }

}