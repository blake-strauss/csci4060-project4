package edu.uga.cs.countryquiz;

public class Countries {
    private long   id;
    private String country;
    private String continent;

    public Countries() {
        this.id = -1;
        this.country = null;
        this.continent = null;
    }

    public Countries( String country, String continent) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.country = country;
        this.continent = continent;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getContinent()
    {
        return continent;
    }

    public void setContinent(String continent)
    {
        this.continent = continent;
    }
}
