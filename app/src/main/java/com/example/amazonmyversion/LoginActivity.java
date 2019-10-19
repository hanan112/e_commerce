package com.example.amazonmyversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazonmyversion.Model.Users;
import com.example.amazonmyversion.Prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity
{
    private EditText InputPhonenmbrt,InputPassword;
    private Button Loginbutton;
    private ProgressDialog loadingBar;
    private String perantDbname="Users";
    private CheckBox CheckboxRememberme;
    private TextView AdmineLink,NotAdmineLink;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Loginbutton=(Button) findViewById(R.id.login_btn);
        InputPhonenmbrt=(EditText) findViewById(R.id.login_phon_number_input);
        InputPassword=(EditText) findViewById(R.id.login_password_input);
        loadingBar=new ProgressDialog(this);
        CheckboxRememberme=(CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);
        AdmineLink=(TextView) findViewById(R.id.admin_panel_link);
        NotAdmineLink=(TextView) findViewById(R.id.not_admin_panel_link);


        Loginbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginUser();

            }
        });


        AdmineLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Loginbutton.setText("Login Admin");
                AdmineLink.setVisibility(View.INVISIBLE);
                NotAdmineLink.setVisibility(View.VISIBLE);
                perantDbname="Admins";

            }
        });
        NotAdmineLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Loginbutton.setText("Login");
                AdmineLink.setVisibility(View.VISIBLE);
                NotAdmineLink.setVisibility(View.INVISIBLE);
                perantDbname="Users";

            }
        });
    }

    private void LoginUser()
    {
        String Phone=InputPhonenmbrt.getText().toString();
        String Password=InputPassword.getText().toString();
        if (TextUtils.isEmpty(Phone))
        {
            Toast.makeText(this, "please input your phone number !..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Password))
        {
            Toast.makeText(this, "please input your password!..", Toast.LENGTH_SHORT).show();
        }
        else
            {
                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("please, waite we are checking your credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                AllowAccessToAccount(Phone,Password);
            }
    }

    private void AllowAccessToAccount(final String phone, final String password)
    {
        if (CheckboxRememberme.isChecked())
        {
            Paper.book().write(prevalent.UserPhoneKey,phone);
            Paper.book().write(prevalent.UserPasswordKey,password);

        }
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(perantDbname).child(phone).exists())
                {
                    Users usersData=dataSnapshot.child(perantDbname).child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone))
                    {

                        if (usersData.getPassword().equals(password))
                        {
                            if (perantDbname.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, " Welcome Admin u are Logged in successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent=new Intent(getApplicationContext(),AdminCategoryActivity.class);
                                startActivity(intent);


                            }
                            else if (perantDbname.equals("Users"))
                                {
                                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                                    prevalent.currentOnlineUser=usersData;
                                    startActivity(intent);

                                }

                        }
                        else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        
                    }

                }
                else
                    {
                        Toast.makeText(LoginActivity.this, "Account with this "+phone+"number do not exists", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "you need to create an account ", Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}

