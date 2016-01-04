package com.scaleablesolutions.crm_aurhentication_sample_using_soap.Helper;

import android.util.Log;

import com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRM.CRMAuth;
import com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRM.SOAPBodies;
import com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRM.CRMAuthHeader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by ajku-ajku on 31/12/2015.
 */


public class ServiceHandler {

    public CRMAuthHeader getAuthHeader(String url, String username, String password) {
        CRMAuth auth = new CRMAuth();
        String authEnvelope = auth.GetAuthEnvelopeOnline(url, username, password);
        String authURL = "https://login.microsoftonline.com/RST2.srf";

        String authResponse = PostRequest(authURL, authEnvelope);

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document x = null;
        try {
            x = builder.parse(new ByteArrayInputStream(authResponse.getBytes()));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NodeList cipherElements = x.getElementsByTagName("CipherValue");
        String token1 = cipherElements.item(0).getTextContent();
        String token2 = cipherElements.item(1).getTextContent();

        NodeList keyIdentiferElements = x
                .getElementsByTagName("wsse:KeyIdentifier");
        String keyIdentifer = keyIdentiferElements.item(0).getTextContent();

        NodeList tokenExpiresElements = x.getElementsByTagName("wsu:Expires");
        String tokenExpires = tokenExpiresElements.item(0).getTextContent();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Date date = null;
        try {
            date = format.parse(tokenExpires);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CRMAuthHeader authHeader = new CRMAuthHeader();
        authHeader.setExpire(date);
        authHeader.setHeader(auth.createSOAPHeader(url, keyIdentifer, token1, token2));
        return authHeader;
    }

    public String getUserId(String url, String header) {

        String completeURL = url + "/XRMServices/2011/Organization.svc";
        String body = SOAPBodies.WhoAmIBody();

        StringBuilder xml = new StringBuilder();
        xml.append("<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:a=\"http://www.w3.org/2005/08/addressing\">");
        xml.append(header);
        xml.append(body);
        xml.append("</s:Envelope>");

        String envepole = xml.toString();

        String response = PostRequest(completeURL, envepole);

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document x = null;
        try {
            x = builder.parse(new ByteArrayInputStream(response.getBytes()));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList nodes = x.getElementsByTagName("b:KeyValuePairOfstringanyType");
        for (int i = 0; i < nodes.getLength(); i++) {
            if ((nodes.item(i).getFirstChild().getTextContent())
                    .equals("UserId")) {
                return nodes.item(i).getLastChild().getTextContent();
            }
        }
        return "";
    }

    public String getUserInfo(String url, String header, String id) {
        String completeURL = url + "/XRMServices/2011/Organization.svc";
        String body = SOAPBodies.UserInfoBody(id);

        StringBuilder xml = new StringBuilder();
        xml.append("<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:a=\"http://www.w3.org/2005/08/addressing\">");
        xml.append(header);
        xml.append(body);
        xml.append("</s:Envelope>");

        String response = PostRequest(completeURL, xml.toString());

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document x = null;
        try {
            x = builder.parse(new ByteArrayInputStream(response.getBytes()));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String firstname = "";
        String lastname = "";
        NodeList nodes = x.getElementsByTagName("b:KeyValuePairOfstringanyType");
        for (int i = 0; i < nodes.getLength(); i++) {
            if ((nodes.item(i).getFirstChild().getTextContent())
                    .equals("firstname")) {
                firstname = nodes.item(i).getLastChild().getTextContent();
            }
            if ((nodes.item(i).getFirstChild().getTextContent())
                    .equals("lastname")) {
                lastname = nodes.item(i).getLastChild().getTextContent();
            }
        }

        return firstname + " " + lastname;
    }

    private String PostRequest(String destinationURL, String body) {
        try {
            URL LoginURL = new URL(destinationURL);
            HttpURLConnection rc = (HttpURLConnection) LoginURL.openConnection();

            rc.setRequestMethod("POST");
            rc.setDoOutput(true);
            rc.setDoInput(true);
            rc.setRequestProperty("Content-Type", "application/soap+xml; charset=UTF-8");
            String reqStr = body;
            int len = reqStr.length();
            rc.setRequestProperty("Content-Length", Integer.toString(len));
            rc.setRequestProperty("Connection", "Keep-Alive");
            rc.setRequestProperty("SOAPAction", "https://schemas.microsoft.com/xrm/2011/Contracts/Services/IOrganizationService/Execute");
            rc.connect();

            OutputStreamWriter out = new OutputStreamWriter(rc.getOutputStream());
            out.write(reqStr, 0, len);
            out.flush();

            int status = rc.getResponseCode();
            if (status != 200) {
                return "";
            }
            InputStreamReader read = new InputStreamReader(rc.getInputStream());
            StringBuilder sb = new StringBuilder();
            int ch = read.read();
            while (ch != -1) {
                sb.append((char) ch);
                ch = read.read();
            }
            String response = sb.toString();
            read.close();
            rc.disconnect();
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
