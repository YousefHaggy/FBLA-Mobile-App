package com.yousefhaggy.fblamobileapp;


import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This page lists all available testing categories. In addition,
// it provides a search view in the action bar to filter the list, which
// allows users to easily find what they are looking for. As the list of
// testing categories included in this app may grow in the future, this
// ensures the UI is still user friendly
public class SearchCategories extends Fragment {
    private ListView listView;
    private String[] categories;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getCategoryList();
        return inflater.inflate(R.layout.search_events, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.categoryListView);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, categories);
        listView.setAdapter(listAdapter);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.helpFloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpDialogFragment dialogFragment = new HelpDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("HelpContent", "Select a category to take tests or quizzes. Tap the search icon in the upper right corner to filter categories");
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getActivity().getSupportFragmentManager(), null);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CategoryMenuDialogFragment categoryMenuDialogFragment = new CategoryMenuDialogFragment();
                Bundle bundle = new Bundle();
                TestBankDatabaseHelper databaseHelper = new TestBankDatabaseHelper(getActivity());
                try {
                    databaseHelper.createDatabase();
                } catch (IOException e) {
                    throw new Error("cant make database");
                }
                try {
                    databaseHelper.openDatabase();
                } catch (SQLException e) {
                    throw e;

                }
                List<String> testList = databaseHelper.getTests(categories[i]);
                databaseHelper.close();
                bundle.putStringArrayList("TestList", (ArrayList) testList);
                bundle.putString("CategoryName", categories[i]);
                categoryMenuDialogFragment.setArguments(bundle);
                categoryMenuDialogFragment.show(getActivity().getSupportFragmentManager(), null);

            }
        });

    }

    public void getCategoryList() {
        TestBankDatabaseHelper databaseHelper = new TestBankDatabaseHelper(getActivity());
        try {
            databaseHelper.createDatabase();
        } catch (IOException e) {
            throw new Error("cant make database");
        }
        try {
            databaseHelper.openDatabase();
        } catch (SQLException e) {
            throw e;

        }
        List<String> dbCategoryList = databaseHelper.getCategoryList();
        categories = new String[dbCategoryList.size()];
        categories = dbCategoryList.toArray(categories);
        databaseHelper.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ArrayList<String> tempList = new ArrayList<String>();
                for (String item : categories) {
                    if (item.toLowerCase().contains(s.toLowerCase())) {
                        tempList.add(item);
                        Log.e("ITEM ", item + " S " + s);
                    }
                }

                if (getActivity() != null) {
                    ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, tempList);
                    listView.setAdapter(newAdapter);

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<String> tempList = new ArrayList<String>();
                for (String item : categories) {
                    if (item.toLowerCase().contains(s.toLowerCase())) {
                        tempList.add(item);
                        Log.e("ITEM ", item + " S " + s);
                    }
                }

                if (getActivity() != null) {
                    ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, tempList);
                    listView.setAdapter(newAdapter);

                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, menuInflater);
    }

}
