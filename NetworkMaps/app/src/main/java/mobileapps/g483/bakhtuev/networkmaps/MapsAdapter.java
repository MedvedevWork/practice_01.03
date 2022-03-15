package mobileapps.g483.bakhtuev.networkmaps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.ViewHolder> {

    private Context context;
    private List<Map> nMaps;
    public MapsAdapter(List<Map> maps,Context context) {
        nMaps = maps;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.map_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map maps = nMaps.get(position);
        holder.mapName.setText(maps.getName());
    }

    @Override
    public int getItemCount() {
        return nMaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mapName;
        public Button btnRename;
        public Button btnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            mapName = itemView.findViewById(R.id.textMap);
            btnRename = itemView.findViewById(R.id.buttonRename);
            btnDelete = itemView.findViewById(R.id.buttonDelete);

            final EditText editText = new EditText(context);
            MaterialAlertDialogBuilder dialog = new
                    MaterialAlertDialogBuilder(context)
                    .setTitle("New map")
                    .setView(editText)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Rename", (dialog1, which) -> {
                    });

            btnRename.setOnClickListener(v -> {
                notifyDataSetChanged();
                dialog.show();
            });

            btnDelete.setOnClickListener(v -> {
                notifyDataSetChanged();
            });
        }
    }
}