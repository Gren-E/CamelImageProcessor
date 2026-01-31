package com.gren.cip.configuration;

import com.gren.cip.processors.ImageProcessor;
import org.apache.camel.builder.RouteBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * A class that configures the route for image processing.
 * @author Ewelina Gren
 * @version 1.0
 */
@Configuration
public class ImageRoute extends RouteBuilder {
    
    private static final Logger LOG = LoggerFactory.getLogger(ImageRoute.class);

    /**
     * Configures the route for image processing.
     */
    @Override
    public void configure() {
        LOG.info("Directory: {}", ImageProcessor.getDirectoryPath());
        from("file:" + ImageProcessor.getDirectoryPath() + "/input?")
                .routeId("ImageRoute")
                .choice()
                .when(exchange -> String.valueOf(exchange.getIn().getHeader("CamelFileName")).endsWith(".png"))
                    .to("file:" + ImageProcessor.getDirectoryPath() + "/archive?filename=${file:onlyname.noext}_${date:now:yyyy-MM-dd'T'HH-mm-ss,SSS}.${file:ext}")
                    .log("Original image ${file:name} archived")
                    .process(new ImageProcessor())
                    .to("file:" + ImageProcessor.getDirectoryPath() + "/processed?filename=${file:onlyname.noext}_${variables:counter}.${file:ext}")
                    .log("Image ${file:name} processed")
                .otherwise()
                    .to("file:" + ImageProcessor.getDirectoryPath() + "/unsupported?filename=${file:onlyname.noext}_${date:now:yyyy-MM-dd'T'HH-mm-ss,SSS}.${file:ext}")
                    .log("Original file ${file:name} unsupported")
                .endChoice();
    }

}
