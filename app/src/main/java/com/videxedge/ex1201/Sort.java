package com.videxedge.ex1201;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Sort extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
    }

    public void sortbtn(View view) {
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.menuDataIn): {
                Intent t = new Intent(this, MainActivity.class);
                startActivity(t);
                break;
            }
            case (R.id.menuWatch): {
                Intent t = new Intent(this, Watchtables.class);
                startActivity(t);
                break;
            }
            case (R.id.menuUpdate): {
                Intent t = new Intent(this, Update.class);
                startActivity(t);
                break;
            }
            default:
                break;
        }
        return true;
    }
}
