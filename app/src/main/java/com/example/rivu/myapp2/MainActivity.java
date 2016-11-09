package com.example.rivu.myapp2;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mButton2;
    private EditText mTitle,mDescription;
    public static  List<Data> listTitles;
    private String title1 , desc;
    private static int count = 0 ;
    private Toolbar toolbar;
    private Dialog dialog;
    private TextView textShow;
    RecyclerView recyclerView;
    private ImageButton mDelete;
    private DataStore datautil;
    private Cursor mdescRetrieve ,mCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        textShow = (TextView)findViewById(R.id.textView5);
        setSupportActionBar(toolbar);
        datautil = new DataStore(getApplicationContext());
        mdescRetrieve = datautil.retrieve();
     //   listTitles=null;
        listTitles = new ArrayList<>();
        while(mdescRetrieve.moveToNext()){
            listTitles.add(new Data(mdescRetrieve.getString(mdescRetrieve.getColumnIndex(DataStore.COLUMN_TITLE)),mdescRetrieve.getString(mdescRetrieve.getColumnIndex(DataStore.COLUMN_DESC))));
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Recycler_View_Adapter adapter = new Recycler_View_Adapter(listTitles, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    // inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    // on clicking the add button from the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add) {
            textShow.setVisibility(View.INVISIBLE);
            dialog = new Dialog(MainActivity.this);
            dialog.setTitle("Your Task");
            dialog.setContentView(R.layout.info_layout);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
            mTitle = (EditText)dialog.findViewById(R.id.infoTitle);
            mDescription =(EditText)dialog.findViewById(R.id.infoDesc);
            mButton2 = (Button)dialog.findViewById(R.id.infoButton);
            mButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    title1 = mTitle.getText().toString();
                    desc   = mDescription.getText().toString();
                    if(title1.isEmpty() || desc.isEmpty()){
                        Toast.makeText(getApplicationContext() ,"Fields should not be blank",Toast.LENGTH_SHORT).show();
                    }
                    else {
                       // mDelete = (ImageButton)item.findViewById(R.id.imageButton);
                        mTitle.setText("");
                        mDescription.setText("");
                        dialog.dismiss();
                       // listTitles.add((new Data(title1, desc)));

                            if(datautil.insertInfo(title1,desc)) {
                            listTitles.add((new Data(title1,desc)));
                            Toast.makeText(getApplicationContext(),"Stored In DataBase",Toast.LENGTH_SHORT).show();
                            recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                            Recycler_View_Adapter adapter = new Recycler_View_Adapter(listTitles, getApplication());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                    }

                }
            });

        }
        if(id ==  R.id.next){
            startActivity(new Intent(this,DetailsActivity.class));
        }
      if(id == R.id.delete){

          final Dialog dialogbox = new Dialog(MainActivity.this);
          dialogbox.setTitle("Confirmation");
          dialogbox.setContentView(R.layout.confirm_layout);
          dialogbox.show();
          Window window = dialogbox.getWindow();
         window.setLayout(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
          Button mYes =(Button)dialogbox.findViewById(R.id.YESButton);
          Button mNo = (Button)dialogbox.findViewById(R.id.button_no);
          mYes.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          datautil.delete();
                                          listTitles.clear();
                                          Intent intent = getIntent();
                                          finish();
                                          startActivity(intent);

                                      }
                                  });


          mNo.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View view){
                  Toast.makeText(getApplicationContext(),"Wont be removed",Toast.LENGTH_SHORT).show();
                  //Intent intent = getIntent();
                  //finish();
                  //startActivity(intent);
                  dialogbox.dismiss();
              }
          });
      }
        return super.onOptionsItemSelected(item);
    }

    // clicking on the text view to get the respective description
    public void click(View v)
    {
        int count=0;
        Bundle bundle = new Bundle();
        Intent intent =  new Intent(this , DetailsActivity.class);
        TextView text = (TextView)v.findViewById(R.id.title);
        String desc="";
        for(Data task : listTitles){

            if(task.title.equals(text.getText().toString())){
                desc = task.description;
                break;
            }
            count++;
        }

        // sending the postion
        bundle.putInt("Position", count);
        count = 0;
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public static int getSize(){
        return listTitles.size();
    }

}
