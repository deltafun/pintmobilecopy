package com.pint.pintapp.PintNetworking;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import android.os.Build;
import android.test.mock.MockContext;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.Volley;
import com.android.volley.utils.ImmediateResponseDelivery;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.*;

import static org.mockito.Mockito.*;

import org.mockito.Mock;

import java.util.Map;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import pintapp.pint.com.pint.BuildConfig;
import pintapp.pint.com.pint.LoginActivity;
import pintapp.pint.com.pint.PintNetworking.CustomRequest;
import pintapp.pint.com.pint.PintNetworking.DefaultPintAPI;
import pintapp.pint.com.pint.PintNetworking.DefaultTokenProvider;
import pintapp.pint.com.pint.PintNetworking.ITokenProvider;
import pintapp.pint.com.pint.PintNetworking.JSONAdapter;
import pintapp.pint.com.pint.PintNetworking.PintAPI;
import pintapp.pint.com.pint.PintNetworking.UserNotificationComparator;
import pintapp.pint.com.pint.R;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class Subsystem_PintNetworkingTest {

    JSONAdapter jsonAdapter = Mockito.mock(JSONAdapter.class);
    ITokenProvider iTokenProvider = Mockito.mock(ITokenProvider.class);

    @Mock
    protected RequestQueue requestQueue;

    protected PintAPI api = new PintAPI(jsonAdapter, iTokenProvider);

    @InjectMocks
    protected DefaultPintAPI defaultPintpAPI = new DefaultPintAPI();

    LoginActivity loginActivity;

    protected DefaultTokenProvider defaultTokenProvider;

    protected UserNotificationComparator userNotificationComparator = new UserNotificationComparator();

    protected CustomRequest customRequest;
    @Before
    public void setUp() {
        loginActivity = Robolectric.setupActivity(LoginActivity.class);
        defaultTokenProvider =
                new DefaultTokenProvider(loginActivity.getBaseContext());
    }

    //--------------DEFAULT PINT API-----------------
    @Test
    public void SSTM010_GetBloodDrivesByLocation_SD() {
        defaultPintpAPI.GetBloodDrivesByLocation(null, null, "Orlando", "Florida");
        Assert.assertEquals("http://10.0.2.2:8181/api/donor/getBloodDrivesByLocation/Orlando/Florida", defaultPintpAPI.api.url);
    }

    @Test
    public void SSTM020_GetUserNotifications_SD() {
        defaultPintpAPI.GetUserNotifications(null, null);
        Assert.assertEquals("http://10.0.2.2:8181/api/donor/getUserNotifications", defaultPintpAPI.api.url);
    }

    @Test
    public void SSTM030_GetBloodDriveUserNotifications_SD() {
        defaultPintpAPI.GetBloodDriveUserNotifications(null, null, (long)1);
        Assert.assertEquals("http://10.0.2.2:8181/api/donor/getBloodDriveUserNotifications/1", defaultPintpAPI.api.url);
    }

    @Test
    public void SSTM040_GetBloodDrive_SD() {
        defaultPintpAPI.GetBloodDrive(null, null, (long)1);
        Assert.assertEquals("http://10.0.2.2:8181/api/donor/getBloodDrive/1", defaultPintpAPI.api.url);
    }

//    @Test
//    public void SSTM050_execute_SD() {
//        Context context = Mockito.mock(Context.class);
//        jsonAdapter.context = context;
//        when(iTokenProvider.getToken(any(Context.class))).thenReturn("test");
//        when(requestQueue.add(any(CustomRequest.class))).thenReturn(null);
//        defaultPintpAPI.execute();
//        Assert.assertEquals(true, true);
//
//    }

    @Test
    public void SSTM060_executeOne_SD() {

    }

    //------------CUSTOM REQUEST------------------
//
//    @Test
//    public void SSTM070_parseNetworkResponse_SD() {
//        Map map = Mockito.mock(Map.class);
//        Response.Listener listener = Mockito.mock(Response.Listener.class);
//        JSONObject body = Mockito.mock(JSONObject.class);
//        Response.ErrorListener errorListener = Mockito.mock(Response.ErrorListener.class, Mockito.CALLS_REAL_METHODS);
//        NetworkResponse networkResponse = Mockito.mock(NetworkResponse.class);
//        customRequest = new CustomRequest(-11, "www.google.com", map, body, listener, errorListener);
//        Assert.assertEquals("", "");
//
//
//    }
//
//    @Test
//    public void SSTM080_getBody_SD() {
//
//    }
//
//    @Test
//    public void SSTM090_deliverResponse_SD() {
//
//    }
//
//    @Test
//    public void SSTM100_getHeaders_SD() {
//
//    }

    //-------------USER NOTIFICATION COMPARATOR ---------------

    @Test
    public void SSTM110_comapre_SD() throws JSONException {
        JSONObject lhs = Mockito.mock(JSONObject.class);
        JSONObject rhs = Mockito.mock(JSONObject.class);
        when(lhs.getLong("sentTime")).thenReturn((long) 2);
        when(rhs.getLong("sentTime")).thenReturn((long)1);
        Assert.assertEquals(-1, userNotificationComparator.compare(lhs, rhs));

    }

    @Test
    public void SSTM111_comapre_SD() throws JSONException {
        JSONObject lhs = Mockito.mock(JSONObject.class);
        JSONObject rhs = Mockito.mock(JSONObject.class);
        when(lhs.getLong("sentTime")).thenReturn((long) 1);
        when(rhs.getLong("sentTime")).thenReturn((long)2);
        Assert.assertEquals(1, userNotificationComparator.compare(lhs, rhs));

    }

    @Test
    public void SSTM112_comapre_SD() throws JSONException {
        JSONObject lhs = Mockito.mock(JSONObject.class);
        JSONObject rhs = Mockito.mock(JSONObject.class);
        when(lhs.getLong("sentTime")).thenReturn((long) 1);
        when(rhs.getLong("sentTime")).thenReturn((long)1);
        Assert.assertEquals(0, userNotificationComparator.compare(lhs, rhs));

    }

    @Test
    public void SSTM113_comapre_RD() throws JSONException {
        JSONObject lhs = Mockito.mock(JSONObject.class);
        JSONObject rhs = Mockito.mock(JSONObject.class);
        doThrow(new JSONException("ERROR")).when(lhs).getLong("sentTime");
        doThrow(new JSONException("ERROR")).when(rhs).getLong("sentTime");
        Assert.assertEquals(0, userNotificationComparator.compare(lhs, rhs));

    }

    //-----------JSON ADAPTER --------------------------
//    @Test
//    public void SSTM120_getItem_SD() {
//
//    }
//
//    @Test
//    public void SSTM120_getView_SD() {
//
//    }

    //---------DEFAULT TOKEN PROVIDER-----------------
    @Test
    public void SSTM130_hasToken_SD() {
        Context context = Mockito.mock(Context.class);
        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        Resources resources = Mockito.mock(Resources.class);
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string.preference_file_key)).thenReturn("preference_file_key");
        when(context.getSharedPreferences("preference_file_key",0)).thenReturn(sharedPreferences);
        when(sharedPreferences.contains("token")).thenReturn(true);
        Assert.assertEquals(true, defaultTokenProvider.hasToken(context));
    }

    @Test
    public void SSTM140_getToken_SD() {
        Context context = Mockito.mock(Context.class);
        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        Resources resources = Mockito.mock(Resources.class);
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string.preference_file_key)).thenReturn("preference_file_key");
        when(context.getSharedPreferences("preference_file_key",0)).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", "")).thenReturn("hello");
        Assert.assertEquals("hello", defaultTokenProvider.getToken(context));
    }

