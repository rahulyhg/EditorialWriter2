package app.vacabulary.editorialwriter.gamefever.editorialwriter;

import android.app.DatePickerDialog;
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

public class WriterActivity extends AppCompatActivity {

    TextView dateTextView;
    int date ,month ,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);


        Calendar c = Calendar.getInstance();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        DateFormat sdf =  SimpleDateFormat.getDateInstance();


        String strDate = sdf.format(c.getTime());

        Toast.makeText(this, "date "+strDate , Toast.LENGTH_SHORT).show();

        dateTextView =(TextView) findViewById(R.id.writerActivity_date_textview);
        dateTextView.setText(strDate);






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


    public void btnClick(View view) {

        EditText headingEditText ,subHeadingEditText ,tagEditText ,sourceEditText ,editorialTextEditText ;

        headingEditText =(EditText)findViewById(R.id.writerActivity_heading_edittext);
        subHeadingEditText =(EditText)findViewById(R.id.writerActivity_subheading_edittext);
        tagEditText =(EditText)findViewById(R.id.writerActivity_tag_edittext);
        sourceEditText =(EditText)findViewById(R.id.writerActivity_source_edittext);
        editorialTextEditText =(EditText)findViewById(R.id.writerActivity_text_edittext);

        EditorialFullInfo editorialFullInfo =new EditorialFullInfo(new EditorialGeneralInfo() , new EditorialExtraInfo());


        editorialFullInfo.getEditorialGeneralInfo().setEditorialHeading(headingEditText.getText().toString());
        editorialFullInfo.getEditorialGeneralInfo().setEditorialDate(dateTextView.getText().toString());
        editorialFullInfo.getEditorialGeneralInfo().setEditorialSource(sourceEditText.getText().toString());
        editorialFullInfo.getEditorialGeneralInfo().setEditorialTag(tagEditText.getText().toString());
        editorialFullInfo.getEditorialGeneralInfo().setEditorialSubHeading(subHeadingEditText.getText().toString());

        editorialFullInfo.getEditorialExtraInfo().setEditorialText(editorialTextEditText.getText().toString());


        DBHelperFirebase dbHelperFirebase =new DBHelperFirebase();
        dbHelperFirebase.insertEditorial(editorialFullInfo);





    }

    public void showDatePicker(View view) {
        showDatePicker();
    }

    public void onGetEditorialFullInfo(EditorialFullInfo editorialFullInfo) {
    }
}
