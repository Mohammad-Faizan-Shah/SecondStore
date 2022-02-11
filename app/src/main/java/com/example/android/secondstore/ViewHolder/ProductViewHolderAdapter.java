package com.example.android.secondstore.ViewHolder;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.secondstore.Fragments.HomeFragment;
import com.example.android.secondstore.Model.ProductsModel;
import com.example.android.secondstore.R;
import com.example.android.secondstore.Fragments.ProductDescriptionFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ProductViewHolderAdapter extends FirebaseRecyclerAdapter<ProductsModel, ProductViewHolderAdapter.productViewHOlder> {


    public ProductViewHolderAdapter(@NonNull FirebaseRecyclerOptions<ProductsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull productViewHOlder holder, int position, @NonNull ProductsModel model) {
        holder.productName.setText(model.getPname());
        holder.productPrice.setText("Price = ₹" + model.getPrice());
        holder.productDescription.setText(model.getDescription());
        holder.postedShow.setText(model.getPosted());
        Picasso.get().load(model.getImage()).into(holder.productImage);

            holder.productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity=(AppCompatActivity)v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new ProductDescriptionFragment(model.getImage(),model.getPname(),model.getDescription(),model.getPrice(),model.getCategory(),model.getDate(),model.getTime(),model.getPid(),model.getPosted())).addToBackStack(null).commit();


                }
            });
    }

    @NonNull
    @Override
    public productViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_itmes, parent, false);
        return new productViewHOlder(view);
    }

    public class productViewHOlder extends RecyclerView.ViewHolder {

        public TextView productName, productDescription, productPrice, postedShow;
        public ImageView productImage;

        public productViewHOlder(@NonNull View itemView) {
            super(itemView);

            productName = (TextView)itemView.findViewById(R.id.product_name);
            productPrice = (TextView)itemView.findViewById(R.id.product_price);
            productDescription = (TextView)itemView.findViewById(R.id.product_description);
            productImage = (ImageView)itemView.findViewById(R.id.product_image);
            postedShow = (TextView)itemView.findViewById(R.id.postedshow);
        }
    }

}




        // old adapter code

                        /*
                        import android.view.View;
                    import android.widget.ImageView;
                    import android.widget.TextView;

                    import androidx.annotation.NonNull;
                    import androidx.recyclerview.widget.RecyclerView;

                    import com.example.android.secondstore.Interface.ItemClickListner;
                    import com.example.android.secondstore.R;

                    public class ProductViewHolderAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

                       public TextView productName, productDescription, productPrice;
                       public ImageView productImage;
                        private ItemClickListner itemClickListner;

                        public ProductViewHolderAdapter(@NonNull View itemView) {
                           super(itemView);

                            productName = (TextView)itemView.findViewById(R.id.product_name);
                            productPrice = (TextView)itemView.findViewById(R.id.product_price);
                            productDescription = (TextView)itemView.findViewById(R.id.product_description);
                            productImage = (ImageView)itemView.findViewById(R.id.product_image);
                       }

                        public void setItemClicklistner(ItemClickListner listner){
                           this.itemClickListner  = listner;
                        }


                        @Override
                        public void onClick(View v) {
                            itemClickListner.onClick(v,getAdapterPosition(),false);
                        }
                    }

                    */