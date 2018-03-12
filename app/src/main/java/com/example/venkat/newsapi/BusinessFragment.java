package com.example.venkat.newsapi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by venkat on 3/3/18.
 */

public class BusinessFragment extends Fragment {
    public List<BusinessNews> business = new ArrayList();
    RecyclerView recyclerView;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    final String url = "https://newsapi.org/v2/top-headlines?sources=the-economist&apiKey=80d7f74f3c2b47ac8d42845578aeab20";
    RecyclerAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_layout, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.business_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GetBusinessNews();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void GetBusinessNews(){
        requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("Tag",response);
                try {
                    JSONObject root = new JSONObject(response);
                    JSONArray cast = root.getJSONArray("articles");
                    for(int i = 0;i<root.length();i++){
                        JSONObject newsinfo = cast.getJSONObject(i);
                        String news_title = newsinfo.optString("title");
                        String news_description = newsinfo.optString("description");
                        String news_url = newsinfo.optString("urlToImage");
                        business.add(new BusinessNews(news_title,news_description,news_url));
                    }
                    recyclerAdapter = new RecyclerAdapter(business,getActivity());
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


    public class BusinessNews{
        String title;
        String description;
        String urlToImage;


        public String getUrlToImage() {
            return urlToImage;
        }

        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }


        public BusinessNews(String title, String description, String urlToImage) {
            this.title = title;
            this.urlToImage = urlToImage;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }



    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

        List<BusinessNews> news ;
        Context context;
        String currentUrl;

        public RecyclerAdapter(List<BusinessNews> news, Context context) {
            this.news = news;
            this.context = context;
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutinflate = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.business_recycle,parent,false);

            return new ViewHolder(layoutinflate);
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

            holder.business_title.setText(news.get(position).getTitle());
           // holder.business_description.setText("Title:-"+news.get(position).getDescription());
            currentUrl = news.get(position).getUrlToImage();
            Glide.with(getActivity()).load(currentUrl).asBitmap().into(holder.business_image);

        }

        @Override
        public int getItemCount() {
            return news.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView business_title,business_description;
            ImageView business_image;
            public ViewHolder(View itemView) {
                super(itemView);
                business_image = (ImageView)itemView.findViewById(R.id.business_image);
                business_title = (TextView)itemView.findViewById(R.id.business_title);
               // business_description = (TextView)itemView.findViewById(R.id.business_description);

            }
        }
    }
}
