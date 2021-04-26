package com.vavv.web.filter;

import com.vavv.web.Utilz;
import com.vavv.web.model.User;
import com.vavv.web.model.UserRole;
import com.vavv.web.repository.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

public class JwtCheckFilter implements Filter {

    private String jwtEncryptSecret;

    private UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("\n\n$$$$$$$$$ JWT Filter Initialization\n\n");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        System.out.println("\n\n\nJwtCheckFilter happening..!!!: " + jwtEncryptSecret);
        HttpServletRequest hsr = (HttpServletRequest) servletRequest;

        String requestedUri = hsr.getRequestURI();
        String authHeaderVal = hsr.getHeader(Utilz.KR_AUTHORIZATION_HEADER);

        System.out.println("requested uri: " + requestedUri
                + "\nauthHeaderVal: " + authHeaderVal + "\n");

        /*ArrayList<String> headNames = Collections.list(hsr.getHeaderNames());
        for (String hn : headNames) {
            System.out.println("Header: " + hn + " = " + hsr.getHeader(hn));
        }

        System.out.println("\n");*/

        /*System.out.println("\nhsr.getParameterMap(): " + hsr.getParameterMap()
            + "\nSize: " + hsr.getParameterMap().size());*/

        for (Map.Entry<String, String[]> entry : hsr.getParameterMap().entrySet())
            System.out.println("Parameter: " + entry + " = " + entry.getValue().toString());

        if (requestedUri.equalsIgnoreCase("/login/signin"))
            filterChain.doFilter(servletRequest, servletResponse);
        else if (!StringUtils.hasText(authHeaderVal)) // Auth header does not exist.
            writeErrorResponse(servletResponse, "Please Login");

        else {

            boolean jwtParsed = false;
            User userFromJwt = null;

            try {
                userFromJwt = Utilz.parseJwt(authHeaderVal, jwtEncryptSecret); // Parse JWT
                jwtParsed = true;
            } catch (Exception e) { // Expired or Bad JWT
                System.out.println("Bad JWT: " + e);
                writeErrorResponse(servletResponse, "Please Login");
            }

            if (jwtParsed) { // JWT parsed without errors

                User userFromRepo = userRepository.findFirstByGuid(userFromJwt.getGuid());

                System.out.println("\n\nUser from repo: " + userFromRepo);

                if (userFromRepo == null || !StringUtils.hasText(userFromRepo.getUsername()))
                    writeErrorResponse(servletResponse, "Bad user..."); // User does not exist in database.
                else {

                    if (userFromJwt.getRole().equals(UserRole.SUPER_ADMIN)
                            || userFromJwt.getRole().equals(UserRole.GROUP_ADMIN)) {

                        System.out.println("\n Super admin user");
                        servletRequest.setAttribute(Utilz.JWT_USER_PROP, userFromJwt);
                        filterChain.doFilter(servletRequest, servletResponse);

                    } else if (requestedUri.contains(Utilz.UPDATE_PICK_DELIVERY_DATE)
                            || requestedUri.contains(Utilz.GET_ORDERS)) {

                        System.out.println("\n update date or getOrders");
                        servletRequest.setAttribute(Utilz.JWT_USER_PROP, userFromJwt);
                        filterChain.doFilter(servletRequest, servletResponse);

                    } else {
                        writeErrorResponse(servletResponse, "Bad Request");
                    }

                }
            }
        }


    }

    private void writeErrorResponse(ServletResponse response, String error) throws IOException {
        JSONObject jo = new JSONObject();
        jo.put("success", false);
        jo.put("error", error);
        response.getWriter().write(jo.toJSONString());
    }


    @Override
    public void destroy() {

    }

    public void setJwtEncryptSecret(String jwtEncryptSecret) {
        this.jwtEncryptSecret = jwtEncryptSecret;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}

