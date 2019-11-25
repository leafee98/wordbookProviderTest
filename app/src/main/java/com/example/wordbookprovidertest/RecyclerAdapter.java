package com.example.wordbookprovidertest;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView word, interpretation, example, id;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.id);
            this.word = itemView.findViewById(R.id.word);
            this.interpretation = itemView.findViewById(R.id.interpretation);
            this.example = itemView.findViewById(R.id.example);
        }
    }

    private List<Word> words;
    private Context context;

    RecyclerAdapter(Context context, List<Word> words) {
        this.context = context;
        this.words = words;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Word w = this.words.get(position);
        holder.id.setText(String.valueOf(w.getId()));
        holder.word.setText(w.getWord());
        holder.interpretation.setText(w.getInterpretation());
        holder.example.setText(w.getExample());
    }

    @Override
    public int getItemCount() {
        return this.words.size();
    }
}
