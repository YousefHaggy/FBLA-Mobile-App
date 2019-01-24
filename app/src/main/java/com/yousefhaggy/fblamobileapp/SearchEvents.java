package com.yousefhaggy.fblamobileapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SearchEvents extends Fragment {
    private ListView listView;
    private String[] events= {"Diagnostic Tests","3-D Animation","Accounting 1","Accounting 2","Agribusiness","Banking and Financial Systems","Business Calculations","Business Communications","Business Ethics","Business Financial Plan","Business Law","Business Plan","Client Service","Computer Applications","Computer Problem Solving","Cyber Security","Database Design","Desktop Publishing / Publication Design","Digital Video Production","E-Business","Economics","Electronic Career Portfolio","Emerging Business Issues","Entrepreneurship","FBLA Principles/Procedures","Future Business Leader","Global Business","Graphic Design","Health Care Administration","Help Desk","Hospitality Managment","Impromptu Speaking","Insurance & Risk Management","Introduction to Business","Introduction to Business Communication","Introduction to Business Presentation","Introduction to Business Procedures","Introduction to Information Technology","Introduction to Parliamentary Procedures","Job Interview","Management Decision Making","Management Information Systems","Marketing","Mobile Application Development","Network Design","Networking Concepts","Personal Finance","Public Service Announcement","Public Speaking 1","Public Speaking 2","Sales Presentation","Securities and Investments","Social Media Campaign","Sports and Entertainment Management","Spreadsheet Applications","Website Design","Word Processing"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_events,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=(ListView) view.findViewById(R.id.categoryListView);
        ArrayAdapter<String> listAdapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_list_item, events);
        listView.setAdapter(listAdapter);
    }
}
