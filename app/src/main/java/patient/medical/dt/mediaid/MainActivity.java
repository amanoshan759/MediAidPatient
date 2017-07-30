package patient.medical.dt.mediaid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import patient.medical.dt.mediaid.fragment.Appointments;
import patient.medical.dt.mediaid.fragment.Bmi;
import patient.medical.dt.mediaid.fragment.DietChart;
import patient.medical.dt.mediaid.fragment.ManageProfile;
import patient.medical.dt.mediaid.fragment.Reports;
import patient.medical.dt.mediaid.fragment.SelectDoctor;
import patient.medical.dt.mediaid.util.SpUtility;
import patient.medical.dt.mediaid.util.Utility;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    private TextView txtUsername, txtEmail;
    private ShareActionProvider mShareActionProvider;


    private void init() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/FaktSoftPro-Blond.ttf");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
        View headernav = navigationView.inflateHeaderView(R.layout.nav_header_main);
        txtUsername = (TextView) headernav.findViewById(R.id.txtUsername);
        txtEmail = (TextView) headernav.findViewById(R.id.txtEmail);
        SharedPreferences sp = SpUtility.getSharedPreference(MainActivity.this);
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean(SpUtility.KEY_FLAG_LOGIN, true);
        if (sp.getBoolean(SpUtility.KEY_SIGN_UP_FLAG, SpUtility.DEFAULT_VALUE_BOOLEAN) && (sp.getBoolean(SpUtility.KEY_FLAG_SIGN_SET, SpUtility.DEFAULT_VALUE_BOOLEAN))) {
            txtUsername.setText(sp.getString(SpUtility.KEY_SIGN_UP_NAME, SpUtility.DEFAULT_VALUE_STRING));
            txtEmail.setText(sp.getString(SpUtility.KEY_SIGN_UP_EMAIL, SpUtility.DEFAULT_VALUE_STRING));
            e.putBoolean(SpUtility.KEY_SIGN_UP_FLAG, false);
            e.putBoolean(SpUtility.KEY_FLAG_SIGN_SET, false);

        } else {
            System.out.println("-----------------------------------------------------------");
            txtUsername.setText(sp.getString(SpUtility.KEY_PATIENT_USERNAME, SpUtility.DEFAULT_VALUE_STRING));
            txtEmail.setText(sp.getString(SpUtility.KEY_PATIENT_EMAIL, SpUtility.DEFAULT_VALUE_STRING));
            e.putBoolean(SpUtility.KEY_SIGN_UP_FLAG, false);
            e.putBoolean(SpUtility.KEY_FLAG_SIGN_SET, false);

        }
        e.commit();

        Appointments appointments = new Appointments();
        FragmentManager fragmentManager = getSupportFragmentManager();
        toolbar.setTitle("Appointments");
        fragmentManager.beginTransaction().replace(R.id.fragmentchange, appointments).commit();

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
        if (id == R.id.ChangePassword) {
            SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean(SpUtility.KEY_FLAG_CHANGE_PASSWORD, true);
            e.commit();
            Utility.startIntent(MainActivity.this, ChangePassowrd.class);
            return true;
        } else if (id == R.id.Share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app!");
            startActivity(shareIntent);
            return true;
        } else if (id == R.id.AboutUs) {
            Utility.startIntent(MainActivity.this, WebView.class);
            return true;
        } else if (id == R.id.Feedback) {
            Utility.startIntent(MainActivity.this, FeedBack.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Appointment) {
            Appointments appointments = new Appointments();
            FragmentManager fragmentManager = getSupportFragmentManager();
            toolbar.setTitle("Appointments");
            fragmentManager.beginTransaction().replace(R.id.fragmentchange, appointments).commit();
        } else if (id == R.id.Reports) {
            Reports reports = new Reports();
            FragmentManager fragmentManager = getSupportFragmentManager();
            toolbar.setTitle("Reports");
            fragmentManager.beginTransaction().replace(R.id.fragmentchange, reports).commit();
        } else if (id == R.id.Manageprofile) {
            ManageProfile manageProfile = new ManageProfile();
            FragmentManager fragmentManager = getSupportFragmentManager();
            toolbar.setTitle("Manage Profile");
            fragmentManager.beginTransaction().replace(R.id.fragmentchange, manageProfile).commit();
        } else if (id == R.id.logout) {
            DialogInterface.OnClickListener dialogclicklistener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE: {
                            SpUtility.clearSharedPreference(MainActivity.this, "med");
                            Utility.startIntent(MainActivity.this, LoginActivity.class);
                            MainActivity.this.finish();
                            break;
                        }
                        case DialogInterface.BUTTON_NEGATIVE: {
                            break;
                        }
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to logout ?");
            builder.setPositiveButton("OK", dialogclicklistener);
            builder.setNegativeButton("Cancel", dialogclicklistener);
            builder.show();
        } else if (id == R.id.selectDoctor) {
            SelectDoctor selectDoctor = new SelectDoctor();
            FragmentManager fragmentManager = getSupportFragmentManager();
            toolbar.setTitle("Select Doctor");
            fragmentManager.beginTransaction().replace(R.id.fragmentchange, selectDoctor).commit();
        } else if (id == R.id.Bmi) {
            Bmi bmi = new Bmi();
            FragmentManager fragmentManager = getSupportFragmentManager();
            toolbar.setTitle("Bmi");
            fragmentManager.beginTransaction().replace(R.id.fragmentchange, bmi).commit();

        } else if (id == R.id.DietChart) {
            DietChart dietChart = new DietChart();
            FragmentManager fragmentManager = getSupportFragmentManager();
            toolbar.setTitle("Diet Chart");
            fragmentManager.beginTransaction().replace(R.id.fragmentchange, dietChart).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
