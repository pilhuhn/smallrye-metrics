/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.smallrye.metrics;

import java.util.Collections;
import java.util.HashMap;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.RegistryType;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hrupp
 */
@ApplicationScoped
public class MetricRegistries {

    @Produces
    @Default
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    @ApplicationScoped
    public MetricRegistry getApplicationRegistry() {
        return get(MetricRegistry.Type.APPLICATION);
    }

    @Produces
    @ApplicationScoped
    @RegistryType(type = MetricRegistry.Type.BASE)
    public MetricRegistry getBaseRegistry() {
        return get(MetricRegistry.Type.BASE);
    }

    @Produces
    @ApplicationScoped
    @RegistryType(type = MetricRegistry.Type.VENDOR)
    public MetricRegistry getVendorRegistry() {
        return get(MetricRegistry.Type.VENDOR);
    }

    public static MetricRegistry get(MetricRegistry.Type type) {
        return registries.computeIfAbsent(type, (t) -> new MetricsRegistryImpl());
    }

    @PreDestroy
    public void cleanUp() {
        registries.clear();
    }

    public static Map<MetricRegistry.Type, MetricRegistry> asMap() {
        return Collections.unmodifiableMap(registries);
    }

    private static final Map<MetricRegistry.Type, MetricRegistry> registries = new ConcurrentHashMap<>();

}
