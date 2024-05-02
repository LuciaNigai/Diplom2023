package com.example.volunteer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonalInformationFragment extends Fragment {

    String url = Url_http.getURL();
    TextView textViewName, textViewEmail;
    ImageView imageViewProfilePic;
    Button buttonChangeName, buttonChangeEmail, buttonChangePhoto, buttonGoBack;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewName=view.findViewById(R.id.user_name);
        textViewEmail=view.findViewById(R.id.user_email);
        imageViewProfilePic=view.findViewById(R.id.imageView_profile);
        //setting user name, email and photo from database
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

                        textViewName.setText(name);
                        textViewEmail.setText(email);
                        imageViewProfilePic.setImageResource(getResources().getIdentifier(image, "drawable", getActivity().getPackageName()));
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

        //------------------------------------------------------------change name button logic------------------------------------------
        AlertDialog.Builder builderName;
        builderName= new AlertDialog.Builder(getActivity());

        buttonChangeName=view.findViewById(R.id.change_name_button);

        buttonChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderName.setTitle("Name change confirmation");
                builderName.setMessage("Are you sure you want to change your name?");
                builderName.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = new ChangeUserNameFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                builderName.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alert = builderName.create();
                //Setting the title manually
                alert.setTitle("Name change confirmation");
                alert.show();
            }
        });

        //----------------------------------------------change name end-----------------------------------------------

        //------------------------------------------------------change user email button logic--------------------

        buttonChangeEmail=view.findViewById(R.id.change_email_button);
        AlertDialog.Builder builderEmail;
        builderEmail= new AlertDialog.Builder(getActivity());

        buttonChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderEmail.setTitle("Email change confirmation");
                builderEmail.setMessage("Are you sure you want to change your email?");
                builderEmail.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = new ChangeUserEmailFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                builderEmail.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alert = builderEmail.create();
                //Setting the title manually
                alert.setTitle("Email change confirmation");
                alert.show();
            }
        });

        //---------------------------------------------------change  email end-------------------------------------

        //------------------------------------------------------change user photo button logic--------------------

        buttonChangePhoto=view.findViewById(R.id.change_photo_button);
        AlertDialog.Builder builderPhoto;
        builderPhoto= new AlertDialog.Builder(getActivity());

        buttonChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderPhoto.setTitle("Photo change confirmation");
                builderPhoto.setMessage("Are you sure you want to change your photo?");
                builderPhoto.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = new ChangeUserPhotoFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                builderPhoto.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alert = builderPhoto.create();
                //Setting the title manually
                alert.setTitle("Photo change confirmation");
                alert.show();
            }
        });

        //---------------------------------------------------change photo end-------------------------------------


        //go back button logic
        buttonGoBack=view.findViewById(R.id.go_back_button);
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}