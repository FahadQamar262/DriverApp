package com.example.dell.driverapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {
    Button btn ;
    EditText ed1 , ed2 ;
    String login_name ,login_pass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn=(Button)findViewById(R.id.button);
        ed1=(EditText)findViewById(R.id.editText2);
        ed2=(EditText)findViewById(R.id.editText3);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(login.this, home.class);
                login.this.startActivity(myIntent);
            }
        });

    }
    public void userLogin(View view)
    {
        login_name=ed1.getText().toString();
        login_pass=ed2.getText().toString();


        String method = "login";
        BackgroundTask bgt = new BackgroundTask(this);
        bgt.execute(method,login_name,login_pass);
    }
}
