package com.taskie.health;

import com.codahale.metrics.health.HealthCheck;
import com.taskie.db.FlatDao;

public class FlatServiceHealthCheck extends HealthCheck {

    private final FlatDao flatDao;

    public FlatServiceHealthCheck(FlatDao flatDao) {
        this.flatDao = flatDao;
    }

    /**
     * Checks if a flat can be acquired from the resource.
     *
     * {@inheritDoc}
     */
    @Override
    protected Result check() {
        try {
            flatDao.findById(1);
        } catch (RuntimeException e) {
            return Result.unhealthy(e.getMessage());
        }
        return Result.healthy();
    }
}
