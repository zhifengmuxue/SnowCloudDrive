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

