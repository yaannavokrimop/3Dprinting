CREATE INDEX user_index ON users (id);
CREATE INDEX order_index ON orders (id);
CREATE INDEX message_index ON message (id);
CREATE INDEX response_index ON responses (order_id, executor_id);
CREATE INDEX equipment_index ON equipment (equip_name);
CREATE INDEX material_index ON material (mat_title);