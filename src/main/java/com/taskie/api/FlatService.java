package com.taskie.api;

import com.taskie.core.Flat;
import com.taskie.core.Flatmate;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Service for all operations concerning flats.
 *
 * @see com.taskie.db.InMemoryFlatDao
 */
public interface FlatService {

    /**
     * Finds a flat by it id.
     *
     * @param flatId id of the flat
     * @return existing flat
     */
    @Nonnull
    Flat findById(long flatId);

    /**
     * Finds users in a flat based on the given id's and, if found,
     * returns as {@link Flatmate} in the same order.
     *
     * @param flatId  id if the flat
     * @param userIds list of user ids
     * @return list of flatmates ordered by input ids
     */
    @Nonnull
    List<Flatmate> findUsers(long flatId, @Nonnull List<String> userIds);
}