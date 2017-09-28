package com.utapass.expense;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by vanessatsai on 2017/9/25.
 */

public class AddActivityPage<T> extends AbstractObject<T>{


    @Override
    public void assertThisScreen() {
        onView(withId(R.id.ed_info)).check(matches(isDisplayed()));
    }


}
