package www.oplibrary.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DBNAME = "OPLibrary.db";
    static final int VERSION = 1;
    // user table
    static final String USER_TABLE_NAME = "User";
    static final String USER_COL1 = "id";
    static final String USER_COL2 = "username";
    static final String USER_COL3 = "emailId";
    static final String USER_COL4 = "password";
    static final String USER_CREATE_TABLE = "create table " + USER_TABLE_NAME + " (" + USER_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_COL2 + " TEXT NOT NULL, " + USER_COL3 + " TEXT, " + USER_COL4 + " TEXT); ";
    static final String USER_DROP_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
    // stock table
    static final String STOCK_TABLE_NAME = "Stock";
    static final String STOCK_COL1 = "bookId";
    static final String STOCK_COL2 = "isbn";
    static final String STOCK_COL3 = "bookTitle";
    static final String STOCK_COL4 = "publisher";
    static final String STOCK_COL5 = "qtyStock";
    static final String STOCK_COL6 = "price";
    static final String STOCK_CREATE_TABLE = "create table " + STOCK_TABLE_NAME + " (" + STOCK_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STOCK_COL2 + " TEXT NOT NULL, " + STOCK_COL3 + " TEXT, " + STOCK_COL4 + " TEXT, " + STOCK_COL5 + " INTEGER, " + STOCK_COL6 + " FLOAT); ";
    static final String STOCK_DROP_TABLE = "DROP TABLE IF EXISTS " + STOCK_TABLE_NAME;
    // issue table
    static final String ISSUE_TABLE_NAME = "Issue";
    static final String ISSUE_COL1 = "issueId";
    static final String ISSUE_COL2 = "bookId";
    static final String ISSUE_COL3 = "customerName";
    static final String ISSUE_COL4 = "customerEmail";
    static final String ISSUE_COL5 = "qtyIssued";
    static final String ISSUE_COL6 = "dateofIssue";
    static final String ISSUE_CREATE_TABLE = "create table " + ISSUE_TABLE_NAME + " (" + ISSUE_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ISSUE_COL2 + " INTEGER NOT NULL, " + ISSUE_COL3 + " TEXT, " + ISSUE_COL4 + " TEXT, " + ISSUE_COL5 + " INTEGER, " + ISSUE_COL6 + " DATE); ";
    static final String ISSUE_DROP_TABLE = "DROP TABLE IF EXISTS " + ISSUE_TABLE_NAME;
    // return table
    static final String RETURN_TABLE_NAME = "Return";
    static final String RETURN_COL1 = "returnId";
    static final String RETURN_COL2 = "issueId";
    static final String RETURN_COL3 = "bookId";
    static final String RETURN_COL4 = "qtyReturned";
    static final String RETURN_COL5 = "dateofReturn";
    static final String RETURN_CREATE_TABLE = "create table " + RETURN_TABLE_NAME + " (" + RETURN_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RETURN_COL2 + " INTEGER NOT NULL, " + RETURN_COL3 + " INTEGER NOT NULL, " + RETURN_COL4 + " INTEGER, " + RETURN_COL5 + " DATE); ";
    static final String RETURN_DROP_TABLE = "DROP TABLE IF EXISTS " + RETURN_TABLE_NAME;
    // publisher table
    static final String PUBLISHER_TABLE_NAME = "Publisher";
    static final String PUBLISHER_COL1 = "publisherId";
    static final String PUBLISHER_COL2 = "publisher";
    static final String PUBLISHER_CREATE_TABLE = "create table " + PUBLISHER_TABLE_NAME + " (" + PUBLISHER_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PUBLISHER_COL2 + " TEXT NOT NULL); ";
    static final String PUBLISHER_DROP_TABLE = "DROP TABLE IF EXISTS " + PUBLISHER_TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    // creates all tables and inserts default values
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(USER_CREATE_TABLE);
        sqLiteDatabase.execSQL(STOCK_CREATE_TABLE);
        sqLiteDatabase.execSQL(ISSUE_CREATE_TABLE);
        sqLiteDatabase.execSQL(RETURN_CREATE_TABLE);
        sqLiteDatabase.execSQL(PUBLISHER_CREATE_TABLE);
        String[] publishers = {"MacMillan", "Penguin Random House", "Pearson Education", "McGraw Hill Education", "Wiley"};
        for (String publisher : publishers) {
            sqLiteDatabase.execSQL("INSERT INTO " + PUBLISHER_TABLE_NAME + " (" + PUBLISHER_COL2 + ") VALUES ('" + publisher + "');");
        }
        User testUser = new User(1, "test", "test@gmail.com", "test");
        ContentValues values = new ContentValues();
        values.put(USER_COL1, testUser.getId());
        values.put(USER_COL2, testUser.getUsername());
        values.put(USER_COL3, testUser.getEmailId());
        values.put(USER_COL4, testUser.getPassword());
        sqLiteDatabase.insert(USER_TABLE_NAME, null, values);
    }

    // inserts a new user into the database
    public boolean insertUser(String username, String emailId, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL2, username);
        contentValues.put(USER_COL3, emailId);
        contentValues.put(USER_COL4, password);
        long result = db.insert(USER_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // method that runs on upgrade
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(USER_DROP_TABLE);
        sqLiteDatabase.execSQL(STOCK_DROP_TABLE);
        sqLiteDatabase.execSQL(ISSUE_DROP_TABLE);
        sqLiteDatabase.execSQL(RETURN_DROP_TABLE);
        sqLiteDatabase.execSQL(PUBLISHER_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    // checks if the user exists in the database
    public Boolean checkUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_COL2 + " = ? AND " + USER_COL4 + " = ? AND " + USER_COL3 + " = ?", new String[]{username, password, email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }
    }

    public Boolean checkUsernameTaken(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_COL2 + " = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }
    }
}
