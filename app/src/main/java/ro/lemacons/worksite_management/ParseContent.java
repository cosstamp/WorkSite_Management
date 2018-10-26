package ro.lemacons.worksite_management;

import android.app.Activity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

class ParseContent {

    private final String KEY_SUCCESS = "status";

    ArrayList<HashMap<String, String>> arraylist;

    ParseContent(Activity activity) {
    }

   boolean isSuccess(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.optString(KEY_SUCCESS).equals("true");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

   String getErrorCode(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            String KEY_MSG = "message";
            return jsonObject.getString(KEY_MSG);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

   ArrayList<MainModel> getInfo(String response) {
       ArrayList<MainModel> mainModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString(KEY_SUCCESS).equals("true")) {

                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    MainModel mainModel = new MainModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    mainModel.setId(dataobj.getString(AndyConstants.Params.ID));
                    mainModel.setName(dataobj.getString(AndyConstants.Params.NAME));
                    mainModel.setBuget(dataobj.getString(AndyConstants.Params.BUGET));

                    mainModelArrayList.add(mainModel);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainModelArrayList;
    }

    /*ArrayList<SantierModel_Detaliu> get_detaliu_santier(String response) {
        ArrayList<SantierModel_Detaliu> SantierModel_DetaliuArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString(KEY_SUCCESS).equals("true")) {

                JSONArray dataArray = jsonObject.getJSONArray("Detaliu_Santier");

                for (int i = 0; i < dataArray.length(); i++) {
                    SantierModel_Detaliu SantierModel_Detaliu = new SantierModel_Detaliu();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    SantierModel_Detaliu.setDetaliuID(dataobj.getString(AndyConstants.Params.DetaliuID));
                    SantierModel_Detaliu.setNume_Material(dataobj.getString(AndyConstants.Params.Nume_Material));
                    SantierModel_Detaliu.setUM(dataobj.getString(AndyConstants.Params.UM));

                    SantierModel_DetaliuArrayList.add(SantierModel_Detaliu);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return SantierModel_DetaliuArrayList;
    }*/

}