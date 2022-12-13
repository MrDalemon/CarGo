package lasalle.midterm.cargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth checkAuth;
    Button btnSignUp,btnBack;
    TextView txtEmail,txtPassword;
    private ProgressBar bar;
    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        checkAuth = FirebaseAuth.getInstance();
        initialize();
    }

    private void initialize() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnSignUp = findViewById(R.id.btnSignup);
        btnSignUp.setOnClickListener(this);
        txtEmail = findViewById(R.id.editTextTextPersonName);
        txtPassword = findViewById(R.id.editTextTextPassword);
        bar = findViewById(R.id.progressBar);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view) {
        int id= view.getId();

        switch (id){
            case R.id.btnSignup:
                if (txtEmail.getText().toString().matches("")||txtPassword.getText().toString().matches("")){
                    Toast.makeText(this,"Username or Password field empty please fill field and try again",Toast.LENGTH_SHORT).show();
                }else{
                    SignUpMethod(txtEmail.getText().toString(),txtPassword.getText().toString());
                }
                break;
            case R.id.btnBack:
                finish();
                break;
        }

    }

    private void SignUpMethod(String email, String password) {
            checkAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Log.d(TAG, "User Created Successfully");
                        FirebaseUser user= checkAuth.getCurrentUser();
                            bar.setVisibility(View.GONE);
                        gotonextactivity(user);
                    }else {
                        Log.w(TAG, " failed sign in ",task.getException());
                        Toast.makeText(SignUp.this,"Login Failed Invalid Username or password",Toast.LENGTH_SHORT).show();
                        gotonextactivity(null);

                    }
                }
            });

    }

    private void gotonextactivity(FirebaseUser user) {

        if (user  != null){
            Intent i = new Intent(this,AllCars.class);
            startActivity(i);
        }

    }
}