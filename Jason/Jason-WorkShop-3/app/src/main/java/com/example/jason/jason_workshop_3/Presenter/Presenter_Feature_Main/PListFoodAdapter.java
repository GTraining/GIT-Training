package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.FeatureModel.Food;
import com.example.jason.jason_workshop_3.Model.FeatureModel.FoodViewHolder;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.FoodActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 24/06/2016.
 */
public class PListFoodAdapter extends RecyclerView.Adapter<FoodViewHolder>{
    private int lastPosition = - 1;
    private FoodActivity mView;
    private FoodViewHolder foodViewHolder;
    private List<Food> foodList = new ArrayList<>();

    public PListFoodAdapter(FoodActivity mView) {
        this.mView = mView;
        foodList = JsonHandler();
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mView).inflate(R.layout.food_item, null);
        foodViewHolder = new FoodViewHolder(view);
        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        try {
            Picasso.with(mView).load(food.getFImageURL()).into(holder.imgFood);
        } catch (Exception e){
            e.printStackTrace();
        }
        holder.tvFoodName.setText(food.getFName() + "("+ food.getFCalo() + ")");
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mView, R.anim.slide_in_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mView.getAssets().open("food.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public List<Food> JsonHandler(){
        List<Food> foodList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = null;
            if (obj != null) {
                m_jArry = obj.getJSONArray("CategoryFood");
                for (int i = 0; i < m_jArry.length(); i++) {
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    String id = jo_inside.getString("id");
                    String name = jo_inside.getString("name");
                    String image_url = jo_inside.getString("image");
                    String Calo = jo_inside.getString("Calo");
                    foodList.add(new Food(id, name,image_url,Calo));
                }
            }
            else Toast.makeText(mView, "Null", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodList;
    }
}
