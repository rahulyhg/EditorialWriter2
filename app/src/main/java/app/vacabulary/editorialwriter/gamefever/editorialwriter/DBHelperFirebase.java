package app.vacabulary.editorialwriter.gamefever.editorialwriter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Created by gamef on 01-03-2017.
 */

public class DBHelperFirebase {


    FirebaseDatabase database;
    private boolean doneGeneral=false;
    private boolean donefull=false;


    public DBHelperFirebase() {
        database = FirebaseDatabase.getInstance();
    }


    public void getEditorialFullInfoByID(final EditorialGeneralInfo editorialGeneralInfo, final UpdaterActivity updaterActivity) {
        /*Return Full editorial object using editorial general info */

        DatabaseReference myRef = database.getReference("EditorialFullInfo/" + editorialGeneralInfo.getEditorialID());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EditorialExtraInfo editorialExtraInfo =  dataSnapshot.getValue(EditorialExtraInfo.class);

                EditorialFullInfo editorialFullInfo = new EditorialFullInfo(editorialGeneralInfo, editorialExtraInfo);


                onEditorialFullInfoById(editorialFullInfo);
                updaterActivity.onGetEditorialFullInfo(editorialFullInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void getEditorialFullInfoByID(final EditorialGeneralInfo editorialGeneralInfo , final WriterActivity activity) {
        /*Return Full editorial object using editorial general info */

        DatabaseReference myRef = database.getReference("EditorialFullInfo/" + editorialGeneralInfo.getEditorialID());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EditorialExtraInfo editorialExtraInfo =  dataSnapshot.getValue(EditorialExtraInfo.class);

                EditorialFullInfo editorialFullInfo = new EditorialFullInfo(editorialGeneralInfo, editorialExtraInfo);


                //onEditorialFullInfoById(editorialFullInfo);
               activity.onGetEditorialFullInfo(editorialFullInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.report(new Exception("article cannot be loadede"));
            }
        });

    }


    private void onEditorialFullInfoById(EditorialFullInfo editorialFullInfo) {
    /*callback Function notifying data is fetched succesfully*/
    }

