
package com.example.sh_stp.activity1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sh_stp.R;
import com.example.sh_stp.data.Motorcycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MotorcycleRecyclerViewAdapter
        extends RecyclerView.Adapter<MotorcycleRecyclerViewAdapter.MotorcycleItemHolder> {

    private List<Motorcycle> motorcycles = new ArrayList<>();

    public void setMotorcycles(List<Motorcycle> motorcycles) {
        this.motorcycles = motorcycles == null
                ? new ArrayList<>()
                : new ArrayList<>(motorcycles);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MotorcycleItemHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.motorcycle_recycler_item,
                parent,
                false
        );

        return new MotorcycleItemHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MotorcycleItemHolder holder,
            int position
    ) {
        holder.bind(motorcycles.get(position));
    }

    @Override
    public int getItemCount() {
        return motorcycles.size();
    }

    public static class MotorcycleItemHolder
            extends RecyclerView.ViewHolder {

        private final TextView textBrand;
        private final TextView textModel;
        private final TextView textYear;
        private final TextView textEngineVolume;

        public MotorcycleItemHolder(@NonNull View itemView) {
            super(itemView);

            textBrand = itemView.findViewById(R.id.textBrand);
            textModel = itemView.findViewById(R.id.textModel);
            textYear = itemView.findViewById(R.id.textYear);
            textEngineVolume =
                    itemView.findViewById(R.id.textEngineVolume);
        }

        public void bind(Motorcycle motorcycle) {
            textBrand.setText(motorcycle.getBrand());
            textModel.setText(motorcycle.getModel());
            textYear.setText("Рік випуску: " + motorcycle.getYear());

            textEngineVolume.setText(
                    String.format(
                            Locale.getDefault(),
                            "Об'єм двигуна: %.1f см³",
                            motorcycle.getEngineVolume()
                    )
            );
        }
    }
}
