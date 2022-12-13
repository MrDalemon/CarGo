package lasalle.midterm.cargo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AllCars extends AppCompatActivity implements View.OnClickListener {

    Button btnAddVehicule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cars);

        initalize();
    }

    private void initalize() {

        btnAddVehicule = findViewById(R.id.btnAddCar);
        btnAddVehicule.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,AddRentalCar.class);
        startActivity(i);
    }
}