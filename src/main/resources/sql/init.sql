USE wangxin;

-- 用户账户表 --
CREATE TABLE user_account_info (
  id INT(11) NOT NULL AUTO_INCREMENT,
  user_uuid VARCHAR(255) NOT NULL ,
  total_assets DECIMAL NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 借款订单表 --
CREATE TABLE borrow_order (
  id INT(11) NOT NULL AUTO_INCREMENT,
  borrow_order_uuid VARCHAR(255) NOT NULL ,
  user_uuid VARCHAR(255) NOT NULL ,
  borrow_amount DECIMAL NOT NULL ,
  product_id INT(11) NOT NULL ,
  state TINYINT(4) NOT NULL ,
  created_time TIMESTAMP DEFAULT current_timestamp,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 支付记录表 --
CREATE TABLE pay_order_notify (
  id INT(11) NOT NULL AUTO_INCREMENT,
  pay_order_uuid VARCHAR(255) NOT NULL DEFAULT '',
  borrow_order_uuid VARCHAR(255) NOT NULL ,
  user_uuid VARCHAR(255) NOT NULL ,
  borrow_amount DECIMAL NOT NULL ,
  product_id INT(11) NOT NULL ,
  created_time TIMESTAMP DEFAULT current_timestamp,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

