package com.example.android.baking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ItemListFragment itemListFragment = new ItemListFragment();

        if (findViewById(R.id.receiptGridView_fragment_container) != null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.receiptGridView_fragment_container, itemListFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.receiptCardView_fragment_container, itemListFragment)
                    .commit();
        }


    }


}
