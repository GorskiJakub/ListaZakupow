package com.example.dell.listazakupow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity {

    Button btnViewProducts;
    Button btnNewProduct;
    Button btnShareList;
    Integer current_list_id = getIntent().getIntExtra("list_id",0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
        btnNewProduct = (Button) findViewById(R.id.btnCreateProduct);
        btnShareList = (Button) findViewById(R.id.btnShareList);
        // view products click event
        btnViewProducts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                i.putExtra("list_id", current_list_id );
                startActivity(i);

            }
        });

        // view products click event
        btnNewProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
                i.putExtra("list_id", current_list_id );
                startActivity(i);

            }
        });

        //view share list event
        btnShareList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launching share list activity
                Intent i = new Intent(getApplicationContext(), ShareListActivity.class);
                i.putExtra("list_id", current_list_id );
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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
}

