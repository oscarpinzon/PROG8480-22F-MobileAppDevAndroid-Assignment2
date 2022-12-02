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

public class IssueBookFragment extends Fragment {
    View v;
    DatePicker dpIssueDate;
    EditText edtBookID, edtCustomerName, edtCustomerEmail, edtQtyIssued;
    Button btnCancel, btnSubmit;
    Intent intent;
    DBHelper dbh;

    public IssueBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_issue_book, container, false);
        // set max date of date time picker to current date
        dpIssueDate = v.findViewById(R.id.datePickerDateIssued);
        dpIssueDate.setMaxDate(System.currentTimeMillis());
        // get references to edit text fields
        edtBookID = v.findViewById(R.id.edtBookId);
        edtCustomerName = v.findViewById(R.id.edtCustomerName);
        edtCustomerEmail = v.findViewById(R.id.edtCustomerEmail);
        edtQtyIssued = v.findViewById(R.id.edtQtyIssued);
        btnCancel = v.findViewById(R.id.btnCancel);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        // handle submit button click
        btnSubmit.setOnClickListener(v -> {
            // get values from edit text fields
            String bookID = edtBookID.getText().toString();
            String customerName = edtCustomerName.getText().toString();
            String customerEmail = edtCustomerEmail.getText().toString();
            String qtyIssued = edtQtyIssued.getText().toString();
            String issueDate = dpIssueDate.getDayOfMonth() + "/" + (dpIssueDate.getMonth() + 1) + "/" + dpIssueDate.getYear();
            // validate input
            if (bookID.isEmpty() || customerName.isEmpty() || customerEmail.isEmpty() || qtyIssued.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // insert data into db
                dbh = new DBHelper(getActivity());
                // check if book exists
                if (dbh.CheckBookExists(bookID)) {
                    // check if book is available
                    if (dbh.CheckBookAvailability(bookID, Integer.parseInt(qtyIssued))) {
                        // check if customer exists
                        if (dbh.CheckCustomerExists(customerEmail)) {
                            // reduce stock of book
                            if (dbh.ReduceBookStock(bookID, Integer.parseInt(qtyIssued))) {
                                // issue book
                                if (dbh.IssueBook(bookID, customerName, customerEmail, Integer.parseInt(qtyIssued), issueDate)) {
                                    Toast.makeText(getActivity(), "Book issued successfully", Toast.LENGTH_SHORT).show();
                                    // clear edit text fields
                                    edtBookID.setText("");
                                    edtCustomerName.setText("");
                                    edtCustomerEmail.setText("");
                                    edtQtyIssued.setText("");
                                    // set date picker to current date
                                    dpIssueDate.updateDate(dpIssueDate.getYear(), dpIssueDate.getMonth(), dpIssueDate.getDayOfMonth());
                                } else {
                                    Toast.makeText(getActivity(), "Book issue failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Error reducing book stock", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Customer does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Book not available. Insufficient Stock", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Book does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // handle cancel button click
        btnCancel.setOnClickListener(v -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        });
        // return view
        return v;
    }
}