package app.vacabulary.editorialwriter.gamefever.editorialwriter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstActivity extends AppCompatActivity {


    private String TAG ="auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        final ProgressDialog pd = new ProgressDialog(FirstActivity.this);
        pd.setMessage("Authenticating ...");
        pd.setCancelable(false);
        pd.show();



        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){

pd.dismiss();

        }else{


            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                pd.dismiss();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                Toast.makeText(FirstActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                                showExitDialog();
                            }

                            // ...
                        }
                    });


        }


    }

    private void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });


        builder.setTitle("Authentication failed");
        builder.setMessage("You can not proceed without authentication");

// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void openWritterActivity(View view) {
        Intent intent = new Intent(this , WriterActivity.class);
        startActivity(intent);


    }

    public void openUpdateActivity(View view) {

        Intent intent = new Intent(this , EditorialListActivity.class);
        startActivity(intent);


    }

    public void openDeleteActivity(View view) {

        Intent intent = new Intent(this , DeleteActivity.class);
        startActivity(intent);


    }

    public void openVocabularyWriter(View view) {
        Intent intent = new Intent(this , VocabularyActivity.class);
        startActivity(intent);


    }
}
