package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emergency.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import models.ViolenceReports;

public class ReportAdapter extends FirebaseRecyclerAdapter<ViolenceReports,ReportAdapter.reportViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ReportAdapter(@NonNull FirebaseRecyclerOptions<ViolenceReports> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull reportViewHolder holder, int position, @NonNull ViolenceReports model) {
            holder.tvType.setText(model.getType());
            holder.tvDescription.setText(model.getDescription());
            holder.tvLocation.setText(model.getLocation());
            holder.tvPhone.setText(model.getPhone());
    }

    @NonNull
    @Override
    public reportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gbv_item,parent,false);

        return new ReportAdapter.reportViewHolder(view);
    }

    class reportViewHolder extends RecyclerView.ViewHolder{
        TextView tvType,tvDescription,tvLocation,tvPhone;
        public reportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType=itemView.findViewById(R.id.tvType);
            tvDescription=itemView.findViewById(R.id.tvDescription);
            tvLocation=itemView.findViewById(R.id.tvLocation);
            tvPhone=itemView.findViewById(R.id.tvPhone);
        }
    }
}
