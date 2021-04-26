package com.vavv.web.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vavv.web.Utilz;
import com.vavv.web.model.MaterialType;
import com.vavv.web.model.Order;
import com.vavv.web.model.OrderType;
import com.vavv.web.model.User;
import com.vavv.web.repository.OrderRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository or) {
        this.orderRepository = or;
    }

    @RequestMapping("/hello")
    public String sayHello() {

        System.out.println("in say ");
        return "order-hello";
    }

    @RequestMapping(value = Utilz.ADD_ORDER, method = RequestMethod.POST)
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {

        System.out.println("Add order: " + order);
        Order so = orderRepository.saveAndFlush(order);
        System.out.println("saved o: " + so);

        return new ResponseEntity(so, HttpStatus.OK);
    }

    @RequestMapping(value = Utilz.UPDATE_ORDER, method = RequestMethod.POST)
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        System.out.println("Update order: " + order);
        return new ResponseEntity(new Order(), HttpStatus.OK);
    }

    @RequestMapping(value = Utilz.UPDATE_PICK_DELIVERY_DATE, method = RequestMethod.POST)
    public ResponseEntity<Order> updatePickDeliverDate(@RequestBody Order order) {
        System.out.println("Update pickDelivery date of order: " + order);
        return new ResponseEntity(new Order(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrders(@RequestParam(value = "from") Date from,
                                                 @RequestParam(value = "to") Date to) {

        System.out.println("From : " + from + "\tTo: " + to);
//        List<Order> orders = orderRepository.findByOrderDateBetween(from, to);
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @RequestMapping(value = Utilz.GET_ALL_ORDERS, method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getAllOrders() {

        LocalDate toDate = LocalDate.now().plusDays(25);
        LocalDate fromDate = LocalDate.now().minusDays(10);

        return this.getAllOrdersDateRange(java.sql.Date.valueOf(fromDate), java.sql.Date.valueOf(toDate));


    }

    @RequestMapping(value = Utilz.GET_ALL_ORDERS_DATE_RANGE, method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getAllOrdersDateRange(
            @RequestParam(value = "from") @DateTimeFormat(pattern = "MM/dd/yyyy") Date from,
            @RequestParam(value = "to") @DateTimeFormat(pattern = "MM/dd/yyyy") Date to) {
    /*public ResponseEntity<List<Order>> getAllOrdersDateRange(
            @RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam(value = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to) {*/

        System.out.println("From : " + from + "\tTo: " + to);

        List<Order> orders = orderRepository.findByPickupOrDeliverDateBetween(from, to);
        System.out.println("\n#Orders: " + orders.size() + "\n");

        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public User getGreeting(@RequestParam(value = "name", defaultValue = "globe") String name) {
        System.out.println("Name sent: " + name);
        return new User();
    }

    @RequestMapping(value = "/dateExperi", method = RequestMethod.GET)
    public ResponseEntity getDateExperiment() {
        System.out.println("In getDateExperiment");
        return new ResponseEntity(new Object() {
            public final boolean success = true;
            @JsonFormat(pattern = "yyyy-MM-dd", timezone = "MST")
            public final Date dt = new Date();
            public final String str = "some string";
            public final char c = 'c';
            public final int n = 1234;
            public final long l = 333l;
            public final double d = 3.3d;
        }, HttpStatus.OK);
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
}
