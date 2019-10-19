package com.example.amazonmyversion.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonmyversion.Interface.ItemClickListner;
import com.example.amazonmyversion.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView textproductname,texxtproductdescription,txtproductprice;
    public ImageView imageView;
    public ItemClickListner listner;
    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);
        imageView=(ImageView) itemView.findViewById(R.id.product_image);
        textproductname=(TextView) itemView.findViewById(R.id.product_name);
        texxtproductdescription=(TextView) itemView.findViewById(R.id.product_description);
        txtproductprice=(TextView) itemView.findViewById(R.id.product_price);






    }
    public void setItemClickListener(ItemClickListner listner)
    {
        this.listner=listner;

    }


    @Override
    public void onClick(View v)
    {
        listner.onClick(v,getAdapterPosition(),false);


    }
}
