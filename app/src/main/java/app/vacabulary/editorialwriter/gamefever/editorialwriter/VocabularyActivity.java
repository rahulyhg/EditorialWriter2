package app.vacabulary.editorialwriter.gamefever.editorialwriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class VocabularyActivity extends AppCompatActivity {


    EditText wordEditText, partOfSpeechEditText, meaningEditText, hindiMeaningEditText, synonymsEditText, antonymsEditText, formsEditText, exampleEditText, relatedEditText, imageURLEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        wordEditText = (EditText)findViewById(R.id.vocabularyActivity_word_editText);
        partOfSpeechEditText = (EditText)findViewById(R.id.vocabularyActivity_partOfSpeech_editText);
        meaningEditText = (EditText)findViewById(R.id.vocabularyActivity_meaning_editText);
        hindiMeaningEditText = (EditText)findViewById(R.id.vocabularyActivity_hindiMeaning_editText);
        synonymsEditText = (EditText)findViewById(R.id.vocabularyActivity_synonyms_editText);
        antonymsEditText = (EditText)findViewById(R.id.vocabularyActivity_antonyms_editText);
        formsEditText = (EditText)findViewById(R.id.vocabularyActivity_form_editText);
        relatedEditText = (EditText)findViewById(R.id.vocabularyActivity_related_editText);
        exampleEditText = (EditText)findViewById(R.id.vocabularyActivity_example_editText);
        imageURLEditText = (EditText)findViewById(R.id.vocabularyActivity_imageURL_editText);



    }


    public void onUploadClick(View view) {
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setmWord(wordEditText.getText().toString().trim());
        vocabulary.setmPartOfSpeech(partOfSpeechEditText.getText().toString().trim());
        vocabulary.setmWordMeaning(meaningEditText.getText().toString().trim());
        vocabulary.setmHindiMeaning(hindiMeaningEditText.getText().toString().trim());
        vocabulary.setmSynonyms(synonymsEditText.getText().toString().trim());
        vocabulary.setmAntonyms(antonymsEditText.getText().toString().trim());
        vocabulary.setmForms(formsEditText.getText().toString().trim());
        vocabulary.setmRelated(relatedEditText.getText().toString().trim());
        vocabulary.setmExample(exampleEditText.getText().toString().trim());
        vocabulary.setmImageURL(imageURLEditText.getText().toString().trim());


        if (vocabulary.getmWord().isEmpty()||vocabulary.getmWordMeaning().isEmpty()||vocabulary.getmHindiMeaning().isEmpty()||vocabulary.getmSynonyms().isEmpty()||vocabulary.getmExample().isEmpty()||vocabulary.getmAntonyms().isEmpty()){
            Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vocabulary.getmImageURL()==null || vocabulary.getmImageURL().isEmpty()){
            vocabulary.setmImageURL("https://firebasestorage.googleapis.com/v0/b/editorial-8cbf6.appspot.com/o/dailyVocabulary.jpeg?alt=media&token=fc80bd9d-a5f7-4a5a-b202-7ad64f416dc1");
        }

        vocabulary.setTimeInMillis(System.currentTimeMillis());

        new DBHelperFirebase().insertVocabularyWord(vocabulary, new DBHelperFirebase.VocabularyListener() {
            @Override
            public void onVocabularyInsert(boolean isSuccessful) {

                if (isSuccessful){
                    Toast.makeText(VocabularyActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    reinitializeViews();
                }else {
                    Toast.makeText(VocabularyActivity.this, "Failed to uploaded", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void reinitializeViews() {
        wordEditText.setText("");
        partOfSpeechEditText.setText("");
        meaningEditText.setText("");
        hindiMeaningEditText.setText("");
        synonymsEditText.setText("");
        antonymsEditText.setText("");
        formsEditText.setText("");
        relatedEditText.setText("");
        exampleEditText.setText("");
        imageURLEditText.setText("");


    }
}
