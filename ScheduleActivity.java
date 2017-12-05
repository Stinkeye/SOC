package com.soc.matthewhaynes.soc;



import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by matthew.haynes on 12/2/2017.
 */

public class ScheduleActivity extends AppCompatActivity {

    /**  Buttons, Text Fields, ect **/
    Button btnViewAll;
    Button btnTblExist;
    Button btnDelete;
    EditText etID;


    DatabaseHelper myDb;
    String TAG="Sched Activity: "; //tag for debugging to console

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);         //auto-generated
        setContentView(R.layout.activity_schedule); //auto-generated
        myDb = new DatabaseHelper(this);


        etID=(EditText)findViewById(R.id.etID); //set variable = id from editText field

        btnDelete= (Button) findViewById(R.id.btnSchedDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() { //invoke action when button is clicked
                    @Override                      //
                    /* Call isInserted() in DatabaseHelper class */
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(etID.getText().toString(),"tblClass");

                        /* Check if deleteRows() returns true or false regarding data deletion*/
                        if(deletedRows > 0)
                            Toast.makeText(ScheduleActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();  //Toast = temporary message bubble popup
                        else
                            Toast.makeText(ScheduleActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show(); //Toast = temporary message bubble popup
                    }
                }
        );



        btnViewAll = (Button) findViewById(R.id.btnViewSched);
        btnViewAll.setOnClickListener(new View.OnClickListener() { //set onClickListener to 'listen' for button clicks
            @Override
            public void onClick(View v) {
                Log.i(TAG, "btnViewAll");   //logs info to the console

                /* set a Cursor object equal to the result of db query getAllData() in DatabaseHelper class */

                Cursor res = myDb.getAllData("tblClass");  //a Cursor object can point to a SINGLE row of the result fetched by a db query

                if(res.getCount() == 0) {        //if no rows are sent back display a message
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                /* buffer that reads in database rows */
                StringBuffer buffer = new StringBuffer();                 //declare a buffer
                while (res.moveToNext()) {                                //move Cursor object 'res' to the next row
                    buffer.append("id :"+ res.getString(0)+"\n");         //index 0 is first db column
                    buffer.append("subject :"+ res.getString(1)+"\n");    //index 1 is second db column
                    buffer.append("class :"+ res.getString(2)+"\n");      //index 2 is third db column
                    buffer.append("title :"+ res.getString(4)+"\n");
                    buffer.append("day :"+ res.getString(6)+"\n");
                    buffer.append("time :"+ res.getString(7)+"\n");
                    buffer.append("room :"+ res.getString(8)+"\n\n");

                }

                // call method to show all db data in a message box
                showMessage("My Schedule",buffer.toString());
             myDb.close();
            }
        });

    }//end OnCreate()

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
