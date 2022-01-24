package com.example.telemedicine.helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.telemedicine.Interfaces.OnGetDocListListener;
import com.example.telemedicine.Interfaces.OnGetDoctorListener;
import com.example.telemedicine.Interfaces.OnLoginListener;
import com.example.telemedicine.Interfaces.OnRegListener;
import com.example.telemedicine.Interfaces.OnUserConsultationRequestListener;
import com.example.telemedicine.models.Constants;
import com.example.telemedicine.models.Doctor;
import com.example.telemedicine.models.User;
import com.example.telemedicine.models.UserConsultationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestSender
{
    private RequestQueue queue;

    private OnRegListener onRegListener;
    private OnLoginListener onLoginListener;
    private OnGetDocListListener onGetDocListListener;
    private OnUserConsultationRequestListener consultationRequestListener;
    private OnGetDoctorListener onGetDoctorListener;

    public HttpRequestSender()
    {
        onRegListener = null;
        onLoginListener = null;
        onGetDocListListener = null;
        consultationRequestListener = null;
        onGetDocListListener = null;
    }

    public void setOnRegListener(OnRegListener onRegListener)
    {
        this.onRegListener = onRegListener;
    }

    public void setOnLoginListener(OnLoginListener onLoginListener)
    {
        this.onLoginListener = onLoginListener;
    }

    public void setOnGetDocListListener(OnGetDocListListener onGetDocListListener)
    {
        this.onGetDocListListener = onGetDocListListener;
    }

    public void setConsultationRequestListener(OnUserConsultationRequestListener consultationRequestListener)
    {
        this.consultationRequestListener = consultationRequestListener;
    }

    public void setOnGetDoctorListener(OnGetDoctorListener onGetDoctorListener)
    {
        this.onGetDoctorListener = onGetDoctorListener;
    }

    public void reg(Context context)
    {
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.REG_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (onRegListener != null)
                            onRegListener.onRegSuccess();
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                if (onRegListener != null)
                    onRegListener.onRegFailure();
            }
        }
        )
        {
            @Override
            public Map<String, String> getHeaders()
            {
                HashMap<String, String> headers = new HashMap<>();
                headers.put(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_VALUE);
                return headers;
            }

            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("FullName", User.getFullName());
                params.put("Birthday", User.getBirthday());
                params.put("Email", User.getEmail());
                params.put("Phone", User.getPhone());
                params.put("Address", User.getAddress());
                params.put("Username", User.getUsername());
                params.put("Password", User.getPassword());
                params.put("Base64Photo", User.getBase64photo());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void auth(Context context)
    {
        queue = Volley.newRequestQueue(context);

        HashMap<String, String> data = new HashMap<>();
        data.put("Email", User.getEmail());
        data.put("Password", User.getPassword());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, Constants.AUTH_URL, new JSONObject(data),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            User.setToken(response.getString("Message"));
                            if (onLoginListener != null)
                                onLoginListener.onLoginSuccess();
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                if (onLoginListener != null)
                    onLoginListener.onLoginFailure();
            }
        }
        );

        queue.add(jsonObjectRequest);
    }

    public void getDocList(Context context)
    {
        queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, Constants.DOC_LIST_URL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try
                        {
                            ArrayList<Doctor> doctors = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject item = response.getJSONObject(i);
                                Doctor doctor =
                                        new Doctor(item.getInt("DocId"),
                                                item.getString("FullName"),
                                                (float) item.getDouble("Stars"),
                                                item.getString("Specs"),
                                                item.getString("Address"),
                                                item.getString("About"),
                                                item.getString("Photo"));
                                doctors.add(doctor);
                            }
                            if (onGetDocListListener != null)
                                onGetDocListListener.onGetDocListSuccess(doctors);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        }
        )
        {
            @Override
            public Map<String, String> getHeaders()
            {
                HashMap<String, String> headers = new HashMap<>();
                headers.put(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_VALUE);
                headers.put("token", User.getToken());
                return headers;
            }
        };

        queue.add(jsonArrayRequest);
    }

    public void getDoc(Context context)
    {
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.GET_DOC_URL + "/" +
                        UserConsultationRequest.getDocId(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            Doctor doctor =
                                    new Doctor(jsonObject.getInt("DocId"),
                                            jsonObject.getString("FullName"),
                                            (float) jsonObject.getDouble("Stars"),
                                            jsonObject.getString("Specs"),
                                            jsonObject.getString("Address"),
                                            jsonObject.getString("About"),
                                            jsonObject.getString("Photo"));
                            if (onGetDoctorListener != null)
                                onGetDoctorListener.onGetDoctorSuccess(doctor);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders()
            {
                HashMap<String, String> headers = new HashMap<>();
                headers.put(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_VALUE);
                headers.put("token", User.getToken());
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    public void userRequestConsultation(Context context)
    {
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.USER_REQUEST_CONSULTATION_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            UserConsultationRequest.setConsId(jsonObject.getInt("ConsId"));
                            UserConsultationRequest.setDocId(jsonObject.getInt("DocId"));
                            UserConsultationRequest.setIsConfirmed(jsonObject.getBoolean("IsConfirmed"));
                            if (consultationRequestListener != null)
                                consultationRequestListener.
                                        onUserConsultationRequestSuccess();
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                        if (consultationRequestListener != null)
                            consultationRequestListener.
                                    onUserConsultationRequestFailure();
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders()
            {
                HashMap<String, String> headers = new HashMap<>();
                headers.put(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_VALUE);
                headers.put("token", User.getToken());
                return headers;
            }

            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();

                params.put("ConsId", String.valueOf(UserConsultationRequest.getConsId()));
                params.put("Name", UserConsultationRequest.getName());
                params.put("Disease", UserConsultationRequest.getDisease());
                params.put("Address", UserConsultationRequest.getAddress());
                params.put("Description", UserConsultationRequest.getDescription());
                params.put("DocId", String.valueOf(UserConsultationRequest.getDocId()));
                params.put("IsConfirmed", String.valueOf(UserConsultationRequest.isConfirmed()));
                return params;
            }
        };

        queue.add(stringRequest);
    }

}
