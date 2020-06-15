package com.example.contact;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>  {
    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }
    private Context context;
    private List<Contact> contactList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView thumbnail;
        public TextView name,number;
        public ViewHolder(View itemView){
            super(itemView);
            thumbnail=itemView.findViewById(R.id.thumbnail);
            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.number);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    String name,number,image;
                    name =contactList.get(pos).getName();
                    number =contactList.get(pos).getNumber();
                    image = contactList.get(pos).getImage();
                    Contact contact = new Contact();
                    contact.setImage(image);
                    contact.setName(name);
                    contact.setNumber(number);
                    Intent i = new Intent(context, SecondActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle b = new Bundle();
                    b.putSerializable("serial", contact);
                    i.putExtras(b);
                    context.startActivity(i);
                }
            });
        }
    }
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.user_row_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int position) {
        Contact contact=contactList.get(position);
        holder.number.setText(contact.getNumber());
        holder.name.setText(contact.getName());
        Glide.with(context).load(contact.getImage()).apply(RequestOptions.circleCropTransform()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}
