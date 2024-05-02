package com.example.volunteer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.volunteer.databinding.ActivityTrytoUploadPhotoBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TrytoUploadPhotoActivity extends AppCompatActivity {
/*
    Bitmap postImage;
    String url1= Url_http.getURL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tryto_upload_photo);
        ImageView imageViewPic = findViewById(R.id.clickToUploadImage);
        Button buttonUpldImage = findViewById(R.id.btnImgUpload);

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

        buttonUpldImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if (postImage != null) {
                    postImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = url1+"login-registration-android/uploadImage.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success")){

                                        Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                                        Bundle bundle=new Bundle();
                                        bundle.putString("image",base64Image);
                                        UploadedPhotoFragment obj = new UploadedPhotoFragment();
                                        obj.setArguments(bundle);
                                        if (savedInstanceState == null){
                                            getSupportFragmentManager().beginTransaction()
                                                    .add(android.R.id.content, new UploadedPhotoFragment ()).commit();

                                        }
                                    }
                                    else Toast.makeText(getApplicationContext(),"Image uploading failed", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("image",base64Image);
                            return  paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });
    }*/
}
