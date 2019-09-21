package com.hk.sub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.hk.sub.R.id.sendButttonID;

public class Feedback extends AppCompatActivity {
Button Send,Clear;
EditText name,feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
//show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //activity
        name=(EditText)findViewById(R.id.unameID);
        feedback=(EditText)findViewById(R.id.uFeedbckID);
        Send=(Button)findViewById(R.id.sendButttonID);
        Clear=(Button)findViewById(R.id.clearbuttonID);

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                feedback.setText("");
            }
        });
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=name.getText().toString();
                String FeedB=feedback.getText().toString();
                //send user comments
                if(Name.isEmpty()||FeedB.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty field!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/email");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hkobir10@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "feedback from SUB apps");
                    intent.putExtra(Intent.EXTRA_TEXT, "Name: " + Name + "\n Message: " + FeedB);
                    startActivity(Intent.createChooser(intent, "Send Feedback with"));
                }
            }
        });
    }



    //implements back button
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
