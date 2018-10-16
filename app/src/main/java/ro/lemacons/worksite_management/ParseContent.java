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

   ArrayList<SantierModel> getInfo(String response) {
       ArrayList<SantierModel> santierModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString(KEY_SUCCESS).equals("true")) {

                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    SantierModel santierModel = new SantierModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    santierModel.setId(dataobj.getString(AndyConstants.Params.ID));
                    santierModel.setName(dataobj.getString(AndyConstants.Params.NAME));
                    santierModel.setBuget(dataobj.getString(AndyConstants.Params.BUGET));

                    santierModelArrayList.add(santierModel);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return santierModelArrayList;
    }

}