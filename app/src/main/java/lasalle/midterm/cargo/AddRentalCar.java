package lasalle.midterm.cargo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import Model.Car;

public class AddRentalCar extends AppCompatActivity implements View.OnClickListener , ValueEventListener, OnSuccessListener, OnFailureListener , OnCompleteListener {

    EditText carId,model,modelYear;
    Button save,delete,browse,upload;
    DatabaseReference Cars;
    ImageView image;
    FirebaseStorage storage;
    StorageReference storageReference;
    ActivityResultLauncher getPhoto;
    Uri filepath;
    String PhotoUrl;
    ProgressDialog progress;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rental_car);

        initalise();
    }

    private void initalise() {

        // text views
        carId = findViewById(R.id.txtCarId);
        model = findViewById(R.id.txtModel);
        modelYear = findViewById(R.id.txtModelYear);
        //img view
        image = findViewById(R.id.imgCar);
        // buttons
        save = findViewById(R.id.btnSave);
        save.setOnClickListener(this);
        delete = findViewById(R.id.btnDelete);
        delete.setOnClickListener(this);
        browse = findViewById(R.id.btnBrowse);
        browse.setOnClickListener(this);
        upload = findViewById(R.id.btnUpload);
        upload.setOnClickListener(this);
        // database reference
        Cars = FirebaseDatabase.getInstance().getReference("Car");
        // storage initialization
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        
       getPhoto =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                getpic(result);

            }
        });
    }



    @Override
    public void onClick(View view) {
        int id= view.getId();

        switch (id){
            case R.id.btnSave:
                save();
                break;
            case R.id.btnDelete:
                remove();
                break;
            case R.id.btnBrowse:
                selectpic();
                break;
            case R.id.btnUpload:
                uploadpic();
                break;
        }
    }

    private void uploadpic() {

        if(filepath != null){
            progress = new ProgressDialog(this);
            progress.setTitle("Driving to Track:");
            progress.show();
            storageReference =storageReference.child("Cars/"+ UUID.randomUUID());
            storageReference.putFile(filepath).addOnSuccessListener(this);
            storageReference.putFile(filepath).addOnFailureListener(this);

        }

    }

    private void selectpic() {
        Intent find = new Intent();
        find.setType("image/");
        find.setAction(Intent.ACTION_GET_CONTENT);
        getPhoto.launch(Intent.createChooser(find,"Select Car Photo"));
    }

    private void getpic(ActivityResult result) {

        if(result.getResultCode()== RESULT_OK && result.getData()!= null){
            filepath = result.getData().getData();
            try{
                Bitmap media = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                image.setImageBitmap(media);
            }catch (Exception e){
                Log.d("Anger", e.getMessage());
            }
        }else{
            Toast.makeText(this,"No Photo Selected",Toast.LENGTH_LONG).show();
        }

    }

    private void remove() {
        try {

            String id = carId.getText().toString();

            Cars = FirebaseDatabase.getInstance().getReference().child("Car").child(id);
            Cars.removeValue();

            Toast.makeText(this,"Selected Car Deleted",Toast.LENGTH_SHORT).show();

            finish();
        }catch (Exception error){
            Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private void save() {
        try {
            String carModel = model.getText().toString();
            int id = Integer.valueOf(carId.getText().toString());
            int modelyear = Integer.valueOf(modelYear.getText().toString());

            if(PhotoUrl!= null){
                Model.Car car =new Car(id,modelyear,carModel,PhotoUrl);
                Cars.child(carId.getText().toString()).setValue(car);

                Toast.makeText(this,"New Car Added",Toast.LENGTH_SHORT).show();

                finish();
            }else{
                Toast.makeText(this,"no photo is selected",Toast.LENGTH_SHORT).show();
            }

        }catch (Exception error){
            Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    public void onSuccess(Object o) {
        Toast.makeText(this,"Car is in the Garage",Toast.LENGTH_LONG).show();
        progress.dismiss();
        storageReference.getDownloadUrl().addOnCompleteListener(this);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this,"The car didn't make it because:"+e.getMessage(),Toast.LENGTH_LONG).show();
        progress.dismiss();
    }

    @Override
    public void onComplete(@NonNull Task task) {
        PhotoUrl = task.getResult().toString();
    }
}