package com.example.volunteer;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.volunteer.BuildConfig.DEBUG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public class MainActivity extends AppCompatActivity {


    //bottom navigation menu begin
    BottomNavigationView bottomNavigationView;
    //bottom navigation menu end

    //logout from navbar part
    TextView textViewName, textViewEmail;
    Menu bottomMenu;
    String getuserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //side menu begin
        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView= findViewById(R.id.side_navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //--------------------------------------------------side nav bar for posting the info from db ------------------------------------
        View header = navigationView.getHeaderView(0);
        TextView nav_name = (TextView) header.findViewById(R.id.name);
        TextView nav_email = (TextView) header.findViewById(R.id.email);
        //-----------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------logout part from side navbar -------------------------------
        textViewName = header.findViewById(R.id.name);
        textViewEmail = header.findViewById(R.id.email);

        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("MyAppName", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("logged", "false").equals("false")) {
            Intent intent = new Intent(MainActivity.this.getApplicationContext(), Login.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
        textViewName.setText(sharedPreferences.getString("name",""));
        textViewEmail.setText(sharedPreferences.getString("email",""));

        //popup window logout confirmation
        AlertDialog.Builder builder;
        builder= new AlertDialog.Builder((MainActivity.this));
        //---------------------------------------------------------logout part from sidebar end------------------------------------------


        //fetch database info to side bar menu

        //--------------------------------------------setting name with the value from the shared preference -------------
        nav_name.setText(sharedPreferences.getString("name",""));
        nav_email.setText(sharedPreferences.getString("email",""));
        //--------connecting to database and specific php script
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this.getApplicationContext());
        String url = Url_http.getURL()+"login-registration-android/profile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        })
        {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("email", sharedPreferences.getString("email",""));
                paramV.put("apiKey", sharedPreferences.getString("apiKey",""));
                return paramV;
            }
        };

        //fetch database info to side bar menu end

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id= item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch(id){
                    case R.id.nav_home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.nav_profile:
                        replaceFragment(new ProfileFragment());
                        break;
                    case R.id.nav_animals:
                        replaceFragment(new AnimalsFragment());
                        break;
                    case R.id.nav_medicine:
                        replaceFragment(new MedicineFragment());
                        break;
                    case R.id.nav_food:
                        replaceFragment(new FoodFragment());
                        break;
                    case R.id.nav_nature:
                        replaceFragment(new NatureFragment());
                        break;
                    case R.id.nav_children:
                       replaceFragment(new ChildrenFragment());
                        break;
                    case R.id.nav_logout:
                        //--------------------------------------------------logout part from side navbar-------------------------------
                        //popup window logout confirmation
                        builder.setTitle("Logout confirmation");
                        builder.setMessage("Are you sure you want to logout?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //logout begin
                                RequestQueue queue = Volley.newRequestQueue(MainActivity.this.getApplicationContext());
                                String url = Url_http.getURL()+"login-registration-android/logout.php";

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                if(response.equals("success")) {
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("logged", "");
                                                    editor.putString("name", "");
                                                    editor.putString("email", "");
                                                    editor.putString("apiKey", "");
                                                    editor.apply();
                                                    Intent intent = new Intent(MainActivity.this.getApplicationContext(), Login.class);
                                                    startActivity(intent);
                                                    MainActivity.this.finish();
                                                }
                                                else
                                                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();

                                    }
                                })
                                {
                                    protected Map<String, String> getParams() {
                                        Map<String, String> paramV = new HashMap<>();
                                        paramV.put("email", sharedPreferences.getString("email",""));
                                        paramV.put("apiKey", sharedPreferences.getString("apiKey",""));
                                        return paramV;
                                    }
                                };
                                queue.add(stringRequest);
                            }
                        });

                        //logout popup window
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        //logout popup window

                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Logout confirmation");
                        alert.show();
                        //--------------------------------------------------logout part from side navbar end-------------------------------
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        //side menu end

        //bottom navigation menu begin
        bottomNavigationView= findViewById(R.id.navbar);
        //the start fragment will be home fragment
        replaceFragment(new HomeFragment());
        //so that home icon will be clicked and not the first one
       bottomNavigationView.findViewById(R.id.home).performClick();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        replaceFragment(new HomeFragment());
                        return true;
                    case R.id.profile:
                        replaceFragment(new ProfileFragment());
                        return true;
                    case R.id.settings:
                        replaceFragment(new ProfilePostsFragment());
                        return true;

                }
                return false;
            }
        });
        //bottom navigation menu end

        ///--------------------------------get post  data from the database------------------------------------------------------


        //get post data from the database

    }

    // menu color

    //side bar begin
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public String getMyData() {
        return getuserEmail;
    }
    //side bar end
}