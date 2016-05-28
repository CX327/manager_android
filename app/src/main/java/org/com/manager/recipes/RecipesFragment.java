package org.com.manager.recipes;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.com.manager.R;
import org.com.manager.bean.RecipesDetailModel;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.RecipesFlagAdapter;
import org.com.manager.util.RecipesRecommendAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecipesFragment extends Fragment {
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    @Bind(R.id.recipes_recommend_list)
    ListView recommendListView;

    private List<RecipesDetailModel> recipesDetailModels;

    RecipesFragment(List<RecipesDetailModel> itemModels) {
        this.recipesDetailModels = itemModels;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (recipesDetailModels != null && recipesDetailModels.size() != 0) {
            noDataLayout.setVisibility(View.GONE);
            recommendListView.setVisibility(View.VISIBLE);
            recommendListView.setAdapter(new RecipesFlagAdapter(getActivity(), recipesDetailModels));
        } else {
            recommendListView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }


}
