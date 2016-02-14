package com.example.dell.listazakupow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShareListActivity extends Activity {

    private ProgressDialog pDialog;
    Integer current_list_id = getIntent().getIntExtra("list_id",0);;

    JSONParser jsonParser = new JSONParser();
    EditText inputMail;

    private static String url_share_list = "https://eu-cdbr-azure-north-d.cloudapp.net:3306/lista_zakupow/share_list.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_list);

        // Edit Text
        inputMail = (EditText) findViewById(R.id.inputMail);

        // Create button
        Button btnShareList = (Button) findViewById(R.id.btnShareList);

        // button click event
        btnShareList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                String mail = inputMail.getText().toString();
                String id_list = current_list_id.toString();
                new ShareList().execute(mail, id_list);
            }
        });
    }

    class ShareList extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShareListActivity.this);
            pDialog.setMessage("Creating conecction..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String mail = args[0];
            String id_list = args[0];

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mail", mail));
            params.add(new BasicNameValuePair("id_list", id_list));

            JSONObject json = jsonParser.makeHttpRequest(url_share_list,
                    "POST", params);

            // check log cat from response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), MainScreenActivity.class);
                    i.putExtra("list_id", current_list_id);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}

