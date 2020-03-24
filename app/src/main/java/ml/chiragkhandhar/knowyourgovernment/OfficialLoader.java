package ml.chiragkhandhar.knowyourgovernment;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OfficialLoader extends AsyncTask<String,Void, ArrayList<Official>>
{
    private MainActivity mainActivity;
    private final String API_TOKEN = "AIzaSyA7D-yk8Cue5NK8PU6jjAW1Df4l70akgAw";
    private static final String TAG = "OfficialLoader";

    public OfficialLoader(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Override
    protected ArrayList<Official> doInBackground(String... strings)
    {
        ArrayList<Official> finalData;
        String CT_ST_ZP = strings[0];

        String DATA_URL = "https://www.googleapis.com/civicinfo/v2/representatives?key="+API_TOKEN+"&address="+CT_ST_ZP;


        String data = getOfficialDatafromURL(DATA_URL);
        finalData = parseJSON(data);

        return finalData;
    }



    private String getOfficialDatafromURL(String URL)
    {
        Uri dataUri = Uri.parse(URL);
        String urlToUse = dataUri.toString();

        StringBuilder sb = new StringBuilder();
        try
        {
            java.net.URL url = new URL(urlToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line).append('\n');
        }
        catch (Exception e)
        {
            Log.e(TAG, "ERROR | OfficialLoader: getOfficialDatafromURL: bp:", e);
            return null;
        }
        return sb.toString();
    }

    private ArrayList<Official> parseJSON(String data)
    {
        ArrayList<Official> tempList = new ArrayList<>();
        setUpLocation(data);
        Official official;

        try
        {
            JSONObject temp = new JSONObject(data);
            JSONArray offices = (JSONArray) temp.get("offices");
            JSONArray officials = (JSONArray) temp.get("officials");
            Log.d(TAG, "parseJSON: bp: Length of Array: "+ offices.length());

            for(int i = 0; i<offices.length(); i++)
            {
                JSONObject office = (JSONObject) offices.get(i);
                JSONObject officialIndices = (JSONObject) offices.get(i);
                JSONArray index = officialIndices.getJSONArray("officialIndices");

                for(int j = 0; j< index.length(); j++)
                {
                    Official official_intermediate;
                    JSONObject officialData_JSON = (JSONObject) officials.get(index.getInt(j)) ;
                    official_intermediate = fetchOfficialDetails(officialData_JSON);
                    official = official_intermediate;
                    official.setTitle(office.getString("name"));                    // Setting Title here because the above statement would nake the title field of official object to null string
                    tempList.add(official);
                }
            }
        }
        catch (Exception e)
        {
            Log.d(TAG, "EXCEPTION | parseJSON: bp: " + e);
        }
        return  tempList;
    }

    private Official fetchOfficialDetails(JSONObject officialData_JSON)
    {
        Official temp = new Official();

        temp.setName(getNameFromData(officialData_JSON));


        return temp;
    }

    private String getNameFromData(JSONObject officialData_json)
    {
        String name = "";
        try
        {
            name = officialData_json.getString("name");
        }
        catch (Exception e)
        {
            Log.d(TAG, "EXCEPTION | fetchOfficialDetails: bp: " + e);
        }

        return name;
    }

    private void setUpLocation(String data)
    {
        TextView location = mainActivity.findViewById(R.id.location);
        try
        {
            JSONObject normalizedInput = new JSONObject(data);
            normalizedInput = normalizedInput.getJSONObject("normalizedInput");
            String city = normalizedInput.getString("city");
            String state = normalizedInput.getString("state");
            String zip = normalizedInput.getString("zip");
            String locationText = city + ", " + state + ", " + zip;
            location.setText(locationText);
        }
        catch (Exception e)
        {
            Log.d(TAG, "EXCEPTION | parseJSON: bp:" + e);
        }
    }



    @Override
    protected void onPostExecute(ArrayList<Official> officials)
    {
        mainActivity.updateOfficialData(officials);
        super.onPostExecute(officials);
    }
}
