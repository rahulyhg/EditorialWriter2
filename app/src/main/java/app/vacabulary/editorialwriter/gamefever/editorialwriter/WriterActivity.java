package app.vacabulary.editorialwriter.gamefever.editorialwriter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
    int date, month, year;

    int sourceIndex ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);


        Calendar c = Calendar.getInstance();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        DateFormat sdf = SimpleDateFormat.getDateInstance();


        String strDate = sdf.format(c.getTime());

        Toast.makeText(this, "date " + strDate, Toast.LENGTH_SHORT).show();

        dateTextView = (TextView) findViewById(R.id.writerActivity_date_textview);
        dateTextView.setText(strDate);
        btn = (Button) findViewById(R.id.writerActivity_upload_button);

    }


    public void showDatePicker() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.datepicker_dialogue);


        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);

        Button selectButton = (Button) dialog.findViewById(R.id.dialogBox_select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                month = datePicker.getMonth() + 1;
                date = datePicker.getDayOfMonth();
                year = datePicker.getYear();

                dateTextView.setText(date + "-" + month + "-" + year);

                dialog.cancel();

            }
        });

        dialog.show();

    }

    EditText headingEditText, subHeadingEditText, tagEditText, sourceEditText, editorialTextEditText;
    View Buttonview;
    Button btn;
    CharSequence sources[] = new CharSequence[] {"The Hindu", "Financial Express", "Economic Times"};


    public void btnClick(View view) {



        headingEditText = (EditText) findViewById(R.id.writerActivity_heading_edittext);
        subHeadingEditText = (EditText) findViewById(R.id.writerActivity_subheading_edittext);
        tagEditText = (EditText) findViewById(R.id.writerActivity_tag_edittext);
        sourceEditText = (EditText) findViewById(R.id.writerActivity_source_edittext);
        editorialTextEditText = (EditText) findViewById(R.id.writerActivity_text_edittext);

        EditorialFullInfo editorialFullInfo = new EditorialFullInfo(new EditorialGeneralInfo(), new EditorialExtraInfo());

        if (!headingEditText.getText().toString().isEmpty()) {
            editorialFullInfo.getEditorialGeneralInfo().setEditorialHeading(headingEditText.getText().toString());
        } else {
            Toast.makeText(this, "Heading field is Empty", Toast.LENGTH_SHORT).show();
            return;

        }
        editorialFullInfo.getEditorialGeneralInfo().setEditorialDate(dateTextView.getText().toString());



        if (!sourceEditText.getText().toString().isEmpty()) {
            editorialFullInfo.getEditorialGeneralInfo().setEditorialSource(sourceEditText.getText().toString());
            editorialFullInfo.getEditorialGeneralInfo().setEditorialSourceIndex(sourceIndex);

        } else {
            Toast.makeText(this, "Source field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!tagEditText.getText().toString().isEmpty()) {
            editorialFullInfo.getEditorialGeneralInfo().setEditorialTag(tagEditText.getText().toString());
            editorialFullInfo.getEditorialGeneralInfo().setEditorialCategory(tagEditText.getText().toString());

        } else {
            Toast.makeText(this, "Tag fiel Empty", Toast.LENGTH_SHORT).show();
            return;
        }


        if (editorialTextEditText.getText().toString().length() > 50) {
            editorialFullInfo.getEditorialExtraInfo().setEditorialText(editorialTextEditText.getText().toString());
        } else {
            Toast.makeText(this, "Editorial Lenght is smaller than 50 char", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String subheading = editorialTextEditText.getText().toString().substring(0, 100);
            editorialFullInfo.getEditorialGeneralInfo().setEditorialSubHeading(subheading);
        }catch(Exception e){
            String subheading = editorialTextEditText.getText().toString().substring(0, 50);
            editorialFullInfo.getEditorialGeneralInfo().setEditorialSubHeading(subheading);

        }

        editorialFullInfo.getEditorialGeneralInfo().setTimeInMillis(Calendar.getInstance().getTimeInMillis());


        btn.setClickable(false);
        Toast.makeText(this, "Posting Editorial", Toast.LENGTH_SHORT).show();

        DBHelperFirebase dbHelperFirebase = new DBHelperFirebase();
        dbHelperFirebase.insertEditorial(editorialFullInfo ,this);


    }

    public void showDatePicker(View view) {
        showDatePicker();
    }

    public void onGetEditorialFullInfo(EditorialFullInfo editorialFullInfo) {
    }

    public void insertEditorialListner(boolean b) {
        if (b) {

            headingEditText.setText("");
            sourceEditText.setText("");
            subHeadingEditText.setText("");
            editorialTextEditText.setText("");
            tagEditText.setText("");
            tagEditText.setText("");
            Toast.makeText(this, "Editorial Posted Sucessfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Failed to post editorial !! please retry later", Toast.LENGTH_SHORT).show();
        }

        btn.setClickable(true);

    }


    public void openSourceSelection(View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose source");
        builder.setItems(sources, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                sourceEditText = (EditText) findViewById(R.id.writerActivity_source_edittext);

                sourceIndex =which;
                sourceEditText.setText(sources[which]);

            }
        });
        builder.show();
    }
}
