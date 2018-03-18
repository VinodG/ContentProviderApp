package com.vinod.contentproviderapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Vinod.Kumar on 18-03-2018.
 */

public class MyProvider extends ContentProvider {
    private static final String TABLE_NAME = "students";
    public static final String _ID="id";
    public static final String _NAME = "name" ;
    SQLiteDatabase db =null;
    static final String PROVIDER_NAME = "com.vinod.contentproviderapp.MyProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/students";
    static final Uri CONTENT_URI = Uri.parse(URL);
    @Override
    public boolean onCreate() {

        Context context = getContext();
        MyDatabase dbase = new MyDatabase(context,"providerdb.db",null, 1);
        db =dbase.getWritableDatabase();
        l("OnCreate Called in Provider");
        return  db==null? false: true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            qb.setTables(TABLE_NAME);

         /*   switch (uriMatcher.match(uri)) {
                case STUDENTS:
                    qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                    break;

                case STUDENT_ID:
                    qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                    break;

                default:
            }

            if (sortOrder == null || sortOrder == ""){
                *//**
                 * By default sort on student names
                 *//*
                sortOrder = NAME;
            }
*/
            Cursor c = qb.query(db,	strings,	s,
                    strings1,null, null, null);
            /**
             * register to watch a content URI for changes
             */
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;

    }

    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues)
    {
       long rowId =  db.insert(TABLE_NAME,null,contentValues);
       if(rowId >0)
       {
           Uri _uri = ContentUris.withAppendedId(CONTENT_URI   ,rowId);
           getContext().getContentResolver().notifyChange(_uri,null);
           return  _uri;
       }
          throw  new SQLException("Failed to insert record at "+uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


    public class MyDatabase extends SQLiteOpenHelper {


        String TAG = "MyDatabase";

        public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            log("Database constructor is called ");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase)
        {

            sqLiteDatabase.execSQL("create table "+TABLE_NAME+"("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+_NAME+" varchar )");
            log("table is created ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            log("table is updated ");

        }
        void log(String str)
        {
            Log.d(TAG,str+"");
        }
        public void insert(ContentValues val)
        {
            SQLiteDatabase db = getWritableDatabase();
            db.insert("student",null,val);
            log("Record is inserted ");

        }

    }
    void l(String str)
    {
        Log.d("ContentProvider: ",str+"");

    }
}
