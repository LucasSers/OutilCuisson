package com.example.outilcuisson;

import android.content.Context;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Ajouter.Ecouteur  {

    private static final String NOM_FICHIER = "fichierCuissons.txt";
    private String platAGerer;
    private ArrayList<String> platsAGerer = new ArrayList<>();

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

    // pour le plat ajouté depuis l'onglet Ajouter
    public String getPlatAGerer() {
        return platAGerer;
    }

    // pour le(s) plat(s) ajouté(s) au démarrage depuis le fichier
    public ArrayList<String> getPlatsAGerer() {
        return platsAGerer;
    }

    /**
     * Appelée au démarrage de l'Activitée
     */
    public void onStart() {
        String cuissonLue;

        InputStreamReader fichier = null;
        try {
            fichier = new InputStreamReader(openFileInput(NOM_FICHIER));
            BufferedReader fichierTexte = new BufferedReader(fichier);

            // lecture des lignes reçues et les ajoutent dans l'ArrayList
            while ((cuissonLue = fichierTexte.readLine()) != null) {
                platsAGerer.add(cuissonLue);
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.fichierNonTrouve, NOM_FICHIER), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.pbLectureFichier, NOM_FICHIER), Toast.LENGTH_LONG).show();
        } finally {
            try {
                if (fichier != null) fichier.close(); // ferme le fichier quoi qu'il arrive
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onStart();
    }

    /**
     * Appelé à la fermeture de l'application
     */
    public void onDestroy() {
        Afficher aModifier = (Afficher) getSupportFragmentManager().findFragmentByTag("f0");

        FileOutputStream fichier = null;
        try {
            fichier = openFileOutput(NOM_FICHIER, Context.MODE_PRIVATE); // PRIVATE -> remplace s'il existe

            // retour à la ligne != de '\n'
            String lineSeparator = System.getProperty("line.separator");
            // formatage des lignes reçues
            for (String cuissonLue : aModifier.getCuissons()) {
                StringBuilder chaine = new StringBuilder();
                chaine.append(cuissonLue).append(lineSeparator);
                // écriture du résultat dans le fichier
                fichier.write(chaine.toString().getBytes());
                fichier.flush(); // vide le buffer
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.fichierNonTrouve, NOM_FICHIER), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this,getString(R.string.pbLectureFichier, NOM_FICHIER),Toast.LENGTH_LONG).show();
        } finally {
            try {
                if (fichier != null) fichier.close(); // ferme le fichier quoi qu'il arrive
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onDestroy();
    }
}