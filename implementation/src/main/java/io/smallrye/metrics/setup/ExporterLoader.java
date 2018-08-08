/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates
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
package io.smallrye.metrics.setup;

import io.smallrye.metrics.MetricRegistries;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import org.eclipse.microprofile.metrics.spi.MetricExporter;
import org.jboss.logging.Logger;

/**
 * Load pluggable exporters, that could be in the sm-metrics package or provided
 * via external Jars.
 * Exporters need to implement {@link org.eclipse.microprofile.metrics.spi.MetricExporter}
 * and provide a <tt>META-INF/services/org.eclipse.microprofile.metrics.spi.MetricExporter</tt>
 * file according to the {{@link ServiceLoader}} interface
 * @author hrupp
 */
class ExporterLoader {

  private static final Logger log = Logger.getLogger(ExporterLoader.class);

  Map<TypeMethod, MetricExporter> load() {
    ClassLoader loader;
    loader = getClass().getClassLoader();
    ServiceLoader<MetricExporter> serviceLoader = ServiceLoader.load(MetricExporter.class, loader);

    Map<TypeMethod, MetricExporter> exporters = new HashMap<>();

    for (MetricExporter exporter : serviceLoader) {
      boolean exists = false;
      TypeMethod key = new TypeMethod(exporter.getMediaType(), exporter.getMethod());
      if (exporters.containsKey(key)) {
        if (exporters.get(key).getPriority() == exporter.getPriority()) {
          log.warnf("There is already an exporter registered for %s with priority %d: '%s'. Rejecting '%s'",
                    key,
                    exporter.getPriority(),
                    exporters.get(key).getClass().getSimpleName(),
                    exporter.getClass().getSimpleName());
          exists = true;
        }
      }
      if (!exists) {
        exporters.put(key, exporter);
        exporter.setRegistries(MetricRegistries.asMap());
        log.infof("Loaded exporter '%s' with prio %d for '%s' ", exporter.getClass().getSimpleName(),
                  exporter.getPriority(), key);
      }
    }
    return exporters;
  }

}


