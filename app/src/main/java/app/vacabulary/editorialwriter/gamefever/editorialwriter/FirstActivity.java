package app.vacabulary.editorialwriter.gamefever.editorialwriter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
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
}
