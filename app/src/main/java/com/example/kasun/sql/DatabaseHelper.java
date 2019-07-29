package com.example.kasun.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DatabaseHelper.db";

    private static final String TABLE_USER = "user";
    private static final String TABLE_TOYS = "toys";
    private static final String TABLE_OODERS = "orders";


    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_ROLE = "user_role";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String COLUMN_TOY_ID = "id";
    private static final String COLUMN_TOY_NAME = "name";
    private static final String COLUMN_TOY_DESCRIPTION = "description";
    private static final String COLUMN_TOY_PRICE = "price";

    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_ORDER_ITEM_ID = "toy_id";
    private static final String COLUMN_ORDER_AMOUNT = "amount";
    private static final String COLUMN_ORDDER_CUSTOMER_ID = "user_id";


    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_ROLE + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_TOYS_TABLE = "CREATE TABLE " + TABLE_TOYS + "("
            + COLUMN_TOY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TOY_NAME + " TEXT,"
            + COLUMN_TOY_DESCRIPTION + " TEXT,"
            + COLUMN_TOY_PRICE + " TEXT"
            + ")";

    private String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_OODERS + "("
            + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ORDER_ITEM_ID + " INTEGER,"
            + COLUMN_ORDER_AMOUNT + " INTEGER,"
            + COLUMN_ORDDER_CUSTOMER_ID + " INTEGER"
            + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_TOYS_TABLE = "DROP TABLE IF EXISTS " + TABLE_TOYS;
    private String DROP_ORDERS_TABLE = "DROP TABLE IF EXISTS " + TABLE_OODERS;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TOYS_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_TOYS_TABLE);
        db.execSQL(DROP_ORDERS_TABLE);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_ROLE, user.getRole());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean validateUser(String email) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public User validateUser(User user) {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_ROLE

        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {user.getEmail(), user.getPassword()};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);


        int cursorCount = cursor.getCount();
        while (cursor.moveToNext()) {

            return new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

        }
        cursor.close();
        db.close();
//        if (cursorCount > 0) {
//        }

        return null;
    }


    public boolean addToy(TOY toy) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", toy.getName());
        contentValues.put("description", toy.getDescription());
        contentValues.put("price", toy.getPrice());
        long result = db.insert(TABLE_TOYS, null, contentValues);

        if (result == -1) {
            return false;
        } else {

            return true;
        }

    }

    public Cursor getToyList() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_TOYS, null);
        return res;
    }

    public Cursor getToyInfo(int ID) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_TOYS + " where id=" + ID, null);
        return res;
    }

    public boolean UpdateToy(String id, String name, String price, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("description", description);
        contentValues.put("ID", id);
        db.update(TABLE_TOYS, contentValues, "id=?", new String[]{id});
        return true;

    }

    public Integer deleteToy(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TOYS, "id=?", new String[]{id});
    }

    public boolean orderToy(TOY toy, String amount, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("toy_id", toy.getId());
        contentValues.put("amount", amount);
        contentValues.put("user_id", user.getId());
        long result = db.insert(TABLE_OODERS, null, contentValues);

        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }

    public Cursor getOrdersList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_OODERS, null);
        return res;
    }

    public Cursor getUserInfo(int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_USER + " where user_id=" + userID, null);
        return res;
    }
}
