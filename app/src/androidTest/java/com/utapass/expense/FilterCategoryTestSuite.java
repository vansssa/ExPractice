package com.utapass.expense;

import android.os.Environment;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Category;
import org.junit.runner.Description;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vanessatsai on 2017/9/21.
 */

public class FilterCategoryTestSuite extends Categories {

    String testsuite ="testsuit.txt";

    public FilterCategoryTestSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
        try {

            ArrayList<Class<?>[]> set = filterByFile(testsuite);
            Set<Class<?>> included= getIncludedCategory(klass,set);
            Set<Class<?>> excluded= getExcludedCategory(klass,set);
            boolean isAnyIncluded= isAnyIncluded(klass);
            boolean isAnyExcluded= isAnyExcluded(klass);
            filter(Categories.CategoryFilter.categoryFilter(isAnyIncluded, included, isAnyExcluded, excluded));
        } catch (NoTestsRemainException e) {
            throw new InitializationError(e);
        }
        assertNoCategorizedDescendentsOfUncategorizeableParents(getDescription());
    }


    private static ArrayList<Class<?>[]> filterByFile(String FileName) {

        //Get the text file
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File("/sdcard/"+FileName);
        String line ;
        Class<?>[] included ;
        Class<?>[] excluded ;
        final ArrayList<Class<?>[]> set= new ArrayList<>();
        final String pkName="com.kddi.android.UtaPass.sqatest.util.EspressoUtil$";
        final String include_header="included=";
        final String exclude_header="excluded=";

        try {
            if(file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));

                while ((line = br.readLine()) != null) {
                    if(line.contains(include_header)) {
                        String[] test = line.substring(line.indexOf("=")+1).split(",");
                        included = new Class<?>[test.length];
                        for (int i = 0; i < test.length; i++) {
                            String type = pkName + test[i].trim();
                            included[i] = Class.forName(type);
                        }
                        if (included != null) {
                            set.add(included);

                        }

                    }
                    if(line.contains(exclude_header)) {
                        String[] test = line.substring(line.indexOf("=")+1).split(",");
                        excluded = new Class<?>[test.length];

                        for (int i = 0; i < test.length; i++) {
                            String type = pkName + test[i].trim();
                            excluded[i] = Class.forName(type);
                        }
                        if (excluded != null) {
                            set.add(excluded);
                        }
                    }

                }
                br.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return set;
    }

    private static Set<Class<?>> getIncludedCategory(Class<?> klass, ArrayList<Class<?>[]> aClass) {

        if (aClass.size() > 0) {
            return createSet(aClass.get(0) == null ? null : aClass.get(0));

        } else {
            Categories.IncludeCategory annotation = klass.getAnnotation(Categories.IncludeCategory.class);
            return createSet(annotation == null ? null : annotation.value());
        }

    }

    private static boolean isAnyIncluded(Class<?> klass) {
        Categories.IncludeCategory annotation= klass.getAnnotation(Categories.IncludeCategory.class);
        return annotation == null || annotation.matchAny();
    }

    private static Set<Class<?>> getExcludedCategory(Class<?> klass, ArrayList<Class<?>[]> aClass) {
        if (aClass.size() > 0) {
            return createSet(aClass.get(1) == null ? null : aClass.get(1));

        } else {
            Categories.ExcludeCategory annotation= klass.getAnnotation(Categories.ExcludeCategory.class);
            return createSet(annotation == null ? null : annotation.value());
        }

    }

    private static boolean isAnyExcluded(Class<?> klass) {
        Categories.ExcludeCategory annotation= klass.getAnnotation(Categories.ExcludeCategory.class);
        return annotation == null || annotation.matchAny();
    }

    private static void assertNoCategorizedDescendentsOfUncategorizeableParents(Description description) throws InitializationError {
        if (!canHaveCategorizedChildren(description)) {
            assertNoDescendantsHaveCategoryAnnotations(description);
        }
        for (Description each : description.getChildren()) {
            assertNoCategorizedDescendentsOfUncategorizeableParents(each);
        }
    }

    private static void assertNoDescendantsHaveCategoryAnnotations(Description description) throws InitializationError {
        for (Description each : description.getChildren()) {
            if (each.getAnnotation(Category.class) != null) {
                throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods.");
            }
            assertNoDescendantsHaveCategoryAnnotations(each);
        }
    }

    // If children have names like [0], our current magical category code can't determine their parentage.
    private static boolean canHaveCategorizedChildren(Description description) {
        for (Description each : description.getChildren()) {
            if (each.getTestClass() == null) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasAssignableTo(Set<Class<?>> assigns, Class<?> to) {
        for (final Class<?> from : assigns) {
            if (to.isAssignableFrom(from)) {
                return true;
            }
        }
        return false;
    }

    private static Set<Class<?>> createSet(Class<?>... t) {
        final Set<Class<?>> set= new HashSet<Class<?>>();
        if (t != null) {
            Collections.addAll(set, t);
        }
        return set;
    }
}
