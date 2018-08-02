package io.smallrye.metrics;

import java.util.HashMap;
import org.eclipse.microprofile.metrics.DefaultMetadata;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricType;

/**
 * Created by bob on 2/5/18.
 */
public class OriginTrackedMetadata extends DefaultMetadata {
    public OriginTrackedMetadata(Object origin, String name, MetricType type) {
        super(name, null, null, type, "", false, new HashMap<>());
        this.origin = origin;
    }

    public Object getOrigin() {
        return this.origin;
    }

    private final Object origin;
}
