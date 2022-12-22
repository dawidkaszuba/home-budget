insert into categories(name, category_type) values('paliwo', 'EXPENSE');
insert into categories(name, category_type) values('żywność', 'EXPENSE');
insert into categories(name, category_type) values('wypłata', 'INCOME');
insert into categories(name, category_type) values('B2B', 'INCOME');

insert into users(first_name, last_name) values("Dawid", "Kaszuba");

insert into expenses(time,last_edit_time, user_id, category_id, value) values('2022-12-12 19:00','2022-12-12 19:00', 1, 1, 100.99);
insert into expenses(time,last_edit_time, user_id, category_id, value) values('2022-12-12 18:45','2022-12-12 19:00', 1, 2, 545.67);

insert into incomes(create_time,last_edit_time, user_id, category_id, value) values('2022-12-01 10:00','2022-12-01 10:00', 1, 3, 10000);
insert into incomes(create_time,last_edit_time, user_id, category_id, value) values('2022-12-01 10:45','2022-12-01 10:45', 1, 4, 5000);


