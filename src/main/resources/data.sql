INSERT INTO cartridge_model (cartridge_model)
VALUES ('78');

INSERT INTO cartridge (deregistration_date, registration_date, serial_number, cartridge_model_id, state_cartridge)
VALUES (null, '2021-07-14', '1111', 1, 3);

INSERT INTO cartridge (deregistration_date, registration_date, serial_number, cartridge_model_id, state_cartridge)
VALUES (null, '2021-07-15', '2222', 1, 6);

INSERT INTO cartridge_model (cartridge_model)
VALUES ('12');

INSERT INTO cartridge (deregistration_date, registration_date, serial_number, cartridge_model_id, state_cartridge)
VALUES (null, '2021-07-15', '3333', 2, 3);

INSERT INTO cartridge (deregistration_date, registration_date, serial_number, cartridge_model_id, state_cartridge)
VALUES (null, '2021-07-16', '4444', 2, 6);

INSERT INTO consumer (name_of_consumer)
VALUES ('207_кабинет');

INSERT INTO consumer (name_of_consumer)
VALUES ('103_кабинет');

INSERT INTO order_for_consumer (order_date, consumer_id)
VALUES ('now', 1);

INSERT INTO status_cartridge_after_consumer (status, is_good_status)
VALUES ('Пустой', true);

INSERT INTO status_cartridge_after_consumer (status, is_good_status)
VALUES ('Брак, полосит', false);

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
