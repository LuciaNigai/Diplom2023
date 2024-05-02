package com.example.volunteer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangeUserNameFragment extends Fragment {


    TextInputEditText textInputEditTextChangeName;
    Button buttonOK, buttonCancel;
    String url = Url_http.getURL();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_user_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputEditTextChangeName=view.findViewById(R.id.change_name);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"login-registration-android/get_user_info.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray itemsJsonArray = new JSONArray(response);

                    for(int i=0; i<itemsJsonArray.length(); i++){
                        JSONObject itemsObject = itemsJsonArray.getJSONObject(i);
                        String name = itemsObject.getString("name");
                        String email= itemsObject.getString("email");
                        String image=itemsObject.getString("user_photo");

                        textInputEditTextChangeName.setText(name);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);

        //cancel button logic

        buttonCancel=view.findViewById(R.id.change_name_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PersonalInformationFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        buttonOK=view.findViewById(R.id.change_name_OK);
        AlertDialog.Builder builder;
        builder= new AlertDialog.Builder(getActivity());
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Name change confirmation");
                builder.setMessage("Are you sure you want to change your name?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String changedName =String.valueOf(textInputEditTextChangeName.getText());
                        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "login-registration-android/change_username.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("success")){
                                            Fragment fragment = new PersonalInformationFragment();
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.container, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            //if want to go to comments page
                                            Toast.makeText(getActivity().getApplicationContext(), "Name Changed", Toast.LENGTH_SHORT).show();
                                        }
                                        else Toast.makeText(getActivity().getApplicationContext(), "Bad bad bad", Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            protected Map<String, String> getParams() {
                                Map<String, String> paramV = new HashMap<>();
                                paramV.put("name", changedName);
                                return  paramV;
                            }
                        };
                        queue.add(stringRequest);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Fragment fragment = new PersonalInformationFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Name change confirmation");
                alert.show();
            }
        });

    }


}