package nhung.nguyen.n01274584;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class NhungActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhung);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nhungNav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nhungFragment_container, new NhHome()).commit();
            navigationView.setCheckedItem(R.id.nhungHome);
        }


    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(browser);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nhungHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.nhungFragment_container,new NhHome()).commit();
                break;
            case R.id.nhungNhung:
                getSupportFragmentManager().beginTransaction().replace(R.id.nhungFragment_container,new NhuDown()).commit();

                break;
            case R.id.nhungNguyen:
                getSupportFragmentManager().beginTransaction().replace(R.id.nhungFragment_container,new NgSrv()).commit();
                break;
            case R.id.nhungSetting:
                getSupportFragmentManager().beginTransaction().replace(R.id.nhungFragment_container,new NhuSet()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}