    public void insertEditorial(EditorialFullInfo editorialFullInfo ) {
        /*insert editorials detail to two diffirent node
        * editorial gn info
        * editorial full info*/

        DatabaseReference myRef = database.getReference("EditorialGeneralInfo");
        String pushkey = myRef.push().getKey();

        editorialFullInfo.getEditorialExtraInfo().setEditorialId(pushkey);
        editorialFullInfo.getEditorialGeneralInfo().setEditorialID(pushkey);


        myRef = database.getReference("EditorialFullInfo/" + pushkey);
        myRef.setValue(editorialFullInfo.getEditorialExtraInfo());

        DatabaseReference myRef2 = database.getReference("EditorialGeneralInfo/" + pushkey);

        myRef2.setValue(editorialFullInfo.getEditorialGeneralInfo(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){


                }
            }
        });






    }



    public void insertEditorial(EditorialFullInfo editorialFullInfo , final WriterActivity writerActivity ) {
        /*insert editorials detail to two diffirent node
        * editorial gn info
        * editorial full info*/

        DatabaseReference myRef = database.getReference("EditorialGeneralInfo");
        String pushkey = myRef.push().getKey();

        editorialFullInfo.getEditorialExtraInfo().setEditorialId(pushkey);
        editorialFullInfo.getEditorialGeneralInfo().setEditorialID(pushkey);


        myRef = database.getReference("EditorialFullInfo/" + pushkey);
        myRef.setValue(editorialFullInfo.getEditorialExtraInfo(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    donefull =true;
                    if(doneGeneral) {
                        writerActivity.insertEditorialListner(true);
                        doneGeneral=false;
                        donefull=false;
                    }
                }else{
                    writerActivity.insertEditorialListner(false);
                    doneGeneral=false;
                    donefull=false;

                }
            }
        });

        DatabaseReference myRef2 = database.getReference("EditorialGeneralInfo/" + pushkey);

        myRef2.setValue(editorialFullInfo.getEditorialGeneralInfo(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    writerActivity.insertEditorialListner(true);
                    doneGeneral =true;
                    if(donefull) {

                        doneGeneral=false;
                        donefull=false;

                    }
                }else{
                        writerActivity.insertEditorialListner(false);
                        doneGeneral=false;
                        donefull=false;

                }
            }
        });






    }



    public void fetchEditorialList(int limit, String end, final DeleteActivity deleteActivity, boolean isFirst) {
        /*return list of editorial of size limit which end at end*/

        DatabaseReference myRef2 = database.getReference("EditorialGeneralInfo");
        Query query;
        if (isFirst) {
            query = myRef2.orderByChild("editorialID").limitToLast(limit);
        } else {
            query = myRef2.orderByChild("editorialID").limitToLast(limit).endAt(end);



        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<EditorialGeneralInfo> editorialGeneralInfoArraylist = new ArrayList<EditorialGeneralInfo>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    EditorialGeneralInfo editorialGeneralInfo =  ds.getValue(EditorialGeneralInfo.class);


                    editorialGeneralInfoArraylist.add(editorialGeneralInfo);
                }

               /// onFetchEditorialList(editorialGeneralInfoArraylist, activity);
                deleteActivity.onFetchEditorialGeneralInfo(editorialGeneralInfoArraylist);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void fetchEditorialList(int limit, String end , final EditorialListActivity activity, boolean isFirst) {
        /*return list of editorial of size limit which end at end*/

        DatabaseReference myRef2 = database.getReference("EditorialGeneralInfo");
        Query query;
        if (isFirst) {
            query = myRef2.limitToLast(limit);
        } else {
            query = myRef2.orderByChild("editorialID").limitToLast(limit).endAt(end);



        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<EditorialGeneralInfo> editorialGeneralInfoArraylist = new ArrayList<EditorialGeneralInfo>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    EditorialGeneralInfo editorialGeneralInfo =  ds.getValue(EditorialGeneralInfo.class);


                    editorialGeneralInfoArraylist.add(editorialGeneralInfo);
                }


                activity.onFetchEditorialGeneralInfo(editorialGeneralInfoArraylist);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseCrash.report(new Exception("Data list cannot be loadede"));


            }
        });


    }

    private void onFetchEditorialList(ArrayList<EditorialGeneralInfo> editorialGeneralInfoArraylist, WriterActivity activity) {

    }


    public void updateEditorial(EditorialFullInfo editorialFullInfo , final UpdaterActivity updaterActivity) {


        DatabaseReference myRef = database.getReference("EditorialGeneralInfo");
        String pushkey = editorialFullInfo.getEditorialGeneralInfo().getEditorialID();

        editorialFullInfo.getEditorialExtraInfo().setEditorialId(pushkey);
        editorialFullInfo.getEditorialGeneralInfo().setEditorialID(pushkey);


        myRef = database.getReference("EditorialFullInfo/" + pushkey);
        myRef.setValue(editorialFullInfo.getEditorialExtraInfo());

        DatabaseReference myRef2 = database.getReference("EditorialGeneralInfo/" + pushkey);

        myRef2.setValue(editorialFullInfo.getEditorialGeneralInfo(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    doneGeneral =true;
                    if(donefull) {
                        updaterActivity.updateEditorialListner(true);
                        doneGeneral=false;
                        donefull=false;

                    }
                }else{
                    updaterActivity.updateEditorialListner(false);
                    doneGeneral=false;
                    donefull=false;

                }
            }
        });








    }


    public void sendNotification(String editorialID , final SendNotificationListener sendNotificationListener){

        DatabaseReference databaseReference = database.getReference("EditorialGeneralInfo/"+editorialID+"/editorialPushNotification");

        databaseReference.setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    sendNotificationListener.onNotificationSent(true);
                }else {
                    sendNotificationListener.onNotificationSent(false);
                }
            }
        });

    }

    public void deleteEditorial(String editorialID , final DeleteActivity deleteActivity) {
        DatabaseReference myRef = database.getReference("EditorialGeneralInfo");



        myRef = database.getReference("EditorialFullInfo/" + editorialID);
        myRef.removeValue();

        DatabaseReference myRef2 = database.getReference("EditorialGeneralInfo/" + editorialID);
myRef2.removeValue(new DatabaseReference.CompletionListener() {
    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        if (databaseError == null) {
            deleteActivity.deleteEditorialListner(true);
        }
    else {
            deleteActivity.deleteEditorialListner(false);
        }
    }

});


    }

    public void insertVocabularyWord(Vocabulary vocab, final VocabularyListener vocabularyListener ){

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Vocabulary").document(vocab.getmWord()).set(vocab)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                vocabularyListener.onVocabularyInsert(true);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                vocabularyListener.onVocabularyInsert(false);
            }
        });

    }


    public interface VocabularyListener{
        public void onVocabularyInsert(boolean isSuccessful);
    }

    public interface SendNotificationListener{
        public void onNotificationSent(boolean isSuccessful);
    }

}
