package com.taskie.resources;

import com.taskie.core.Flat;
import com.taskie.core.Flatmate;
import com.taskie.db.FlatDao;
import com.taskie.util.TestData;
import org.junit.Test;

import java.util.Collections;

import static com.taskie.util.GenericTypes.userList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Requests {@link HallOfFameResource#getHallOfFame}
 */
public class HallOfFameTest extends AbstractRequestTest {

    private static final String PATH = "flats/1/hof";
    private static final FlatDao FLAT_DAO = mock(FlatDao.class);

    private static final Flatmate USER = TestData.flatmate();

    public HallOfFameTest() {
        super(resourceRule(new HallOfFameResource(FLAT_DAO)), PATH);
    }

    @Test
    public void requestHallOfFame() {
        Flat flat = mock(Flat.class);
        when(flat.getUsers()).thenReturn(Collections.singleton(USER));
        when(FLAT_DAO.findById(1)).thenReturn(flat);
        assertThat(request().get(userList())).contains(USER.deriveUser());
        verify(flat).getUsers();
        verify(FLAT_DAO).findById(1);
    }
}