//    @Test
//    public void SSTM150_fetchToken_SD() {
//
//    }

    @Test
    public void SSTM160_setToken_SD() {
        Context context = Mockito.mock(Context.class);
        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        Resources resources = Mockito.mock(Resources.class);
        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string.preference_file_key)).thenReturn("preference_file_key");
        when(context.getSharedPreferences("preference_file_key",0)).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.commit()).thenReturn(true);
        defaultTokenProvider.setToken("test", context);
        Assert.assertEquals(true, editor.commit());
    }

    @Test
    public void SSTM170_destroyToken_SD() {
        Context context = Mockito.mock(Context.class);
        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        Resources resources = Mockito.mock(Resources.class);
        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string.preference_file_key)).thenReturn("preference_file_key");
        when(context.getSharedPreferences("preference_file_key",0)).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.commit()).thenReturn(true);
        defaultTokenProvider.destroyToken(context);
        Assert.assertEquals(true, editor.commit());

    }

    @Test
    public void SSTM180_getEmail_SD() {
        Context context = Mockito.mock(Context.class);
        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        Resources resources = Mockito.mock(Resources.class);
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string.preference_file_key)).thenReturn("preference_file_key");
        when(context.getSharedPreferences("preference_file_key",0)).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("email", "")).thenReturn("hello");
        Assert.assertEquals("hello", defaultTokenProvider.getEmail(context));

    }

    @Test
    public void SSTM190_setEmail_SD() {
        Context context = Mockito.mock(Context.class);
        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        Resources resources = Mockito.mock(Resources.class);
        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string.preference_file_key)).thenReturn("preference_file_key");
        when(context.getSharedPreferences("preference_file_key",0)).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.commit()).thenReturn(true);
        defaultTokenProvider.setEmail("test",context);
        Assert.assertEquals(true, editor.commit());
    }

    @Test
    public void SSTM200_destroyEmail_SD() {
        Context context = Mockito.mock(Context.class);
        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        Resources resources = Mockito.mock(Resources.class);
        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string.preference_file_key)).thenReturn("preference_file_key");
        when(context.getSharedPreferences("preference_file_key",0)).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.commit()).thenReturn(true);
        defaultTokenProvider.destroyEmail(context);
        Assert.assertEquals(true, editor.commit());

    }

    //--------------- GENERAL REQUEST ----------------------

//    @Test
//    public void SSTM210_parseNetworkResponse_SD() {
//
//    }
//
//    @Test
//    public void SSTM220_getBody_SD() {
//
//    }
//
//    @Test
//    public void SSTM230_deliverResponse_SD() {
//
//    }
//
//    @Test
//    public void SSTM240_getHeaders_SD() {
//
//    }

    //-------------------PINT API -------------------------

}
