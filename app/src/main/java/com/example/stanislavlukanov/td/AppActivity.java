package com.example.stanislavlukanov.td;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.stanislavlukanov.td.Navigation_Menu_Fragments.ArhieveFragment;
import com.example.stanislavlukanov.td.Navigation_Menu_Fragments.CatalogFragment;
import com.example.stanislavlukanov.td.Navigation_Menu_Fragments.DevRes.DevelopmentsFragment;
import com.example.stanislavlukanov.td.Navigation_Menu_Fragments.DevRes.Tab1;
import com.example.stanislavlukanov.td.Navigation_Menu_Fragments.DevRes.Tab2;
import com.example.stanislavlukanov.td.Navigation_Menu_Fragments.MainFragment;
import com.example.stanislavlukanov.td.Navigation_Menu_Fragments.NotesFragment;
import com.example.stanislavlukanov.td.Navigation_Menu_Fragments.StatisticFragment;


public class AppActivity extends AppCompatActivity implements Tab1.OnFragmentInteractionListener,Tab2.OnFragmentInteractionListener{

    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private void showMessage(@StringRes int string) {   // метод для показа уведомления (можно использовать как дефолтный и просто вызывать по несколько раз)
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.ac_app);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit();


          ActionBar actionbar = getSupportActionBar();
          actionbar.setDisplayHomeAsUpEnabled(true);
          actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
          mDrawerLayout = findViewById(R.id.drawer_layout);
          drawerToggle = setupDrawerToggle();
          mDrawerLayout.addDrawerListener(drawerToggle);

          NavigationView navigationView = findViewById(R.id.nav_view);
          navigationView.setNavigationItemSelectedListener(
                  new NavigationView.OnNavigationItemSelectedListener() {
                      @Override
                      public boolean onNavigationItemSelected(MenuItem menuItem) {

                          switch (menuItem.getItemId()){
                              case (R.id.nav_main):
                                  setTitle(R.string.app_main_MainMenu_name);
                                  getSupportFragmentManager().beginTransaction()
                                          .replace(R.id.container, MainFragment.newInstance())
                                          .addToBackStack(MainFragment.class.getName())
                                          .commit();
                                  break;
                              case (R.id.nav_notes):
                                  setTitle(R.string.app_main_NotesMenu_name);
                                  getSupportFragmentManager().beginTransaction()
                                          .replace(R.id.container, NotesFragment.newInstance())
                                          .addToBackStack(NotesFragment.class.getName())
                                          .commit();
                                  break;
                              case (R.id.nav_developmens):
                                  setTitle(R.string.app_main_DevelopmentsMenu_name);
                                  getSupportFragmentManager().beginTransaction()
                                          .replace(R.id.container, DevelopmentsFragment.newInstance())
                                          .addToBackStack(DevelopmentsFragment.class.getName())
                                          .commit();
                                  break;
                              case (R.id.nav_statistics):
                                  setTitle(R.string.app_main_StatisticsMenu_name);
                                  getSupportFragmentManager().beginTransaction()
                                          .replace(R.id.container, StatisticFragment.newInstance())
                                          .addToBackStack(StatisticFragment.class.getName())
                                          .commit();
                                  break;
                              case (R.id.nav_catalog):
                                  setTitle(R.string.app_main_CatalogMenu_name);
                                  getSupportFragmentManager().beginTransaction()
                                          .replace(R.id.container, CatalogFragment.newInstance())
                                          .addToBackStack(CatalogFragment.class.getName())
                                          .commit();
                                  break;
                              case (R.id.nav_arhieve):
                                  setTitle(R.string.app_main_ArhieveMenu_name);
                                  getSupportFragmentManager().beginTransaction()
                                          .replace(R.id.container, ArhieveFragment.newInstance())
                                          .addToBackStack(ArhieveFragment.class.getName())
                                          .commit();
                                  break;
                                  default:
                                      break;
                          }
                          // Нажали на айтем и он закрылся
                          menuItem.setChecked(true);
                          mDrawerLayout.closeDrawers();

                          return true;
                      }
                  });

    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open,  R.string.navigation_drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()){
            case android.R.id.home:
                // if(android.R.id.home) Логика открытия по кнопке меню
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    return true;
                }
            case R.id.actionSettings:
                //todo
                // Activity Настройки
              //  intent.putExtra(EXTRA_MESSAGE, "Hello");
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.actionLogout:
                startActivity(new Intent(this, AuthActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
