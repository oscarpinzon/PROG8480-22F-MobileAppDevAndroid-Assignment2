package www.oplibrary.com;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class BookInformationDialog extends DialogFragment {
    private final Book book;

    public BookInformationDialog(Book book) {
        this.book = book;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("ISBN: " + book.getIsbn() + "\n" +
                        "Title: " + book.getTitle() + "\n" +
                        "Publisher: " + book.getPublisher() + "\n" +
                        "Quantity in Stock: " + book.getQtyInStock() + "\n" +
                        "Price: " + book.getPrice())
                .setTitle("Book Information")
                .setPositiveButton("OK", (dialog, id) -> {
                });
        return builder.create();
    }
}
