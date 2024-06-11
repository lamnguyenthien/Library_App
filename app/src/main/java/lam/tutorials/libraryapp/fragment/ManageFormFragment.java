package lam.tutorials.libraryapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lam.tutorials.libraryapp.R;
import lam.tutorials.libraryapp.adapter.FormAdapter;
import lam.tutorials.libraryapp.database.BookDAO;
import lam.tutorials.libraryapp.database.FormDAO;
import lam.tutorials.libraryapp.database.UserDAO;
import lam.tutorials.libraryapp.databinding.FragmentManageFormBinding;
import lam.tutorials.libraryapp.entity.Form;
import lam.tutorials.libraryapp.entity.User;


public class ManageFormFragment extends Fragment {

    FragmentManageFormBinding binding;
    RecyclerView recyclerView;
    List<Form> formList;
    List<Form> borrowFormList;
    List<Form> buyFormList;
    FormAdapter adapter;
    private int id_user;
    ArrayAdapter arrayAdapter = null;
    private int fil =1;


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
        binding = FragmentManageFormBinding.inflate(inflater, container, false);
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
        formList = formDAO.getAllForms();
        buyFormList = formDAO.getAllFormsByType("BuyForm");
        borrowFormList = formDAO.getAllFormsByType("BorrowForm");
        Collections.reverse(formList);
        Collections.reverse(buyFormList);
        Collections.reverse(borrowFormList);

        adapter = new FormAdapter(getContext(),formList,id_user,role);
        binding.recyclerView.setAdapter(adapter);

        binding.tablerowStatus.setVisibility(View.GONE);


        arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.formstatusfilter, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinStatus.setAdapter(arrayAdapter);

        binding.spinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = (String) parent.getItemAtPosition(position);
                borrowFormList = formDAO.getAllFormsByType("BorrowForm");
                if(status.equals("Tất cả")) {
                    adapter.changDataList(borrowFormList);
                }else{
                    filterListStatus(status);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changDataList(buyFormList);
                fil = 2;
                binding.tablerowStatus.setVisibility(View.GONE);
            }
        });

        binding.btnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changDataList(borrowFormList);
                fil = 3;
                borrowFormList = formDAO.getAllFormsByType("BorrowForm");
                binding.tablerowStatus.setVisibility(View.VISIBLE);
            }
        });

        binding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changDataList(formList);
                fil = 1;
                binding.tablerowStatus.setVisibility(View.GONE);
            }
        });

        binding.btnExportFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyFormList = formDAO.getAllFormsByType("BuyForm");
                borrowFormList = formDAO.getAllFormsByType("BorrowForm");
                exportToExcecl(buyFormList,borrowFormList);
            }
        });

        binding.searchForm.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
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

    public void filterListStatus(String status) {
        ArrayList<Form> filterListStatus = new ArrayList<>();
        for(Form form:borrowFormList) {
            if(form.getStatus().toLowerCase().equals(status.toLowerCase())) {
                filterListStatus.add(form);
            }
        }
        adapter.changDataList(filterListStatus);
    }

    private void searchListForm(String text) {
        ArrayList<Form> searchList = new ArrayList<>();
        if(fil == 1) {
            for(Form form:formList) {
                if(form.getCode().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(form);
                    Log.d("Test_fil1","Add");
                }
                Log.d("Test_fil1","Hello");
            }
        }else if(fil == 2) {
            for(Form form:buyFormList) {
                if(form.getCode().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(form);
                }
            }
        }else if(fil==3) {
            for(Form form:borrowFormList) {
                if(form.getCode().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(form);
                }
            }
        }
        adapter.changDataList(searchList);
    }

    private void exportToExcecl(List<Form> buyFormList, List<Form> borrowFormList) {
        //Lấy thời điểm hiện tại
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        String currentDate = dateFormat.format(date);

        //Tạo file
        Workbook workbook = new XSSFWorkbook();
        Sheet buyFormSheet = workbook.createSheet("Buy Form");
        Sheet borrowFormSheet = workbook.createSheet("Borrow Form");

        Row headerRowBuy = buyFormSheet.createRow(0);
        String[] headers = {"Id", "Id_book", "Id_user","Ngày mua","Số lượng","Thành tiền"};
        for(int i = 0; i < headers.length; i++) {
            Cell headerCell = headerRowBuy.createCell(i);
            headerCell.setCellValue(headers[i]);
        }

        Row headerRowBorrow = borrowFormSheet.createRow(0);
        String[] headers2 = {"Id", "Id_book", "Id_user","Ngày đăng ký","Ngày nhận","Ngày trả","Số lượng", "Tiền ứng"};
        for(int i = 0; i < headers2.length;i++) {
            Cell headerCell = headerRowBorrow.createCell(i);
            headerCell.setCellValue(headers2[i]);
        }

        for(int i = 0; i < buyFormList.size(); i++) {
            Form form = buyFormList.get(i);
            Row dataRow = buyFormSheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(form.getId());
            dataRow.createCell(1).setCellValue(form.getId_book());
            dataRow.createCell(2).setCellValue(form.getId_user());
            dataRow.createCell(3).setCellValue(form.getRegis_date());
            dataRow.createCell(4).setCellValue(form.getQuality());
            dataRow.createCell(5).setCellValue(form.getTotal());
        }

        for(int i = 0; i < borrowFormList.size(); i++) {
            Form form = borrowFormList.get(i);
            Row dataRow = borrowFormSheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(form.getId());
            dataRow.createCell(1).setCellValue(form.getId_book());
            dataRow.createCell(2).setCellValue(form.getId_user());
            dataRow.createCell(3).setCellValue(form.getRegis_date());
            dataRow.createCell(4).setCellValue(form.getReceive_date());
            dataRow.createCell(5).setCellValue(form.getReturn_date());
            dataRow.createCell(6).setCellValue(form.getQuality());
            dataRow.createCell(7).setCellValue(form.getTotal());
        }

        try {
            File file = new File(getContext().getExternalFilesDir(null),"FormList_"+currentDate+ ".xlsx");

            if(file.getParentFile()!=null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            Log.d("export_file", file.getAbsolutePath());
            Toast.makeText(getContext(), "Xuất file thành công", Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Xuất file thất bại" + e, Toast.LENGTH_LONG).show();
        }
    }


}