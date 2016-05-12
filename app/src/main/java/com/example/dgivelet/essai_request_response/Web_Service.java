package com.example.dgivelet.essai_request_response;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;



/**
 * Created by dgivelet on 20/04/2016.
 */
public class Web_Service extends AppCompatActivity{



    private Intent intent;
    private final String URL = "http://10.0.2.2:8080/RestMongo-1.0-SNAPSHOT/webresources/produit/liste";
    public TextView viewdata;
    public EditText viewquantite,viewnom,viewlieu;
    public static final int INDENT_SPACES = 1;
    public static final String ENCODING = "UTF-8";
    public String produit;
    int valeurbase,valeurupdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaire);

        intent=getIntent();
        produit=intent.getStringExtra("code");
        viewdata= (TextView) findViewById(R.id.viewdata);
        viewnom= (EditText) findViewById(R.id.viewnom);
        viewlieu= (EditText) findViewById(R.id.viewlieu);
        viewquantite= (EditText) findViewById(R.id.viewquantite);
        DisplayProduct();
    }


    public void DisplayProduct() {

        String newurl ="http://pc872:8085/ProjetStageAfpaAlteca2016-1.0-SNAPSHOT/webresources/produit/get/"+ produit;
        //String newurl ="http://10.0.2.2:8080/RestMongo-1.0-SNAPSHOT/webresources/produit/get/"+ produit;
        Log.e("DisplaProduct","newurl:"+newurl );

        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle handle = client.get(newurl, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    try {
                        String str = new String(responseBody, ENCODING);

                        Log.e("onsuccess", "str: " + str);

                        JSONObject jsonObject = new JSONObject(str);

                        //JSONArray jarray=new JSONArray(str);
                        //String result=jarray.toString(INDENT_SPACES);

                        String result2 = jsonObject.toString();

                        Log.e("onsuccess", "result2" + result2);

                        //viewdata.setText(jsonObject.toString(INDENT_SPACES));
                        //JSONObject json = (JSONObject) jarray.get(0);
                        Log.e("onsuccess", "json: " + jsonObject);

                        viewnom.setText(jsonObject.get("nom").toString());
                        viewlieu.setText(jsonObject.get("lieu").toString());
                        viewquantite.setText(jsonObject.get("quantite").toString());
                        valeurbase = Integer.parseInt(viewquantite.getText().toString());
                    }   catch (NullPointerException e){

                        new AlertDialog.Builder(Web_Service.this)

                                .setMessage("Produit inexistant voulez vous le créer?")
                                .setPositiveButton("Création", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        viewnom.setText(produit);


                                    }
                                })
                                .setNegativeButton("Annulation", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Web_Service.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                })


                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }

                } catch (UnsupportedEncodingException e) {

                    Log.e("ENCODING ERROR", e.getMessage());
                } catch (JSONException e) {
                    Log.e("JSON ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("REQUEST FAILURE", "status: "+ statusCode);
                new AlertDialog.Builder(Web_Service.this).setMessage("Vous êtes deconnecté du réseau WIFI").show();
            }
        });

    }

    public void updateStock(View view) {

        valeurupdate= Integer.parseInt(viewquantite.getText().toString());

        if(valeurbase!=valeurupdate){

            new AlertDialog.Builder(this)

                    .setMessage("Etes vous sûr de vouloir modifier la quantité de ce Produit")
                    .setPositiveButton("Modification", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            int quantite= Integer.parseInt(viewquantite.getText().toString());
                            String nom=viewnom.getText().toString();
                            String newurl ="http://pc872:8085/ProjetStageAfpaAlteca2016-1.0-SNAPSHOT/webresources/produit/modif/"+ nom+"/"+quantite;
                            Log.e("updateStock","newurl:"+newurl );
                            AsyncHttpClient client = new AsyncHttpClient();
                            Log.e("updateStock","juste avant le try" );

                            RequestHandle handle = client.put(newurl, new AsyncHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                    Toast.makeText(Web_Service.this, "update ok", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Toast.makeText(Web_Service.this, "update failed", Toast.LENGTH_SHORT).show();
                                    Log.e("REQUEST FAILURE", "status: "+ statusCode);
                                    new AlertDialog.Builder(Web_Service.this).setMessage("Vous êtes deconnecté du réseau WIFI").show();
                                }
                            });

                        }
                    })
                    .setNegativeButton("Annulation", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })


                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        else {
            new AlertDialog.Builder(this)

                    .setMessage("pas de modification voulez vous validez")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            Log.d("onClick: ","ok");
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            Log.d("onClick: ","no");
                        }
                    })

                    .setIcon(R.drawable.scanner)
                    .show();
            //alert.setContentView(R.layout.framelayout);

        }

    }

    public void insertStock(View view) {

        int quantite= Integer.parseInt(viewquantite.getText().toString());
        String nom=viewnom.getText().toString();
        String lieu=viewlieu.getText().toString();
        String newurl ="http://pc872:8085/ProjetStageAfpaAlteca2016-1.0-SNAPSHOT/webresources/produit/insert/"+ nom+"/"+quantite+"/"+lieu;
        Log.e("updateStock","newurl:"+newurl );
        AsyncHttpClient client = new AsyncHttpClient();
        Log.e("updateStock","juste avant le try" );

        RequestHandle handle = client.post(newurl, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Toast.makeText(Web_Service.this, "insert ok", Toast.LENGTH_SHORT).show();

            }



            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Web_Service.this, "insert failed", Toast.LENGTH_SHORT).show();
                Log.e("REQUEST FAILURE", "status: "+ statusCode);
            }
        });


    }
}

