package com.taskie.db;

import com.taskie.util.TestData;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryFlatDaoTest {

    private InMemoryFlatDao dao = InMemoryFlatDao.create();

    @Test
    public void create() {
        assertThat(InMemoryFlatDao.create()).isNotNull();
    }

    @Test
    public void generateUserId() {
        assertThat(InMemoryFlatDao.generateUserId(TestData.flatmate().getName())).isNotBlank();
    }

    @Test
    public void findById() {
        assertThat(dao.findById(1)).isNotNull();
    }

    @Test
    public void findUsers() {
        assertThat(dao.findUsers(1, Collections.singletonList(
                InMemoryFlatDao.generateUserId(TestData.flatmate().getName()))))
                .contains(TestData.flatmate());
    }
}