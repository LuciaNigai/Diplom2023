package com.example.volunteer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AddEditPost extends AppCompatActivity {


    //for photo
    ImageView imageViewPostPic;
    Bitmap postImage;
    TextInputEditText postDescription;
    String url1 = Url_http.getURL();
    String postId;
    //for photo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_post);
        //for image
        imageViewPostPic = findViewById(R.id.post_edit_add_image);

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                postImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                imageViewPostPic.setImageBitmap(postImage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });
        imageViewPostPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });
        //for image end


        //spinner logic
        String[] PostCategory = new String[]{"Animals", "Medicine", "Food", "Nature", "Children"};
        String[] PostState = new String[]{"Need help", "Offer help"};
        Spinner spinnerCategory, spinnerState;
        spinnerCategory = (Spinner) findViewById(R.id.spinner_choose_post_category);
        spinnerState = (Spinner) findViewById(R.id.spinner_choose_post_state);
        postDescription = (TextInputEditText) findViewById(R.id.editTextTextPostDescription);

        ArrayAdapter<CharSequence> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.PostCategory));
        spinnerCategory.setAdapter(adapterCategory);
        ArrayAdapter<CharSequence> adapterState = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.PostState));
        spinnerState.setAdapter(adapterState);

        //spinner logic end


        //buttons logic

        //cancel button logic
        Button buttonCancelPost;
        buttonCancelPost = findViewById(R.id.add_post_button_cancel);
        buttonCancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instead of main activity there can be the fragment home but the speed will be slower and the app can crash
                Intent cancelPostIntent = new Intent(AddEditPost.this, MainActivity.class);
                startActivity(cancelPostIntent);
            }
        });

        //cancel button logic end

        //add button logic
        Button buttonAddPost;
        buttonAddPost = findViewById(R.id.add_post_button_OK);

        postId = getIntent().getStringExtra("postId");
        //Toast.makeText(getApplicationContext(), postId, Toast.LENGTH_SHORT).show();

        if (!TextUtils.isEmpty(postId)) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url1 + "login-registration-android/post_update_get.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray itemsJsonArray = new JSONArray(response);

                        for (int i = 0; i < itemsJsonArray.length(); i++) {
                            JSONObject itemsObject = itemsJsonArray.getJSONObject(i);
                            String category = itemsObject.getString("post_category");
                            String state = itemsObject.getString("post_state");
                            String postDescriptionDB = itemsObject.getString("post_description");
                            String postImageBD = itemsObject.getString("post_photo");
                            String postId = itemsObject.getString("post_id");

                            spinnerCategory.setSelection(((ArrayAdapter<String>) spinnerCategory.getAdapter()).getPosition(category));
                            spinnerState.setSelection(((ArrayAdapter<String>) spinnerState.getAdapter()).getPosition(state));
                            postDescription.setText(postDescriptionDB);

                            imageViewPostPic.setImageResource(getResources().getIdentifier(postImageBD, "drawable", getPackageName()));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddEditPost.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("post_id", postId);
                    return paramV;
                }
            };
            Volley.newRequestQueue(AddEditPost.this).add(stringRequest);
        }

        buttonAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postId != null) {
                    String PostCategoryString = spinnerCategory.getSelectedItem().toString();
                    String PostStateString = spinnerState.getSelectedItem().toString();
                    String PostDescriptionString = String.valueOf(postDescription.getText());
                    ByteArrayOutputStream byteArrayOutputStream;
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    if (postImage != null) {
                        postImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        String url = url1 + "login-registration-android/post_update.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("success")) {
                                            Intent uploadPostIntent = new Intent(AddEditPost.this, MainActivity.class);
                                            startActivity(uploadPostIntent);
                                            Toast.makeText(getApplicationContext(), "Post Changed", Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(getApplicationContext(), "Can't change someone else's post", Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            protected Map<String, String> getParams() {
                                Map<String, String> paramV = new HashMap<>();
                                paramV.put("post_id",postId);
                                paramV.put("post_category", PostCategoryString);
                                paramV.put("post_state", PostStateString);
                                paramV.put("post_description", PostDescriptionString);
                                paramV.put("image", base64Image);
                                return paramV;
                            }
                        };
                        queue.add(stringRequest);
                    }
                    else Toast.makeText(getApplicationContext(),"Upload Image",Toast.LENGTH_SHORT).show();
                }
                else {
                        //instead of main activity there can be the fragment home but the speed will be slower and the app can crash
                        //-------------------we can define onitemSelectedlistener and to write data into the database------------------------
                        //!!!!!!!!!!!!!!!!!!!
                        String PostCategoryString = spinnerCategory.getSelectedItem().toString();
                        String PostStateString = spinnerState.getSelectedItem().toString();
                        String PostDescriptionString = String.valueOf(postDescription.getText());
                        ByteArrayOutputStream byteArrayOutputStream;
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        if (postImage != null) {
                            postImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            byte[] bytes = byteArrayOutputStream.toByteArray();
                            final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            String url = url1 + "login-registration-android/post_add.php";
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("success")) {
                                                Intent uploadPostIntent = new Intent(AddEditPost.this, MainActivity.class);
                                                startActivity(uploadPostIntent);
                                                Toast.makeText(getApplicationContext(), "Post Uploaded", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(getApplicationContext(), "Bad bad bad", Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                protected Map<String, String> getParams() {
                                    Map<String, String> paramV = new HashMap<>();
                                    paramV.put("post_category", PostCategoryString);
                                    paramV.put("post_state", PostStateString);
                                    paramV.put("post_description", PostDescriptionString);
                                    paramV.put("image", base64Image);
                                    return paramV;
                                }
                            };
                            queue.add(stringRequest);
                        }
                    }
                }
            });
    }
}
