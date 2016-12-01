package com.pint.pintapp.PintNetworking;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.robolectric.shadows.RoboLayoutInflater;
import org.robolectric.shadows.ShadowView;
import org.robolectric.shadows.ShadowViewGroup;
import org.robolectric.shadows.httpclient.FakeHttp;
import pintapp.pint.com.pint.BuildConfig;
import pintapp.pint.com.pint.LoginActivity;
import pintapp.pint.com.pint.PintNetworking.*;
import pintapp.pint.com.pint.PintType;
import pintapp.pint.com.pint.R;

import static junit.framework.Assert.*;
import static org.awaitility.Awaitility.await;

/**
 * Created by stevefoo on 11/26/16.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class Subsystem_PintNetworkingRobo {

    private CustomRequest customRequest;
    private LoginActivity loginActivity;
    private Context loginContext;
    private ITokenProvider tokenProvider;
    private Application application;

    public static final String[] TABS = {"Blood Drives", "Notifications"};
    public static final String EMAIL = "Xuejiao@gmail.com";
    public static final String PASS = "Xuejiao12345";

    @Before
    public void setUp() throws Exception {
        FakeHttp.getFakeHttpLayer().interceptHttpRequests(true);
        login();
        application = RuntimeEnvironment.application;
    }

    @After
    public void tearDown() {
        tokenProvider = null;
    }

    // setUp helper
    private void login() throws Exception {
        if (tokenProvider == null) {
            loginActivity = Robolectric.setupActivity(LoginActivity.class);
            Button loginButton = (Button) loginActivity
                                    .findViewById(R.id.loginButtonLogin);
            loginContext = loginActivity.getBaseContext();
            loginActivity.onClick(loginButton);
        }

        Field tokenProviderField = loginActivity.getClass()
                                            .getDeclaredField("tokenProvider");
        tokenProviderField.setAccessible(true);

        tokenProvider = (ITokenProvider) tokenProviderField.get(loginActivity);
    }

    // DefaultTokenProvider ****************************************************

    @Test
    public void SS_01_HasToken_SD() {
        await().atLeast(3, TimeUnit.SECONDS);
        tokenProvider.setToken("token", loginContext);
        assertTrue(tokenProvider.hasToken(loginContext));
    }

    // CustomRequest ***********************************************************

    //Run all general and custom requests here
    @Test
    public void SS_02_getBody_SD() throws Exception {
        // Arrange
        // create mocks
        Response.Listener listener = Mockito.mock(Response.Listener.class);
        Response.ErrorListener errorListener = Mockito
                .mock(Response.ErrorListener.class, Mockito.CALLS_REAL_METHODS);

        // create NetworkResponse
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("1", "{foo}");

        // Act
        customRequest = new CustomRequest(-1, "www.google.com", headers, null,
                                            listener, errorListener);
        byte[] actualBody = customRequest.getBody();

        // Assert
        assertNull(actualBody);
    }

    @Test
    public void SS_03_deliverResponse_SD() throws Exception {
        // Arrange
        // create mocks
        Response.Listener listener = Mockito.mock(Response.Listener.class);
        Response.ErrorListener errorListener = Mockito
                .mock(Response.ErrorListener.class, Mockito.CALLS_REAL_METHODS);

        // create NetworkResponse
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("1", "{foo}");

        // get method
        Method deliverResponseMethod = CustomRequest.class
                    .getDeclaredMethod("deliverResponse", Object.class);
        deliverResponseMethod.setAccessible(true);

        // Act
        customRequest = new CustomRequest(-1, "www.google.com", headers, null,
                listener, errorListener);
        deliverResponseMethod.invoke(customRequest, listener);
    }

//    @Test
//    public void SS_04_parseNetworkResponse_SD() throws Exception {
//        // Arrange
//        // create mocks
//        Response.Listener listener = Mockito.mock(Response.Listener.class);
//        JSONObject body = Mockito.mock(JSONObject.class);
//        Response.ErrorListener errorListener = Mockito
//                .mock(Response.ErrorListener.class, Mockito.CALLS_REAL_METHODS);
//
//        // create NetworkResponse
//        byte[] data = new byte[25];
//        Map<String, String> headers = new LinkedHashMap<>();
//        headers.put("email", EMAIL);
//        NetworkResponse networkResponse = new NetworkResponse(data);
//
//        // Act
//        customRequest = new CustomRequest(-1, "www.google.com", headers, body,
//                listener, errorListener);
//        Response actual = customRequest.parseNetworkResponse(networkResponse);
//
//        // Assert
//        assertNull(actual);
//    }

    // GeneralRequest **********************************************************

    @Test
    public void SS_05_getBody_SD() throws Exception {
        // Arrange
        // create mocks
        Response.Listener listener = Mockito.mock(Response.Listener.class);
        Response.ErrorListener errorListener = Mockito
                .mock(Response.ErrorListener.class, Mockito.CALLS_REAL_METHODS);

        // create NetworkResponse
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("1", "{foo}");

        // Act
        GeneralRequest generalRequest = new GeneralRequest(-1, "www.google.com", headers, null,
                listener, errorListener);
        byte[] actualBody = generalRequest.getBody();

        // Assert
        assertNull(actualBody);
    }

    @Test
    public void SS_06_deliverResponse_SD() throws Exception {
        // Arrange
        // create mocks
        Response.Listener listener = Mockito.mock(Response.Listener.class);
        Response.ErrorListener errorListener = Mockito
                .mock(Response.ErrorListener.class, Mockito.CALLS_REAL_METHODS);

        // create NetworkResponse
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("1", "{foo}");

        // get method
        Method deliverResponseMethod = GeneralRequest.class
                .getDeclaredMethod("deliverResponse", Object.class);
        deliverResponseMethod.setAccessible(true);

        // Act
        GeneralRequest generalRequest = new GeneralRequest(-1, "www.google.com", headers, null,
                listener, errorListener);
        deliverResponseMethod.invoke(generalRequest, listener);
    }

//    @Test
//    public void SS_07_parseNetworkResponse_SD() throws Exception {
//        // Arrange
//        // create mocks
//        Response.Listener listener = Mockito.mock(Response.Listener.class);
//        JSONObject body = Mockito.mock(JSONObject.class);
//        Response.ErrorListener errorListener = Mockito
//                .mock(Response.ErrorListener.class, Mockito.CALLS_REAL_METHODS);
//
//        // create NetworkResponse
//        byte[] data = new byte[25];
//        Map<String, String> headers = new LinkedHashMap<>();
//        headers.put("email", EMAIL);
//        NetworkResponse networkResponse = new NetworkResponse(data);
//
//        // get method
//        Method parseNetworkResponseMethod = GeneralRequest.class
//                .getDeclaredMethod("parseNetworkResponse", NetworkResponse.class);
//        parseNetworkResponseMethod.setAccessible(true);
//
//        // Act
//        GeneralRequest generalRequest = new GeneralRequest(-1, "www.google.com", headers, body,
//                listener, errorListener);
//        Response actual = (Response) parseNetworkResponseMethod
//                .invoke(generalRequest, networkResponse);
//
//        // Assert
//        assertNull(actual);
//    }

    // JSONAdapter *************************************************************

    @Test
    public void SS_08_getItem_SD() {
        // Arrange
        // create JSONAdaper
        List<JSONObject> objects = new ArrayList<>();
        objects.add(new JSONObject());
        int pintType = PintType.BLOODDRIVE;
        JSONAdapter adapter = new JSONAdapter(loginContext, objects, pintType);
        // Act
        JSONObject actualItem = adapter.getItem(0);
        // Assert
        assertEquals(objects.get(0), actualItem);
    }

    @Test
    public void SS_09_getView_SD() throws Exception {
        // Arrange
        // create JSONAdaper
        List<JSONObject> objects = new ArrayList<>();
        JSONObject obj = new JSONObject();
        obj.put("title", "title");
        obj.put("description", "description");
        objects.add(obj);

        objects.add(new JSONObject());
        int pintType = PintType.BLOODDRIVE;
        JSONAdapter adapter = new JSONAdapter(loginContext, objects, pintType);

        // Act
        View actual = adapter.getView(0, null, null);
        assertNotNull(actual);
    }

    @Test
    public void SS_10_getView_SD() throws Exception {
        // Arrange
        // create JSONAdaper
        List<JSONObject> objects = new ArrayList<>();
        JSONObject obj = new JSONObject();
        obj.put("title", "title");
        obj.put("description", "description");
        obj.put("shortDescription", "shortDescription");
        obj.put("bloodDriveTitle", "bloodDriveTitle");
        obj.put("hasSeen", false);
        objects.add(obj);

        objects.add(new JSONObject());
        int pintType = PintType.BLOODDRIVEUSERNOTIFICATION;
        JSONAdapter adapter = new JSONAdapter(loginContext, objects, pintType);

        // Act
        View actual = adapter.getView(0, null, null);
        assertNotNull(actual);
    }

//    @Test
//    public void SS_11_getView_SD() throws Exception {
//        // Arrange
//        // create JSONAdaper
//        List<JSONObject> objects = new ArrayList<>();
//        JSONObject obj = new JSONObject();
//        obj.put("description", "description");
//        obj.put("shortDescription", "shortDescription");
//        obj.put("bloodDriveTitle", "bloodDriveTitle");
//        obj.put("hasSeen", false);
//        objects.add(obj);
//
//        objects.add(new JSONObject());
//        int pintType = PintType.BLOODDRIVEUSERNOTIFICATION;
//        JSONAdapter adapter = new JSONAdapter(loginContext, objects, pintType);
//
//        // Act
//        View actual = adapter.getView(0, null, null);
//        assertNotNull(actual);
//    }

//    @Test
//    public void SS_12_getView_SD() throws Exception {
//        // Arrange
//        // create JSONAdaper
//        List<JSONObject> objects = new ArrayList<>();
//        JSONObject obj = new JSONObject();
//        obj.put("description", "description");
//        obj.put("shortDescription", "shortDescription");
//        obj.put("bloodDriveTitle", "bloodDriveTitle");
//        obj.put("hasSeen", false);
//        objects.add(obj);
//
//        objects.add(new JSONObject());
//        int pintType = PintType.BLOODDRIVE;
//        JSONAdapter adapter = new JSONAdapter(loginContext, objects, pintType);
//
//        // Act
//        View actual = adapter.getView(0, null, null);
//        assertNotNull(actual);
//    }

    // DefaultPintAPI **********************************************************

    @Test
    public void SS_13_execute_SD() throws Exception {
        // Arrange
        // create JSONAdaper
        List<JSONObject> objects = new ArrayList<>();
        JSONObject obj = new JSONObject();
        obj.put("description", "description");
        obj.put("shortDescription", "shortDescription");
        obj.put("bloodDriveTitle", "bloodDriveTitle");
        obj.put("hasSeen", false);
        objects.add(obj);

        objects.add(new JSONObject());
        int pintType = PintType.BLOODDRIVE;
        JSONAdapter adapter = new JSONAdapter(loginContext, objects, pintType);
        // create defaultpintapi
        DefaultPintAPI api = new DefaultPintAPI();
        api.GetUserNotifications(adapter, tokenProvider);
        api.execute();
        api.executeOne();
    }

    @Test
    public void SS_14_execute_SD() throws Exception {
        // Arrange
        // create JSONAdaper
        List<JSONObject> objects = new ArrayList<>();
        JSONObject obj = new JSONObject();
        obj.put("description", "description");
        obj.put("shortDescription", "shortDescription");
        obj.put("bloodDriveTitle", "bloodDriveTitle");
        obj.put("hasSeen", false);
        objects.add(obj);

        objects.add(new JSONObject());
        int pintType = PintType.BLOODDRIVE;
        JSONAdapter adapter = new JSONAdapter(loginContext, objects, pintType);
        // create defaultpintapi
        DefaultPintAPI api = new DefaultPintAPI();
        api.GetUserNotifications(adapter, tokenProvider);
        api.executeOne();
    }
}
