package ml.chiragkhandhar.knowyourgovernment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener
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
            new OfficialLoader(this).execute("60616");
        }
        else
        {
            noNetworkDialog(getString(R.string.networkErrorMsg1));
        }

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
//        Log.d(TAG, "updateOfficialData: bp: officialArrayList[0]: " + officialArrayList.get(0));
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
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
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
                String searchString = et.getText().toString().trim();
                if(!searchString.equals(""))
                {
                    location.setText("");
                    new OfficialLoader(MainActivity.this).execute(searchString);
                }
                else
                {
                    Toast.makeText(MainActivity.this, R.string.nullSearchStringMsg, Toast.LENGTH_SHORT).show();
                    search();
                }

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

        Intent i = new Intent(this,OfficialActivity.class);
        i.putExtra("location", location.getText());
        i.putExtra("official",temp);
        startActivity(i);
    }

}
