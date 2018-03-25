/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author taleb
 */
public class RequestHandler {

    public enum RequestType {
        POST, GET, PUT, DELETE
    }

    private final RequestType currentRequestType;
    private String url;
    private final Hashtable<String, String> properties;
    private final Hashtable<String, String> pathVars;

    public RequestHandler(String restServiceUrl, RequestType requestType) {
        this.url = restServiceUrl;
        this.currentRequestType = requestType;
        this.properties = new Hashtable<>();
        this.pathVars = new Hashtable<>();
    }

    public RequestHandler putPathVar(String pathVarName, String value) {
        this.pathVars.put(pathVarName, value);
        return this;
    }

    public RequestHandler putProperty(String propertyName, String value) {
        properties.put(propertyName, value);
        return this;
    }

    public String Request() throws MalformedURLException, ProtocolException, IOException {
        URL urlConnector = null;
        HttpURLConnection http;
        String sj = "";

        
        
        StringJoiner sjProperties = new StringJoiner("&");
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            sjProperties.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        StringJoiner sjVarPath = new StringJoiner("/");
        for (Map.Entry<String, String> entry : pathVars.entrySet()) {
            sjVarPath.add(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        sj = sjVarPath.toString() + "?" + sjProperties.toString();
        this.url += sj;
        System.out.println("url asked : \n"+this.url);
        urlConnector = new URL(this.url);

        http = (HttpURLConnection) urlConnector.openConnection();

        http.setDoInput(true);
        http.setDoOutput(false);
       // http.setDoOutput(true);
        http.setRequestMethod(this.currentRequestType.name()); // PUT is another valid option

       // byte[] out = sj.getBytes(StandardCharsets.UTF_8);
       // int length = out.length;

       // http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        //http.setRequestProperty("Method", "GET");
        http.connect();
        //OutputStream os = http.getOutputStream();
       // os.write(out); // envoi de requetes

        BufferedReader br;
        if (200 <= http.getResponseCode() && http.getResponseCode() <= 299) {
            br = new BufferedReader(new InputStreamReader(http.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        int output;
        while ((output = br.read()) != -1) {
            System.out.println(output);
            sb.append((char) output);
        }

        System.out.println(sb.toString());// reponse json

        return sb.toString();
    }

}
