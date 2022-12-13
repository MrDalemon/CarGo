package lasalle.midterm.cargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth checkAuth;
    TextView txtUsername , txtPassword;
    Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAuth = FirebaseAuth.getInstance();
        initialise();

    }

    private void initialise() {
        txtUsername = findViewById(R.id.editUsername);
        txtPassword = findViewById(R.id.editPassword);
        btnSignIn = findViewById(R.id.btnsignin);
        btnSignIn.setOnClickListener(this);
        btnSignUp = findViewById(R.id.btnsignup);
        btnSignUp.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = checkAuth.getCurrentUser();

    }

    @Override
    public void onClick(View view) {
        int id= view.getId();

        switch (id){
            case R.id.btnsignin:
                if (txtUsername.getText().toString().matches("")||txtPassword.getText().toString().matches("")){
                    Toast.makeText(this,"Username or Password field empty please fill field and try again",Toast.LENGTH_SHORT).show();
                }else{
                    checkAuth.signInWithEmailAndPassword(txtUsername.getText().toString(),txtPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "sucessful sign in");
                                FirebaseUser user = checkAuth.getCurrentUser();
                                gotonextactivity(user);
                            }else {
                                Log.w(TAG, " failed sign in ",task.getException());
                                Toast.makeText(MainActivity.this,"Login Failed Invalid Username or password",Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
                break;
            case R.id.btnsignup:
                Intent i = new Intent(this,SignUp.class);
                startActivity(i);
                break;
        }
    }
    
    private void gotonextactivity(FirebaseUser user) {

        if (user  != null){
            Intent i = new Intent(this,AllCars.class);
            startActivity(i);
        }

    }





}