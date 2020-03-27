package ml.chiragkhandhar.knowyourgovernment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OfficialActivity extends AppCompatActivity
{
    private ConstraintLayout cs, dc;
    private TextView title, name, party, address,addressLab, email, emailLab, url, urlLab, phone, phoneLab, location;
    private ImageView dp, partyLogo;
    private static final String TAG = "OfficialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);
        setupComponents();
        setUpLocation();
        fillData();
    }

    void setupComponents()
    {
        cs = findViewById(R.id.constrainedLayout);
        dc = findViewById(R.id.detailsCard);
        location = findViewById(R.id.location);
        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        party = findViewById(R.id.party);
        address = findViewById(R.id.address);
        addressLab = findViewById(R.id.addrressLabel);
        email = findViewById(R.id.email);
        emailLab = findViewById(R.id.emailLabel);
        url = findViewById(R.id.url);
        urlLab = findViewById(R.id.urlLabel);
        phone = findViewById(R.id.phone);
        phoneLab = findViewById(R.id.phoneLabel);
        dp = findViewById(R.id.dp);
        partyLogo = findViewById(R.id.partyLogo);
    }

    void setUpLocation()
    {
        if(getIntent().hasExtra("location"))
            location.setText(getIntent().getStringExtra("location"));
        else
            location.setText("");
    }

    void fillData()
    {
        if(getIntent().hasExtra("official"))
        {
            Official temp = (Official) getIntent().getSerializableExtra("official");
            title.setText(temp.getTitle());
            name.setText(temp.getName());
            party.setText(temp.getParty());
            if(!temp.getAddress().equals(""))
                address.setText(temp.getAddress());
            else
            {
                addressLab.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
            }

            if(!temp.getEmails().equals(""))
                email.setText(temp.getEmails());
            else
            {
                emailLab.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
            }

            if(!temp.getUrls().equals(""))
                url.setText(temp.getUrls());
            else
            {
                urlLab.setVisibility(View.GONE);
                url.setVisibility(View.GONE);
            }

            if(!phone.getUrls().equals(""))
                phone.setText(temp.getPhones());
            else
            {
                phoneLab.setVisibility(View.GONE);
                phone.setVisibility(View.GONE);
            }

            if(temp.getParty().trim().toLowerCase().contains("democratic"))
                setUpDemocraticTheme();
            else if(temp.getParty().trim().toLowerCase().contains("republican"))
                setUpRepublicanTheme();
            else
                setUpNonPartisanTheme();

            loadProfilePicture(temp.getPhotoURL().trim());
        }
    }

    void setUpDemocraticTheme()
    {
        location.setBackgroundResource(R.color.dark_blue);
        partyLogo.setImageResource(R.drawable.dem_logo);
        cs.setBackgroundResource(R.color.blue);
        dc.setBackgroundResource(R.drawable.dem_details_bg);
        getWindow().setNavigationBarColor(getColor(R.color.blue));
    }

    void setUpRepublicanTheme()
    {
        location.setBackgroundResource(R.color.dark_red);
        partyLogo.setImageResource(R.drawable.rep_logo);
        cs.setBackgroundResource(R.color.red);
        dc.setBackgroundResource(R.drawable.rep_details_bg);
        getWindow().setNavigationBarColor(getColor(R.color.red));
    }
    void setUpNonPartisanTheme()
    {
        location.setBackgroundResource(R.color.extra_dark_grey);
        partyLogo.setImageResource(R.drawable.default_party);
        cs.setBackgroundResource(R.color.dark_grey);
        dc.setBackgroundResource(R.drawable.np_details_bg);
        getWindow().setNavigationBarColor(getColor(R.color.dark_grey));
    }

    void loadProfilePicture(String URL)
    {
        Log.d(TAG, "bp: loadProfilePicture: URL: " + URL);
        Picasso picasso = new Picasso.Builder(this).build();

        if(URL.equals(""))
        {
            dp.setImageResource(R.drawable.default_dp);
        }
        else
        {
            picasso.load(URL)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder)
                    .into(dp);
        }
    }
}
