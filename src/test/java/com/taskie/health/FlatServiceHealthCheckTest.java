package com.taskie.health;

import com.taskie.api.FlatService;
import com.taskie.core.Flat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlatServiceHealthCheckTest {

    private FlatService flatDao;
    private FlatServiceHealthCheck check;

    @Before
    public void setUp() {
        flatDao = mock(FlatService.class);
        check = new FlatServiceHealthCheck(flatDao);
    }

    @Test
    public void healthy() {
        when(flatDao.findById(anyLong())).thenReturn(mock(Flat.class));
        assertTrue("Service health", check.check().isHealthy());
    }

    @Test
    public void unhealthy() {
        when(flatDao.findById(anyLong())).thenThrow(new IllegalStateException("Issue"));
        assertFalse("Service health", check.check().isHealthy());
    }
}