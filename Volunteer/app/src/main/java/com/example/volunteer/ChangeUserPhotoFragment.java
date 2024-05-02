package com.example.volunteer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChangeUserPhotoFragment extends Fragment {

    ImageView imageViewChangePhoto;
    Button buttonOK, buttonCancel;
    String url = Url_http.getURL();

    Bitmap postImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_user_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewChangePhoto=view.findViewById(R.id.user_change_image);


        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                postImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                imageViewChangePhoto.setImageBitmap(postImage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });
        imageViewChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });
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

                        imageViewChangePhoto.setImageResource(getResources().getIdentifier(image, "drawable", getActivity().getPackageName()));
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

        buttonCancel=view.findViewById(R.id.change_photo_cancel);
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


        buttonOK=view.findViewById(R.id.change_photo_OK);
        AlertDialog.Builder builder;
        builder= new AlertDialog.Builder(getActivity());
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Photo change confirmation");
                builder.setMessage("Are you sure you want to change your profile photo?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ByteArrayOutputStream byteArrayOutputStream;
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        if (postImage != null) {
                            postImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            byte[] bytes = byteArrayOutputStream.toByteArray();
                            final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "login-registration-android/change_userphoto.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("success")) {
                                                Fragment fragment = new PersonalInformationFragment();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.container, fragment);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
                                                //if want to go to comments page
                                                Toast.makeText(getActivity().getApplicationContext(), "Image Changed", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(getActivity().getApplicationContext(), "Bad bad bad", Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                protected Map<String, String> getParams() {
                                    Map<String, String> paramV = new HashMap<>();
                                    paramV.put("image", base64Image);
                                    return paramV;
                                }
                            };
                            queue.add(stringRequest);
                        }
                        else
                           Toast.makeText(getActivity().getApplicationContext(),"Upload Image",Toast.LENGTH_SHORT).show();
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
                alert.setTitle("Photo change confirmation");
                alert.show();
            }
        });
    }
}