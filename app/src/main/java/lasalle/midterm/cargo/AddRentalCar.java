package lasalle.midterm.cargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import Model.Car;

public class AddRentalCar extends AppCompatActivity implements View.OnClickListener , ValueEventListener {

    EditText carId,model,modelYear;
    Button save,delete,browse,upload;
    DatabaseReference Cars,Car;
    StorageReference photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rental_car);

        initalise();
    }

    private void initalise() {

        carId = findViewById(R.id.txtCarId);
        model = findViewById(R.id.txtModel);
        modelYear = findViewById(R.id.txtModelYear);
        save = findViewById(R.id.btnSave);
        save.setOnClickListener(this);
        delete = findViewById(R.id.btnDelete);
        delete.setOnClickListener(this);
        browse = findViewById(R.id.btnBrowse);
        browse.setOnClickListener(this);
        upload = findViewById(R.id.btnUpload);
        upload.setOnClickListener(this);

        Cars = FirebaseDatabase.getInstance().getReference("Car");
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
                remove();
                break;
            case R.id.btnUpload:
                remove();
                break;
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

            Model.Car car =new Car(id,modelyear,carModel,);
            Cars.child(carId.getText().toString()).setValue(car);

            Toast.makeText(this,"New Car Added",Toast.LENGTH_SHORT).show();

            finish();
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
}