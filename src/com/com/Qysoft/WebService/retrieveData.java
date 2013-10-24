package com.com.Qysoft.WebService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 13-9-16.
 */
public class retrieveData {
    private static final String NAMESPACE ="http://QySoftMES/";
    private static String URL = "http://198.168.0.145/wsMESForAndroid/Service.asmx";
    private static final String ACTNAME ="RetrieveData";
    private static final String ACTURL ="http://QySoftMES/RetrieveData";
    public static List<List<String>> RetrieveDetail(String sqlText)
    {
        try{
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.debug = true;
            SoapObject rpc = new SoapObject(NAMESPACE, ACTNAME);
            rpc.addProperty("sqlText", sqlText);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //envelope.bodyOut = ht;
            envelope.dotNet = false;
            envelope.setOutputSoapObject(rpc);

            ht.call(ACTURL, envelope);
            SoapObject detail =(SoapObject) envelope.bodyIn;
            Object oResult;//("RetrieveSalerDetailStringResult");
            oResult = (Object)detail.getProperty(0);
            String result = (String)oResult;
            JSONArray jsonArray = new JSONArray(result);
            List<List<String>> lists = new ArrayList<List<String>>();
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONArray json = jsonArray.getJSONArray(i);//.getJSONObject(i);
                List<String> list = new ArrayList<String>();
                for(int i2=0;i2<json.length();i2++)
                {
                    list.add(json.getString(i2));
                }
                lists.add(list);
            }
            return lists;
        }
        catch (Exception e) {
            return null;
        }
    }
}
