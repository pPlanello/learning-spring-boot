package com.pplanello.learning.spring.project.record;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserRecordTest {

    @Test
    void it_should_not_be_null() {
        assertThat(USER_RECORD)
            .as("It should not be null")
            .isNotNull();
    }

    @Test
    void it_should_create_user_without_id() {
        assertThat(USER_RECORD_WITHOUT_ID)
            .as("It should create user without id")
            .isNotNull();
    }

    @Test
    void it_should_return_user_id_when_creation_is_without_id() {
        assertThat(USER_RECORD_WITHOUT_ID.id())
            .as("It should return user id when creation is without id")
            .isEqualTo(ID);
    }

    @Test
    void it_should_throw_error_when_field_name_is_null() {
        assertThatThrownBy(() -> new UserRecord(null, "email"))
            .as("It should throw error when field name is null")
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Field name cannot be null or empty");
    }

    @Test
    void it_should_throw_error_when_field_email_is_null() {
        assertThatThrownBy(() -> new UserRecord("name", null))
            .as("It should throw error when field email is null")
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Field email cannot be null or empty");
    }

    @Test
    void it_should_throw_error_when_field_name_is_blank() {
        assertThatThrownBy(() -> new UserRecord("", "email"))
            .as("It should throw error when field name is blank")
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Field name cannot be null or empty");
    }

    @Test
    void it_should_throw_error_when_field_email_is_blank() {
        assertThatThrownBy(() -> new UserRecord("name", ""))
            .as("It should throw error when field email is blank")
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Field email cannot be null or empty");
    }

    private static final String ID = "name" + "email";
    private static final UserRecord USER_RECORD = new UserRecord(ID, "name", "email");
    private static final UserRecord USER_RECORD_WITHOUT_ID = new UserRecord("name", "email");

}