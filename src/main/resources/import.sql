insert into budget_user(first_name, last_name, user_name, password, roles) values('Dawid', 'Kaszuba', 'dkaszuba', '$2a$12$BpjOXITCwRiucibTA.5UEupmp6AaNKFDxiP2tDde.MvvPziSKSWgu', 'ROLE_ADMIN, ROLE_USER');

insert into categories(name, category_type, budget_user_id) values('paliwo', 'EXPENSE', 1);
insert into categories(name, category_type, budget_user_id) values('żywność', 'EXPENSE', 1);
insert into categories(name, category_type, budget_user_id) values('wypłata', 'INCOME', 1);
insert into categories(name, category_type, budget_user_id) values('B2B', 'INCOME', 1);

insert into expenses(creation_time,last_edit_time, budget_user_id, category_id, value) values('2023-01-02 19:00','2023-01-02 19:00', 1, 1, 100.99);
insert into expenses(creation_time,last_edit_time, budget_user_id, category_id, value) values('2023-01-02 18:45','2023-01-02 19:00', 1, 2, 545.67);

insert into incomes(creation_time,last_edit_time, budget_user_id, category_id, value) values('2023-01-01 10:00','2023-12-02 10:00', 1, 3, 10000);
insert into incomes(creation_time,last_edit_time, budget_user_id, category_id, value) values('2023-01-01 10:45','2023-12-02 10:45', 1, 4, 5000);


