package app.vacabulary.editorialwriter.gamefever.editorialwriter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditorialListActivity extends AppCompatActivity {

    private List<EditorialGeneralInfo> editorialListArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditorialGeneralInfoAdapter mAdapter;

    // private ListView mDrawerList;
    // private ArrayAdapter<String> mDrawerAdapter;
    View addMoreButton;
    ProgressBar progressBar;
    private boolean isRefreshing = true;
    private  boolean isSplashScreenVisible =true;
    public static boolean isShowingAd =false;
    public static String shareLink ="";
    public static int listLimit =15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_list);

        fetchEditorialGeneralList();


        setContentView(R.layout.activity_editorial_list);
        isSplashScreenVisible=false;




        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


// set the icon
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        recyclerView =(RecyclerView)findViewById(R.id.editoriallist_recyclerview);

        mAdapter = new EditorialGeneralInfoAdapter(editorialListArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.addItemDecoration(new DividerItemDecorator(this, LinearLayoutManager.VERTICAL));


        recyclerView.setAdapter(mAdapter);



        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(this, recyclerView ,new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        onItemTouch(position);


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                        onItemLongClick(position);
                    }
                })
        );


        // mDrawerList = (ListView)findViewById(R.id.editoriallist_activity_drawer_listview);
        // initializeNavDrawer();

        addMoreButton = (View) findViewById(R.id.editoriallist_activity_add_button);
        addMoreButton.setVisibility(View.INVISIBLE);

        progressBar=(ProgressBar)findViewById(R.id.editoriallist_activity_progressbar);
        progressBar.setVisibility(View.VISIBLE);



        if(isShowingAd) {
        }



    }

    private void onItemLongClick(final int position) {


        new AlertDialog.Builder(EditorialListActivity.this)
                .setTitle("Send Notification")
                .setMessage("Want to sent notification for this editorial")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendNotification(position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void sendNotification(int position) {
        new DBHelperFirebase().sendNotification(editorialListArrayList.get(position).getEditorialID(), new DBHelperFirebase.SendNotificationListener() {
            @Override
            public void onNotificationSent(boolean isSuccessful) {
                Toast.makeText(EditorialListActivity.this, "Notification Sent "+isSuccessful, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onItemTouch(int position) {
        EditorialGeneralInfo editorialgenralInfo = editorialListArrayList.get(position);
        Intent i = new Intent(this , UpdaterActivity.class);
        i.putExtra("editorialID",editorialgenralInfo.getEditorialID());
        i.putExtra("editorialDate",editorialgenralInfo.getEditorialDate());
        i.putExtra("editorialHeading",editorialgenralInfo.getEditorialHeading());
        i.putExtra("editorialSource",editorialgenralInfo.getEditorialSource());
        i.putExtra("editorialSubheading",editorialgenralInfo.getEditorialSubHeading());
        i.putExtra("editorialTag",editorialgenralInfo.getEditorialTag());

        startActivity(i);


    }


    public void fetchEditorialGeneralList(){
        DBHelperFirebase dbHelperFirebase = new DBHelperFirebase();
        dbHelperFirebase.fetchEditorialList(EditorialListActivity.listLimit ,"" ,this , true);

    }

    public void loadMoreClick(View view) {
        DBHelperFirebase dbHelperFirebase = new DBHelperFirebase();
        dbHelperFirebase.fetchEditorialList(EditorialListActivity.listLimit ,editorialListArrayList.get(editorialListArrayList.size()-1).getEditorialID() ,this ,false);

        addMoreButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

    }


    public void onFetchEditorialGeneralInfo(ArrayList<EditorialGeneralInfo> editorialGeneralInfoArraylist) {



        int insertPosition = editorialListArrayList.size();
        if(insertPosition != 0) {
            editorialGeneralInfoArraylist.remove(editorialGeneralInfoArraylist.size() - 1);
        }

        for (EditorialGeneralInfo editorialGeneralInfo : editorialGeneralInfoArraylist){
            editorialListArrayList.add(insertPosition ,editorialGeneralInfo);
        }
        mAdapter.notifyDataSetChanged();



        addMoreButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        isRefreshing =false;

    }



}
