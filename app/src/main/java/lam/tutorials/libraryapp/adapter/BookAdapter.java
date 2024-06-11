package lam.tutorials.libraryapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lam.tutorials.libraryapp.R;
import lam.tutorials.libraryapp.StudentBookDetailActivity;
import lam.tutorials.libraryapp.TeacherBookDetailActivity;
import lam.tutorials.libraryapp.entity.Book;

public class BookAdapter extends RecyclerView.Adapter<MyBookViewHolder>
{
    private Context context;
    private List<Book> booklist;

    private int id_user;

    private String role;

    public BookAdapter(Context context, List<Book> booklist, int id_user, String role) {
        this.context = context;
        this.booklist = booklist;
        this.id_user = id_user;
        this.role = role;
    }

    @NonNull
    @Override
    public MyBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_book_item,parent,false);
        return new MyBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookViewHolder holder, int position) {
        holder.tvBookName.setText(booklist.get(position).getName());
        holder.tvBookAuthor.setText(booklist.get(position).getAuthor());
        holder.tvBookPublishComp.setText("NXB: "+ booklist.get(position).getPublish_comp());
        holder.tvBookDate.setText("XB: " + booklist.get(position).getPublish_date());
        holder.tvCategory.setText("Thể loại: "+ booklist.get(position).getCategory());
        holder.tvPrice.setText("Giá: "+ String.valueOf(booklist.get(position).getPrice()) +" VND");
        holder.bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if(role.equals("Teacher")) {
                        Intent intent = new Intent(context, TeacherBookDetailActivity.class);
                        intent.putExtra("id_book", booklist.get(position).getId());
                        intent.putExtra("id_teacher",id_user);
                        context.startActivity(intent);
                    }else{
                        Intent intent = new Intent(context, StudentBookDetailActivity.class);
                        intent.putExtra("id_book", booklist.get(position).getId());
                        intent.putExtra("id_student",id_user);
                        context.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return booklist.size();
    }

    public void serachDataList(ArrayList<Book> SearchList) {
        booklist = SearchList;
        notifyDataSetChanged();
    }
}



class MyBookViewHolder extends RecyclerView.ViewHolder {

    TextView tvBookName, tvBookDate, tvBookAuthor, tvBookPublishComp, tvCategory, tvPrice;
    LinearLayout bookCard;
    public MyBookViewHolder(@NonNull View itemView) {
        super(itemView);

        tvBookName = itemView.findViewById(R.id.tv_book_name);
        tvBookDate = itemView.findViewById(R.id.publish_date);
        tvBookAuthor = itemView.findViewById(R.id.tv_author);
        tvBookPublishComp = itemView.findViewById(R.id.tv_publish_comp);
        tvCategory = itemView.findViewById(R.id.tv_category);
        tvPrice = itemView.findViewById(R.id.tv_price);
        bookCard = itemView.findViewById(R.id.recCard);
    }
}
