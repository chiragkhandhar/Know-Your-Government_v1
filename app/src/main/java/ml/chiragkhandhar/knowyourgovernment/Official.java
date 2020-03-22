package ml.chiragkhandhar.knowyourgovernment;

import java.io.Serializable;
import java.util.ArrayList;

public class Official implements Serializable
{
    private String name, title;
    private String address_line1, address_line2, address_city, address_state, address_zip;
    private String party;
    private ArrayList<String> phones;
    private ArrayList<String> urls;
    private ArrayList<String> emails;
    private String photoURL;
    private ArrayList<Channel> channels;

    public Official()
    {
        name = "";
        title = "";
        address_line1 = "";
        address_line2 = "";
        address_city = "";
        address_state = "";
        address_zip = "";
        party = "";
        phones = new ArrayList<>();
        urls = new ArrayList<>();
        emails = new ArrayList<>();
        photoURL = "";
        channels = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAddress_line1()
    {
        return address_line1;
    }

    public String getAddress_line2()
    {
        return address_line2;
    }

    public String getAddress_city()
    {
        return address_city;
    }

    public String getAddress_state()
    {
        return address_state;
    }

    public String getAddress_zip()
    {
        return address_zip;
    }

    public String getParty() {
        return party;
    }

    public ArrayList<String> getPhones()
    {
        return phones;
    }

    public ArrayList<String> getUrls()
    {
        return urls;
    }

    public ArrayList<String> getEmails()
    {
        return emails;
    }

    public String getPhotoURL()
    {
        return photoURL;
    }

    public ArrayList<Channel> getChannels()
    {
        return channels;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setAddress_line1(String address_line1)
    {
        this.address_line1 = address_line1;
    }

    public void setAddress_line2(String address_line2)
    {
        this.address_line2 = address_line2;
    }

    public void setAddress_city(String address_city)
    {
        this.address_city = address_city;
    }

    public void setAddress_state(String address_state)
    {
        this.address_state = address_state;
    }

    public void setAddress_zip(String address_zip)
    {
        this.address_zip = address_zip;
    }

    public void setParty(String party)
    {
        this.party = party;
    }

    public void setPhones(ArrayList<String> phones)
    {
        this.phones = phones;
    }

    public void setUrls(ArrayList<String> urls)
    {
        this.urls = urls;
    }

    public void setEmails(ArrayList<String> emails)
    {
        this.emails = emails;
    }

    public void setPhotoURL(String photoURL)
    {
        this.photoURL = photoURL;
    }

    public void setChannels(ArrayList<Channel> channels)
    {
        this.channels = channels;
    }
}
