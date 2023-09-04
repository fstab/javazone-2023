package de.fstab.demo.jcon2023;

import com.google.auto.service.AutoService;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizer;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import io.opentelemetry.sdk.metrics.Aggregation;
import io.opentelemetry.sdk.metrics.InstrumentSelector;
import io.opentelemetry.sdk.metrics.InstrumentType;
import io.opentelemetry.sdk.metrics.SdkMeterProviderBuilder;
import io.opentelemetry.sdk.metrics.View;

@AutoService(AutoConfigurationCustomizerProvider.class)
public class HistogramConfigExtension
        implements AutoConfigurationCustomizerProvider {

    @Override
    public void customize(AutoConfigurationCustomizer autoConfiguration) {
        autoConfiguration.addMeterProviderCustomizer(this::configureSdkMeterProvider);
    }

    private SdkMeterProviderBuilder configureSdkMeterProvider(SdkMeterProviderBuilder sdkMeterProviderBuilder, ConfigProperties configProperties) {
        return sdkMeterProviderBuilder.registerView(
                InstrumentSelector.builder()
                        .setName("htt.server.duration2")
                        .setType(InstrumentType.HISTOGRAM)
                        .build(),
                View.builder()
                        .setAggregation(Aggregation.base2ExponentialBucketHistogram(160, 8))
                        .build()
        );
    }
}
