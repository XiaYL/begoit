package com.begoit.mooc.offline;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesData;
import com.begoit.mooc.offline.utils.GsonUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        System.out.print("com.begoit.mooc.offline : " + appContext.getPackageName());
    }

}
