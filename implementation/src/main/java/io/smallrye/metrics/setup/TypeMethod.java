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

import java.util.Objects;
import org.eclipse.microprofile.metrics.spi.MetricExporter;

/**
 * Tuple to store media type and method as combined key
 * @author hrupp
 */
public class TypeMethod {
  private final String mediaType;
  private final MetricExporter.HttpMethod method;

  public TypeMethod(String mediaType, MetricExporter.HttpMethod method) {

    this.mediaType = mediaType;
    this.method = method;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TypeMethod that = (TypeMethod) o;
    return Objects.equals(mediaType, that.mediaType) &&
        method == that.method;
  }

  @Override
  public int hashCode() {
    return Objects.hash(mediaType, method);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("[");
    sb.append(method);
    sb.append(" '").append(mediaType).append('\'');
    sb.append(']');
    return sb.toString();
  }
}
