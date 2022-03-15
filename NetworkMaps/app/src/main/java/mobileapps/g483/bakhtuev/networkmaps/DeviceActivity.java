package mobileapps.g483.bakhtuev.networkmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}