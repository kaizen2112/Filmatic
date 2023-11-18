package com.example.filmatic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filmatic.Activity.DetailsActivity;
import com.example.filmatic.Domain.Datum;
import com.example.filmatic.R;

import java.util.ArrayList;
import java.util.List;

public class SpecificFilmListAdapter extends RecyclerView.Adapter<SpecificFilmListAdapter.ViewHolder> {
    private List<Datum> specificMovies;
    private Context context;

    public SpecificFilmListAdapter(List<Datum> specificMovies) {
        this.specificMovies = specificMovies;
    }


    public void addData(Datum movie) {
        specificMovies.add(movie);
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum movie = specificMovies.get(position);
        holder.titleTxt.setText(movie.getTitle());
        holder.ScoreTxt.setText(String.valueOf(movie.getImdbRating()));

        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailsActivity.class);
            intent.putExtra("id", movie.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return specificMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, ScoreTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            ScoreTxt = itemView.findViewById(R.id.scoreTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
