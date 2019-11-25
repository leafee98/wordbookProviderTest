package com.example.wordbookprovidertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final Uri uri = Uri.parse("content://com.example.wordbook.provider.Provider/word");
    private static final Word testWord = new Word(2333333, "provider test",
            "提供器测试", "we are testing our provider.");
    private Button add, del, mod, search, getAll;
    private RecyclerView recycle;

    private void add() {
        ContentValues values = new ContentValues();
        values.put("word", testWord.getWord());
        values.put("interpretation", testWord.getInterpretation());
        values.put("example", testWord.getExample());
        Uri u = getContentResolver().insert(uri, values);
        int index = u.toString().lastIndexOf('/');
        testWord.setId(Long.parseLong(u.toString().substring(index + 1)));

        this.search(null);
    }

    private void del() {
        getContentResolver().delete(uri, String.valueOf(testWord.getId()), null);

        this.search(null);
    }

    private void mod() {
        ContentValues values = new ContentValues();
        values.put("id", testWord.getId());
        values.put("word", testWord.getWord());
        values.put("interpretation", testWord.getInterpretation());
        values.put("example", testWord.getExample() + "Here we updated it.");
        getContentResolver().update(uri, values, null, null);

        this.search(null);
    }

    private void search(String w) {
        Cursor cursor = getContentResolver().query(uri, null, w, null, null);
        ArrayList<Word> result = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                result.add(new Word(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("word")),
                        cursor.getString(cursor.getColumnIndex("interpretation")),
                        cursor.getString(cursor.getColumnIndex("example"))

                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        Log.d(this.getClass().getName(), "items count: " + result.size());
        for (int i = 0; i < result.size(); ++i) {
            Log.d(this.getClass().getName(), "item: " + result.get(i).toString());
        }
        this.recycle.setAdapter(new RecyclerAdapter(this, result));
    }

    private void assignAction() {
        this.add.setOnClickListener((View v) -> this.add());
        this.del.setOnClickListener((View v) -> this.del());
        this.mod.setOnClickListener((View v) -> this.mod());
        this.search.setOnClickListener((View v) -> this.search("test"));
        this.getAll.setOnClickListener((View v) -> this.search(null));
    }

    private void assignView() {
        this.add = this.findViewById(R.id.add);
        this.del = this.findViewById(R.id.del);
        this.mod = this.findViewById(R.id.mod);
        this.search = this.findViewById(R.id.search);
        this.getAll = this.findViewById(R.id.all);

        this.recycle = this.findViewById(R.id.word_list);
        this.recycle.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.assignView();
        this.assignAction();
        this.search(null);
    }
}
