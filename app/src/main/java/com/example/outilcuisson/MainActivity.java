package com.example.outilcuisson;

import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements Ajouter.Ecouteur  {


    private String platAGerer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 pager = findViewById(R.id.activity_main_viewpager);
        TabLayout gestionnaireOnglet = findViewById(R.id.tab_layout);


        pager.setAdapter(new AdapteurPage(this));

        String[] titreOnglet = {getString(R.string.ongletAfficher),
                                getString(R.string.ongletAjouter)};

        new TabLayoutMediator(gestionnaireOnglet, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titreOnglet[position]);
            }
        }).attach();

        // permet de cacher la status bar d'Android par défaut en haut de l'écran
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void recevoirPlat(String lePlat) {
        platAGerer = lePlat;
        Afficher aModifier = (Afficher) getSupportFragmentManager().findFragmentByTag("f0");

        if (aModifier != null ) {
            aModifier.ajouterPlat(lePlat);
        }
    }

    public String getPlatAGerer() {
        return platAGerer;
    }


}