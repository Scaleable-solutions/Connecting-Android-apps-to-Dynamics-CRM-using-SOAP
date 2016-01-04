package com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRM;

/**
 * Created by ajku-ajku on 31/12/2015.
 */
public class SOAPBodies {

    public static String WhoAmIBody() {
        StringBuilder xml = new StringBuilder();
        xml.append("<s:Body>");
        xml.append("<Execute xmlns=\"http://schemas.microsoft.com/xrm/2011/Contracts/Services\">");
        xml.append("<request i:type=\"c:WhoAmIRequest\" xmlns:b=\"http://schemas.microsoft.com/xrm/2011/Contracts\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:c=\"http://schemas.microsoft.com/crm/2011/Contracts\">");
        xml.append("<b:Parameters xmlns:d=\"http://schemas.datacontract.org/2004/07/System.Collections.Generic\"/>");
        xml.append("<b:RequestId i:nil=\"true\"/>");
        xml.append("<b:RequestName>WhoAmI</b:RequestName>");
        xml.append("</request>");
        xml.append("</Execute>");
        xml.append("</s:Body>");
        return xml.toString();
    }

    public static String UserInfoBody(String id) {
        StringBuilder xml = new StringBuilder();
        xml.append("<s:Body>");
        xml.append("<Execute xmlns=\"http://schemas.microsoft.com/xrm/2011/Contracts/Services\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">");
        xml.append("<request i:type=\"a:RetrieveRequest\" xmlns:a=\"http://schemas.microsoft.com/xrm/2011/Contracts\">");
        xml.append("<a:Parameters xmlns:b=\"http://schemas.datacontract.org/2004/07/System.Collections.Generic\">");
        xml.append("<a:KeyValuePairOfstringanyType>");
        xml.append("<b:key>Target</b:key>");
        xml.append("<b:value i:type=\"a:EntityReference\">");
        xml.append("<a:Id>" + id + "</a:Id>");
        xml.append("<a:LogicalName>systemuser</a:LogicalName>");
        xml.append("<a:Name i:nil=\"true\" />");
        xml.append("</b:value>");
        xml.append("</a:KeyValuePairOfstringanyType>");
        xml.append("<a:KeyValuePairOfstringanyType>");
        xml.append("<b:key>ColumnSet</b:key>");
        xml.append("<b:value i:type=\"a:ColumnSet\">");
        xml.append("<a:AllColumns>false</a:AllColumns>");
        xml.append("<a:Columns xmlns:c=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\">");
        xml.append("<c:string>firstname</c:string>");
        xml.append("<c:string>lastname</c:string>");
        xml.append("</a:Columns>");
        xml.append("</b:value>");
        xml.append("</a:KeyValuePairOfstringanyType>");
        xml.append("</a:Parameters>");
        xml.append("<a:RequestId i:nil=\"true\" />");
        xml.append("<a:RequestName>Retrieve</a:RequestName>");
        xml.append("</request>");
        xml.append("</Execute>");
        xml.append("</s:Body>");
        return xml.toString();
    }
}
