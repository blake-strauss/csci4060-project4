package edu.uga.cs.countryquiz;

public class Quizzes {
    private long   id;
    private String date;
    private String result;

    public Quizzes( String date, String result) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.date = date;
        this.result = result;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
