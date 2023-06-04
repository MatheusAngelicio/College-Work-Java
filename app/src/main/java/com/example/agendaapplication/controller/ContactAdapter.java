package com.example.agendaapplication.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendaapplication.R;
import com.example.agendaapplication.model.Contact;
import com.example.agendaapplication.model.DataModel;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(View view,int position);
    }

    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public void setOnItemClicklistener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setLongClickListener(OnItemLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(view -> {
                if (clickListener != null) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(view -> {
                if (longClickListener != null){
                    return longClickListener.onItemLongClick(view, getAdapterPosition());
                }
                return false;
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(
                R.layout.item_recycler_view,
                parent,
                false
        );
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact c = DataModel.getInstance().contacts.get(position);
        holder.textView1.setText(c.getName());
        holder.textView2.setText(c.getPhone());

    }

    @Override
    public int getItemCount() {
        return DataModel.getInstance().contacts.size();
    }

}
