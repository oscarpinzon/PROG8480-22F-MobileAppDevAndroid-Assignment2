package www.oplibrary.com;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Book> mList;

    public BookListAdapter(List<Book> list) {
        super();
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_record_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Book book = mList.get(position);
        ((ViewHolder) holder).mIsbn.setText(String.valueOf(book.getIsbn()));
        ((ViewHolder) holder).mTitle.setText(book.getTitle());
        ((ViewHolder) holder).mPublisher.setText(book.getPublisher());
        ((ViewHolder) holder).mQty.setText(String.valueOf(book.getQtyInStock()));
        ((ViewHolder) holder).mPrice.setText(String.valueOf(book.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mIsbn, mTitle, mPublisher, mQty, mPrice;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mIsbn = itemView.findViewById(R.id.txtIsbn);
        mTitle = itemView.findViewById(R.id.txtTitle);
        mPublisher = itemView.findViewById(R.id.txtPublisher);
        mQty = itemView.findViewById(R.id.txtQty);
        mPrice = itemView.findViewById(R.id.txtPrice);
    }
}
