create database snow_cloud_drive;
use snow_cloud_drive;
create user 'zfmx'@'%' identified by '123456';
grant all privileges on snow_cloud_drive.* to 'zfmx'@'%';