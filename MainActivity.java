package com.soc.matthewhaynes.sqliteapp;

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

    DatabaseHelper myDb;

    /* Declare TextFields and Buttons variables */
    EditText editDEPT, editCLASS, editSECTION, editTIME;
    Button btnAddData;
    Button btnViewAll;
    Button btnViewUpdate; //update is currently broken
    Button btnDelete;

    /* onCreate is where you initialize your activity, using findViewById(int) to retrieve the widgets in that UI that you need to interact with programmatically*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //auto generated
        setContentView(R.layout.activity_main); //auto generated

        myDb = new DatabaseHelper(this); //will call constructor of DataBaseHelper class

        /* Casting buttons and Text fields in UI*/
        editDEPT= (EditText)findViewById(R.id.editText_dept);
        editCLASS= (EditText)findViewById(R.id.editText_class);
        editSECTION= (EditText)findViewById(R.id.editText_section);
        editTIME= (EditText)findViewById(R.id.editText_time);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_viewAll);
        btnViewUpdate= (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);

        /* Excecute a button action if an OnClickListener catches a button-press */
        AddData(); //call Function to add data to database
        viewAll(); //view all entries in database
        //UpdateData(); //this is broken, it needs a primary key to function correctly
        DeleteData(); //delete entry in database, currently entered to DEPT

    }

    /* code that handles Delete Button */
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editDEPT.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /* all update data functions are broken */
    public void UpdateData() {
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editDEPT.getText().toString(),
                                editCLASS.getText().toString(),
                                editSECTION.getText().toString(), editTIME.getText().toString()); //checks for update data
                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /*code that handles Add Data button*/
    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editDEPT.getText().toString(),
                                editCLASS.getText().toString(),
                                editSECTION.getText().toString(),editTIME.getText().toString() );
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show(); // Toast = message bubble
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show(); //Toast = message bubble
                    }
                }
        );
    }

    /* code that handles View Classes button */
    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("DEPT :"+ res.getString(0)+"\n"); //index 0 is DEPT
                            buffer.append("CLASS :"+ res.getString(1)+"\n");
                            buffer.append("SECTION :"+ res.getString(2)+"\n");
                            buffer.append("TIME :"+ res.getString(3)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    /** i forgot what this does */
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
