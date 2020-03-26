package ml.chiragkhandhar.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class OfficialActivity extends AppCompatActivity
{
    private TextView title, name, party, address, email, url, phone, location;
    private ImageView dp, partyLogo;
    private static final String TAG = "OfficialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);
        Log.d(TAG, "bp: onCreate: Entered Official Activity");
        setupComponents();
        setUpLocation();
        fillData();

    }

    void setupComponents()
    {
        Log.d(TAG, "bp: setupComponents: Setting up Components...");
        location = findViewById(R.id.location);
        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        party = findViewById(R.id.party);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        url = findViewById(R.id.url);
        phone = findViewById(R.id.phone);
        dp = findViewById(R.id.dp);
        partyLogo = findViewById(R.id.partyLogo);
        Log.d(TAG, "bp: setupComponents: Components set up done.");
    }

    void setUpLocation()
    {
        Log.d(TAG, "bp: setUpLocation: Setting up location..");
        if(getIntent().hasExtra("location"))
            location.setText(getIntent().getStringExtra("location"));
        else
            location.setText("");
        Log.d(TAG, "bp: setUpLocation: Location setup done.");
    }

    void fillData()
    {
        if(getIntent().hasExtra("official"))
        {
            Official temp = (Official) getIntent().getSerializableExtra("official");
            title.setText(temp.getTitle());
            name.setText(temp.getName());
            party.setText(temp.getParty());
            address.setText(temp.getAddress());
            email.setText(temp.getEmails());
            url.setText(temp.getUrls());
            phone.setText(temp.getPhones());

            if(temp.getParty().trim().toLowerCase().contains("democratic"))
                partyLogo.setImageResource(R.drawable.dem_logo);
            else if(temp.getParty().trim().toLowerCase().contains("republican"))
                partyLogo.setImageResource(R.drawable.rep_logo);
            else
                partyLogo.setImageResource(R.drawable.default_party);
        }
    }
}
