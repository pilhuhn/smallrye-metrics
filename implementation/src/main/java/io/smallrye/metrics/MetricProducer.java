/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package io.smallrye.metrics;

import io.smallrye.metrics.interceptors.MetricName;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Gauge;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.Timer;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.RegistryType;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.util.SortedMap;

/**
 * @author hrupp
 */
@ApplicationScoped
public class MetricProducer {

    @Inject
    @RegistryType(type=MetricRegistry.Type.APPLICATION)
    MetricRegistry applicationRegistry;

    @Inject
    private MetricName metricName;

    @Produces
    <T> Gauge<T> getGauge(InjectionPoint ip) {
        // A forwarding Gauge must be returned as the Gauge creation happens when the declaring bean gets instantiated and the corresponding Gauge can be injected before which leads to producing a null value
        return () -> {
            // TODO: better error report when the gauge doesn't exist
            SortedMap<String, Gauge> gauges = applicationRegistry.getGauges();
            String name = metricName.of(ip);
            return ((Gauge<T>) gauges.get(name)).getValue();
        };
    }

    @Produces
    Counter getCounter(InjectionPoint ip) {
        return this.applicationRegistry.counter(getMetadata(ip, MetricType.COUNTER));
    }

    @Produces
    Histogram getHistogram(InjectionPoint ip) {
        return this.applicationRegistry.histogram(getMetadata(ip, MetricType.HISTOGRAM));
    }

    @Produces
    Meter getMeter(InjectionPoint ip) {
        return this.applicationRegistry.meter(getMetadata(ip, MetricType.METERED));
    }

    @Produces
    Timer getTimer(InjectionPoint ip) {
        return this.applicationRegistry.timer(getMetadata(ip, MetricType.TIMER));
    }

    private Metadata getMetadata(InjectionPoint ip, MetricType type) {
        Metadata metadata = new OriginTrackedMetadata(ip, metricName.of(ip), type);
        Metric metric = ip.getAnnotated().getAnnotation(Metric.class);
        if (metric != null) {
/*
            if (!metric.unit().isEmpty()) {
                metadata.setUnit(metric.unit());
            }
            if (!metric.description().isEmpty()) {
                metadata.setDescription(metric.description());
            }
            if (!metric.displayName().isEmpty()) {
                metadata.setDisplayName(metric.displayName());
            }
            if (metric.tags().length > 0) {
                for (String tag : metric.tags()) {
                    metadata.addTags(tag);
                }
            }
*/
        }
        return metadata;
    }
}
