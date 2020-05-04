package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText input_txt;
    TextView output_txt;
    Button read, write, clear;
    private String filename = "cse.txt";
    private String filepath = "BMSCE";
    File myFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_txt = (EditText) findViewById(R.id.editText);
        write = (Button) findViewById((R.id.write_b));
        read = (Button) findViewById(R.id.read_b);
        clear = (Button) findViewById(R.id.clear_b);
        output_txt = (TextView) findViewById(R.id.textView);

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            write.setEnabled(false);
            read.setEnabled(false);
            clear.setEnabled(false);
        } else {
            myFile = new File(getExternalFilesDir(filepath), filename);
        }

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = input_txt.getText().toString();
                try {
                    FileOutputStream fo = new FileOutputStream(myFile);
                    fo.write(msg.getBytes());
                    fo.close();
                    Toast.makeText(getBaseContext(), "Data wrote to SD Card", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                String buf = "";
                try {
                    FileInputStream fin = new FileInputStream(myFile);
                    DataInputStream in = new DataInputStream(fin);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    while ((message = br.readLine()) != null) {
                        buf += message;
                    }
                    output_txt.setText(buf);
                    br.close();
                    fin.close();
                    Toast.makeText(getBaseContext(), "Data Recived from SDCARD", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_txt.setText(" ");
                output_txt.setText(" ");
            }
        });

    }

    private boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
