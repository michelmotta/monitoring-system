package com.example.michel.monitoringsystemmvp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michel.monitoringsystemmvp.R;
import com.example.michel.monitoringsystemmvp.model.Temperature;

import java.util.List;

public class TemperatureListAdapter extends RecyclerView.Adapter<TemperatureListAdapter.ViewHolder>{

    private List<Temperature> temperatureList;
    private Context context;

    public TemperatureListAdapter(List<Temperature> temperatureList, Context context) {
        this.temperatureList = temperatureList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_historic_temperature, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Temperature temperature = temperatureList.get(position);

        holder.textViewTempId.setText("Código da Temperatura: " + String.valueOf(temperature.getId()));
        holder.textViewServerId.setText("Código do Servidor: " + String.valueOf(temperature.getServer_id()));
        holder.textViewServerName.setText("Nome do Servidor: " + temperature.getServer_name());
        holder.textViewTemp.setText("Temperatura: " + String.valueOf(temperature.getTemperature()));
        holder.textViewHum.setText("Umidade: " + String.valueOf(temperature.getHumidity()));
        holder.textViewTime.setText("Tempo: " + temperature.getTime());

    }

    @Override
    public int getItemCount() {
        return temperatureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTempId;
        public TextView textViewServerId;
        public TextView textViewServerName;
        public TextView textViewTemp;
        public TextView textViewHum;
        public TextView textViewTime;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTempId = itemView.findViewById(R.id.textViewTempId);
            textViewServerId = itemView.findViewById(R.id.textViewServerId);
            textViewServerName = itemView.findViewById(R.id.textViewServerName);
            textViewTemp = itemView.findViewById(R.id.textViewTemp);
            textViewHum = itemView.findViewById(R.id.textViewHum);
            textViewTime = itemView.findViewById(R.id.textViewTime);

        }
    }
}
