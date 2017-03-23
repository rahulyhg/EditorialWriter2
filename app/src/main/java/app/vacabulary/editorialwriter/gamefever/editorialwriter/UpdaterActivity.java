package app.vacabulary.editorialwriter.gamefever.editorialwriter;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdaterActivity extends AppCompatActivity {

    TextView dateTextView;
    int date ,month ,year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updater);

        Calendar c = Calendar.getInstance();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        DateFormat sdf =  SimpleDateFormat.getDateInstance();


        String strDate = sdf.format(c.getTime());

        Toast.makeText(this, "date "+strDate , Toast.LENGTH_SHORT).show();

        dateTextView =(TextView) findViewById(R.id.updaterActivity_date_textview);
        dateTextView.setText(strDate);




        resolveIntent(getIntent());



    }

    private void resolveIntent(Intent i) {

        EditorialGeneralInfo editorialGeneralInfo = new EditorialGeneralInfo();
        editorialGeneralInfo.setEditorialID(i.getExtras().getString("editorialID"));
        editorialGeneralInfo.setEditorialDate(i.getExtras().getString("editorialDate"));
        editorialGeneralInfo.setEditorialHeading(i.getExtras().getString("editorialHeading"));
        editorialGeneralInfo.setEditorialSource(i.getExtras().getString("editorialSource"));
        editorialGeneralInfo.setEditorialSubHeading(i.getExtras().getString("editorialSubheading"));
        editorialGeneralInfo.setEditorialTag(i.getExtras().getString("editorialTag"));

        DBHelperFirebase dbHelper = new DBHelperFirebase();
        dbHelper.getEditorialFullInfoByID(editorialGeneralInfo, this);



        EditText editText = (EditText) findViewById(R.id.updaterActivity_heading_edittext);
        editText.setText(editorialGeneralInfo.getEditorialHeading());
        editText = (EditText) findViewById(R.id.updaterActivity_source_edittext);
        editText.setText(editorialGeneralInfo.getEditorialSource());
        editText = (EditText) findViewById(R.id.updaterActivity_subheading_edittext);
        editText.setText(editorialGeneralInfo.getEditorialSubHeading());

        editText = (EditText) findViewById(R.id.updaterActivity_tag_edittext);
        editText.setText(editorialGeneralInfo.getEditorialTag());

        TextView textView = (TextView) findViewById(R.id.updaterActivity_date_textview);
        textView.setText(editorialGeneralInfo.getEditorialDate());


    }


    public void showDatePicker(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.datepicker_dialogue);


        final DatePicker datePicker =(DatePicker)dialog.findViewById(R.id.datePicker);

        Button selectButton = (Button)dialog.findViewById(R.id.dialogBox_select_button) ;
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                month= datePicker.getMonth()+1;
                date= datePicker.getDayOfMonth();
                year =datePicker.getYear();

                dateTextView.setText(date +"-"+month+"-"+year);

                dialog.cancel();

            }
        });

        dialog.show();

    }
View btnView;

    public void btnClick(View view) {
btnView=view;
        EditText headingEditText ,subHeadingEditText ,tagEditText ,sourceEditText ,editorialTextEditText ;

        headingEditText =(EditText)findViewById(R.id.updaterActivity_heading_edittext);
        subHeadingEditText =(EditText)findViewById(R.id.updaterActivity_subheading_edittext);
        tagEditText =(EditText)findViewById(R.id.updaterActivity_tag_edittext);
        sourceEditText =(EditText)findViewById(R.id.updaterActivity_source_edittext);
        editorialTextEditText =(EditText)findViewById(R.id.updaterActivity_text_edittext);

        EditorialFullInfo editorialFullInfo =new EditorialFullInfo(new EditorialGeneralInfo() , new EditorialExtraInfo());


        editorialFullInfo.getEditorialGeneralInfo().setEditorialHeading(headingEditText.getText().toString());
        editorialFullInfo.getEditorialGeneralInfo().setEditorialDate(dateTextView.getText().toString());
        editorialFullInfo.getEditorialGeneralInfo().setEditorialSource(sourceEditText.getText().toString());
        editorialFullInfo.getEditorialGeneralInfo().setEditorialTag(tagEditText.getText().toString());
        editorialFullInfo.getEditorialGeneralInfo().setEditorialSubHeading(subHeadingEditText.getText().toString());

        editorialFullInfo.getEditorialExtraInfo().setEditorialText(editorialTextEditText.getText().toString());


        btnView.setVisibility(View.GONE);

        DBHelperFirebase dbHelperFirebase =new DBHelperFirebase();
        dbHelperFirebase.updateEditorial(editorialFullInfo ,this);





    }

    public void showDatePicker(View view) {
        showDatePicker();
    }

    public void btnuploadClick(View view) {
    }

    public void onGetEditorialFullInfo(EditorialFullInfo editorialFullInfo) {
        EditText editText = (EditText) findViewById(R.id.updaterActivity_text_edittext);
        editText.setText(editorialFullInfo.getEditorialExtraInfo().getEditorialText());

    }

    public void updateEditorialListner(boolean b) {
        if (b) {


            Toast.makeText(this, "Editorial Updated  Sucessfully", Toast.LENGTH_SHORT).show();

        } else {
            btnView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Failed to Update editorial !! please retry later", Toast.LENGTH_SHORT).show();
        }



    }
}
