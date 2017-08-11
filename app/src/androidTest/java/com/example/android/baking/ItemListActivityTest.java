package com.example.android.baking;


import android.content.Intent;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ItemListActivityTest {

    @Rule
    public ActivityTestRule<ItemListActivity> mActivityTestRule = new ActivityTestRule<>(ItemListActivity.class);

    private Fragment fragment;
    private ItemListActivity activity;

    @Before
    public void setup() {
        // Setup shared mock information or do your dependency injection
        fragment = new ItemListFragment();
    }

   /* @Test
    public void itemListActivityTest() {
        ViewInteraction recyclerViewRecipe = onView(allOf(
                withId(R.id.rv_receipt_list), isDisplayed()));

        recyclerViewRecipe.perform(scrollToPosition(1), click());

        ViewInteraction recyclerViewDetail = onView(allOf(
                withId(R.id.rv_item_steps), isDisplayed()));

        recyclerViewDetail.perform(scrollToPosition(1), click());

    }*/

    @Test public void fragmentTest() {
        // Setup your test specific mocks here

        activity = mActivityTestRule.launchActivity(new Intent());
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.receiptCardView_fragment_container, fragment)
                .commit();

        ViewInteraction recyclerViewRecipe = onView(allOf(
                withId(R.id.rv_receipt_list), isDisplayed()));

        recyclerViewRecipe.perform(scrollToPosition(1), click());

        ViewInteraction recyclerViewDetails = onView(allOf(withId(R.id.rv_item_steps), isDisplayed()));

        recyclerViewDetails.perform(scrollToPosition(1),click());
    }

}
