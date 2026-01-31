package com.gren.cip;

import com.gren.cip.processors.ImageProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if ("-directory".equals(args[i])) {
                ImageProcessor.setDirectory(args[i + 1]);
            }
        }

        if (ImageProcessor.getDirectoryPath() == null) {
            ImageProcessor.setDirectory("dir/image");
        }

        SpringApplication.run(Main.class);
    }

}