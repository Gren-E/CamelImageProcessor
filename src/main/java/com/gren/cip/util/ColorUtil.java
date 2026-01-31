package com.gren.cip.util;

import java.awt.Color;

/**
 * Class providing color adjustment tools.
 * @author Ewelina Gren
 * @version 1.0
 */
public class ColorUtil {

    /**
     * Creates a {@code Color} by replacing the RGB values of the provided color with their mean value. Returns {@code null} if the original color is {@code null}.
     * @param color a {@code Color} to be turned to grayscale.
     * @return a grayscale {@code Color} closest in value to the original color.
     */
    public static Color grayscale(Color color) {
        if (color == null) {
            return null;
        }
        int meanValue = (color.getRed() + color.getBlue() + color.getGreen()) / 3;
        return new Color(meanValue, meanValue, meanValue, color.getAlpha());
    }

}
