package com.pp4jk.blogrestapi.util;

public enum TestConstants {

    POST_OBJECT("Post"),
    POST_TITLE_FIRST("First Title"),
    POST_TITLE_SECOND("Second Title"),
    POST_CONTENT_FIRST("First Content"),
    POST_CONTENT_SECOND("Second Content"),

    CATEGORY_OBJECT("Category"),
    CATEGORY_NAME_FIRST("First Category"),
    CATEGORY_NAME_SECOND("Second Category"),

    COMMENT_OBJECT("Comment"),
    COMMENT_NAME_FIRST("First Comment"),
    COMMENT_NAME_SECOND("Second Comment"),
    COMMENT_EMAIL_FIRST("firstcom@email.com"),
    COMMENT_EMAIL_SECOND("secondcom@email.com"),
    COMMENT_BODY_FIRST("First Body"),
    COMMENT_BODY_SECOND("Second Body"),

    ROLE_OBJECT("Role"),
    ADMIN_ROLE("ROLE_ADMIN"),
    USER_ROLE("ROLE_USER"),

    USER_OBJECT("User"),
    USER_NAME_FIRST("First Name"),
    USER_NAME_SECOND("Second Name"),
    USER_USERNAME_FIRST("firstUsername"),
    USER_USERNAME_SECOND("secondUsername"),
    USER_EMAIL_FIRST("firstEmail@email.com"),
    USER_EMAIL_SECOND("secondEmail@email.com"),
    USER_PASSWORD_FIRST("firstP@ssword123"),
    USER_PASSWORD_SECOND("secondP@ssword456"),

    DESCRIPTION_FIRST("First Description"),
    DESCRIPTION_SECOND("Second Description"),
    ID_FIELD("id"),
    ;

    private final String key;

    TestConstants(String key) {
        this.key = key;
    }

    public String value() {
        return key;
    }
}
