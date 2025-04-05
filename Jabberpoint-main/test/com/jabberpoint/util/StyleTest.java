package com.jabberpoint.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Font;
import org.junit.Before;
import org.junit.Test;

public class StyleTest {

    @Before
    public void setUp() {
        Style.createStyles();
    }

    @Test
    public void createStylesInitializesStylesWithCorrectProperties() {
        Style style0 = Style.getStyle(0);
        Style style1 = Style.getStyle(1);

        assertNotNull(style0);
        assertNotNull(style1);

        assertEquals(0, style0.indent);
        assertEquals(Color.red, style0.color);

        assertEquals(20, style1.indent);
        assertEquals(Color.blue, style1.color);
    }

    @Test
    public void getStyleWithIndexBeyondRangeReturnsLastStyle() {
        Style style = Style.getStyle(10);
        assertNotNull(style);
        assertEquals(90, style.indent);
    }

    @Test
    public void styleConstructorSetsAllProperties() {
        Style style = new Style(15, Color.green, 24, 8);
        assertEquals(15, style.indent);
        assertEquals(Color.green, style.color);
        assertEquals(24, style.fontSize);
        assertEquals(8, style.leading);
    }

    @Test
    public void getFontReturnsScaledFont() {
        Style style = new Style(15, Color.green, 24, 8);
        Font font1 = style.getFont(1.0f);
        Font font2 = style.getFont(2.0f);
        assertEquals(24, font1.getSize());
        assertEquals(48, font2.getSize());
    }

    @Test
    public void toStringContainsStyleProperties() {
        Style style = new Style(15, Color.green, 24, 8);
        String result = style.toString();
        assertTrue(result.contains("15"));
        assertTrue(result.contains("java.awt.Color"));
        assertTrue(result.contains("24"));
        assertTrue(result.contains("8"));
    }
}

