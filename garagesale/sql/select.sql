select * from orders, products, users
where orders.user_id=users.id AND orders.id=products.order_id