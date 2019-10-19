package com.example.amazonmyversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amazonmyversion.Model.Users;
import com.example.amazonmyversion.Prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{
    private Button joinNowbutton,loginbutton;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joinNowbutton=(Button) findViewById(R.id.main_join_now_btn);
        loginbutton=(Button)findViewById(R.id.main_login_btn);
        loadingBar=new ProgressDialog(this);

        Paper.init(this);
        loginbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        joinNowbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        String UserPhoneKey=Paper.book().read(prevalent.UserPhoneKey);
        String UserPasswordKey=Paper.book().read(prevalent.UserPasswordKey);
        if(UserPhoneKey!=null && UserPasswordKey!=null)
        {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey,UserPasswordKey);
                loadingBar.setTitle("Already logged in");
                loadingBar.setMessage("please waite... ");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }


    }

    private void AllowAccess(final String phone, final String password)
    {
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users usersData=dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone))
                    {

                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Logged in successfully ", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "password incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this "+phone+"number do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "you need to create an account ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }
}
