package ro.lemacons.worksite_management;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class FragmentSantierMateriale extends Fragment {

    View view;
    private RecyclerView myrecyclerview;
    private List<SantierMaterialeModel> lstMateriale;

    ParseContent parseContent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parseContent = new ParseContent((SantierActivity)getActivity());



//        lstMateriale = new ArrayList<>();
//        lstMateriale.add(new SantierMaterialeModel("Sort","500", "to"));
//        lstMateriale.add(new SantierMaterialeModel("Piatra sparta","2000", "to"));
    }

    public FragmentSantierMateriale() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.santier_materiale_fragment, container,false);
        myrecyclerview = view.findViewById(R.id.materiale_recyclerview_id);

        parseJson();

 //       SantierMaterialeAdapter recycleradapter = new SantierMaterialeAdapter(getContext(),lstMateriale);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
//        myrecyclerview.setAdapter(recycleradapter);
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    void parseJson() {

        if (!AndyUtils.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), "Internet is required!", Toast.LENGTH_LONG).show();
            return;
        }

        AndyUtils.showSimpleProgressDialog(getActivity());

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response;
                HashMap<String, String> map=new HashMap<>();
                map.put("GUID", ((SantierActivity)getActivity()).getuniqueID());
                map.put("page", "Santier");
                map.put("nume_santier", ((SantierActivity)getActivity()).getNume_santier());

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
                Log.d("json_santier",result);

                if (parseContent.isSuccess(result)) {
                    AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog
                    lstMateriale = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray dataArray = jsonObject.getJSONArray("materiale");

                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject dataobj = dataArray.getJSONObject(i);
                            Log.d("json_obj", dataobj.toString());
                            lstMateriale.add(new SantierMaterialeModel(
                                    dataobj.getString("nume"),
                                    dataobj.getString("cantitate"),
                                    dataobj.getString("um"))
                            );
                        }
                        SantierMaterialeAdapter recycleradapter = new SantierMaterialeAdapter(getContext(),lstMateriale);
                        myrecyclerview.setAdapter(recycleradapter);

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog
                    Toast.makeText(getActivity(), parseContent.getErrorCode(result), Toast.LENGTH_LONG).show();
                }

            }
        }.execute();
    }
}
