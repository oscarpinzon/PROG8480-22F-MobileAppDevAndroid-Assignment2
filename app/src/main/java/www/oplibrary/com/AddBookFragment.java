package www.oplibrary.com;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddBookFragment extends Fragment {
    View v;
    Spinner spnPublisher;
    DBHelper dbh;
    EditText edtISBN, edtTitle, edtQtyInStock, edtPrice;
    Button btnCancel, btnSave;
    Intent intent;
    Boolean insertStatus;

    public AddBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_book, container, false);
        // populate spinner from db
        spnPublisher = v.findViewById(R.id.spnPublisher);
        dbh = new DBHelper(getActivity());
        String[] arrPublisher = dbh.getPublisherList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrPublisher);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPublisher.setAdapter(adapter);
        // get references to edit text fields
        edtISBN = v.findViewById(R.id.edtISBN);
        edtTitle = v.findViewById(R.id.edtBookTitle);
        spnPublisher = v.findViewById(R.id.spnPublisher);
        edtQtyInStock = v.findViewById(R.id.edtQtyStock);
        edtPrice = v.findViewById(R.id.edtPrice);
        btnCancel = v.findViewById(R.id.btnCancel);
        btnSave = v.findViewById(R.id.btnSave);
        // handle cancel button click
        btnCancel.setOnClickListener(v -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        });

        // handle submit button click
        btnSave.setOnClickListener(v -> {
            if (edtISBN.getText().toString().equals("") || edtTitle.getText().toString().equals("") || edtQtyInStock.getText().toString().equals("") || edtPrice.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                return;
            }
            Book objBook = CreateBook();
            insertStatus = dbh.InsertBook(objBook);
            if (insertStatus) {
                Toast.makeText(getActivity(), "Book Added Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Book Addition Failed", Toast.LENGTH_LONG).show();
            }
        });

        // return view
        return v;
    }

    // creates a book object from the data entered in the edit text fields
    private Book CreateBook() {
        Book objBook = new Book();
        objBook.setIsbn(edtISBN.getText().toString());
        objBook.setTitle(edtTitle.getText().toString());
        objBook.setPublisher(spnPublisher.getSelectedItem().toString());
        objBook.setQtyInStock(Integer.parseInt(edtQtyInStock.getText().toString()));
        objBook.setPrice(Double.parseDouble(edtPrice.getText().toString()));
        return objBook;
    }
}