package com.example.fypproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.name);
        holder.brandTextView.setText("Brand\n" + product.brand);
        holder.priceTextView.setText("$" + product.price);
        holder.tvStock.setText("庫存 " + product.quantity);

        // Use Glide to load the image, with a default image if imageUrl is empty
        Glide.with(holder.itemView.getContext())
                .load(product.imageUrl.isEmpty() ? R.drawable.ic_launcher_foreground : product.imageUrl)
                .into(holder.productImageView);

        holder.llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId", product.id);
                holder.itemView.getContext().startActivity(intent);
            }
        });
//
//        // Set Buy button click event
//        holder.buyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Decrease product quantity
//                int currentQuantity = Integer.parseInt(product.quantity);
//                if (currentQuantity > 0) {
//                    currentQuantity--;
//                    product.quantity = String.valueOf(currentQuantity);
//                    holder.quantityTextView.setText("Quantity: " + product.quantity);
//
//                    // Update Firebase database with the new quantity
//                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("products").child(product.id);
//                    databaseReference.child("quantity").setValue(product.quantity);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, brandTextView, priceTextView, tvStock;
        ImageView productImageView;
        Button buyButton, btnDetail;
        LinearLayout llProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.product_name);
            brandTextView = itemView.findViewById(R.id.product_brand);
            priceTextView = itemView.findViewById(R.id.product_price);
            productImageView = itemView.findViewById(R.id.product_image);
            tvStock = itemView.findViewById(R.id.product_stock);
            llProduct = itemView.findViewById(R.id.llProduct);
        }
    }
}