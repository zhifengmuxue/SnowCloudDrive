create database snow_cloud_drive;
use snow_cloud_drive;
create user 'zfmx'@'%' identified by '123456';
grant all privileges on snow_cloud_drive.* to 'zfmx'@'%';

CREATE TABLE file_folder (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             folder_name VARCHAR(255) NOT NULL,
                             path VARCHAR(255) NOT NULL,
                             owner_id INT,
                             parent_id INT,
                             create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                             update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE file (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      filename VARCHAR(255) NOT NULL,
                      path VARCHAR(255) NOT NULL,
                      size VARCHAR(50) NOT NULL,
                      owner_id INT,
                      folder_id INT,
                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (folder_id) REFERENCES file_folder(id) ON DELETE CASCADE
);


INSERT INTO file_folder (folder_name, path)
VALUES
    ('文件夹1', '/path/to/文件夹1'),
    ('文件夹2', '/path/to/文件夹2');

INSERT INTO file (filename, path, size, create_time, update_time)
VALUES
    ('文件名.txt', '/path/to/文件名.txt', '15 KB', NOW(), NOW()),
    ('图片.png', '/path/to/图片.png', '200 KB', NOW(), NOW()),
    ('文档.pdf', '/path/to/文档.pdf', '1.2 MB', NOW(), NOW());

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