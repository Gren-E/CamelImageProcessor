package com.gren.cip.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.function.Function;

/**
 * Class providing image tools.
 * @author Ewelina Gren
 * @version 1.0
 */
public class ImageUtil {

    /**
     * Creates a deep copy of a provided {@code BufferedImage}.
     * @param bufferedImage a {@code BufferedImage} to be copied.
     * @return a copy of the input as {@code BufferedImage}.
     */
    public static BufferedImage deepCopy(BufferedImage bufferedImage) {
        ColorModel colorModel = bufferedImage.getColorModel();
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(null);
        return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
    }

    /**
     * Converts the {@code Image} to grayscale.
     * @param image an {@code Image} to be altered.
     * @return a new {@code Image} which is a grayscale version of the original one.
     */
    public static Image convertToGrayscale(Image image) {
        BufferedImage newImage = deepCopy((BufferedImage) image);

        forEachPixel(newImage, point -> {
            Color xyColor = new Color(newImage.getRGB(point.x, point.y), true);
            return ColorUtil.grayscale(xyColor);
        });

        return newImage;
    }

    /**
     * Loops through each pixel of an image and repaints it according to the provided function.
     */
    private static void forEachPixel(BufferedImage newImage, Function<Point, Color> action) {
        int width = newImage.getWidth();
        int height = newImage.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newImage.setRGB(x, y, action.apply(new Point(x, y)).getRGB());
            }
        }
    }

}
