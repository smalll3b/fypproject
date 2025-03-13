package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {
    private Button btnHomePage, btnPersonalInfo;
    private EditText searchEditText;
    private TextView totalPriceText;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList, originalCartList;
    private DatabaseReference cartRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        // 初始化視圖
        btnHomePage = findViewById(R.id.btnHomePage);
        btnPersonalInfo = findViewById(R.id.btnPersonalInfo);
        searchEditText = findViewById(R.id.searchEditText);
        totalPriceText = findViewById(R.id.totalPriceText);
        recyclerView = findViewById(R.id.recyclerView);

        // 設置 RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItemList = new ArrayList<>();
        originalCartList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartItemList);
        recyclerView.setAdapter(cartAdapter);

        // 初始化 Firebase 參考
        cartRef = FirebaseDatabase.getInstance().getReference("cart");

        // 按鈕點擊事件
        btnHomePage.setOnClickListener(view -> {
            Intent intent = new Intent(ShoppingCartActivity.this, IndexUserActivity.class);
            startActivity(intent);
            finish();
        });

        btnPersonalInfo.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCartActivity.this, LoginUserActivity.class);
            startActivity(intent);
            finish();
        });

        // 加載購物車數據
        loadCartItems();

        // 搜尋欄監聽
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCartItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    // 搜尋功能：根據關鍵字篩選列表
    private void filterCartItems(String query) {
        cartItemList.clear();
        if (query.isEmpty()) {
            cartItemList.addAll(originalCartList);
        } else {
            for (CartItem item : originalCartList) {
                if (item.productName.toLowerCase().contains(query.toLowerCase()) ||
                        item.productBrand.toLowerCase().contains(query.toLowerCase())) {
                    cartItemList.add(item);
                }
            }
        }
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice(); // 每次搜尋後更新總價
    }

    // 加載購物車項目
    private void loadCartItems() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                originalCartList.clear();
                cartItemList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartItem cartItem = snapshot.getValue(CartItem.class);
                    if (cartItem != null) {
                        originalCartList.add(cartItem);
                        cartItemList.add(cartItem);
                    }
                }
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice(); // 加載數據後更新總價
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShoppingCartActivity.this, "無法加載購物車數據", Toast.LENGTH_SHORT).show();
                Log.e("ShoppingCartActivity", "加載失敗", databaseError.toException());
            }
        });
    }

    // 計算購物車總價
    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : cartItemList) {
            totalPrice += item.price * item.quantity;
        }
        totalPriceText.setText(String.format("總價：$%.2f", totalPrice));
    }
}
