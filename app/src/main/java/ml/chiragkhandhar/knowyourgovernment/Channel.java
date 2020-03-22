package ml.chiragkhandhar.knowyourgovernment;

public class Channel
{
    private String type, id;

    public Channel(String type, String id)
    {
        this.type = type;
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public String getId()
    {
        return id;
    }
}
