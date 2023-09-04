package de.fstab.demo.javazone2023;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.css.Counter;

import java.util.Random;

@SpringBootApplication
@RestController
public class GreetingService {

    private final Logger logger = LoggerFactory.getLogger(GreetingService.class);
    private final Random random = new Random(1);
    private final LongCounter counter = GlobalOpenTelemetry.get()
            .meterBuilder("my-custom-instrumentation")
            .setInstrumentationVersion("1.0.0")
            .build()
            .counterBuilder("my.custom.counter")
            .setDescription("Custom Counter")
            .setUnit("1")
            .build();

    public static void main(String[] args) {
        SpringApplication.run(GreetingService.class, args);
    }

    @GetMapping("/greeting")
    public String sayHello() throws InterruptedException {

        counter.add(1, Attributes.of(AttributeKey.stringKey("payment_method"), "credit card"));

        logger.info("Fabian says: The /greeting endpoint was called.");
        Thread.sleep((long) (Math.abs((random.nextGaussian() + 1.0) * 100.0)));
        Thread.sleep((long) (Math.abs((random.nextGaussian() + 1.0) * 100.0)));
        if (random.nextInt(10) < 3) {
            throw new RuntimeException("simulating an error");
        }
        return "Hello, World!\n";
    }
}
