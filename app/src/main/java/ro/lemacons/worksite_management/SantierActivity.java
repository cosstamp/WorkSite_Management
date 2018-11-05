package ro.lemacons.worksite_management;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SantierActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    SantierViewAdapter santierViewAdapter;
    String uniqueID;
    String nume_santier;
    String jsonMateriale ;
    private ParseContent parseContent = new ParseContent(this);
    private SwipeRefreshLayout swiperefresh_materiale;

    @Override
    protected void onStart() {
        super.onStart();
    }

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
        tabLayout.setupWithViewPager(viewPager);

    }


    public String getuniqueID() {
        return uniqueID;
    }

    public String getNume_santier(){
        return nume_santier;
    }


    @SuppressLint("StaticFieldLeak")
    public void parseJson() {

        if (!AndyUtils.isNetworkAvailable(SantierActivity.this)) {
            Toast.makeText(SantierActivity.this, "Internet is required!", Toast.LENGTH_LONG).show();

            return;
        }

        AndyUtils.showSimpleProgressDialog(SantierActivity.this);

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response;
                HashMap<String, String> map=new HashMap<>();
                map.put("GUID", uniqueID);
                map.put("page", "Santier");
                map.put("nume_santier", nume_santier);
                try {
                    HttpRequest req = new HttpRequest(AndyConstants.ServiceType.URL);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }


            protected void onPostExecute(String result) {
                //do something with response
                onTaskCompleted(result);
                setFragments(jsonMateriale);
            }
        }.execute();
    }

    private void onTaskCompleted(String response) {
        //Log.d("responsejson", response);

        if (parseContent.isSuccess(response)) {
            AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                jsonMateriale = jsonObject.toString();

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(SantierActivity.this, parseContent.getErrorCode(response), Toast.LENGTH_LONG).show();
            AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog
        }
    }

    private void setFragments(String jsonMateriale){

        //Add new tabs
        Bundle bundle_materiale = new Bundle();
        bundle_materiale.putString("materiale", jsonMateriale);
        bundle_materiale.putString("uniqueId", uniqueID);
        bundle_materiale.putString("nume_santier", nume_santier);
        FragmentSantierMateriale fragmentSantierMateriale = new FragmentSantierMateriale();
        fragmentSantierMateriale.setArguments(bundle_materiale);
        santierViewAdapter.AddFragment(fragmentSantierMateriale, "Materiale");

        Bundle bundle_personal = new Bundle();
        bundle_personal.putString("personal","txt Json Personal");
        FragmentSantierPersonal fragmentSantierPersonal = new FragmentSantierPersonal();
        fragmentSantierPersonal.setArguments(bundle_personal);
        santierViewAdapter.AddFragment(fragmentSantierPersonal, "Personal");


        santierViewAdapter.AddFragment(new FragmentSantierTransport(), "Transport");
        santierViewAdapter.AddFragment(new FragmentSantierUtilaje(), "Utilaje");

        viewPager.setAdapter(santierViewAdapter);

    }

}
