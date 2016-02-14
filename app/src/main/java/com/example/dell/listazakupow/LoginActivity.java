package com.example.dell.listazakupow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    private ProgressDialog pDialog;

    EditText email, password;
    Button login;

    JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_login = "https://eu-cdbr-azure-north-d.cloudapp.net:3306/lista_zakupow/login.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        findViewsById();
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // execute method invokes doInBackground() where we open a Http URL connection using the given Servlet URL
                //and get output response from InputStream and return it.
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                new Login().execute(mail, pass);

            }
        });
    }
    private void findViewsById() {

        email = (EditText) findViewById(R.id.TFemail);
        password = (EditText) findViewById(R.id.TFpassword);
        login = (Button) findViewById(R.id.Blogin);
    }
    private class Login extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // Getting email and password from user input

            String email = args[0];
            String pass = args[1];

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("e",email));
            params.add(new BasicNameValuePair("p",pass));
            JSONObject json = jsonParser.makeHttpRequest(url_login, "GET", params);
            //  Log.d("Login: ", json.toString());
            try {

                int success = json.getInt(TAG_SUCCESS);
                if(success == 1){
                    Intent i = new Intent(getApplicationContext(), UserListActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("email", email);
                    startActivity(i);
                    finish();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

}