#Connecting Android apps to Dynamics CRM using SOAP ndpoint

Non .Net clients can access Microsoft Dynamics CRM business data using SOAP end point or Web API. In this tutorial we will learn how to perform SOAP authentication to Microsoft Dynamics CRM from Swift. We have made a sample application which sends WhoAmIRequest and CRM responds back with the logged in name.

--------

#Working

##ServiceHandler

This class is responsible to retrieve data from network request in this app.

##LocalStorage

It is a simple implemention of **SharedPreference** to store SOAP Header and Expire time.

##CRMAuth

This class contains CRM Authentication Envelop that is used to authenticate user with Dynamics CRM.

##CRMAuthHeader

This is simply a container class which holds the return values for authentication.

```java

public class CRMAuthHeader {

    private String header;

    private Date Expire;

    public CRMAuthHeader() {
    }

    public CRMAuthHeader(String header, Date expire) {
        this.header = header;
        Expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Date getExpire() {
        return Expire;
    }

    public void setExpire(Date expire) {
        Expire = expire;
    }
}
```

