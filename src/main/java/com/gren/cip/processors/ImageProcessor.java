package com.gren.cip.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.gren.cip.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A processor class that reads an image, processes it and saves a copy.
 * @author Ewelina Gren
 * @version 1.0
 */
public class ImageProcessor implements Processor {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private static File directory;

    /**
     * Reads, processes and saves image.
     * @param exchange an exchange from Camel route
     * @throws IOException if the file can't be read
     */
    @Override
    public void process(Exchange exchange) throws IOException {
        BufferedImage image = readImage(exchange);
        image = processImage(image);
        writeImage(image, exchange);
    }

    /**
     * Reads an image from the exchange.
     * @param exchange an exchange from Camel route
     * @return {@code BufferedImage} taken from the file from the exchange
     * @throws IOException if the file can't be read
     */
    private BufferedImage readImage(Exchange exchange) throws IOException {
        InputStream body = exchange.getIn().getBody(InputStream.class);
        return ImageIO.read(body);
    }

    /**
     * Processes image to turn it to greyscale.
     * @param image the original {@code BufferedImage}
     * @return a processed {@code BufferedImage}
     */
    private BufferedImage processImage(BufferedImage image) {
        return (BufferedImage) ImageUtil.convertToGrayscale(image);
    }

    /**
     * Replaces the original image from the exchange with the modified one.
     * @param image the modified {@code BufferedImage} to be saved
     * @param exchange an exchange from Camel route
     * @throws IOException if the file can't be read
     */
    private void writeImage(BufferedImage image, Exchange exchange) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        exchange.getIn().setBody(outputStream.toByteArray());
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "image/png");
        exchange.setVariable("counter", counter.incrementAndGet());
    }

    /**
     * Sets the directory for image processing.
     * @param dir the target directory
     */
    public static void setDirectory(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            throw new IllegalArgumentException("Invalid directory: " + dir);
        }
        directory = file;
    }

    /**
     * Returns the directory for image processing.
     * @return the directory
     */
    public static String getDirectoryPath() {
        return directory != null ? directory.getAbsolutePath() : null;
    }
}
