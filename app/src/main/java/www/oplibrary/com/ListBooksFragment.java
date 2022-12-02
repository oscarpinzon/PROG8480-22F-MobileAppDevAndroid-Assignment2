package www.oplibrary.com;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListBooksFragment extends Fragment {
    View v;
    RecyclerView rcView;
    List<Book> mList = new ArrayList<>();
    DBHelper dbh;
    BookListAdapter mAdapter;
    Intent intent;
    public ListBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_list_book, container, false);

        // populates the recycler view with the list of books
        rcView = v.findViewById(R.id.rcView);
        dbh = new DBHelper(getActivity());
        Cursor cursor1 = dbh.ListBook();

        if (cursor1 == null) {
            Toast.makeText(getActivity(), "No book records found", Toast.LENGTH_LONG).show();
        } else {
            cursor1.moveToFirst();
            do {
                Book book = new Book();
                book.setIsbn(cursor1.getString(1));
                book.setTitle(cursor1.getString(2));
                book.setPublisher(cursor1.getString(3));
                book.setQtyInStock(cursor1.getInt(4));
                book.setPrice(cursor1.getDouble(5));
                mList.add(book);
            } while (cursor1.moveToNext());
            // close the cursor
            cursor1.close();
            dbh.close();
            // bind the adapter
            BindAdapter();
        }

        // handle cancel button click
        v.findViewById(R.id.btnCancel).setOnClickListener(v -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        });

        // return the view
        return v;
    }

    // binds the adapter to the recycler view
    @SuppressLint("NotifyDataSetChanged")
    private void BindAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcView.setLayoutManager(layoutManager);
        mAdapter = new BookListAdapter(mList);
        rcView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}