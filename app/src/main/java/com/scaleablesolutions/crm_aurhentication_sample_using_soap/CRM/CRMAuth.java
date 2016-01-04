package com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRM;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * Created by ajku-ajku on 31/12/2015.
 */
public class CRMAuth {

    public String GetAuthEnvelopeOnline(String url, String username, String password){

        String urnAddress = GetUrnOnline(url);
        Date now = new Date();

        StringBuilder xml = new StringBuilder();
        xml.append("<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:a=\"http://www.w3.org/2005/08/addressing\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">");
        xml.append("<s:Header>");
        xml.append("<a:Action s:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/02/trust/RST/Issue</a:Action>");
        xml.append("<a:MessageID>urn:uuid:" + java.util.UUID.randomUUID()
                + "</a:MessageID>");
        xml.append("<a:ReplyTo>");
        xml.append("<a:Address>http://www.w3.org/2005/08/addressing/anonymous</a:Address>");
        xml.append("</a:ReplyTo>");
        xml.append("<a:To s:mustUnderstand=\"1\">https://login.microsoftonline.com/RST2.srf</a:To>");
        xml.append("<o:Security s:mustUnderstand=\"1\" xmlns:o=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">");
        xml.append("<u:Timestamp u:Id=\"_0\">");
        xml.append("<u:Created>" + String.format("%tFT%<tT.%<tLZ", now)
                + "</u:Created>");
        xml.append("<u:Expires>"
                + String.format("%tFT%<tT.%<tLZ", AddMinutes(60, now))
                + "</u:Expires>");
        xml.append("</u:Timestamp>");
        xml.append("<o:UsernameToken u:Id=\"uuid-"
                + java.util.UUID.randomUUID() + "-1\">");
        xml.append("<o:Username>" + username + "</o:Username>");
        xml.append("<o:Password>" + password + "</o:Password>");
        xml.append("</o:UsernameToken>");
        xml.append("</o:Security>");
        xml.append("</s:Header>");
        xml.append("<s:Body>");
        xml.append("<trust:RequestSecurityToken xmlns:trust=\"http://schemas.xmlsoap.org/ws/2005/02/trust\">");
        xml.append("<wsp:AppliesTo xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\">");
        xml.append("<a:EndpointReference>");
        xml.append("<a:Address>urn:" + urnAddress + "</a:Address>");
        xml.append("</a:EndpointReference>");
        xml.append("</wsp:AppliesTo>");
        xml.append("<trust:RequestType>http://schemas.xmlsoap.org/ws/2005/02/trust/Issue</trust:RequestType>");
        xml.append("</trust:RequestSecurityToken>");
        xml.append("</s:Body>");
        xml.append("</s:Envelope>");

        return xml.toString();
    }

    public String createSOAPHeader(String url,String keyIdentifier, String token1, String token2){



        StringBuilder xml = new StringBuilder();
        xml.append("<s:Header>");
        xml.append("<a:Action s:mustUnderstand=\"1\">http://schemas.microsoft.com/xrm/2011/Contracts/Services/IOrganizationService/Execute</a:Action>");
        xml.append("<Security xmlns=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">");
        xml.append("<EncryptedData Id=\"Assertion0\" Type=\"http://www.w3.org/2001/04/xmlenc#Element\" xmlns=\"http://www.w3.org/2001/04/xmlenc#\">");
        xml.append("<EncryptionMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#tripledes-cbc\"/>");
        xml.append("<ds:KeyInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">");
        xml.append("<EncryptedKey>");
        xml.append("<EncryptionMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\"/>");
        xml.append("<ds:KeyInfo Id=\"keyinfo\">");
        xml.append("<wsse:SecurityTokenReference xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">");
        xml.append("<wsse:KeyIdentifier EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\" ValueType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier\">"
                + keyIdentifier + "</wsse:KeyIdentifier>");
        xml.append("</wsse:SecurityTokenReference>");
        xml.append("</ds:KeyInfo>");
        xml.append("<CipherData>");
        xml.append("<CipherValue>" + token1 + "</CipherValue>");
        xml.append("</CipherData>");
        xml.append("</EncryptedKey>");
        xml.append("</ds:KeyInfo>");
        xml.append("<CipherData>");
        xml.append("<CipherValue>" + token2 + "</CipherValue>");
        xml.append("</CipherData>");
        xml.append("</EncryptedData>");
        xml.append("</Security>");
        xml.append("<a:MessageID>urn:uuid:" + java.util.UUID.randomUUID()
                + "</a:MessageID>");
        xml.append("<a:ReplyTo>");
        xml.append("<a:Address>http://www.w3.org/2005/08/addressing/anonymous</a:Address>");
        xml.append("</a:ReplyTo>");
        xml.append("<a:To s:mustUnderstand=\"1\">" + url
                + "/XRMServices/2011/Organization.svc</a:To>");
        xml.append("</s:Header>");

        return xml.toString();
    }


    private String GetUrnOnline(String url) {
        if (url.toUpperCase().contains("CRM2.DYNAMICS.COM"))
            return "crmsam:dynamics.com";
        if (url.toUpperCase().contains("CRM4.DYNAMICS.COM"))
            return "crmemea:dynamics.com";
        if (url.toUpperCase().contains("CRM5.DYNAMICS.COM"))
            return "crmapac:dynamics.com";
        if (url.toUpperCase().contains("CRM6.DYNAMICS.COM"))
            return "crmoce:dynamics.com";
        if (url.toUpperCase().contains("CRM7.DYNAMICS.COM"))
            return "crmjpn:dynamics.com";
        if (url.toUpperCase().contains("CRM8.DYNAMICS.COM"))
            return "crmgcc:dynamics.com";

        return "crmna:dynamics.com";
    }

    private Date AddMinutes(int minutes, Date time) {
        long ONE_MINUTE_IN_MILLIS = 60000;
        long currentTime = time.getTime();
        Date newDate = new Date(currentTime + (minutes * ONE_MINUTE_IN_MILLIS));
        return newDate;
    }
}
