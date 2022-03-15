package mobileapps.g483.bakhtuev.networkmaps;

public class Map {

    private String mName;
    private int Id;

    public Map(String name, int id)
    {
        mName = name;
        Id = id;
    }

    public String getName() {
        return mName;
    }
    public int getID() {
        return Id;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setLastId(int lastId) {
        this.Id = lastId;
    }
}