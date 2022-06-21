package com.example.kpetshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kpetshop.Prevalent.Prevalent;
import com.example.kpetshop.model.Admins;
import com.example.kpetshop.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText edPhone, edPass;
    private Button LoginButton;
    private ProgressDialog loadBar;
    private String ParentDbName = "Users";
    private String AdminsDbName = "Admins";
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.btLgn);
        edPhone = findViewById(R.id.logPhone);
        edPass = findViewById(R.id.logPassword);
        register = findViewById(R.id.Register);
        loadBar = new ProgressDialog(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String phone = edPhone.getText().toString();
        String password = edPass.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please Enter your Phone Number!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter your Password!", Toast.LENGTH_SHORT).show();
        }
        else{
            loadBar.setTitle("Login");
            loadBar.setMessage("Please wait");
            loadBar.setCanceledOnTouchOutside(false);
            loadBar.show();
            AllowAccessAccount(phone, password);
        }
    }

    private void AllowAccessAccount(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ParentDbName).child(phone).exists()) {
                    Users UserData = dataSnapshot.child(ParentDbName).child(phone).getValue(Users.class);
                    if (UserData.getPhone().equals(phone)) {
                        if (UserData.getPassword().equals(password)) {
                                Toast.makeText(LoginActivity.this, "Login Success.", Toast.LENGTH_SHORT).show();
                            loadBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else
                         {
                            loadBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is  Incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (dataSnapshot.child(AdminsDbName).child(phone).exists()) {
                    Admins AdminData = dataSnapshot.child(AdminsDbName).child(phone).getValue(Admins.class);
                    if (AdminData.getPhone().equals(phone)) {
                        if (AdminData.getPassword().equals(password)) {
                                Toast.makeText(LoginActivity.this, "Welcome Admin.", Toast.LENGTH_SHORT).show();
                            loadBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);
                        } else {
                            loadBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is  Incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Account with this " + phone + " number does not exist.", Toast.LENGTH_SHORT).show();
                        loadBar.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}