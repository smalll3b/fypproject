package com.example.fypproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        holder.brandTextView.setText(product.brand);
        holder.priceTextView.setText(product.price);
        holder.weightTextView.setText(product.weight);
        holder.descriptionTextView.setText(product.description);
        holder.quantityTextView.setText(product.quantity);

        // 使用 Glide 加载图片，如果imageUrl为空，则加载默认图片
        Glide.with(holder.itemView.getContext())
                .load(product.imageUrl.isEmpty() ? R.drawable.ic_launcher_foreground : product.imageUrl)
                .into(holder.productImageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, brandTextView, priceTextView, weightTextView, descriptionTextView, quantityTextView;
        ImageView productImageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.product_name);
            brandTextView = itemView.findViewById(R.id.product_brand);
            priceTextView = itemView.findViewById(R.id.product_price);
            weightTextView = itemView.findViewById(R.id.product_weight);
            descriptionTextView = itemView.findViewById(R.id.product_description);
            quantityTextView = itemView.findViewById(R.id.product_quantity);
            productImageView = itemView.findViewById(R.id.product_image);
        }
    }
}



