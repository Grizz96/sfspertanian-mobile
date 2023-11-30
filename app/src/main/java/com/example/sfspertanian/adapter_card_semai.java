package com.example.sfspertanian;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class adapter_card_semai extends RecyclerView.Adapter<adapter_card_semai.ViewHolder> {

    private List<ModelCardSemai> dataList;
    private Context context;

    // Constructor for AdapterCardSemai
    public adapter_card_semai(Context context, List<ModelCardSemai> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    // Inner class ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleCard, subTitleCard, timeCard, dateCard;

        public ViewHolder(View itemView) {
            super(itemView);

            titleCard = itemView.findViewById(R.id.titleCard);
            subTitleCard = itemView.findViewById(R.id.subTitleCard);
            timeCard = itemView.findViewById(R.id.timeCard);
            dateCard = itemView.findViewById(R.id.DateCard);
        }
    }

    // onCreateViewHolder to create a new ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_catatan_semai, parent, false);
        return new ViewHolder(view);
    }

    // onBindViewHolder to fill data into the views in ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelCardSemai data = dataList.get(position);

        holder.titleCard.setText(data.getTitle());
        holder.subTitleCard.setText("Detail: " + data.getSubtitle());
        holder.timeCard.setText(data.getTime());
        holder.dateCard.setText(data.getDate());
    }

    // getItemCount to get the number of items in the data set
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
