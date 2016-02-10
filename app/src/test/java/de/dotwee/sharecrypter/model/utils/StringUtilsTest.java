package de.dotwee.sharecrypter.model.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lukas Wolfsteiner on 06.02.2016.
 */
public class StringUtilsTest {

    @Test
    public void testCapitalizeFirstLetter() throws Exception {
        String valueToTest = "test", expectedValue = "Test";

        assertEquals(StringUtils.capitalizeFirstLetter(valueToTest), expectedValue);
    }
}