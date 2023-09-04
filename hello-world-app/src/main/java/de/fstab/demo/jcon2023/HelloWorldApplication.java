package de.fstab.demo.jcon2023;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@SpringBootApplication
@RestController
public class HelloWorldApplication {

    private final OkHttpClient client = new OkHttpClient();
    private final Random random = new Random(2);
    private int currentGreetingService = -1;

    @Value("#{'${greeting.service.port}'.split(',')}")
    private int[] greetingServicePorts;

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

    @GetMapping("/")
    public String sayHello() throws Exception {
        currentGreetingService = (currentGreetingService + 1) % greetingServicePorts.length;
        Thread.sleep((long) (Math.abs((random.nextGaussian() + 1.0) * 100.0)));
        Request request = new Request.Builder().url("http://localhost:" + greetingServicePorts[currentGreetingService] + "/greeting").build();
        try (Response response = client.newCall(request).execute()) {
            Thread.sleep((long) (Math.abs((random.nextGaussian() + 1.0) * 50.0)));
            return response.body().string() + "\n";
        }
    }
}
