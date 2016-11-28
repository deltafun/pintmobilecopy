package com.pint.pintapp.PintNetworking;

import android.os.Build;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.LinkedHashMap;
import java.util.Map;

import pintapp.pint.com.pint.BuildConfig;
import pintapp.pint.com.pint.PintNetworking.CustomRequest;

/**
 * Created by stevefoo on 11/26/16.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public abstract class Subsystem_PintNetworkingRobo {

    protected CustomRequest customRequest;

    //Run all general and custom requests here
    @Test
    public void SSTM070_parseNetworkResponse_SD() {
        Map map = Mockito.mock(Map.class);
        Response.Listener listener = Mockito.mock(Response.Listener.class);
        JSONObject body = Mockito.mock(JSONObject.class);
        Response.ErrorListener errorListener = Mockito.mock(Response.ErrorListener.class, Mockito.CALLS_REAL_METHODS);
        NetworkResponse networkResponse = Mockito.mock(NetworkResponse.class, Mockito.CALLS_REAL_METHODS);
        customRequest = new CustomRequest(-1, "www.google.com", map, body, listener, errorListener);
        final Map obj=new LinkedHashMap<>();
        obj.put("name","foo");
//        networkResponse.headers = obj;
//        networkResponse.data = new byte[10];
        Assert.assertEquals("", customRequest.parseNetworkResponse(networkResponse).toString());


    }
}
