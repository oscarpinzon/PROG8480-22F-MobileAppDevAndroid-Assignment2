package www.oplibrary.com;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class ReturnBookFragment extends Fragment {
    View v;
    DatePicker dpReturnDate;
    EditText edtIssueId, edtBookId, edtQtyReturned;
    Button btnCancel, btnSubmit;
    DBHelper dbh;
    Intent intent;

    public ReturnBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_return_book, container, false);
        // set max date of date time picker to current date
        dpReturnDate = v.findViewById(R.id.datePickerDateOfReturn);
        dpReturnDate.setMaxDate(System.currentTimeMillis());
        // make book id read only
        edtBookId = v.findViewById(R.id.edtBookId);
        edtBookId.setEnabled(false);
        // get references to controls
        edtIssueId = v.findViewById(R.id.edtIssueId);
        edtQtyReturned = v.findViewById(R.id.edtQtyReturned);
        btnCancel = v.findViewById(R.id.btnCancel);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        // handle cancel button click
        btnCancel.setOnClickListener(v -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        });
        // populate book id on issue id change
        edtIssueId.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                dbh = new DBHelper(getActivity());
                String issueId = edtIssueId.getText().toString();
                if (!issueId.isEmpty()) {
                    String bookId = dbh.getBookId(issueId);
                    if (bookId != null) {
                        edtBookId.setText(bookId);
                    } else {
                        Toast.makeText(getActivity(), "Invalid Issue ID", Toast.LENGTH_SHORT).show();
                        edtBookId.setText("");
                    }
                }
            }
        });
        // handle submit button click
        btnSubmit.setOnClickListener(v -> {
            dbh = new DBHelper(getActivity());
            String issueId = edtIssueId.getText().toString();
            String bookId = edtBookId.getText().toString();
            String qtyReturned = edtQtyReturned.getText().toString();
            String returnDate = dpReturnDate.getYear() + "-" + (dpReturnDate.getMonth() + 1) + "-" + dpReturnDate.getDayOfMonth();
            if (issueId.isEmpty() || bookId.isEmpty() || qtyReturned.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                int qtyReturnedInt = Integer.parseInt(qtyReturned);
                if (qtyReturnedInt > 0) {
                    int qtyIssued = dbh.getQtyIssued(issueId);
                    if (qtyReturnedInt <= qtyIssued) {
                        // update stock
                        int qtyAvailable = dbh.getQtyAvailable(bookId);
                        int qtyUpdated = qtyAvailable + qtyReturnedInt;
                        boolean updateStatus = dbh.updateQtyAvailable(bookId, qtyUpdated);
                        // update issued qty
                        int qtyIssuedUpdated = qtyIssued - qtyReturnedInt;
                        boolean updateIssuedStatus = dbh.updateQtyIssued(issueId, qtyIssuedUpdated);
                        if (updateStatus && updateIssuedStatus) {
                            boolean returnStatus = dbh.returnBook(issueId, bookId, qtyReturnedInt, returnDate);
                            if (returnStatus) {
                                Toast.makeText(getActivity(), "Book Returned Successfully", Toast.LENGTH_SHORT).show();
                                // clear all fields
                                edtIssueId.setText("");
                                edtBookId.setText("");
                                edtQtyReturned.setText("");
                                dpReturnDate.updateDate(dpReturnDate.getYear(), dpReturnDate.getMonth(), dpReturnDate.getDayOfMonth());
                            } else {
                                Toast.makeText(getActivity(), "Book Return Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Book Return Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Quantity Returned cannot be greater than Quantity Issued", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Quantity Returned cannot be less than or equal to 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}