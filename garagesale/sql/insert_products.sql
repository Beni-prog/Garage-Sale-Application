update products set nr_of_items=5, order_id=null
where id=1

insert into products(id, code, name, price, nr_of_items, category)
values (1, '111', 'Laptop Asus', 700, 10, 0);

insert into product_issues(product_id, issues)
values (1, '');

insert into products (id, code, name, price, nr_of_items, category)
values (2, '112', 'iPhone', 900, 15, 4);

insert into product_issues(product_id, issues)
values (2, '');

insert into products (id, code, name, price, nr_of_items, category)
values (3, '113', 'LG Monitor', 400, 13, 1);

insert into product_issues(product_id, issues)
values (3, '');

insert into products (id, code, name, price, nr_of_items, category)
values (4, '114', 'Samsung TV', 600, 12, 2);

insert into product_issues(product_id, issues)
values (4, '');

insert into products (id, code, name, price, nr_of_items, category)
values (5, '115', 'ASUS Projector', 680, 15, 3);

insert into product_issues(product_id, issues)
values (5, '');

insert into products (id, code, name, price, nr_of_items, category)
values (6, '116', 'Lenovo Tablet', 370, 10, 5);

insert into product_issues(product_id, issues)
values (6, '');