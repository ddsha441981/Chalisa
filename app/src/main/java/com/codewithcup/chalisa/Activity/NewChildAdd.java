package com.codewithcup.chalisa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithcup.chalisa.Model.SaveChildModel;
import com.codewithcup.chalisa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class NewChildAdd extends AppCompatActivity {

    EditText name, image, count, description;
    Button saveBtn, loadBtn;
    TextView textdata;

    //firestore
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    CollectionReference dataRef = mFirestore.collection("categories");
    // DocumentReference noteRef = mFirestore.document("categories/index1");

    private static final String TAG = "NewChildAdd";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_COUNT = "count";
    private static final String KEY_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_child_add);

        name = findViewById(R.id.child_name);
        image = findViewById(R.id.child_image);
        count = findViewById(R.id.child_count);
        description = findViewById(R.id.child_description);
        textdata = findViewById(R.id.textview_data);
        // saveBtn = findViewById(R.id.button);
    }

    public void saveDataInFireStore(View view) {
        String _name = name.getText().toString();
        String _image = image.getText().toString();
        String _count = count.getText().toString();
        String _description = description.getText().toString();
        /*Map<String, Object> note = new HashMap<>();
        note.put(KEY_NAME, _name);
        note.put(KEY_IMAGE, _image);
        note.put(KEY_COUNT, _count);
        note.put(KEY_DESCRIPTION, _description);*/
        SaveChildModel childModel = new SaveChildModel(_name, _image, _count, _description);
        // mFirestore.collection("").document("").set(note)
        dataRef.document("CHALISA").collection("Chalisa Node").add(childModel);
    }

    public void loadingData(View view) {
        Log.d(TAG,"++++++++++++++++++++++++++++++++++++++++++++" + dataRef);
        dataRef.document("count").collection("Chalisa Node").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            SaveChildModel model = documentSnapshot.toObject(SaveChildModel.class);
                            Log.d(TAG,"Modelllllllllllllllllllllllllllllllllllllllllllllllllll" + model);
                            model.setCount(documentSnapshot.getId());
                            String documentId = model.getCount();
                            Log.d(TAG,"idddddddddddddddddddddddddddddddddddddddddddddd" + documentId);
                            data += "ID: " + documentId;

                        }
                        textdata.setText(data);
                    }
                });

    }

/*noteRef.set(childModel)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {

            Toast.makeText(NewChildAdd.this, "Data Saved", Toast.LENGTH_SHORT).show();
        }
    })
            .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

            Toast.makeText(NewChildAdd.this, "Error", Toast.LENGTH_SHORT).show();
            Log.d(TAG,e.toString());
        }
    });*/

     /*   noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){
                    SaveChildModel model = documentSnapshot.toObject(SaveChildModel.class);
                    String name = model.getName();
                    String image = model.getImage();
                    String count = model.getCount();
                    String description = model.getDescription();
                    textdata.setText("name" + name + " " + image + " " + count + " " + description);
                }else{
                    Toast.makeText(NewChildAdd.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(NewChildAdd.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,e.toString());
                    }
                });
    }*/
}