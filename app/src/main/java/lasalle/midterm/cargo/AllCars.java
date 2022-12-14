package lasalle.midterm.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import Model.Car;


public class AllCars extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth checkAuth;
    FirebaseListAdapter adapter;
    Button btnAddVehicule,btnHistory,btnWishlist,btnLogout,btnShowallrentals;
    ListView listAllCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cars);

        checkAuth = FirebaseAuth.getInstance();

        initalize();
    }

    private void initalize() {

        FirebaseUser currentUser = checkAuth.getCurrentUser();
        // initialise all buttons
        btnAddVehicule = findViewById(R.id.btnAddCar);
        btnAddVehicule.setOnClickListener(this);
        btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(this);
        btnWishlist= findViewById(R.id.btnWishlist);
        btnWishlist.setOnClickListener(this);
        btnLogout= findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
        btnShowallrentals = findViewById(R.id.btnShowRentals);
        btnShowallrentals.setOnClickListener(this);
        // for spefic intent
        Intent r = new Intent(this,AddRentals.class);

        // initialise list view
        listAllCars = (ListView) findViewById(R.id.listAllCars);

        Query query = FirebaseDatabase.getInstance().getReference().child("Car");
        FirebaseListOptions<Car> options = new FirebaseListOptions.Builder<Car>().setLayout(R.layout.element).setQuery(query,Car.class).setLifecycleOwner(this).build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView carid = v.findViewById(R.id.elementId);
                TextView models = v.findViewById(R.id.elementModel);
                TextView modelyear = v.findViewById(R.id.elementModelYear);
                ImageView photo = v.findViewById(R.id.elementPhoto);

                Car car =(Car) model;
                carid.setText("Car Id: "+Integer.toString(car.getCarId()));
                models.setText("Car Model: "+car.getVehicule());
                modelyear.setText("Model Year: "+Integer.toString(car.getModelYear()));
                Picasso.get().load(car.getCarPic()).into(photo);
                
            }
        };
            listAllCars.setAdapter(adapter);

            listAllCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("do you work", "onItemClick: "+i);
                    i++;
                    r.putExtra("carID",i);
                    startActivity(r);

                }
            });

            // check if user is admin
            if (currentUser.getEmail().equals("admin@gmail.com")){
                btnAddVehicule.setVisibility(View.VISIBLE);
                btnShowallrentals.setVisibility(View.VISIBLE);
                //Log.d("ButonShown", currentUser.getEmail());
            }else{
                btnAddVehicule.setVisibility(View.INVISIBLE);
                btnShowallrentals.setVisibility(View.INVISIBLE);
               // Log.d("ButtonHidden", currentUser.getEmail());
        }
    }


    @Override
    public void onClick(View view) {

        int id= view.getId();

        switch (id){
            case R.id.btnAddCar:
                Intent a = new Intent(this,AddRentalCar.class);
                startActivity(a);
                break;
            case R.id.btnHistory:
                //TODO History of rented vechicules per user
                Intent h = new Intent(this,History.class);
                startActivity(h);
                break;
            case R.id.btnWishlist:
                // TODO wishlisted cars
                Intent w = new Intent(this,Wishlist.class);
                startActivity(w);
                break;
            case R.id.btnShowRentals:
                // TODO show rentals for all users (admin feature)
                Intent i = new Intent(this,ShowAllRentals.class);
                startActivity(i);
                break;
            case R.id.btnLogout:
                FirebaseAuth.getInstance().signOut();
                Log.d("sign out","Sign out successful");
                finish();
                break;
        }

    }
}