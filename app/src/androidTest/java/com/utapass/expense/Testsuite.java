package com.utapass.expense;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by vanessatsai on 2017/9/21.
 */


@RunWith(FilterCategoryTestSuite.class)
@Categories.IncludeCategory()
@Categories.ExcludeCategory()
@Suite.SuiteClasses( {ExampleInstrumentedTest.class })
public class Testsuite {

}
