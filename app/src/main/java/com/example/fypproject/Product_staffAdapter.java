package com.example.fypproject;

import android.content.Intent;
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

public class Product_staffAdapter extends RecyclerView.Adapter<Product_staffAdapter.ProductViewHolder> {
    private List<Product> productList;
    private DatabaseReference mDatabase;

    public Product_staffAdapter(List<Product> productList) {
        this.productList = productList;
        mDatabase = FirebaseDatabase.getInstance().getReference("products");
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_edit, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText("Name: " + product.name);
        holder.brandTextView.setText("Brand: " + product.brand);
        holder.priceTextView.setText("Price: " + product.price);
        holder.weightTextView.setText("Weight: " + product.weight);
        holder.descriptionTextView.setText("Description: " + product.description);
        holder.quantityTextView.setText("Quantity: " + product.quantity);

        Glide.with(holder.itemView.getContext())
                .load(product.imageUrl.isEmpty() ? R.drawable.ic_launcher_foreground : product.imageUrl)
                .into(holder.productImageView);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), EditProductActivity.class);
                intent.putExtra("productId", product.id);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int adapterPosition = holder.getAdapterPosition();
                mDatabase.child(product.id).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            try {
                                productList.remove(adapterPosition);
                                notifyItemRemoved(adapterPosition);
                                notifyItemRangeChanged(adapterPosition, productList.size());
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace(); // Handle the exception gracefully
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, brandTextView, priceTextView, weightTextView, descriptionTextView, quantityTextView;
        ImageView productImageView;
        Button editButton, deleteButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.product_name);
            brandTextView = itemView.findViewById(R.id.product_brand);
            priceTextView = itemView.findViewById(R.id.product_price);
            weightTextView = itemView.findViewById(R.id.product_weight);
            descriptionTextView = itemView.findViewById(R.id.product_description);
            quantityTextView = itemView.findViewById(R.id.product_quantity);
            productImageView = itemView.findViewById(R.id.product_image);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
