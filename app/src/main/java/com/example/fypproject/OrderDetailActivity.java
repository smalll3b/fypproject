package com.example.fypproject;

import android.os.Bundle;
import android.util.Log;
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

public class OrderDetailActivity extends AppCompatActivity {
    private RecyclerView ordersRecyclerView;
    private OrdersAdapter ordersAdapter;
    private List<OrderItem> orderItemList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // 初始化 RecyclerView
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderItemList = new ArrayList<>();
        ordersAdapter = new OrdersAdapter(orderItemList);
        ordersRecyclerView.setAdapter(ordersAdapter);

        // Firebase 參考
        ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        // 加載訂單數據
        loadOrders();
    }

    private void loadOrders() {
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderItemList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrderItem orderItem = snapshot.getValue(OrderItem.class);
                    if (orderItem != null) {
                        orderItemList.add(orderItem);
                    }
                }
                ordersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderDetailActivity.this, "無法加載訂單數據", Toast.LENGTH_SHORT).show();
                Log.e("OrderDetailActivity", "加載失敗", databaseError.toException());
            }
        });
    }
}
