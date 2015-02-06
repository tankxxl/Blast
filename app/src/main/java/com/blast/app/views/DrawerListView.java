package com.blast.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blast.app.R;
import com.blast.app.adapters.DrawerAdapter;
import com.blast.app.events.DrawerNavigationItemClickedEvent;
import com.blast.app.models.DrawerItem;
import com.blast.app.utils.NavigationBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rgz on 2014/7/14.
 */
public class DrawerListView extends ListView implements ListView.OnItemClickListener {

    public DrawerListView(Context context) {
        this(context, null);
    }

    public DrawerListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAdapter();
        setOnItemClickListener(this);
    }

    private void initAdapter() {
        List<String> itemTitles = Arrays.asList(getResources().getStringArray(R.array.blast_formula_titles));
        List<DrawerItem> items = new ArrayList<DrawerItem>();
        for (int i = 0; i < itemTitles.size(); i++) {
            items.add(new DrawerItem(itemTitles.get(i), R.drawable.ic_pos1));
        }
//        items.add(new DrawerItem(itemTitles.get(0), R.drawable.ic_pos1));
//        items.add(new DrawerItem(itemTitles.get(1), R.drawable.ic_pos1));
//        items.add(new DrawerItem(itemTitles.get(2), R.drawable.ic_pos1));
//        items.add(new DrawerItem(itemTitles.get(3), R.drawable.ic_pos1));
//        items.add(new DrawerItem(itemTitles.get(4), R.drawable.ic_pos1));
//        items.add(new DrawerItem(itemTitles.get(5), R.drawable.ic_pos1));
//        items.add(new DrawerItem(itemTitles.get(6), R.drawable.ic_pos1));
//        items.add(new DrawerItem(itemTitles.get(7), R.drawable.ic_pos1));
//        items.add(new DrawerItem(itemTitles.get(8), R.drawable.ic_pos1));
//        items.add(new DrawerItem(itemTitles.get(9), R.drawable.ic_pos1));

        DrawerAdapter adapter = new DrawerAdapter(getContext());
        adapter.addAll(items);
        setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String drawerText = ((DrawerItem) parent.getAdapter().getItem(position)).getDrawerText();
        NavigationBus.getInstance().post(new DrawerNavigationItemClickedEvent(position, drawerText));
    }
}
