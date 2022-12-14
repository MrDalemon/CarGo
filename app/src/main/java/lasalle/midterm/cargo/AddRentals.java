package lasalle.midterm.cargo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import Model.Rental;

public class AddRentals extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference rental;
    private FirebaseAuth check;
    Button btnSaveRental,btnBackToAllCars;
    EditText startdate,enddate;
    int carid=0;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rentals);
        check = FirebaseAuth.getInstance();
        initialize();
    }

    private void initialize() {
        carid = (int) getIntent().getExtras().getSerializable("carID");

        btnSaveRental= findViewById(R.id.btnSaveRental);
        btnSaveRental.setOnClickListener(this);
        btnBackToAllCars= findViewById(R.id.btnToAllCars);
        btnSaveRental.setOnClickListener(this);
        startdate = findViewById(R.id.txtStartDate);
        enddate = findViewById(R.id.txtEndDate);

        rental = FirebaseDatabase.getInstance().getReference("Rental");
        FirebaseUser currentUser = check.getCurrentUser();
        userid = currentUser.getUid();
        //Log.d("is it working", "initialize: "+carid);

    }

    @Override
    public void onClick(View view) {
        int id= view.getId();

        switch (id){
            case R.id.btnSaveRental:
                saveRental();
                break;
            case R.id.btnToAllCars:
                finish();
                break;
    }
}

    private void saveRental() {
        Query querry = rental.orderByChild("rentalId").limitToLast(1);
        final int[] rentalid = {0};
        querry.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Iterable<DataSnapshot> snap = snapshot.getChildren();
                Iterator<DataSnapshot> why = snap.iterator();
                DataSnapshot value = (DataSnapshot) why.next();
               // Log.d("plz work", "saveRental: "+value.child("rentalId").getValue());
                rentalid[0] = (int) value.child("rentalId").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
            int rentalids = rentalid[0];
            rentalids++;
        if(!startdate.getText().toString().matches("")&&!enddate.getText().toString().matches(" ")){
            Rental rent =new Rental(carid,rentalids,userid,startdate.getText().toString(),enddate.getText().toString());
            rental.child(String.valueOf(rentalids)).setValue(rental);

            Toast.makeText(this,"Rental Confirmed",Toast.LENGTH_SHORT).show();

            finish();
        }else{
            Toast.makeText(this,"Invalid Dates",Toast.LENGTH_SHORT).show();
        }




    }
    }