package com.vavv.web;

import com.vavv.web.model.User;
import com.vavv.web.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class Utilz implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String JWT_SUB = "krUserJwt";
    public static final String JWT_USER_PROP = "userData";
    public static final String JWT_USER_ROLE_PROP = "role";
    public static final String JWT_USER_GUID_PROP = "guid";
    public static final long JWT_EXPIRATION_TIME = 86400000L; // 1 day

    public static final String BAD_CREDENTIALS = "Bad username or password";

    public static final String KR_AUTHORIZATION_HEADER = "authorization";

    public static final String UPDATE_PICK_DELIVERY_DATE = "/updatePickDeliverDate";
    public static final String ADD_ORDER = "/addOrder";
    public static final String UPDATE_ORDER = "/updateOrder";
    public static final String GET_ALL_ORDERS = "/getAllOrders";
    public static final String GET_ALL_ORDERS_DATE_RANGE = "/getAllOrdersDateRange";
    public static final String GET_ORDERS = "/getOrders";

    public static final Map<Integer, String> CRON_JOB_PARAM_TYPE_VALS_MAP = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 1L;

        {
            put(1, "Date");
        }
    };

    public static User parseJwt(String jwt, String secret) throws Exception {

        System.out.println("parsing jwt");

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt);

        /*System.out.println("claims: " + claims);
        System.out.println("claims signature: " + claims.getSignature());
        System.out.println("claims body: " + claims.getBody());
        System.out.println("claims body sub: " + claims.getBody().getSubject());
        System.out.println("claims header: " + claims.getHeader());
        System.out.println("claims userData: " + claims.getBody().get(Utilz.JWT_USER_PROP));*/

        Map dataMap = (Map) claims.getBody().get(Utilz.JWT_USER_PROP);
        System.out.println("map: " + dataMap);

        User user = new User();
        user.setGuid(dataMap.get(JWT_USER_GUID_PROP).toString());
        user.setRole(UserRole.valueOf(dataMap.get(JWT_USER_ROLE_PROP).toString()));
        System.out.println(user);

        return user;

    }

}
