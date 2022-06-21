package com.example.kpetshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmActivity extends AppCompatActivity {
    private EditText nameEdt, phoneEdt, addressEdt, cityEdt;
    private Button confirmOrderBtn;
    private String totalPrice;
    private final String totalPrices = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        confirmOrderBtn = findViewById(R.id.confirm_final_order_btn);
        nameEdt = findViewById(R.id.shipment_name);
        phoneEdt = findViewById(R.id.shipment_phone_number);
        addressEdt = findViewById(R.id.shipment_address);
        cityEdt = findViewById(R.id.shipment_city);
        totalPrice = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = " + totalPrice + " rupiah", Toast.LENGTH_SHORT).show();

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }
    private void check() {
        if(TextUtils.isEmpty(nameEdt.getText().toString())){
            Toast.makeText(this, "Please write your Full Name!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEdt.getText().toString())){
            Toast.makeText(this, "Please write your Phone Number!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEdt.getText().toString())){
            Toast.makeText(this, "Please enter your Address!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEdt.getText().toString())){
            Toast.makeText(this, "Please write your City Name!", Toast.LENGTH_SHORT).show();
        }
        else {
            confirmOrder();
        }
    }

    private void confirmOrder() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("ViewOrders").child("phone");

        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("totalAmount",totalPrice);
        orderMap.put("name", nameEdt.getText().toString());
        orderMap.put("phone", phoneEdt.getText().toString());
        orderMap.put("address", addressEdt.getText().toString());
        orderMap.put("city", cityEdt.getText().toString());
        orderMap.put("date", saveCurrentDate);
        orderMap.put("time", saveCurrentTime);
        orderMap.put("state","not shipped");
        ordersRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference()
                            .child("cart list")
                            .child("User View")
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ConfirmActivity.this, "Your Final Order has been Placed successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });
    }
}