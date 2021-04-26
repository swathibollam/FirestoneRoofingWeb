CREATE TABLE
  USER
(
  guid        CHAR(40)                         NOT NULL,
  first_name  VARCHAR(25)                      NOT NULL,
  last_name   VARCHAR(25)                      NOT NULL,
  license_num INT(20)                          NOT NULL,
  dob         DATE,
  username    VARCHAR(20)                      NOT NULL,
  password    VARCHAR(100)                     NOT NULL,
  role        VARCHAR(20) DEFAULT 'CONTRACTOR' NOT NULL,
  email       VARCHAR(40),
  active      BIT DEFAULT b'1' NOT NULL,
  PRIMARY KEY (guid),
  CONSTRAINT username_unique_constraint UNIQUE (username)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE
  orders
(
  guid                   CHAR(40)         NOT NULL,
  purchase_order_number  INT              NOT NULL,
  job_name               VARCHAR(20)      NOT NULL,
  material_type          VARCHAR(12)      NOT NULL,
  order_type             VARCHAR(10)      NOT NULL,
  product_type           VARCHAR(50)      NOT NULL,
  order_date             DATETIME         NOT NULL,
  pickup_or_deliver_date DATETIME         NOT NULL,
  address_line                   VARCHAR(30)      NOT NULL,
  city                   VARCHAR(30)      NOT NULL,
  state                  VARCHAR(30)      NOT NULL,
  order_placed           BIT DEFAULT b'1' NOT NULL,
  is_picked_or_delivered BIT DEFAULT b'0' NOT NULL,
  completed_date         DATETIME,
  note                   VARCHAR(100),
  user_id                CHAR(40)         NOT NULL,
  sales_order_number     INT              NOT NULL,
  PRIMARY KEY (guid),
  CONSTRAINT fkUserKey FOREIGN KEY (user_id) REFERENCES `user` (`guid`),
  INDEX fkUserKey (user_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE
  ORDER_CONFIRMATION
(
  id           INT              NOT NULL AUTO_INCREMENT,
  confirmed    BIT DEFAULT b'0' NOT NULL,
  confirmed_at DATETIME         NOT NULL,
  prior_days   INT              NOT NULL,
  order_id     CHAR(40)         NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES `orders` (`guid`),
  INDEX fk_order_id_idx (order_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO user (guid, first_name, last_name, license_num, dob, username, password)
VALUES ('10645c4a-cc25-11e7-acdc-96395d26a8d8', 'Michael', 'Jordan', 9483336, '1976-10-10', 'mjordan', 'jordan');
INSERT INTO user (guid, first_name, last_name, license_num, dob, username, password)
VALUES ('36ce3c3e-cc25-11e7-acdc-96395d26a8d8', 'Lebron', 'James', 9487026, '1976-10-15', 'ljames', 'ljames');
INSERT INTO user (guid, first_name, last_name, license_num, dob, username, password)
VALUES ('77888298-cc25-11e7-acdc-96395d26a8d8', 'Trisha', 'Yearwood', 111111, '1976-10-20', 'tyearwood', 'yearwood');

# Dummy data in to orders table
INSERT
INTO
  test.orders
  (
    orders.guid,
    orders.purchase_order_number,
    orders.job_name,
    orders.material_type,
    orders.order_type,
    orders.order_date,
    orders.pickup_or_deliver_date,
    orders.city,
    orders.order_placed,
    orders.is_picked_or_delivered,
    orders.note,
    orders.user_id,
    orders.sales_order_number
  )
VALUES
  (
    uuid(),
    (ROUND(RAND() * 400000-3000) + 3000), -- ROUND((RAND() * (max-min))+min)
    ELT(0.5 + RAND() * 2, 'SUPERIOR', 'RED'),
    ELT(0.5 + RAND() * 4, 'insulation','metal','membrane','skylites'),
    ELT(0.5 + RAND() * 2, 'Delivery','Pickup'),
    DATE_ADD(utc_timestamp(), INTERVAL -6*rand() DAY),
    DATE_ADD(utc_timestamp(), INTERVAL 4*rand() DAY),
    ELT(0.5 + RAND() * 6, 'North Dakota','Montana','Denver','Salt Lake City', 'Los Angeles', 'San Francisco'),
    b'1',
    b'0',
    ELT(0.5 + RAND() * 2, 'Knowledge is power.',''),
    ELT(0.5 + RAND() * 3, '10645c4a-cc25-11e7-acdc-96395d26a8d8','36ce3c3e-cc25-11e7-acdc-96395d26a8d8', '77888298-cc25-11e7-acdc-96395d26a8d8'),
    (ROUND(RAND() * 6000000-3000) + 3000) -- ROUND((RAND() * (max-min))+min)
  );
