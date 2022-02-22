package com.example.outilcuisson;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listeCuissons;
    ArrayList<String> lesCuissons = new ArrayList<>();
    //CustomListAdaptater<String> adaptateur;
    ArrayAdapter<String> adaptateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listeCuissons = findViewById(R.id.liste);

        lesCuissons.add(OutilCuisson.transformeEnChaine("azertyuiopQSDFGHQQ",1, 10, 200));
        lesCuissons.add(OutilCuisson.transformeEnChaine("Gratin dauphinois",0, 50, 180));
        lesCuissons.add(OutilCuisson.transformeEnChaine("Tarte aux pommes",0, 40, 205));
        lesCuissons.add(OutilCuisson.transformeEnChaine("azertyuiopQSDFGHQ",1, 10, 200));

        adaptateur = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lesCuissons);

        listeCuissons.setAdapter(adaptateur);

        listeCuissons.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        //text = lesModules.get(position);
        //Toast.makeText(this, getString(R.string.toast, text), Toast.LENGTH_SHORT).show();
    }
}