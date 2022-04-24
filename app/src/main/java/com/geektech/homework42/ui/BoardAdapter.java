package com.geektech.homework42.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.homework42.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    private String[] titles = new String[] {"Привет","Hello","Салам"};
    private int[] images = new int[]{R.drawable.img1,R.drawable.img2,R.drawable.img3};
    private String[] description = new String[] {"Россия","America","Кыргызстан"};

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoardViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pager_board,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    class BoardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textTitle, textDescription;
        private Button button;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        public void bind(int position) {
            textTitle.setText(titles[position]);
            imageView.setImageResource(images[position]);
            textDescription.setText(description[position]);

            if (position == titles.length - 1)
                button.setVisibility(View.VISIBLE);
            else
                button.setVisibility(View.INVISIBLE);
        }

        private void findViewById(@NonNull View itemView) {
            textTitle = itemView.findViewById(R.id.text_title);
            button = itemView.findViewById(R.id.pager_btn);
            imageView = itemView.findViewById(R.id.pager_image);
            textDescription = itemView.findViewById(R.id.text_description);
        }
    }
}
