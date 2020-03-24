package ml.chiragkhandhar.knowyourgovernment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener
{
    private RecyclerView rv;
    private TextView location;
    private ArrayList<Official> officialArrayList = new ArrayList<>();
    private OfficialAdapter officialAdapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComponents();

        officialAdapter = new OfficialAdapter(officialArrayList,this);
        rv.setAdapter(officialAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        if(networkChecker())
        {
            getOfficialData();
            new OfficialLoader(this).execute("Chicago");
        }
        else
        {
            noNetworkDialog(getString(R.string.networkErrorMsg1));
        }

    }

    private void getOfficialData()
    {
        ArrayList<Official> tempList = new ArrayList<>();

        String title1 = "U.S. Senator";
        String title2 = "U.S. Representative";
        String name1 = "Chirag Khandhar";
        String name2 = "Shloka Bhalgat";
        String party1 = "(Democratic Party)";
        String party2 = "(Republican Party)";

        for(int i = 0; i<10; i++)
        {
            Official temp1 = new Official();
            temp1.setTitle(title1);
            temp1.setName(name1);
            temp1.setParty(party1);
            tempList.add(temp1);

            Official temp2 = new Official();
            temp2.setTitle(title2);
            temp2.setName(name2);
            temp2.setParty(party2);
            tempList.add(temp2);
        }

        Log.d(TAG, "onCreate: bp: tempList: " + tempList);
//        updateOfficialData(tempList);
    }

    public void setupComponents()
    {
        rv = findViewById(R.id.recycler);
        location = findViewById(R.id.location);
    }

    public boolean networkChecker()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null)
            return false;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void noNetworkDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(R.drawable.ic_error);
        builder.setTitle(R.string.networkErrorTitle);
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateOfficialData(ArrayList<Official> tempList)
    {
        officialArrayList.clear();
        officialArrayList.addAll(tempList);
        Log.d(TAG, "updateOfficialData: bp: officialArrayList[0]: " + officialArrayList.get(0));
        officialAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.opt_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.search:
                if(networkChecker())
                {
                    search();
                }
                else
                    noNetworkDialog(getString(R.string.networkErrorMsg2));
                break;
            case R.id.about:
                // Some Stuff
                break;
            default:
                Toast.makeText(this,"Invalid Option",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void search()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(et);
        builder.setIcon(R.drawable.ic_search_accent);

        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(MainActivity.this, "Search Button Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(MainActivity.this, R.string.negativeBtn2, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setMessage(R.string.searchMsg);
        builder.setTitle(R.string.searchTitle);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view)
    {
        int position = rv.getChildAdapterPosition(view);
        Official temp = officialArrayList.get(position);

        // Do Stuff
        Toast.makeText(this, temp.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View view)
    {
        int position = rv.getChildAdapterPosition(view);
        Official temp = officialArrayList.get(position);

        // Dialog with a layout

        // Inflate the dialog's layout
        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams")
        final View v = inflater.inflate(R.layout.dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Please enter the values:");
//        builder.setTitle("Dialog Layout");

        // Set the inflated view to be the builder's view
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }
}
