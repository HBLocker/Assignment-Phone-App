package com.example.admin.myapplication;
import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
public class MainActivity extends AppCompatActivity {
    EditText input, key;
    TextView output;
    Button btn_encrypt, btn_decrypt, _Inbox, view_key, read_file;
    String OutPut_string;
    Boolean hasBeenPaused = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText)findViewById(R.id.InputText);
        key = (EditText)findViewById(R.id.cipher);
        output= (TextView) findViewById(R.id.out_edit_text);
        btn_encrypt = (Button)findViewById(R.id.encrypt);
        btn_decrypt = (Button)findViewById(R.id.decrypt);
        _Inbox   = (Button)findViewById(R.id.inbox);
        read_file = (Button)findViewById(R.id.readFILE);	
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SEND_SMS)) { //PERMISSIONS
            // If user declines, do something
        } else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.SEND_SMS}, 1); //?
        }

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)) {
            // If user declines, do something
        } else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_SMS}, 1); //?
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // If user declines, do something
        } else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1); //?
        }

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.BROADCAST_SMS)) {
            // If user declines, do something
        } else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.BROADCAST_SMS}, 1); //?
        }
        btn_encrypt.setOnClickListener(new OnClickListener() { //BUTTON FOR THE ENCRYPTION FUNCTION TO BE CALLED
            @Override
            public void onClick(View v) {
                try {
                    OutPut_string = encrypt_func(input.getText().toString(), key.getText().toString());
                    output.setText(OutPut_string); //outputs encrypted values
                    Intent intent = new Intent(getApplicationContext(),test_message.class);
                    intent.putExtra("cipher_txt",OutPut_string); //pushes values from Main to sending sms activiry
                    startActivity(intent);
                    MainActivity.super.onPause(); //PAUSES ONCE BUTTON


                    finish(); //p
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        _Inbox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SmsActivity.class); //goes to inbox activity
                startActivity(intent);
                MainActivity.super.onPause(); //pauses activiry
            }
        });

        read_file.setOnClickListener(new OnClickListener() { //goes to the read file acticiry of the app
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReadFile.class);
                startActivity(intent);
                MainActivity.super.onPause();
            }
        });

        btn_decrypt.setOnClickListener(new OnClickListener() { //calls the decrypt function 
            @Override

            public void onClick(View v) {
                try {
                    OutPut_string = decrypt(OutPut_string,key.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                output.setText(OutPut_string);

            }
        });
        Intent intent = getIntent();
        String str = intent.getStringExtra("file_text");
        if(str!=null) //if null set ot tas txt anyways
        {
            key.setText(str);
        }

    } //encrypt function  https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html

    // encryption functions of the application
    private String decrypt(String outPut_string, String password)throws Exception  { //decrypts the text once handled, works how encryption works but backwards
        SecretKeySpec key = generateKey(password);

        Cipher C =  Cipher.getInstance("Blowfish"); //Blow fish used its  astream cipher, I enjoy crypto as a hobby (maths and how it works) real world i would use Rc4
        C.init(Cipher.DECRYPT_MODE,key);
        byte[] decoded_value =Base64.decode(OutPut_string,Base64.DEFAULT);
        byte[] decval  = C.doFinal(decoded_value);
        String  decrypted_value = new String(decval);
        return decrypted_value;
    }

    public String encrypt_func(String Data,String password) throws Exception { //encrypt function for app

        SecretKeySpec key = generateKey(password);
        Cipher C = Cipher.getInstance("Blowfish");
        C.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted_value = C.doFinal(Data.getBytes());
        String encryptedValues = Base64.encodeToString(encrypted_value, Base64.DEFAULT);
        return encryptedValues;
    
    }


    private  SecretKeySpec generateKey(final String password)throws Exception{ //creates key

        final MessageDigest digest = MessageDigest.getInstance("MD5");

        byte[] bytes = password.getBytes("UTF-8"); //reads into an array and m65d
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest(); //ends digest here
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"Blowfish");
        return  secretKeySpec;
    }
    // save keys and load keys from from storage

}


