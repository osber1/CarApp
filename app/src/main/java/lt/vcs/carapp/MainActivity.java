package lt.vcs.carapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.List;

import lt.vcs.carapp.additional.EditAuto;
import lt.vcs.carapp.fragments.InsuranceFragment;
import lt.vcs.carapp.fragments.MainFragment;
import lt.vcs.carapp.fragments.MaintenanceFragment;
import lt.vcs.carapp.fragments.MileageFragment;
import lt.vcs.carapp.fragments.ServiceFragment;
import lt.vcs.carapp.fragments.SettingsFragment;
import lt.vcs.carapp.fragments.TechFragment;
import lt.vcs.carapp.model.Car;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Spinner spinner;
    long id;
    int idAuto;
    Fragment fragment = new Fragment();
    Bundle bundle = new Bundle();
    Car car;

    public static Date convertString2Date(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        spinner = findViewById(R.id.nameSpinner);
        setSpinnerInfo();

        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new MainFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (menuItem.getItemId() == R.id.review) {
            fragment = new MainFragment();
        } else if (menuItem.getItemId() == R.id.maintenance) {
            fragment = new MaintenanceFragment();
        } else if (menuItem.getItemId() == R.id.service) {
            fragment = new ServiceFragment();
        } else if (menuItem.getItemId() == R.id.insurance) {
            fragment = new InsuranceFragment();
        } else if (menuItem.getItemId() == R.id.tech) {
            fragment = new TechFragment();
        } else if (menuItem.getItemId() == R.id.mileage) {
            fragment = new MileageFragment();
        } else if (menuItem.getItemId() == R.id.settings) {
            fragment = new SettingsFragment();
        }
        fragmentTransaction.replace(R.id.container_fragment, fragment).commit();
        this.id = menuItem.getItemId();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.editItem) {
            car = databaseHandler.getCarById(idAuto);
            EditAuto editAuto = new EditAuto();
            bundle.putInt("spinnerItemId", car.getId());
            bundle.putString("autoName", car.getAutoName());
            bundle.putString("autoAbout", car.getAutoAbout());
            editAuto.setArguments(bundle);
            editAuto.show(getSupportFragmentManager(), "editAuto");
        } else if (menuItem.getItemId() == R.id.deleteItem) {
            databaseHandler.deleteAuto(idAuto);
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            finish();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof MainFragment) {
//            super.onBackPressed();
        }
        showHome();
    }

    public void setSpinnerInfo() {
        List<Car> carList = databaseHandler.getCarList();
        ArrayAdapter<Car> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainFragment mainFragment = new MainFragment();
                Car car = (Car) spinner.getItemAtPosition(position);
                idAuto = car.getId();
                bundle.putInt("spinnerItemId", car.getId());
                mainFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, mainFragment).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void showHome() {
        fragment = new MainFragment();
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment, fragment.getTag()).commit();
        }
    }

    public void setDate(View v) {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_fragment);
        if (f instanceof MaintenanceFragment) {
            dateMaintenance(c, sdf);
        } else if (f instanceof ServiceFragment) {
            dateService(c, sdf);
        } else if (f instanceof InsuranceFragment) {
            dateInsurance(c, sdf);
        } else if (f instanceof TechFragment) {
            dateTech(c, sdf);
        }
    }

    public void dateTech(Calendar c, SimpleDateFormat sdf) {
        EditText techFrom = findViewById(R.id.techFromDate);
        TextView techTo = findViewById(R.id.techToDate);
        int timePeriod = 24;

        String techFromString = getDateString(c, sdf);
        String techToString = addTime(sdf, techFromString, timePeriod);

        techFrom.setText(techFromString);
        techTo.setText(techToString);
    }

    public void dateInsurance(Calendar c, SimpleDateFormat sdf) {
        EditText insuranceFrom = findViewById(R.id.insuranceFromDate);
        TextView insuranceTo = findViewById(R.id.insuranceToDate);
        EditText period = findViewById(R.id.insuranceTimeInterval);

        int timePeriod;
        if (!period.getText().toString().matches("")) {
            timePeriod = Integer.parseInt(period.getText().toString());
        } else timePeriod = 0;

        String insuranceFromString = getDateString(c, sdf);
        String insuranceToString = addTime(sdf, insuranceFromString, timePeriod);

        insuranceFrom.setText(insuranceFromString);
        insuranceTo.setText(insuranceToString);
    }

    public void dateService(Calendar c, SimpleDateFormat sdf) {
        EditText serviceDate = findViewById(R.id.serviceDate);
        String serviceDateString = getDateString(c, sdf);
        serviceDate.setText(serviceDateString);
    }

    private void dateMaintenance(Calendar c, SimpleDateFormat sdf) {
        EditText maintenanceDate = findViewById(R.id.maintenanceDate);
        String maintenanceDateString = getDateString(c, sdf);
        maintenanceDate.setText(maintenanceDateString);
    }

    private String getDateString(Calendar c, SimpleDateFormat sdf) {
        Date currentDate = c.getTime();
        return sdf.format(currentDate);
    }

    private String addTime(SimpleDateFormat sdf, String techFromString, int timePeriod) {
        Date date = convertString2Date(techFromString);
        Date date1 = addMonth(date, timePeriod);
        return sdf.format(date1);
    }
}
