package com.codewithcup.chalisa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithcup.chalisa.Model.ModelClass;
import com.codewithcup.chalisa.R;
import com.codewithcup.chalisa.Adpater.RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    List<ModelClass> list;
    RecyclerView recyclerView;
    FloatingActionButton mFloatingActionButton;
    RecyclerViewAdapter viewAdapter;
    FirebaseFirestore mFirestore;
    TextView aboutUsName, aboutUsDesc;
    ImageView aboutUsImage;
    private Boolean wifiConnected = false;
    private Boolean mobileConnected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Before Loading Data Check Internet Connection
        checkNetworkConnectionToLoading();
    }

    private void checkNetworkConnectionToLoading() {

        //Initialize Connectivity Mananger
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //get Active network info
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // NetworkInfo mobCon = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        // NetworkInfo wifiCon = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);

        //check network status
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {


            //when internet is inactive
            //initialize dialog
            Dialog dialog = new Dialog(this);
            //set content view
            dialog.setContentView(R.layout.alert_dialog);
            //set outside touch
            dialog.setCanceledOnTouchOutside(false);
            //set dialog width and height
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            //set transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //set Animation
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            //Initialize dialog variable
            Button btnConnectToNetwork = dialog.findViewById(R.id.connect_to_network);


            //perform on click listener
            btnConnectToNetwork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //calling reCreate Method(rcreate method is predifine method which create again by again dialog)
                    // recreate();
                    //calling activity where want to go
                    //finish();
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                }
            });

            //show dialog
            dialog.show();
        }//if
        else if (networkInfo != null && networkInfo.isConnected()) {
            //when internet is active

            wifiConnected = networkInfo.getType() == connectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType() == connectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                Toast.makeText(this, "Connected to Wifi Network", Toast.LENGTH_SHORT).show();
                //Load App Main Content
                callingAllMethodMain();


            } else if (mobileConnected) {
                Toast.makeText(this, "Connected to Mobile Network", Toast.LENGTH_SHORT).show();
                //Load App Main Content
                callingAllMethodMain();

            }
        } /*else {
            //Load Url
            webView.loadUrl("https://www.google.com");
        }*/

    }

    private void callingAllMethodMain() {


        //Calling Hooks method
        callingHooks();

        //Floating Button hide and show
        callingFloationActionButton();

        list = new ArrayList<>();
        mFirestore = FirebaseFirestore.getInstance();

        clearAll();
        callingMainCategory();

        //backButton Press
        // onBackPressed();
    }

    private void callingHooks() {

        aboutUsName = findViewById(R.id.about_us_name);
        aboutUsImage = findViewById(R.id.about_us_image);
        aboutUsDesc = findViewById(R.id.about_us_desc);
        // okButton = findViewById(R.id.buttonOk);


        mFloatingActionButton = findViewById(R.id.floating_action_button);
        recyclerView = findViewById(R.id.recyclerView_id);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.about_us, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                //Toast.makeText(MainActivity.this, "Click On Floating Button", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void callingFloationActionButton() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });

    }

    private void callingMainCategory() {
        //Before Loading clearAll List
        clearAll();

        list = new ArrayList<>();
        //pass recyclerview
        callingMainCategoryRecyclerView();

        mFirestore.collection("categories").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ModelClass aClass = new ModelClass();
                                aClass.setTitle(documentSnapshot.get("title").toString());
                                aClass.setThumbnail(documentSnapshot.get("thumbnail").toString());
                                aClass.setDescription(documentSnapshot.get("description").toString());
                                list.add(aClass);
                            }
                            viewAdapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        }


                    }
                });


    }

    private void callingMainCategoryRecyclerView() {

        viewAdapter = new RecyclerViewAdapter(this, list);

        recyclerView.setAdapter(viewAdapter);

    }

    private void clearAll() {

        if (list != null) {
            list.clear();
            if (viewAdapter != null) {
                viewAdapter.notifyDataSetChanged();
            }
        }
        list = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {

        // Put your own code here which you want to run on back button click.

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Chalisa")
                .setMessage("Are you sure, you want to continue ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "Selected Option: YES", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        //Toast.makeText(MainActivity.this, "Selected Option: No", Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog dialog = builder.create();
        dialog.show();

        /*Toast.makeText(MainActivity.this,"Back Button is clicked MainActivity.", Toast.LENGTH_LONG).show();
        super.onBackPressed();
        finish();*/
    }

}
