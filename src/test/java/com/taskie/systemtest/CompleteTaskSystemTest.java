package com.taskie.systemtest;

import com.taskie.api.TaskInfo;
import com.taskie.core.Frequency;
import io.restassured.http.ContentType;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Collections;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CompleteTaskSystemTest extends AbstractSystemTest {

    @Test
    public void completeTaskRollsDateAndUpdatesRotation() throws Exception {

        TaskInfo before = readJson(get(path("/flats/1/tasks/1")), TaskInfo.class);
        LOG.info("Before: {}", before);

        given().contentType(ContentType.JSON)
                .body(Collections.singletonMap("user", "d9ffaca46d5990ec39501bcdf22ee7a1"))
                .when().post(path("/flats/1/tasks/1/complete"))
                .then().assertThat().statusCode(204);

        TaskInfo after = readJson(get(path("/flats/1/tasks/1")), TaskInfo.class);
        LOG.info("After: {}", after);


        // roll date by frequency
        assertThat(DateTime.parse(after.getStart()))
                .isEqualByComparingTo(
                        Frequency.valueOf(before.getFrequency()).apply(DateTime.parse(before.getStart())));

        // rotating current user to the back
        assertThat(before.getUserIds().get(0)).isEqualTo(after.getUserIds().get(2));
        assertThat(before.getUserIds().get(1)).isEqualTo(after.getUserIds().get(0));
        assertThat(before.getUserIds().get(2)).isEqualTo(after.getUserIds().get(1));
    }
}
