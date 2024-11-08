CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    role VARCHAR(100),
    is_enable BOOLEAN DEFAULT TRUE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

insert into sys_user (username, password, role,email, is_enable) values
('admin', '{bcrypt}$2a$10$MTB706BWlb/tyqvmnInv2eLJDy9eRTnigYWXPREhUImCJygXff7aW', 'admin', 'zfmx@gmail.com',true);