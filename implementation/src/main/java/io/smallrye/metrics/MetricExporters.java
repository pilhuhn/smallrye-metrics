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
package io.smallrye.metrics;

import io.smallrye.metrics.setup.TypeMethod;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.microprofile.metrics.spi.MetricExporter;

/**
 * Singleton to hold the list of exporters
 * @author hrupp
 */
public class MetricExporters {

  private static MetricExporters mi;
  private static Map<TypeMethod, MetricExporter> exporters;

  private MetricExporters() {
    exporters = new HashMap<>();
  }

  public static MetricExporters getInstance() {
    if (mi == null) {
      mi = new MetricExporters();
    }
    return mi;
  }

  Map<TypeMethod,MetricExporter> getExporters() {
    return exporters;
  }


  public MetricExporter get(TypeMethod tm) {
    return exporters.get(tm);
  }

  public void addAll(Map<TypeMethod, MetricExporter> map) {
    exporters.putAll(map);
  }
}
