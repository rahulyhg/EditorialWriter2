package app.vacabulary.editorialwriter.gamefever.editorialwriter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity {

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
    public static int listLimit =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);


        fetchEditorialGeneralList();





        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


// set the icon
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        recyclerView =(RecyclerView)findViewById(R.id.delete_recyclerview);

        mAdapter = new EditorialGeneralInfoAdapter(editorialListArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.addItemDecoration(new DividerItemDecorator(this, LinearLayoutManager.VERTICAL));


        recyclerView.setAdapter(mAdapter);



        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(this, recyclerView ,new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        // do whatever

                        //onItemTouch(position);

                        new AlertDialog.Builder(DeleteActivity.this)
                                .setTitle("Delete Editorial")
                                .setMessage("Are you sure you want to delete this Editorial?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        DBHelperFirebase db = new DBHelperFirebase();
                                        db.deleteEditorial(editorialListArrayList.get(position).getEditorialID(),DeleteActivity.this);


                                        editorialListArrayList.remove(position);
                                        mAdapter.notifyDataSetChanged();
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

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        // mDrawerList = (ListView)findViewById(R.id.editoriallist_activity_drawer_listview);
        // initializeNavDrawer();

        addMoreButton = (View) findViewById(R.id.delete_activity_add_button);
        addMoreButton.setVisibility(View.INVISIBLE);

        progressBar=(ProgressBar)findViewById(R.id.delete_activity_progressbar);
        progressBar.setVisibility(View.VISIBLE);



        if(isShowingAd) {
        }




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
        editorialGeneralInfoArraylist.remove(editorialGeneralInfoArraylist.size()-1);

        for (EditorialGeneralInfo editorialGeneralInfo : editorialGeneralInfoArraylist){
            editorialListArrayList.add(insertPosition ,editorialGeneralInfo);
        }
        mAdapter.notifyDataSetChanged();



        addMoreButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        isRefreshing =false;

    }


    public void loadMoreClickdelete(View view) {
        loadMoreClick(view);
    }

    public void deleteEditorialListner(boolean b) {
        if (b) {


            Toast.makeText(this, "Editorial Deleted Sucessfully", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "Failed to Delete editorial !! please retry later", Toast.LENGTH_SHORT).show();
        }

    }
}
