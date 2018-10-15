package ro.lemacons.worksite_management;

import android.app.Activity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

class ParseContent {

    private final String KEY_SUCCESS = "status";
    private final String KEY_MSG = "message";
    private Activity activity;

    ArrayList<HashMap<String, String>> arraylist;

    ParseContent(Activity activity) {
        this.activity = activity;
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
            return jsonObject.getString(KEY_MSG);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

   ArrayList<PlayersModel> getInfo(String response) {
       ArrayList<PlayersModel> playersModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString(KEY_SUCCESS).equals("true")) {

                arraylist = new ArrayList<HashMap<String, String>>();
                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    PlayersModel playersModel = new PlayersModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    playersModel.setName(dataobj.getString(AndyConstants.Params.NAME));
                    playersModel.setCountry(dataobj.getString(AndyConstants.Params.COUNTRY));
                    playersModel.setCity(dataobj.getString(AndyConstants.Params.CITY));
                    playersModelArrayList.add(playersModel);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playersModelArrayList;
    }

}