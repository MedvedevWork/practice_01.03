package mobileapps.g483.bakhtuev.networkmaps;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    EditText login;
    EditText password;
    Toast message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        login = findViewById(R.id.editRegUser);
        password = findViewById(R.id.editRegPassword);
        message = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    public void onRegClick(View view) throws InterruptedException {
        HttpRequest request = new HttpRequest(this)
        {
            @Override
            public void onSuccess(String data) {
                if (data == "false")
                {
                    message.setText("User already exists");
                    message.show();
                }
                else
                {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    intent.putExtra("user", login.getText().toString());
                    intent.putExtra("password", password.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
            @Override
            public void onError() {
                message.setText("Register failed!");
                message.show();
            }
        };
        JSONObject obj = new JSONObject();
        try
        {
            obj.put("usr", login.getText().toString());
            obj.put("passwd", password.getText().toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        request.post("http://v2.fxnode.ru:8081/rpc/add_account",obj.toString());
    }

    public void onCancelClick(View v) { finish(); }
}