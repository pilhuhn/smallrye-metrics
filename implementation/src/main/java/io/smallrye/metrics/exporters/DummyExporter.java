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
package io.smallrye.metrics.exporters;

import java.util.Map;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.spi.MetricExporter;

/**
 * This is only a Dummy to show the rejection of a Exporter,
 * that has the same <method, media type, priority> than a previous one.
 * @author hrupp
 * TODO remove before merging
 */
public class DummyExporter implements MetricExporter {
  @Override
  public int getPriority() {
    return 1;
  }

  @Override
  public String getMediaType() {
    return "text/plain";
  }

  @Override
  public String exportOneMetric(MetricRegistry.Type scope, String metricName) {
    return null;  // TODO: Customise this generated block
  }

  @Override
  public String exportOneScope(MetricRegistry.Type scope) {
    return null;  // TODO: Customise this generated block
  }

  @Override
  public String exportAllScopes() {
    return null;  // TODO: Customise this generated block
  }

  @Override
  public void setRegistries(Map<MetricRegistry.Type, MetricRegistry> registryMap) {
    // TODO: Customise this generated block
  }
}
