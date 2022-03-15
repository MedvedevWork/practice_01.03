package mobileapps.g483.bakhtuev.networkmaps;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText login;
    EditText password;
    Toast message_error;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.editUser);
        password = findViewById(R.id.editPassword);

        message_error = Toast.makeText(this,"",Toast.LENGTH_SHORT);

        login.setText("user");
        password.setText("password");
    }

    public void onLogIn(View v) throws JSONException, InterruptedException
    {
        HttpRequest request = new HttpRequest(this)
        {
            @Override
            public void onSuccess(String data) {
                if (data.equals("null"))
                { }
                else
                {
                    Param.token = data.replace("\"","");
                    Intent maps = new Intent(MainActivity.this, MapsActivity.class);
                    Log.e(TAG, "onSuccess: " + Param.token );
                    startActivity(maps);
                }
            }
            @Override
            public void onError() {
                message_error.setText("Request failed!");
                message_error.show();
            }
        };
        JSONObject object = new JSONObject();
        try
        {
            object.put("uname", login.getText().toString());
            object.put("usecret", password.getText().toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        request.post("http://v2.fxnode.ru:8081/rpc/open_session",object.toString());
    }

    public void onRegister(View v)
    {
        Intent reg = new Intent(this, RegistrationActivity.class);
        someActivityResultLauncher.launch(reg);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        login.setText(data.getStringExtra("user"));
                        password.setText(data.getStringExtra("password"));
                    }
                }
            });
}