package lam.tutorials.libraryapp.adapter;

import static lam.tutorials.libraryapp.service.BookService.getNameBookById;
import static lam.tutorials.libraryapp.service.UserService.getNameById;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lam.tutorials.libraryapp.R;
import lam.tutorials.libraryapp.TeacherBookDetailActivity;
import lam.tutorials.libraryapp.database.FormDAO;
import lam.tutorials.libraryapp.entity.Book;
import lam.tutorials.libraryapp.entity.Form;

public class FormAdapter extends RecyclerView.Adapter<MyFormViewHolder>
{

    private Context context;
    private List<Form> formlist;
    private int id_user;
    private String role;

    public FormAdapter(Context context, List<Form> formlist, int id_user, String role) {
        this.context = context;
        this.formlist = formlist;
        this.id_user = id_user;
        this.role = role;
    }

    @NonNull
    @Override
    public MyFormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_form_item,parent,false);
        return new MyFormViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyFormViewHolder holder, int position) {
        holder.tvBookName.setText("Tên sách: " + getNameBookById(formlist.get(position).getId_book(),context));
        holder.tvStatus.setText("Trạng thái: " + formlist.get(position).getStatus());
        holder.tvQuality.setText("Số lượng: " + formlist.get(position).getQuantity());
        holder.tvCode.setText("Code: " + formlist.get(position).getCode());
        holder.tvUserName.setVisibility(View.GONE);
        if(role.equals("Student")) {
            holder.btnChangeStatus.setVisibility(View.GONE);
            if(formlist.get(position).getType().equals("BuyForm")) {
                holder.tvRegisDate.setText("Ngày mua: " + formlist.get(position).getRegis_date());
                holder.tvTotal.setText("Tổng tiền: " + formlist.get(position).getTotal() + " VND");
                holder.tableRow.setVisibility(View.GONE);
            }else if (formlist.get(position).getType().equals("BorrowForm")){
                holder.tableRow.setVisibility(View.VISIBLE);
                holder.tvTotal.setText("Tiền ứng: " + formlist.get(position).getTotal() + " VND");
                holder.tvRegisDate.setText("Ngày đăng ký: " + formlist.get(position).getRegis_date());
                holder.tvReturnDate.setText("Ngày trả: " + formlist.get(position).getReturn_date());
            }
        }else {
            holder.btnChangeStatus.setVisibility(View.GONE);
            if(formlist.get(position).getType().equals("BuyForm")) {
                holder.tvRegisDate.setText("Ngày mua: " + formlist.get(position).getRegis_date());
                holder.tvTotal.setText("Tổng tiền: " + formlist.get(position).getTotal() + " VND");
                holder.tableRow.setVisibility(View.GONE);
            }else if (formlist.get(position).getType().equals("BorrowForm")){
                holder.tvUserName.setText(getNameById(formlist.get(position).getId_user(),context));
                holder.tableRow.setVisibility(View.VISIBLE);
                holder.tvTotal.setText("Tiền ứng: " + formlist.get(position).getTotal() + " VND");
                holder.tvRegisDate.setText("Ngày đăng ký: " + formlist.get(position).getRegis_date());
                holder.tvReturnDate.setText("Ngày trả: " + formlist.get(position).getReturn_date());
                holder.btnChangeStatus.setVisibility(View.VISIBLE);
                if(formlist.get(position).getStatus().equals("Chờ nhận")) {
                    holder.btnChangeStatus.setText("Đã nhận");
                }else if(formlist.get(position).getStatus().equals("Đã nhận")) {
                    holder.btnChangeStatus.setText("Đã trả");
                }else if(formlist.get(position).getStatus().equals("Đã trả")) {
                    holder.btnChangeStatus.setVisibility(View.GONE);
                }
            }
        }
        holder.btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận trạng thái");
                builder.setMessage("Bạn có chắc chắn muốn thay đổi trạng thái đơn này?");

                builder.setPositiveButton("Thay đổi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Form cform = formlist.get(holder.getAdapterPosition());
                        FormDAO formDAO = new FormDAO(context);
                        if(cform.getStatus().equals("Chờ nhận")) {
                            cform.setStatus("Đã nhận");
                            formDAO.updateForm(cform);
                            notifyDataSetChanged();
                        }else if(cform.getStatus().equals("Đã nhận")) {
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
                            String currentDate = dateFormat.format(date);

                            cform.setReturn_date(currentDate);
                            cform.setStatus("Đã trả");
                            formDAO.updateForm(cform);
                            notifyDataSetChanged();
                        }
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng dialog
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        holder.formCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getBindingAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return formlist.size();
    }

    public void changDataList(List<Form> CurrentFormList) {
        formlist = CurrentFormList;
        notifyDataSetChanged();
    }

}

class MyFormViewHolder extends RecyclerView.ViewHolder {

    TextView tvBookName, tvUserName, tvStatus, tvQuality, tvTotal, tvRegisDate, tvReceiveDate, tvReturnDate, tvCode;
    Button btnChangeStatus;
    LinearLayout formCard;
    TableRow tableRow;

    public MyFormViewHolder(@NonNull View itemView) {
        super(itemView);

        tvBookName = itemView.findViewById(R.id.tv_book_name);
        tvUserName = itemView.findViewById(R.id.tv_user_name);
        tvStatus = itemView.findViewById(R.id.tv_form_status);
        tvQuality = itemView.findViewById(R.id.tv_quality);
        tvTotal = itemView.findViewById(R.id.tv_total);
        tvRegisDate = itemView.findViewById(R.id.tv_regis_date);
        tvReturnDate = itemView.findViewById(R.id.tv_return_date);
        tvCode = itemView.findViewById(R.id.tv_code);
        tableRow = itemView.findViewById(R.id.tableRow);
        btnChangeStatus = itemView.findViewById(R.id.btnChangeStatus);
        formCard = itemView.findViewById(R.id.formCard);
    }
}
