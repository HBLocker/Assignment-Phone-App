package com.example.admin.myapplication;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class ReadFile extends AppCompatActivity {
    EditText FileText,FileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file);
        FileName = findViewById(R.id.FileName);
        FileText = findViewById(R.id.fileText);
    }
    public void save(View v) {
        String text = FileText.getText().toString();
        FileOutputStream fileOUT = null;
        try {
            fileOUT = openFileOutput(FileName.getText().toString(), MODE_PRIVATE); //MODE_private file will only be acsessed by the app
            fileOUT.write(text.getBytes());
            FileText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FileName.getText().toString(), //prints where the file has been saved too
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOUT != null) {
                try {
                    fileOUT.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View v) { //function for loading the file
        FileInputStream fileInput = null; //sets as null otherwise it wont work
        try {
            fileInput = openFileInput(FileName.getText().toString()); //calls thedit text name user set
            InputStreamReader isr = new InputStreamReader(fileInput); //https://docs.oracle.com/javase/7/docs/api/java/io/InputStreamReader.html
            BufferedReader br = new BufferedReader(isr);//Bufferreader class reads in chunks into buffer when it has data
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            FileText.setText(sb.toString());
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("file_text",FileText.getText().toString()); //should send form A to B?
            startActivity(intent); //pushs back to main activity
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInput != null) {
                try {
                    fileInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    ReadFile.super.finish();
                }
            }
        }
    }
}