package com.example.farmahomie;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmahomie.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        //esto estaba antes pero no lo se utilizar, es un metodo para el boton central de debajo
        /* binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        );*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        //Creating the popupmenu with the three buttons

    }


    public void showPopup(View view){
        PopupMenu popup=new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popupmenu);
        popup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Codigo de barras", Toast.LENGTH_SHORT);
                return true;

            case R.id.item2:
                Toast.makeText(this, "Codigo nacional", Toast.LENGTH_SHORT);
                return true;
            case R.id.item3:
                Toast.makeText(this, "Nombre del medicamento", Toast.LENGTH_SHORT);
               switchMaintoNewmed();
            case R.id.action_settings:{
                switchMaintoSettings();
            }
                return true;
        default:
            return false;
        }
    }
        //menu para setting

    public void openMaps(View view) {
        // Do something in response to button
        switchMaintoMaps();

    }

    private void switchMaintoMaps() {

        startActivity(new Intent(MainActivity.this, MapsActivity.class));

    }

    public void openSettings(View view) {
        // Do something in response to button
        switchMaintoSettings();

    }

    private void switchMaintoSettings() {

        startActivity(new Intent(MainActivity.this, SettingsActivity.class));

    }
    private void switchMaintoNewmed() {

        startActivity(new Intent(MainActivity.this, NewMedActivity.class));

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}