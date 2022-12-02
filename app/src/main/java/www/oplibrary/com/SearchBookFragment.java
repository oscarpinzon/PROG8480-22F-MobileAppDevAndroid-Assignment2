package www.oplibrary.com;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchBookFragment extends Fragment {
    View v;
    EditText edtBookId;
    Button btnCancel, btnSearch;
    DBHelper dbh;
    Intent intent;

    public SearchBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search_book, container, false);
        // get references to controls
        edtBookId = v.findViewById(R.id.edtBookId);
        btnCancel = v.findViewById(R.id.btnCancel);
        btnSearch = v.findViewById(R.id.btnSearch);
        // handle cancel button click
        btnCancel.setOnClickListener(v -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        });
        // handle search button click
        btnSearch.setOnClickListener(v -> {
            dbh = new DBHelper(getActivity());
            String bookId = edtBookId.getText().toString();
            if (!bookId.isEmpty()) {
                Book book = dbh.getBook(bookId);
                if (book != null) {
                    BookInformationDialog dialog = new BookInformationDialog(book);
                    dialog.show(getParentFragmentManager(), "Book Information");
                } else {
                    Toast.makeText(getActivity(), "Book not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please enter book id", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}