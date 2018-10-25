package com.cristian.proyecto_firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private TextView txtid;
    private TextView txtcontraseña;
    private Button btnLogin;
    private GoogleApiClient mgGoogleApiClient;
    private SignInButton signInButton;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 9001){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){

                txtid.setText(result.getSignInAccount().getDisplayName());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mgGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        txtcontraseña = findViewById(R.id.txtpassword);
        txtid = findViewById(R.id.id);
        btnLogin = findViewById(R.id.login);
        signInButton = findViewById(R.id.googleLogin);




        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();



        if(auth.getCurrentUser()!=null){
            Intent i = new Intent(this,Main.class);

            startActivity(i);

            finish();

            return;
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(mgGoogleApiClient);
                startActivityForResult(i,9001);
            }
        });









        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUsuario(txtid.getText().toString(), txtcontraseña.getText().toString());

            }
        });


    }


    private void loginUsuario(String email, String pass){
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(Login.this,"la contraseña esta correcta",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this,Main.class);

                    startActivity(i);

                    finish();
                }else{

                    Toast.makeText(Login.this,"la contraseña esta mala pirtobo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
