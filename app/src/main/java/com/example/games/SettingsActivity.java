package com.example.games;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
public class SettingsActivity extends AppCompatActivity {
Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        if(!pref.contains("service_is_on")) {
            editor.putBoolean("service_is_on", false);
            editor.commit();
        }
        Toolbar toolbar= findViewById(R.id.toolbar);
        Switch notificationSwitch=findViewById(R.id.articles_notifications);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(getSharedPreferences("MyPref",0).getBoolean("service_is_on",false))
            notificationSwitch.setChecked(true);
        else
            notificationSwitch.setChecked(false);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    getSharedPreferences("MyPref",0).edit().putBoolean("service_is_on",true).apply();
                    System.out.println(getSharedPreferences("MyPref",0).getBoolean("service_is_on",false));
                    serviceIntent = new Intent(SettingsActivity.this, NewsService.class);
                    SettingsActivity.this.startService(serviceIntent);
                }
                else {
                    getSharedPreferences("MyPref",0).edit().putBoolean("service_is_on",false).apply();
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
