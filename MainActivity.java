package com.soc.matthewhaynes.sqliteapp; //this will be unique to your package.  REMEMBER TO CLEAN --> REBUILD

/**********************************  CUT BELOW HERE TO PASTE *****************************************************/

/**
 *  TUTORIAL FOUND AT
 *  http://www.codebind.com/android-tutorials-and-examples/android-sqlite-tutorial-example/
 *  https://www.youtube.com/watch?v=cp2rL3sAFmI
 */

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb; //define database object

    EditText editDEPT, editCLASS, editSECTION, editTIME; //Declare a text to attach to txt field @+id  found in activity.xml
    Button btnAddData;                                   //Declare a button to attach to a button @+id  found in activity.xml
    Button btnViewAll;
    Button btnViewUpdate; //update is currently broken
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //auto-generated
        setContentView(R.layout.activity_main);//auto-generated
        
        /* Declare new database object */
        myDb = new DatabaseHelper(this); //will call constructor of Helper class

        /* Casting buttons and Text fields */
        editDEPT= (EditText)findViewById(R.id.editText_dept);  //attaches textfield '@+id' from activity.xml (in app/res/layout) to variable in this class
        editCLASS= (EditText)findViewById(R.id.editText_class); // this is basically (Cast-to-text-field) findViewbyId(integer);
        editSECTION= (EditText)findViewById(R.id.editText_section);
        editTIME= (EditText)findViewById(R.id.editText_time);
        btnAddData = (Button) findViewById(R.id.button_add); //attaches Button '@+id' from activity.xml (in app/res/layout) to variable in this class
        btnViewAll = (Button) findViewById(R.id.button_viewAll);
        btnViewUpdate= (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);

        /* Call all Button Methods. If one is Clicked an 'onClickListener' (listens for buttons clicks) will activate.  */
        AddData();
        viewAll();
        //UpdateData(); //this is broken, it needs a primary key to function correctly
        DeleteData();

    }

    /* Handles Add Data button when pressed. Calls deleteRows() in DatabaseHelper */
    public void DeleteData() {
        btnDelete.setOnClickListener(               //add a 'onClickListener' to button ..notice pattern (this always here)
                new View.OnClickListener() {        //invoke action when button is clicked ..notice pattern (this always here)
                    @Override                      //..notice pattern (this always here)

                    /* Call isInserted() in DatabaseHelper class */
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editDEPT.getText().toString());

                        /* Check if deleteRows() returns true or false regarding data deletion*/
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();  //Toast = temporary message bubble popup
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show(); //Toast = temporary message bubble popup
                    }
                }
        );
    }

    /* all update data functions are broken ..disregard for now */
    public void UpdateData() {
        btnViewUpdate.setOnClickListener(     //add a 'onClickListener' to button ..notice pattern (this always here)
                new View.OnClickListener() {  //invoke action when button is clicked ..notice pattern (this always here)
                    @Override                 //..notice pattern (this always here)
                    public void onClick(View v) { //declare action to be taken when button clicked

                        /* Call isUpdated() in DatabaseHelper class to update db */
                        boolean isUpdate = myDb.updateData(editDEPT.getText().toString(),
                                                           editCLASS.getText().toString(),
                                                           editSECTION.getText().toString(),
                                                           editTIME.getText().toString()); //call method with parameters

                        /* Check if isUpdated() returns true or false regarding data insertion */
                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();   //Toast = temporary message bubble popup
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();  //Toast = temporary message bubble popup
                    }
                }
        );
    }

    /* Handles Add Data button when pressed. Calls isInserted() in DatabaseHelper. Data will be added to database*/
    public  void AddData() {
        btnAddData.setOnClickListener(         //add a 'onClickListener' to button ..notice pattern (this always here)
                new View.OnClickListener() {   //invoke action when button is clicked ..notice pattern (this always here)
                    @Override                  //..notice pattern (this always here)
                    public void onClick(View v) { //declare action to be taken when button clicked

                        /* Call isInserted() in DatabaseHelper class */
                        boolean isInserted = myDb.insertData(editDEPT.getText().toString(),
                                                             editCLASS.getText().toString(),
                                                             editSECTION.getText().toString(),
                                                             editTIME.getText().toString() );//call method with parameters

                        /* Check if isInserted() returns true or false regarding data insertion */
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();  //Toast = temporary message bubble popup
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();  //Toast = temporary message bubble popup
                    }
                }
        );
    }

    /* Handles ViewDatabase button when pressed. Calls getAllData() in DatabaseHelper */
    public void viewAll() {
        btnViewAll.setOnClickListener(            //add a 'onClickListener' to button ..notice pattern (this always here)
                new View.OnClickListener() {      //invoke action when button is clicked ..notice pattern (this always here)
                    @Override                     //..notice pattern (this always here)
                    public void onClick(View v) { //declare action to be taken when button clicked

                        /* set a Cursor object equal to the result of db query getAllData() in DatabaseHelper class */
                        Cursor res = myDb.getAllData();  //a Cursor object can point to a SINGLE row of the result fetched by a db query
                        if(res.getCount() == 0) {        //if no rows are sent back display a message
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        /* ..not sure.  Some sort of buffer that reads in database rows */
                        StringBuffer buffer = new StringBuffer();                 //declare a buffer
                        while (res.moveToNext()) {                                //move Cursor object 'res' to the next row
                            buffer.append("ID :"+ res.getString(0)+"\n");         //index 0 is first db column
                            buffer.append("Name :"+ res.getString(1)+"\n");       //index 1 is second db column
                            buffer.append("Description :"+ res.getString(2)+"\n");//index 2 is third db column
                            buffer.append("Tag :"+ res.getString(3)+"\n\n");      //index 3 is fourth db column
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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    */
}
