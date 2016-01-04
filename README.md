#Connecting Android apps to Dynamics CRM using SOAP endpoint

Non .Net clients can access Microsoft Dynamics CRM business data using SOAP end point or Web API. In this tutorial we will learn how to perform SOAP authentication to Microsoft Dynamics CRM from Android. We have made a sample application which sends WhoAmIRequest and CRM responds back with the logged in name.

--------

#Working

##ServiceHandler

This class is responsible to retrieve data from network request in this app.

##LocalStorage

It is a simple implementation of **SharedPreference** to store SOAP Header and Expire time.

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

##SOAPBodies

This class holds the SOAP bodies which is used in SOAP request inside SOAP Envelope as it contains the query that do some action(Create, Update, Delete and Retrieve etc.).

##CRMAuthuthenticationTask

This class simply make network call to authenticate the user with help of Authentication envelop from **CRMAuth** class and pass it to **ServiceHandler** class. This class takes these three parameters:
 
 1. Organization URL
 2. Username
 3. Password
 
And return **CRMAuthHeader** object.

##WhoAmITask

This class takes CRM SOAP Header and URL and returns User Id.

##UserInfoTask

This class takes CRM SOAP Header, URL and User Id and reruns logged in Name

-------

#Conclusion

The given sample is great starter app to perform authentication in Android apps. It gives a good overview of how you can make connection with CRM in external apps not developed in .NET. You can freely use this sample in your apps for authentication purposes.
