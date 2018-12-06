package com.estore;

/**
 * Created by Ashutosh on 28 Jun, 2017.
 */
public class QueryConstant {

    public static String USER_QUERY = "select email_id ,password, true, user_id, mobile_no, first_name, last_name " +
            "from user " +
            "where email_id = ?";

    public static String USER_ROLE_QUERY = "select email_id, 'USER' from user where email_id=?";
}
