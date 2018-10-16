package ro.lemacons.worksite_management;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private ParseContent parseContent;
    private final int jsoncode = 1;
    private ListView listView;
    private AdapterView<?> parent;
    private View view;
    private int position;
    private long id;
    private Intent MyIntent;
    private String uniqueID;
    private SwipeRefreshLayout swiperefresh;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseContent = new ParseContent(this);
        listView = (ListView) findViewById(R.id.lv);

        MyIntent = new Intent(this, SantiereActivity.class);

        uniqueID = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.name);
                String text = textView.getText().toString();

                MyIntent.putExtra("santier", text.replace("Name: ", "") );
                MyIntent.putExtra("uniqueID", uniqueID);

                startActivity(MyIntent);

            }
        });

        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
            
        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                       // Log.i("Refresh", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        parseJson();
                    }
                }
        );

        parseJson();

    }

    @SuppressLint("StaticFieldLeak")
    void parseJson() {

        if (!AndyUtils.isNetworkAvailable(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "Internet is required!", Toast.LENGTH_LONG).show();
            swiperefresh.setRefreshing(false);
            return;
        }

        AndyUtils.showSimpleProgressDialog(MainActivity.this);

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response;
                HashMap<String, String> map=new HashMap<>();
                map.put("GUID", uniqueID);
                map.put("page", "Main");
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
                Log.d("json_main",result);
                swiperefresh.setRefreshing(false);
                onTaskCompleted(result,jsoncode);

            }
        }.execute();
    }

    private void onTaskCompleted(String response, int serviceCode) {
        //Log.d("responsejson", response);
        switch (serviceCode) {
            case jsoncode:
                if (parseContent.isSuccess(response)) {
                    AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog
                    ArrayList<SantierModel> santierModelArrayList = parseContent.getInfo(response);
                    CustomeAdapter customeAdapter = new CustomeAdapter(this, santierModelArrayList);
                    listView.setAdapter(customeAdapter);

                }else {
                    Toast.makeText(MainActivity.this, parseContent.getErrorCode(response), Toast.LENGTH_LONG).show();
                    AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog
                }
        }
    }



}
