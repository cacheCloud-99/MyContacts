package com.example.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String Url = "https://api.androidhive.info/json/contacts.json";
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private DividerItemDecoration mDividerItemDecoration;
    private List<Contact> mContactList;
    private ContactAdapter mContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
        recyclerView = findViewById(R.id.recycler_view);
        mContactList = new ArrayList<>();
        mContactAdapter = new ContactAdapter(getApplicationContext(), mContactList);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLinearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mContactAdapter);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
            private ClickListener clickListener;
            private GestureDetector gestureDetector;

            public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
                this.clickListener = clickListener;
                gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && clickListener != null) {
                            clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                        }
                    }
                });
            }

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onClick(child, rv.getChildAdapterPosition(child));
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        }
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String name, number, image;
                name = mContactList.get(position).getName().toString();
                number = mContactList.get(position).getNumber().toString();
                image = mContactList.get(position).getImage().toString();
                Contact contact = new Contact();
                contact.setImage(image);
                contact.setName(name);
                contact.setNumber(number);
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("serial", contact);
                i.putExtras(b);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "You have pressed for long time", Toast.LENGTH_LONG).show();
            }
        }));
    }

    private void getData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Contact contact = new Contact();
                        contact.setName(jsonObject.getString("name"));
                        contact.setNumber(jsonObject.getString("phone"));
                        contact.setImage(jsonObject.getString("image"));
                        mContactList.add(contact);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mContactAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}