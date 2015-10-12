package com.nrg.lau.render;
public class DataSource
{
  private String name;
  private String driver;
  private String url;
  private String password;
  private String userName;

  public DataSource(String name, String driver, String url, String userName, String password)
  {
    this.name = name;
    this.driver = driver;
    this.url = url;
    this.userName = userName;
    this.password = password;
  }

  public String getName()
  {
    return name;
  }

  public String getDriver()
  {
    return driver;
  }

  public String getURL()
  {
    return url;
  }

  public String getPassword()
  {
    return password;
  }

  public String getUserName()
  {
    return userName;
  }
  
  public String toString() {
      return("name="+this.name + " & driver=" +  this.driver + " & url=" +  this.url + " & password=" +  this.password + " & userName=" +  this.userName);
  }
  
}