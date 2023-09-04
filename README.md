# Application Monitoring with Grafana and OpenTelemetry

This is the demo for my talk [Application Monitoring with Grafana and OpenTelemetry](https://2023.javazone.no/program/fa0469ab-f13c-417d-bf68-d7818ed02aa3) at [JavaZone 2023](https://javazone.no/).

# Slides

[Google Slides](https://docs.google.com/presentation/d/14qDI5RiBA3ZPj3iXr7mD_6vRduUq6B9D529gTCPM_Mg/edit?usp=sharing)

# Demo

Compile the Java code (requires Java 17):

```sh
./mvnw clean package
```

Run the demo:

```sh
docker-compose up
```

Open Grafana on [http://localhost:3000](http://localhost:3000). Default user is _admin_ with password _admin_.

_Note: The `docker-compose.yaml` works on my Linux with docker-compose 2.20.3, but I've heard reports that other versions or operating systems have issues with the `network_mode: host` setting. I'm using `network_mode: host` because I found it easiest if all containers can address each other on `localhost`.  If you run into issues with that you might need to configure networking differently._

