package com.company.application.authserver.constant;

public class Constant {

    public final static String COMPONENT_SCAN_PATH = "com.company.application.authserver";

    public final static String AUTH_MANAGER_BEAN = "authenticationManagerBean";
    public final static String DATASOURCE = "dataSource";
    public final static String SCHEMA_SCRIPT = "classpath:schema.sql";

    public final static String OAUTH_CLIENT_ID = "${security.oauth2.client.client-id}";
    public final static String OAUTH_CLIENT_SECRET = "${security.oauth2.client.client-secret}";
    public final static String OAUTH_CLIENT_GRANT_TYPES = "${security.oauth2.client.authorized-grant-types}";
    public final static String OAUTH_CLIENT_SCOPE = "${security.oauth2.client.scope}";
    public final static String OAUTH_CLIENT_ACCESS_TOKEN_VALIDITY = "${security.oauth2.client.access-token-validity-seconds}";
    public final static String OAUTH_CLIENT_REFRESH_TOKEN_VALIDITY = "${security.oauth2.client.refresh-token-validity-seconds}";

    public final static String USER_AUTHORITY = "USER";

    public final static String AUTH_EMAIL_MANDATORY = "auth.email.mandatory";
    public final static String AUTH_EMAIL_INVAID = "auth.email.invalid";
    public final static String AUTH_PASSWORD_MANDATORY = "auth.password.mandatory";
    public final static String AUTH_PASSWORD_INVALID = "auth.password.invalid";

    public final static String SUCCESS_MESSAGE = "success";
    public final static String NULL = null;
}
