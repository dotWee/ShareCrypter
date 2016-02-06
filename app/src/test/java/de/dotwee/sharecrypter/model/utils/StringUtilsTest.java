package de.dotwee.sharecrypter.model.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import de.dotwee.sharecrypter.BuildConfig;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lukas Wolfsteiner on 06.02.2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class StringUtilsTest {

    @Test
    public void testCapitalizeFirstLetter() throws Exception {
        String valueToTest = "test", expectedValue = "Test";

        assertEquals(StringUtils.capitalizeFirstLetter(valueToTest), expectedValue);
    }
}