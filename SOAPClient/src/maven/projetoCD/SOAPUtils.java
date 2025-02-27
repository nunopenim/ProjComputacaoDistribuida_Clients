package maven.projetoCD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SOAPUtils {
	//Monolytic functions
	public static String testFunction() {
		String functionName = "test";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:testResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            return webServiceResponse;
              
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
    
	public static boolean[] authenticateFunction(String uid, String pw) {
		String functionName = "authenticator";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                              "<arg0>" + uid + "</arg0>" +
                              "<arg1>" + pw + "</arg1>" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:authenticatorResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            boolean[] returnBool = new boolean[2];
            //System.out.println(webServiceResponse);
            if ("null".equals(webServiceResponse)) {
            	return null;
            }
            if ("truetrue".equals(webServiceResponse)) {
            	returnBool[0] = true;
            	returnBool[1] = true;
            }
            else if ("truefalse".equals(webServiceResponse)) {
            	returnBool[0] = true;
            	returnBool[1] = false;
            }
            else if ("falsetrue".equals(webServiceResponse)) {
            	returnBool[0] = false;
            	returnBool[1] = true;
            }
            else if ("falsefalse".equals(webServiceResponse)) {
            	returnBool[0] = false;
            	returnBool[1] = false;
            }
            else {
            	returnBool = null;
            }
            return returnBool;
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
	
	public static boolean voteFunction(String uid, String itemID) {
		String functionName = "votarEmItem";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                              "<arg0>" + uid + "</arg0>" +
                              "<arg1>" + itemID + "</arg1>" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:votarEmItemResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            boolean retBool = false;
            //System.out.println(webServiceResponse);
            if ("true".equals(webServiceResponse)) {
            	retBool = true;
            }
            return retBool;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
	
	public static String getVotingItem(String id) {
		String functionName = "getItem";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                              "<arg0>" + id + "</arg0>" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:getItemResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            return webServiceResponse;
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
	
	public static String listAllVotingStuff() {
		String functionName = "listarItens";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:listarItensResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            String[] divider = webServiceResponse.split(Pattern.quote("|"));
            String response = "";
            for (String s : divider) {
            	response += s + "\n";
            }
            return response;
              
        } 
        catch (Exception e) 
        {
            return null;
        }
    }

	public static String listResultados() {
		String functionName = "getResults";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:getResultsResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            String[] divider = webServiceResponse.split(Pattern.quote("|"));
            String response = "";
            for (String s : divider) {
            	response += s + "\n";
            }
            return response;
              
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
	
	public static String totalVotos() {
		String functionName = "obterTotalVotos";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:obterTotalVotosResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            return webServiceResponse;
              
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
	
	public static boolean jaVotou(String uid) {
		String functionName = "hasVoted";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                              "<arg0>" + uid + "</arg0>" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:hasVotedResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            boolean retBool = false;
            //System.out.println(webServiceResponse);
            if ("true".equals(webServiceResponse)) {
            	retBool = true;
            }
            return retBool;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }

	public static boolean add_user(String uid) {
		String functionName = "adduser";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                              "<arg0>" + uid + "</arg0>" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:adduserResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            boolean retBool = false;
            //System.out.println(webServiceResponse);
            if ("true".equals(webServiceResponse)) {
            	retBool = true;
            }
            return retBool;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
	
	public static boolean rm_user(String uid) {
		String functionName = "rmuser";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                              "<arg0>" + uid + "</arg0>" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:rmuserResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            boolean retBool = false;
            //System.out.println(webServiceResponse);
            if ("true".equals(webServiceResponse)) {
            	retBool = true;
            }
            return retBool;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
	
	public static boolean jaComecou() {
		String functionName = "hasStarted";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:hasStartedResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            boolean retBool = false;
            //System.out.println(webServiceResponse);
            if ("true".equals(webServiceResponse)) {
            	retBool = true;
            }
            return retBool;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
	
	public static boolean jaAcabou() {
		String functionName = "hasEnded";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:hasEndedResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            boolean retBool = false;
            //System.out.println(webServiceResponse);
            if ("true".equals(webServiceResponse)) {
            	retBool = true;
            }
            return retBool;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
	
	public static long startingTime() {
		String functionName = "startTime";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:startTimeResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            long returnNum = Long.parseLong(webServiceResponse);
            return returnNum;
        } 
        catch (Exception e) 
        {
            return -1;
        }
    }

	public static long duration() {
		String functionName = "sLength";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:sLengthResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            long returnNum = Long.parseLong(webServiceResponse);
            return returnNum;
        } 
        catch (Exception e) 
        {
            return -1;
        }
    }
	
	public static boolean setStart(long epoch) {
		String functionName = "setStartTime";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                              "<arg0>" + epoch + "</arg0>" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:setStartTimeResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            boolean retBool = false;
            //System.out.println(webServiceResponse);
            if ("true".equals(webServiceResponse)) {
            	retBool = true;
            }
            return retBool;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }

	public static boolean setLength(long seconds) {
		String functionName = "setLength";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                              "<arg0>" + seconds + "</arg0>" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:setLengthResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            boolean retBool = false;
            //System.out.println(webServiceResponse);
            if ("true".equals(webServiceResponse)) {
            	retBool = true;
            }
            return retBool;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
	
	public static String itemGanhador() {
		String functionName = "winningItem";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:winningItemResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            return webServiceResponse;
              
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
	
	public static String listAllUsers() {
		String functionName = "getAllUsers";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:getAllUsersResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            String[] divider = webServiceResponse.split(Pattern.quote("|"));
            String response = "";
            for (String s : divider) {
            	response += s + "\n";
            }
            return response;
              
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
	
	public static String listAllSessionUsers() {
		String functionName = "getAllUsersInSession";
        String wsURL = "http://localhost:8080/TomcatFrontendSOAP/services/frontend?wsdl";
        URL url = null;
        URLConnection connection = null;
        HttpURLConnection httpConn = null;
        String responseString = null;
        String outputString="";
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;
         
        String xmlInput =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hel=\"http://projetoCD.maven/\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>" +
                           "<hel:"+functionName+">" +
                              "<!--Optional:-->" +
                            "</hel:"+functionName+">" +
                        "</soapenv:Body>" +
                     "</soapenv:Envelope>";
         
        try
        {
            url = new URL(wsURL);
            connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
 
            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
 
            String SOAPAction = "";
            // Set the appropriate HTTP parameters.
             httpConn.setRequestProperty("Content-Length", String
                     .valueOf(buffer.length));
            httpConn.setRequestProperty("Content-Type",
                    "text/xml; charset=utf-8");
             
             
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out = httpConn.getOutputStream();
            out.write(buffer);
            out.close();
             
            // Read the response and write it to standard out.
            isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);
             
            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            // return outputString.toString();
             
            // Get the response from the web service call
            Document document = parseXmlFile(outputString);
             
            NodeList nodeLst = document.getElementsByTagName("ns2:getAllUsersInSessionResponse");
            String webServiceResponse = nodeLst.item(0).getTextContent();
            String[] divider = webServiceResponse.split(Pattern.quote("|"));
            String response = "";
            for (String s : divider) {
            	response += s + "\n";
            }
            return response;
              
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
	
	// Document Parses stuff
    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
             InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
