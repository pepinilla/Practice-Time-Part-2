package com.example.carolina.practicetimetwo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.MyDataSource;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.textView2)
    TextView textView;
    @Bind(R.id.search)
    Button search;
    @Bind(R.id.textfileRead)
    Button textfileRead;


    private MyDataSource dataSource;

    //private Map<Integer, Person> map;
    private SparseArray<Person> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dataSource = new MyDataSource(this);
        dataSource.initData();

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //map = new HashMap<>();
                sparseArray = new SparseArray<Person>();

                for (int i = 0; i < 1000; i++) {
                    Person p = new Person(i, "Person" + i);
                    //map.put(i,p);
                    sparseArray.append(i, p);
                }
                //displayMessage("There are " + map.size() + " Person objects");
                displayMessage("There are " + sparseArray.size() + " Person objects");
            }
        });
    }

    public void displayMessage(String message) {
        text.append(message + "\n");
    }

    public void searchDatabase(View view) {

        String searchString = editText.getText().toString();

        if (searchString.length() == 0) {
            Toast.makeText(MainActivity.this, "Enter an integer between 1 and 100",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int indexValue = new Integer(searchString);

        Cursor cursor = dataSource.selectRecord(indexValue);
        if (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MyDataSource.PERSON_NAME));
            Toast.makeText(MainActivity.this, "You found " + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Person not found", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.search)
    public void onViewClickedSearch() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDatabase(v);
            }
        });
    }

    private String readFileFromAssets(String filename) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(
                    getAssets().open(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();

        } catch (IOException e) {
            displayMessage(e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                displayMessage(e.getMessage());
            }
        }

        return null;
    }

    @OnClick(R.id.textfileRead)
    public void onViewClickedRead() {
        textfileRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = readFileFromAssets("lorem_ipsum.txt");
                if (text != null){
                    textView.setText(text);
                }

            }
        });
    }
}
