package ro.lemacons.worksite_management;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentSantierMateriale extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    String guid;
    SwipeRefreshLayout swipeLayout;
    ParseContent parseContent;
    private RecyclerView myrecyclerview;
    private List<SantierMaterialeModel> lstMateriale = new ArrayList<>();
    private String nume_santier;


    public FragmentSantierMateriale() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //preia date de la SantierActivity
        String txtJson = getArguments().getString("materiale");
        guid = getArguments().getString("uniqueId");
        nume_santier = getArguments().getString("nume_santier");

        parseContent = new ParseContent(getActivity());

        setAdapter(txtJson);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.santier_materiale_fragment, container,false);
        myrecyclerview = view.findViewById(R.id.materiale_recyclerview_id);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        SantierMaterialeAdapter recycleradapter = new SantierMaterialeAdapter(getContext(),lstMateriale);
        myrecyclerview.setAdapter(recycleradapter);

        swipeLayout = view.findViewById(R.id.swipe_container_materiale);
        swipeLayout.setOnRefreshListener(this);

        return view;
    }

    private void setAdapter(String jsonMateriale){
        lstMateriale.clear();
        try {

            JSONObject dataobj = new JSONObject(jsonMateriale);
            JSONArray dataArray = dataobj.getJSONArray("materiale");

            for (int i = 0; i < dataArray.length(); i++) {

                JSONObject dataobjarray = dataArray.getJSONObject(i);
                lstMateriale.add(new SantierMaterialeModel(
                        dataobjarray.getString("nume"),
                        dataobjarray.getInt("cantitate"),
                        dataobjarray.getInt("cant_nec"),
                        dataobjarray.getString("um"))
                );
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onRefresh() {
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response;
                HashMap<String, String> map=new HashMap<>();
                map.put("GUID", guid);
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
            }
        }.execute();
    }

    private void onTaskCompleted(String response) {
        setAdapter(response);

        myrecyclerview.getAdapter().notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
    }


    private void AddMaterial(){
        Toast.makeText(getActivity(), "Hi, ", Toast.LENGTH_SHORT).show();

    }



}
