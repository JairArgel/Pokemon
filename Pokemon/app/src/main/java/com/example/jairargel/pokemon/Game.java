package com.example.jairargel.pokemon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Game extends AppCompatActivity implements View.OnClickListener{
    TextView Nombre_Pokemon, Nombre1_Pokemon,Puntaje,Puntaje1;
    ImageView Imagen_Pokemon, Imagen1_Pokemon;
    TextView healt1;
    TextView healt2;
    int sal1 = 100;
    Button bt2;
    int sal2 = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Nombre_Pokemon=(TextView) findViewById(R.id.Nombre);
        Nombre1_Pokemon=(TextView) findViewById(R.id.Nombre1);
        healt1 = (TextView) findViewById(R.id.salud);
        healt2 = (TextView)findViewById(R.id.salud2);
        bt2 = (Button)findViewById(R.id.btatac);
        bt2.setOnClickListener(this);
        Imagen_Pokemon=(ImageView) findViewById(R.id.Pokemon);
        Imagen1_Pokemon=(ImageView) findViewById(R.id.Pokemon1);
        Random random=new Random();
        int id1= (int) (Math.random()*500);
        int id2=(int) (Math.random()*500);
        traernombre(id1,Nombre_Pokemon);
        traernombre(id2,Nombre1_Pokemon);
        cargarimagen(id1,Imagen_Pokemon);
        cargarimagen(id2,Imagen1_Pokemon);
    }



    public void  descarcarimagen(ImageView imageView,String url){
        ImageLoader mImageLoader;
        mImageLoader = MySingleton.getInstance(this).getImageLoader();
        mImageLoader.get(url, ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
    }

    private void cargarimagen(int id, final ImageView imageView) {
        MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        String url ="http://pokeapi.co/api/v2/pokemon-form/"+id+"/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject jsonObject1=new JSONObject(jsonObject.getString("sprites"));
                            String url=jsonObject1.getString("front_default").toString();
                            descarcarimagen(imageView,url);
                            Log.i("imagen: ", jsonObject1.getString("front_default").toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("","That didn't work!");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void traernombre(int id, final TextView t){
        MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        String url ="http://pokeapi.co/api/v2/pokemon/"+id+"/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            Log.i("nombre",jsonObject.getString("name"));
                            t.setText( jsonObject.getString("name"));
                            JSONArray jsonArray=jsonObject.getJSONArray("abilities");
                            for (int i=0;i<jsonArray.length();i++) {

                                Log.i("abilities: ", jsonArray.getJSONObject(i).toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("","That didn't work!");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void onClick(View v) {
        //Un id para implementar con un swith de 1 caso
        int id;
        id = v.getId();
        switch (id)
        {
            case R.id.btatac:

                int id1 = (int) (Math.random() * 40);
                int id2 = (int) (Math.random() * 40);

                sal1= sal1 - id1;
                healt1.setText(String.valueOf(sal1));
                sal2 = sal2 - id2;
                healt2.setText(String.valueOf(sal2));

                if (Integer.parseInt(healt1.getText().toString()) <= 0){
                    bt2.setEnabled(false);
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fin del Juego, Ganaste", Toast.LENGTH_SHORT);

                    toast1.show();
                }else if(Integer.parseInt(healt2.getText().toString()) <= 0) {
                    bt2.setEnabled(false);
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Fin del Juego, Perdiste", Toast.LENGTH_SHORT);

                    toast1.show();
                }else {

                }

                break;
        }
    }



}

