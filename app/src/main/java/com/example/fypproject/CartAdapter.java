package com.example.fypproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItemList;

    public CartAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);

        // 綁定數據到視圖
        holder.productName.setText(cartItem.productName);
        holder.productBrand.setText("品牌: " + cartItem.productBrand);
        holder.productPrice.setText("價格: $" + cartItem.price);
        holder.productQuantity.setText("數量: " + cartItem.quantity);

        // 使用 Glide 加載圖片
        Glide.with(holder.itemView.getContext())
                .load(cartItem.productImageUrl)
                .into(holder.productImage);

        // 設置刪除按鈕
        holder.btnDelete.setOnClickListener(v -> {
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart");
            String cartId = cartItem.cartId; // 獲取唯一 cartId

            if (cartId != null) {
                cartRef.child(cartId).removeValue()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // 刪除本地數據
                                if (position >= 0 && position < cartItemList.size()) {
                                    cartItemList.remove(position); // 刪除數據
                                    Log.i("CartAdapter", "項目已刪除，cartId: " + cartId);
                                } else {
                                    Log.e("CartAdapter", "無效位置: " + position);
                                }
                            } else {
                                Log.e("CartAdapter", "Firebase 刪除失敗: " + cartId);
                            }
                        });
            } else {
                Log.e("CartAdapter", "cartId 為 null，無法刪除");
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productBrand, productPrice, productQuantity;
        public ImageView productImage;
        public Button btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            // 綁定視圖
            productName = itemView.findViewById(R.id.product_name);
            productBrand = itemView.findViewById(R.id.product_brand);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productImage = itemView.findViewById(R.id.product_image);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
