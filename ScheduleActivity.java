package com.soc.matthewhaynes.sqliteapp; //this is different depending on the PATH to your project. remember to CLEAN -> REBUILD when downloading

/**********************************  CUT BELOW HERE TO PASTE *****************************************************/

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScheduleActivity extends AppCompatActivity {

    Button btnViewAll;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);         //auto-generated
        setContentView(R.layout.activity_schedule); //auto-generated


        /*  CODE HERE */




    }


    /* Handles ViewDatabase button when pressed. Calls getAllData() in DatabaseHelper */
    public void viewAll() {
        btnViewAll.setOnClickListener(            //add a 'onClickListener' to button ..notice pattern (this always here)
                new View.OnClickListener() {      //invoke action when button is clicked ..notice pattern (this always here)
                    @Override                     //..notice pattern (this always here)
                    public void onClick(View v) { //declare action to be taken when button clicked

                        /* set a Cursor object equal to the result of db query getAllData() in DatabaseHelper class */
                        Cursor res = myDb.getAllData("schedTable");  //a Cursor object can point to a SINGLE row of the result fetched by a db query
                        if(res.getCount() == 0) {        //if no rows are sent back display a message
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        /* ..not sure.  Some sort of buffer that reads in database rows */
                        StringBuffer buffer = new StringBuffer();                 //declare a buffer
                        while (res.moveToNext()) {                                //move Cursor object 'res' to the next row
                            buffer.append("DEPT :"+ res.getString(0)+"\n");         //index 0 is first db column
                            buffer.append("CLASS :"+ res.getString(1)+"\n");       //index 1 is second db column
                            buffer.append("SECTION :"+ res.getString(2)+"\n");//index 2 is third db column
                            buffer.append("TIME :"+ res.getString(3)+"\n\n");      //index 3 is fourth db column
                        }

                        // call method to show all db data in a message box
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    /* method to show requested db data in message box */
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
