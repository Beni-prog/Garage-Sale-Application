CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `account_status` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `receipt_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `receipt_id` (`receipt_id`)
);


CREATE TABLE `cards` (
  `id` bigint NOT NULL,
  `cvv` varchar(255) DEFAULT NULL,
  `exp_date` tinyblob,
  `name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `order_request_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_request_id` (`order_request_id`)
);

CREATE TABLE `products` (
  `id` bigint NOT NULL,
  `category` int DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nr_of_items` int NOT NULL,
  `price` double NOT NULL,
  `order_id` bigint DEFAULT NULL,
  `order_request_id` bigint DEFAULT NULL,
  `receipt_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `order_request_id` (`order_request_id`),
  KEY `receipt_id` (`receipt_id`)
);


CREATE TABLE `product_issues` (
  `product_id` bigint NOT NULL,
  `issues` varchar(255) DEFAULT NULL,
  KEY `product_id` (`product_id`)
);

CREATE TABLE `order_request` (
  `id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `orders` (
  `id` bigint NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `status` int DEFAULT NULL,
  `total_amount` double NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
);

CREATE TABLE `receipts` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint NOT NULL,
  `status` int DEFAULT NULL,
  `exp_date` tinyblob,
  `card_number` varchar(255) DEFAULT NULL,
  `sum` double DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
);


CREATE TABLE `receipt_purchased_assets` (
  `receipt_id` bigint NOT NULL,
  `purchased_assets` varchar(255) DEFAULT NULL,
  KEY `receipt_id` (`receipt_id`)
);

CREATE TABLE `receipt_product_out_of_stock_purchased_products` (
  `receipt_product_out_of_stock_id` bigint NOT NULL,
  `purchased_products` varchar(255) DEFAULT NULL,
  KEY `receipt_product_out_of_stock_id` (`receipt_product_out_of_stock_id`)
);

CREATE TABLE `receipt_same_category_purchased_products` (
  `receipt_same_category_id` bigint NOT NULL,
  `purchased_products` varchar(255) DEFAULT NULL,
  KEY `receipt_same_category_id` (`receipt_same_category_id`)
);

CREATE TABLE `receipt_successful_order_purchased_products` (
  `receipt_successful_order_id` bigint NOT NULL,
  `purchased_products` varchar(255) DEFAULT NULL,
  KEY `receipt_successful_order_id` (`receipt_successful_order_id`)
);