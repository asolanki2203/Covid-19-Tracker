package com.example.android.covid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<StatesModel> {

    private Context context;
    private List<StatesModel> stateModelsList;
    //making one more list for filtering search
    private List<StatesModel> stateModelsListFiltered;

    public CustomAdapter(@NonNull Context context, List<StatesModel> stateModelsList) {
        super(context, R.layout.list_item,stateModelsList);

        this.context = context;
        this.stateModelsList = stateModelsList;
        stateModelsListFiltered = stateModelsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null,true);
        TextView tvCountryName = view.findViewById(R.id.tvStateName);
        //ImageView imageView = view.findViewById(R.id.imageFlag);
        //using the filtered list for both cases, initial when the activity opens and when we search
        tvCountryName.setText(stateModelsListFiltered.get(position).getState());
        //Glide.with(context).load(stateModelsListFiltered.get(position).getFlag()).into(imageView);
        return  view;
    }

    //making some fxns

    @Override
    public int getCount() {
        return stateModelsListFiltered.size();
    }

    @Nullable
    @Override
    public StatesModel getItem(int position) {
        return stateModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //main fxn
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = stateModelsList.size();
                    filterResults.values = stateModelsList;

                }else{
                    List<StatesModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(StatesModel itemsModel:stateModelsList){
                        if(itemsModel.getState().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                stateModelsListFiltered = (List<StatesModel>) results.values;
                Affected_Sates.stateModelsList = (List<StatesModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
