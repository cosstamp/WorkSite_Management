package ro.lemacons.worksite_management;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.HashMap;

public class SantierActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    SantierViewAdapter santierViewAdapter;
    String uniqueID;
    String nume_santier;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santier);

        Intent intent = getIntent();
        uniqueID = intent.getStringExtra("uniqueID");
        nume_santier = intent.getStringExtra("santier");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nume_santier);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        santierViewAdapter = new SantierViewAdapter(getSupportFragmentManager());

        //Add new tabs
        santierViewAdapter.AddFragment(new FragmentSantierMateriale(), "Materiale");
        santierViewAdapter.AddFragment(new FragmentSantierPersonal(), "Personal");
        santierViewAdapter.AddFragment(new FragmentSantierTransport(), "Transport");
        santierViewAdapter.AddFragment(new FragmentSantierUtilaje(), "Utilaje");



        viewPager.setAdapter(santierViewAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }


    public String getuniqueID() {
        return uniqueID;
    }

    public String getNume_santier(){
        return nume_santier;
    }
}
