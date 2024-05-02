package com.example.volunteer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.text.method.PasswordTransformationMethod;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    TextInputEditText textInputEditTextName, textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSubmit;
    String name, email, password, image;
    TextView textViewError, textViewBackToLogin;
    ProgressBar progressBar;
    CheckBox checkBoxShow;
    ImageView imageViewPic;

    //image upload part
    Bitmap postImage;

    private static final int PICK_IMAGE_REQUEST=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        textInputEditTextName = findViewById(R.id.name);
        textInputEditTextEmail=findViewById(R.id.email);
        textInputEditTextPassword= findViewById(R.id.password);
        buttonSubmit = findViewById(R.id.submit);
        textViewError = findViewById(R.id.error);
        progressBar = findViewById(R.id.loading);
        checkBoxShow=findViewById(R.id.showButton);
        textViewBackToLogin = findViewById(R.id.backToLogin);
        imageViewPic = findViewById(R.id.registration_load_profilepicture);

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                postImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                imageViewPic.setImageBitmap(postImage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });
        imageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewError.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                name = String.valueOf(textInputEditTextName.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                //image part
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (postImage != null) {
                    postImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = Url_http.getURL() + "login-registration-android/register.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    if (response.equals("Success")) {
                                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        textViewError.setText(response);
                                        textViewError.setVisibility(View.VISIBLE);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            textViewError.setText(error.getLocalizedMessage());
                            textViewError.setVisibility(View.VISIBLE);

                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("name", name);
                            paramV.put("email", email);
                            paramV.put("password", password);
                            paramV.put("image",base64Image);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });

        checkBoxShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    textInputEditTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    textInputEditTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        textViewBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
    //for image

}