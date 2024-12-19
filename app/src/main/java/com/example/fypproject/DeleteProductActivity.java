package com.example.fypproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteProductActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView lvProducts;
    private Button btnDelete;
    private List<String> productList;
    private List<String> productIdList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        // 初始化Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 連接介面元件
        lvProducts = findViewById(R.id.lvProducts);
        btnDelete = findViewById(R.id.btnDelete);

        productList = new ArrayList<>();
        productIdList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, productList);
        lvProducts.setAdapter(adapter);
        lvProducts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // 從Firebase讀取產品列表
        loadProductList();

        // 設定刪除按鈕點擊事件
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedProducts();
            }
        });
    }

    private void loadProductList() {
        mDatabase.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                productIdList.clear();
                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    String productName = productSnapshot.child("name").getValue(String.class);
                    String productId = productSnapshot.getKey();
                    productList.add(productName);
                    productIdList.add(productId);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DeleteProductActivity", "Failed to load product list", databaseError.toException());
            }
        });
    }

    private void deleteSelectedProducts() {
        for (int i = 0; i < lvProducts.getCount(); i++) {
            if (lvProducts.isItemChecked(i)) {
                String productId = productIdList.get(i);
                mDatabase.child("products").child(productId).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Log.d("DeleteProductActivity", "Product deleted successfully");
                            // 可以在這裡添加通知用戶的邏輯，例如Toast
                        })
                        .addOnFailureListener(e -> {
                            Log.e("DeleteProductActivity", "Failed to delete product", e);
                        });
            }
        }
        loadProductList();
    }
}
