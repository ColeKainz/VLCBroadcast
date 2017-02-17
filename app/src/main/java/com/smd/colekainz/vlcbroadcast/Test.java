package com.smd.colekainz.vlcbroadcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.Cache;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class Test extends Activity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final RequestQueue queue = Volley.newRequestQueue(this);

        tv = (TextView)findViewById(R.id.textURL);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (intent.getData() != null && intent.getData().getPath() != null){
            String url ="http://192.168.1.22:8080/requests/status.xml?command=in_play&input=" + intent.getData().toString();
            tv.setText(url);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            tv.setText("Response is: "+ response.substring(0,500));
                            queue.stop();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            tv.setText("Error: " + error.toString());
                            queue.stop();
                        }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = ":!#@$^&%*)(";
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
            };

            tv.setText("TEST");
            queue.add(stringRequest);
        }
    }
}
