package com.vavv.web.controller;

import com.vavv.web.Utilz;
import com.vavv.web.model.User;
import com.vavv.web.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userRepository;

    @Value("${jwt.encode.param}")
    private String jwtEncodeParam;

    public LoginController(UserRepository ur) {
        this.userRepository = ur;
    }

    @RequestMapping("/hello")
    public String sayHello() {

        System.out.println("in say ");
        return "login-hello";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user) {

        System.out.println("User submitted account: " + user);

        // validate posted values.
        if (user == null || user.getUsername() == null || user.getPassword() == null
                || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword()))
            return new ResponseEntity(new Object() {
                public final boolean success = false;
                public final String error = Utilz.BAD_CREDENTIALS;
            }, HttpStatus.OK);

        // query userRepository with username, pass
        List<User> users = userRepository.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (users == null || users.size() <= 0)
            return new ResponseEntity(new Object() {
                public final boolean success = false;
                public final String error = Utilz.BAD_CREDENTIALS;
            }, HttpStatus.OK);

        // create JWT token with role, userId
        User foundUser = users.get(0);
        String jwt = createJWT(foundUser);

        // create ResponseEntity with success to true and JWT token.
        HttpHeaders headers = new HttpHeaders();
        headers.add("1", "uno");
        return new ResponseEntity(new Object() {
            public final boolean success = true;
            public final String tok = jwt;
        }, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public ResponseEntity getAllUsers() {

        List<User> users = userRepository.findAllActiveUsers();
        System.out.println("\n#Users: " + users.size() + "\n");

        return new ResponseEntity(users, HttpStatus.OK);
    }


    private String createJWT(User user) {

        Claims claims = Jwts.claims();

        Map<String, Object> map = new HashMap<>();
        map.put(Utilz.JWT_USER_ROLE_PROP, user.getRole().toString());
        map.put(Utilz.JWT_USER_GUID_PROP, user.getGuid());
        claims.put(Utilz.JWT_USER_PROP, map);

        claims.setSubject(Utilz.JWT_SUB);
        claims.setExpiration(new Date(new Date().getTime() + Utilz.JWT_EXPIRATION_TIME)); // expires in 1 day
        claims.setIssuedAt(new Date());

        String builtJwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtEncodeParam)
                .compact();

        System.out.println("t" + builtJwt + "w");

        return builtJwt;
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public User getGreeting(@RequestParam(value = "name", defaultValue = "globe") String name) {
        System.out.println("Name sent: " + name);
        return new User();
    }

    @RequestMapping(value = "/signin-anonymous-obj-return", method = RequestMethod.POST)
    public ResponseEntity signinAnonymousObjectReturnSample(@RequestBody User user) {
        System.out.println("User: " + user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("1", "uno");
        return new ResponseEntity(new Object() {
            public final boolean success = true;
            public final Date dt = new Date();
            public final String str = "some string";
            public final char c = 'c';
            public final int n = 1234;
            public final long l = 333l;
            public final double d = 3.3d;
        }, headers, HttpStatus.OK);
    }

    private String hashPassword(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt(12));
    }

    private boolean checkPassword(String pass, String hashedPass) {
        return BCrypt.checkpw(pass, hashedPass);
    }
}
