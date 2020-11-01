package com.app.hnbpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.hnbpro.Videos.video1;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_sair) {
                    usuario.signOut();
                    VoltarParaaTeladeLogin();
                } else if (id == R.id.nav_share) {
                    enviarEmail();
                } else if(id == R.id.nav_settings){
                    Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                    startActivity(intent);
                    finish();
                }
                return onMenuItemSelected(id,item);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.toolbar);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
    private void VoltarParaaTeladeLogin(){
        Intent intent = new Intent(MainActivity.this,FormLogin.class);
        startActivity(intent);
        finish();
    }
    public void enviarEmail() {
        final String PACKAGE_GOOGLEEMAIL = "com.google.android.gm";
        Intent email = new Intent(Intent.ACTION_SEND);//Enviar um e-mail
        email.putExtra(Intent.EXTRA_EMAIL, new String []{"hnbproime@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, " ");
        email.putExtra(Intent.EXTRA_TEXT, " ");
        email.setType("message/rfc822");
        email.setPackage(PACKAGE_GOOGLEEMAIL);
        startActivity(Intent.createChooser(email," "));
    }
    public void irparaTeste(View view){
        Intent intent = new Intent(getApplicationContext(), Teste.class);
        startActivity(intent);
    }
    public void irparaTeste2(View view){
        Intent intent = new Intent(getApplicationContext(), Teste2.class);
        startActivity(intent);
    }
}