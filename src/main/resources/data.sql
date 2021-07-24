INSERT INTO сartridge_model (id, cartridge_model, required_limit_in_stock)
VALUES(0, '78', 10);

INSERT INTO cartridge (id, is_decommissioned, registration_date, serial_number, cartridge_model_id)
VALUES(0, false, '2021-07-14', '787878', 0);

INSERT INTO сartridge_model (id, cartridge_model, required_limit_in_stock)
VALUES(1, '12', 10);

INSERT INTO cartridge (id, is_decommissioned, registration_date, serial_number, cartridge_model_id)
VALUES(1, false, '2021-07-15', '121212', 1);

INSERT INTO consumer (id, name_of_consumer)
VALUES(0, '207_кабинет');

INSERT INTO order_for_consumer (id, order_date, consumer_id)
VALUES(0, 'now', 0);

INSERT INTO сartridge_status (id, status)
VALUES(0, 'Пустой');

INSERT INTO сartridge_status (id, status)
VALUES(1, 'Брак, полосит');

INSERT INTO list_cartridge_for_consumer (id, date_the_cartridge_was_return, cartridge_id, cartridge_status_id, order_for_consumer_id)
VALUES(0, null, 1, null, 0);
