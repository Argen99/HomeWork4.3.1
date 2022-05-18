package com.geektech.homework42;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.homework42.models.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements Filterable {

    private ArrayList<Article> list;
    private OnItemClickListener clickListener;

    public NewsAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Article article) {
        list.add(0,article);
        notifyItemInserted(0);
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public Article getItem(int position) {
        return list.get(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addItems(List<Article> list) {
        list.sort(Comparator.comparing(Article::getDate));
        Collections.reverse(list);
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Article> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(list);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Article article:list) {
                    if (article.getText().toLowerCase().contains(filterPattern)){
                        filteredList.add(article);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Article> list1 = new ArrayList<>();
            list1.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
    public void setList(List<Article> list) {
        this.list = (ArrayList<Article>) list;
        notifyDataSetChanged();
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView textView, tvCount, tvDate;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            textView = itemView.findViewById(R.id.text_view);
            tvCount = itemView.findViewById(R.id.tv_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    clickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(Article article) {
            String sdf = new SimpleDateFormat("HH:mm, dd MMM yyy", Locale.getDefault()).format(new Date(article.getDate()));
            tvDate.setText(sdf);
            textView.setText(article.getText());
            String str = String.valueOf(getAdapterPosition() + 1);
            tvCount.setText(str);
        }
    }
}
