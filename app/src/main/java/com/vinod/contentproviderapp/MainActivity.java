package com.vinod.contentproviderapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveData(View view)
    {
        ContentValues val = new ContentValues();
        String str = ((EditText)findViewById(R.id.etInput)).getText().toString();
        if(!TextUtils.isEmpty(str )) {
            val.put(MyProvider._NAME,str);
            Uri uri = getContentResolver().insert(MyProvider.CONTENT_URI,val );
            ((EditText)findViewById(R.id.etInput)).setText("");
            l(uri.getPath()+"");

        }
    }
    void l(String str)
    {
        Log.d("MainActivity : ",str+"");

    }
}
