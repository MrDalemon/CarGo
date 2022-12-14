package lasalle.midterm.cargo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AllCars extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth checkAuth;
    Button btnAddVehicule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cars);

        checkAuth = FirebaseAuth.getInstance();

        initalize();
    }

    private void initalize() {

        FirebaseUser currentUser = checkAuth.getCurrentUser();

        btnAddVehicule = findViewById(R.id.btnAddCar);
        btnAddVehicule.setOnClickListener(this);

            if (!currentUser.getEmail().equals("admin@gmail.com")){
                btnAddVehicule.setVisibility(View.VISIBLE);
                Log.d("ButonShown", currentUser.getEmail());
            }else{
                btnAddVehicule.setVisibility(View.INVISIBLE);
                Log.d("ButtonHidden", currentUser.getEmail());
        }
    }


    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,AddRentalCar.class);
        startActivity(i);
    }
}