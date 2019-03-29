package com.example.administrateur.projetandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AvisFragment.OnFragmentInteractionListener,
MapFragment.OnFragmentInteractionListener,
CreateAvisFragment.OnFragmentInteractionListener{

    private static FragmentManager fm = null;
    private static Fragment fragment = null;

    private static List<Avis> avisList = new ArrayList<>();
    public RecyclerView recyclerView;
    private AppAvisbase appdb;
    private static AvisDAO avisDAO;
    private Spinner spinnerRegion;
    private static AvisDAO avisDAO2;
    private static RestaurantDAO restaurantDAO;
    private AppRestaurantsbase appdbRes;
    String[] lRestaurant;
    private static List<Restaurant> restaurantList = new ArrayList<>();

    public static void addAvis(Avis avis) {
        Avis avisAdd = avis;
        (new InsertAsyncTaskAvis(avisDAO)).execute(avisAdd);
        (new GetAllAvisAsyncTask(avisDAO)).execute();
        (new GetAllAvisAsyncTask(avisDAO2)).execute();
        (new GetAllRestaurantsAsyncTask(restaurantDAO)).execute();

//        fragment = new HomeFragment();
//        fm.beginTransaction().replace(R.id.content_main,fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //bdd
        appdb = AppAvisbase.getAvisbase(this);
        appdbRes = AppRestaurantsbase.getRestaurantsbase(this);

        avisDAO = appdb.avisDAO();
        avisDAO2 = appdb.avisDAO();

        restaurantDAO = appdbRes.restaurantDAO();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);


        if (! sp.getBoolean(getResources().getString(R.string.key_is_db_initialized),false)) {
            prepareRestaurant();
            prepareCharacterData();
            prepareCharacterData2();
            sp.edit().putBoolean(getResources().getString(R.string.key_is_db_initialized),true).apply();
        }

        (new GetAllAvisAsyncTask(avisDAO)).execute();
        (new GetAllAvisAsyncTask(avisDAO2)).execute();
        (new GetAllRestaurantsAsyncTask(restaurantDAO)).execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                fragment = new CreateAvisFragment(restaurantList);
                fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.content_main,fragment).commit();
            }
        });

        fm = getSupportFragmentManager();
        fragment = new HomeFragment();
        fm.beginTransaction().replace(R.id.content_main,fragment).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        fragment = new HomeFragment();


        switch(id){
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_map:
                fragment = new MapFragment(restaurantList);
                break;
            case R.id.nav_avis:
                fragment = new AvisFragment(avisList, restaurantList);
                break;
            default:
        }


        fm.beginTransaction().replace(R.id.content_main,fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    public void addAvis(Avis avis){
//        Avis avisAdd = avis;
//        (new InsertAsyncTaskAvis(avisDAO)).execute(avisAdd);
//    }

    private void prepareRestaurant() {
        Restaurant restaurant1, restaurant2, restaurant3, restaurant4;
        restaurant1 = new Restaurant("Manhattan",47.6414,6.856098);
        restaurant2 = new Restaurant("Le Millénium",47.638194,6.855091);
        restaurant3 = new Restaurant("Eatside",47.646401,6.854729);
        restaurant4 = new Restaurant("Los Tacos",47.653115,6.851338);

        (new InsertAsyncTaskRestaurants(restaurantDAO)).execute(restaurant1, restaurant2, restaurant3, restaurant4);
    }

    private void prepareCharacterData() {
        Avis avis1, avis2, avis3, avis4, avis5, avis6;
        avis1 = new Avis("Manhattan","toto", "12/02/2019", 7,"Excellent service , rapidité efficacité  avec des pizzas diverses et de meilleures goût.\n" +
                "Bref excellent rapport prix qualité.\n" +
                "A recommandé");
        avis2 = new Avis("Manhattan","bob", "02/02/2019", 6,"Franchement avant c'était super livraison à l'heure qualité au rendez-vous maintenant c'est médiocre .. pizza arrive cramé , sec avec des ingrédients qui manque .. livraison super longue .. j'hesite à plus y aller");
        avis3 = new Avis("Manhattan","Jean", "15/02/2019", 5,"Bon accueil");
        avis4 = new Avis("Manhattan","Pascal", "30/03/2019", 8,"Les pizza sont bonne,mais la relation client et pas top,personnel souvent froid,ne répond pas au appel par moment,mais les prix sont corrects et on se régale a chaque fois");
        avis5 = new Avis("Manhattan","Pierre", "21/01/2019", 7,"Super tacos, rapport qualité-prix excellent");
        avis6 = new Avis("Manhattan","Sandrine", "17/10/2018", 7,"Très bon rapport qualité prix lieu propre est service rapide");
        (new InsertAsyncTaskAvis(avisDAO)).execute(avis1, avis2, avis3, avis4, avis5, avis6);
    }

    private void prepareCharacterData2() {
        Avis avis1, avis2, avis3, avis4, avis5, avis6;
        avis1 = new Avis("Le Millénium","Dimitri", "12/02/2019", 7,"On y mange bien et le personnel est très sympathique !");
        avis2 = new Avis("Le Millénium","Paul", "02/02/2019", 6,"Franchement avant c'était super livraison à l'heure qualité au rendez-vous maintenant c'est médiocre .. pizza arrive cramé , sec avec des ingrédients qui manque .. livraison super longue .. j'hesite à plus y aller");
        avis3 = new Avis("Le Millénium","Alexis", "15/02/2019", 3,"Tres déçu du Millenium.\n" +
                "Je ne savais pas que les propriétaires avaient changé .\n" +
                "Pain mou , plus du tout croustillant .\n" +
                "Quasiment pas de sauces dans les sandwichs .\n" +
                "La qualite a fortement  baissé , je n'y retournerai pas.\n" +
                "Point positif : le personnel est très agreable");
        avis4 = new Avis("Le Millénium","Raph", "30/03/2019", 8,"Accueil sympathique et service au top. J'ai testé leur Kebab suite à sa réputation qui est excellente et justifiée. \nParfait pour se restaurer rapidement.");
        avis5 = new Avis("Le Millénium","Claude", "21/01/2019", 7,"On y mange très bien, personnel à l'écoute parfois un peu d'attente mais je pense que c'est pareil ailleurs");
        avis6 = new Avis("Le Millénium","Karine", "17/10/2018", 7,"service rapide et de qualité");
        (new InsertAsyncTaskAvis(avisDAO2)).execute(avis1, avis2, avis3, avis4, avis5, avis6);
    }

    private static class InsertAsyncTaskAvis extends AsyncTask<Avis, Void, Void> {
        private AvisDAO dao;
        InsertAsyncTaskAvis(AvisDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Avis... avis) {
            for (Avis a : avis) {
                this.dao.insertAvis(a);
            }
            return null;
        }
    }

    private class InsertAsyncTaskRestaurants extends AsyncTask<Restaurant, Void, Void> {
        private RestaurantDAO dao;
        InsertAsyncTaskRestaurants(RestaurantDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            for (Restaurant r : restaurants){
                this.dao.insertRestaurant(r);
            }
            return null;
        }
    }

    private static class GetAllAvisAsyncTask extends AsyncTask<Void,Void,Void> {
        private AvisDAO mAsyncTaskDao;
        ArrayList<Avis> avis;

        GetAllAvisAsyncTask(AvisDAO dao){
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            avis = new ArrayList<>(mAsyncTaskDao.getAllAvis());
            return null;
        }
        @Override
        protected void onPostExecute(Void voids){
            avisList = avis;
        }
    }

    private static class GetAllRestaurantsAsyncTask extends AsyncTask<Void,Void,Void> {
        private RestaurantDAO mAsyncTaskDao;
        ArrayList<Restaurant> restaurants;

        GetAllRestaurantsAsyncTask(RestaurantDAO dao){
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            restaurants = new ArrayList<>(mAsyncTaskDao.getAllRestaurant());
            return null;
        }
        @Override
        protected void onPostExecute(Void voids){
            restaurantList = restaurants;
        }
    }
}
