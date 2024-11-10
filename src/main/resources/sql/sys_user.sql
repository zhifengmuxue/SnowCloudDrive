CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(100),
    is_enable BOOLEAN DEFAULT TRUE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

insert into sys_user (username, password, role, is_enable) values
('admin', '$2a$10$Tn.bIqJR2edyvPgDm2w43u6SnA3KkJuAHnT4.2G8s2fK2TxLQUtty', 'admin',true);