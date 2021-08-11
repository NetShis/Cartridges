INSERT INTO cartridge_model (cartridge_model)
VALUES ('78');

INSERT INTO cartridge (deregistration_date, registration_date, serial_number, cartridge_model_id)
VALUES (null, '2021-07-14', '78_1', 1);

INSERT INTO cartridge (deregistration_date, registration_date, serial_number, cartridge_model_id)
VALUES (null, '2021-07-15', '78_2', 1);

INSERT INTO cartridge_model (cartridge_model)
VALUES ('12');

INSERT INTO cartridge (deregistration_date, registration_date, serial_number, cartridge_model_id)
VALUES (null, '2021-07-15', '12_3', 2);

INSERT INTO cartridge (deregistration_date, registration_date, serial_number, cartridge_model_id)
VALUES (null, '2021-07-16', '12_4', 2);

INSERT INTO consumer (name_of_consumer)
VALUES ('207_кабинет');

INSERT INTO consumer (name_of_consumer)
VALUES ('103_кабинет');

INSERT INTO order_for_consumer (order_date, consumer_id)
VALUES ('now', 1);

INSERT INTO status_cartridge_after_consumer (status)
VALUES ('Пустой');

INSERT INTO status_cartridge_after_consumer (status)
VALUES ('Брак, полосит');

INSERT INTO status_cartridge_after_refueller (status)
VALUES ('Заправлен');

INSERT INTO cartridge_for_consumer (date_the_cartridge_was_return, cartridge_id, status_cartridge_after_consumer_id,
                                    order_for_consumer_id)
VALUES (null, 1, null, 1);

INSERT INTO cartridge_for_consumer (date_the_cartridge_was_return, cartridge_id, status_cartridge_after_consumer_id,
                                    order_for_consumer_id)
VALUES (null, 3, null, 1);

INSERT INTO refueller (name_of_refueller, is_default)
VALUES ('Коковкин', true);

INSERT INTO refueller (name_of_refueller, is_default)
VALUES ('Муравьев', false);
