package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IndexUserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private List<Product> filteredProductList = new ArrayList<>();
    private EditText searchEditText;
    private Button aiChatBtn, btnShoppingCart, btnPersonalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter(filteredProductList);
        recyclerView.setAdapter(productAdapter);

        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });

        aiChatBtn = findViewById(R.id.aiChatBtn);
        btnShoppingCart = findViewById(R.id.btnShoppingCart);
        btnPersonalInfo = findViewById(R.id.btnPersonalInfo);


        // Set onClick event for "AI Chat" button
        aiChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexUserActivity.this, AIChatActivity.class);
                startActivity(intent);
            }
        });

        btnShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexUserActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexUserActivity.this, LoginUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 从Firebase读取数据
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    if (product != null) {
                        product.id = snapshot.getKey(); // Set the id field
                        productList.add(product);
                    }
                }
                filter(searchEditText.getText().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 处理数据库读取错误
            }
        });
    }

    private void filter(String text) {
        filteredProductList.clear();
        for (Product product : productList) {
            if (product.name.toLowerCase().contains(text.toLowerCase()) ||
                    product.brand.toLowerCase().contains(text.toLowerCase())) {
                filteredProductList.add(product);
            }
        }
        productAdapter.notifyDataSetChanged();
    }
}
