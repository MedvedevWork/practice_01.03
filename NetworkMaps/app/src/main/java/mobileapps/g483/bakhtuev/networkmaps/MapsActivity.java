package mobileapps.g483.bakhtuev.networkmaps;

import static android.content.ContentValues.TAG;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import mobileapps.g483.bakhtuev.networkmaps.databinding.ActivityMapsBinding;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;

import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity {

    private ActivityMapsBinding binding;
    RecyclerView recMaps;
    Toast message;
    MapsAdapter adapter;
    ArrayList<Map> maps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recMaps = findViewById(R.id.viewMaps);

        message = Toast.makeText(this,"",Toast.LENGTH_SHORT);

        Log.e(TAG, "onSuccessMaps: " + Param.token);

        ListRequest();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
        {
            final EditText editText = new EditText(MapsActivity.this);
            MaterialAlertDialogBuilder dialog = new
                    MaterialAlertDialogBuilder(MapsActivity.this)
                    .setTitle("New map")
                    .setView(editText)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", (dialog1, which) ->
                    {
                        HttpRequest request = new HttpRequest(this)
                        {
                            @Override
                            public void onSuccess(String data)
                            {
                                if(data.equals("null"))
                                {
                                    message.setText("Invalid credentials");
                                    message.show();
                                }
                                else
                                {
                                    ListRequest();
                                }
                            }
                            @Override
                            public void onError()
                            {
                                message.setText("Request failed");
                                message.show();
                            }
                        };
                        JSONObject object = new JSONObject();
                        try
                        {
                            object.put("mname", editText.getText().toString());
                            object.put("tok", Param.token);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            request.post("http://v2.fxnode.ru:8081/rpc/add_map", object.toString());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
            dialog.show();
        });
    }

    public void ListRequest()
    {
        maps.clear();
        HttpRequest request = new HttpRequest(this)
        {
            @Override
            public void onSuccess(String data) {
                Log.e(TAG, "successfully request");
                if (data.equals("null"))
                {
                    message.setText("Failed to upload notes");
                    message.show();
                }
                else
                {
                    try
                    {
                        JSONArray jsonArray = new JSONArray(data);
                            Log.e(TAG, "loop " + jsonArray.length());
                            for(int i = 0; i< jsonArray.length();i++) {
                                JSONObject rec = jsonArray.getJSONObject(i);
                                int id = rec.getInt("mid");
                                String name = rec.getString("mname");
                                maps.add(new Map(name,id));
                                Log.i("Name",name);
                            }
                        adapter = new MapsAdapter(maps,MapsActivity.this);
                        recMaps.setAdapter(adapter);
                        recMaps.setLayoutManager(new LinearLayoutManager(MapsActivity.this));
                    }
                    catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            @Override
            public void onError() {
                message.setText("Request failed");
                message.show();
            }
        };
        JSONObject obj = new JSONObject();
        try {
            obj.put("tok", Param.token);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            request.post("http://v2.fxnode.ru:8081/rpc/get_maps",obj.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}