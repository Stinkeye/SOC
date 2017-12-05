package com.soc.matthewhaynes.soc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.InputStream;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class Login extends AppCompatActivity {

    /* Declare new database object */
    DatabaseHelper myDb;

    /* used in log console to id msg */
    private static final String TAG = "Login";

    /* declare buttons and Text fields. Attaches '@+id' (Button & Field ids) from /app/res/layout/activity.xml to vars in this class */
    EditText editUserName, editPassword;
    Button btnLogin;
    Button btnNewUser;

    InputStream inputStream;
    String[] data;
    /* onCreate(Bundle) is where you initialize your activity.
    When Activity is started and application is not loaded,
    then both onCreate() methods will be called. But for subsequent starts of Activity ,
    the onCreate() of application will not be called */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        myDb = new DatabaseHelper(this); //will call constructor of Helper class

        /*
        inputStream = getResources().openRawResource(R.raw.cecs);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while ((csvLine= reader.readLine()) != null){
                data= csvLine.split(",");
                try{
                    Log.e("Data ", ""+data[0]+" "+data[1]+" "+data[2]+" "+data[3]+" "+data[4]+" "+data[5]+" "+data[6]+" "+data[7]+" "+data[8]+" "+data[9]+" "+data[10]+" "+data[11] +" "+data[12]+" "+data[13]);
                }catch(Exception e){
                    Log.e("Problem",e.toString());
                }
            }
        } catch (IOException ex){
            throw new RuntimeException("Error in reading csv file: " +ex);
        }
        */

         /* Casting buttons and Text fields. Attaches '@+id' (Button & Field ids) from /app/res/layout/activity.xml to vars in this class */
        editUserName= (EditText)findViewById(R.id.editText_userName);   //attaches textfield '@+id' from activity.xml (in app/res/layout) to variable in this class
        editPassword= (EditText)findViewById(R.id.editText_Password); // this is basically (Cast-to-text-field) findViewbyId(integer);
        btnLogin = (Button) findViewById(R.id.button_Login); //attaches Button '@+id' from activity.xml (in app/res/layout) to variable in this class
        btnNewUser = (Button) findViewById(R.id.button_newUser);




        /* Call all Button Methods. If one is Clicked an 'onClickListener' (listens for buttons clicks) will activate.  */
        //AddData();
        //viewAll();

        /*  CHANGE TO SEARCH SCREEN IF BUTTON IS PRESSED */
        btnLogin.setOnClickListener(new View.OnClickListener() { //set onClickListenere to 'listen' for button clicks
            @Override
            public void onClick(View v) {
                //v.setEnabled(false);
                String NameText = editUserName.getText().toString();
                String PassText = editPassword.getText().toString();
                String url = "http://socserver.azurewebsites.net/user?username=" + NameText + "&password=" + PassText;
                final String InValided = "Invalid UserName or Password";
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(responseBody != null){
                            String response = new String(responseBody);
                            if(response.equals("{\"Response\":\"Error\",\"Error\":\"Username/Password combination not found\"}"))
                            {
                                TextView Invalid = (TextView) findViewById(R.id.textView_Invalid);
                                Invalid.setText(InValided);
                            }
                            else{
                                Intent intent = new Intent(Login.this, MainActivity.class); //define the 'intent' you want to accomplish 'Intent' aka new screen
                                startActivity(intent); //start the intent (new screen for this purpose)
                            }
                            //TextView txtResponse = (TextView) findViewById(R.id.editText_userName);
                            //txtResponse.setText(new String(responseBody));

                        }
                        else{
                            TextView Invalid = (TextView) findViewById(R.id.textView_Invalid);
                            Invalid.setText(InValided);
                        }
                        //v.setEnabled(true);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //v.setEnabled(true);
                    }
                });
            }
        });

        btnNewUser.setOnClickListener(new View.OnClickListener() { //set onClickListenere to 'listen' for button clicks
            @Override
            public void onClick(View v) {
                Log.d(TAG, "About to Log in");   //logs info to the console
                Intent intent = new Intent(Login.this, NewUserActivity.class); //define the 'intent' you want to accomplish 'Intent' aka new screen
                startActivity(intent); //start the intent (new screen for this purpose)
            }
        });
    }//end onCreate method

}
