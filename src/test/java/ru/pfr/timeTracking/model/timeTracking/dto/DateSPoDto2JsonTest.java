package ru.pfr.timeTracking.model.timeTracking.dto;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;


import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@TestConfiguration
class DateSPoDto2JsonTest {

    @Autowired
    private JacksonTester<DateSPoDto2> json;

    @Test
    public void testSerialize() throws Exception {

        DateSPoDto2 userDetails = new DateSPoDto2(
                LocalDate.of(1993, 1, 1),
                LocalDate.of(2000, 2, 2)
        );

        JsonContent<DateSPoDto2> result = this.json.write(userDetails);

        assertThat(result).hasJsonPathStringValue("$.dateS");
        assertThat(result).extractingJsonPathStringValue("$.dateS").isEqualTo("01.01.1993");
        assertThat(result).extractingJsonPathStringValue("$.datePo").isEqualTo("02.02.2000");
        //assertThat(result).doesNotHaveJsonPath("$.enabled");
    }
}