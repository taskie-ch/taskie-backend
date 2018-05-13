package com.taskie.health;

import com.codahale.metrics.health.HealthCheck;
import com.taskie.api.FlatService;

/**
 * Checks the availability of a {@link FlatService}
 */
public class FlatServiceHealthCheck extends HealthCheck {

    /**
     * Provides flat and user data.
     */
    private final FlatService flatService;

    public FlatServiceHealthCheck(FlatService flatService) {
        this.flatService = flatService;
    }

    /**
     * Checks if a flat can be acquired from the resource.
     * <p>
     * {@inheritDoc}
     */
    @Override
    protected Result check() {
        try {
            flatService.findById(1);
        } catch (RuntimeException e) {
            return Result.unhealthy(e.getMessage());
        }
        return Result.healthy();
    }
}
