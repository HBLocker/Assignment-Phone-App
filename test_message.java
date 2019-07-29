package com.example.admin.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.telephony.SmsManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
public class test_message extends AppCompatActivity {
    Intent intent; //defined
    Button send_message;
    EditText cipher_box, phone_Number; //defined
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_message);
        send_message = (Button) findViewById(R.id.send); //button send message
        phone_Number = (EditText) findViewById(R.id.phone_Numbr);
        cipher_box = (EditText) findViewById(R.id.cipher_box); //gets called from main func to input txt into here hopefully
        Intent intent = getIntent();
        String str = intent.getStringExtra("cipher_txt"); //reads from main to here
        cipher_box.setText(str);
        intent = new Intent(getApplicationContext(), test_message.class);
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(cipher_box.getText().toString(),null,cipher_box.getText().toString(),null,null);
                    Toast.makeText(test_message.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(test_message.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}




