INSERT INTO `sys_config` (`config_name`, `config_value`) VALUES ('emailTo', 'xxxx@xxx;');
INSERT INTO `sys_config` (`config_name`, `config_value`) VALUES ('authCountHistory@1169272d0d1340d09a02', 'xxx');
INSERT INTO `sys_config` (`config_name`, `config_value`) VALUES ('delCountHistory@1169272d0d1340d09a02', 'xxx');
INSERT INTO `sys_config` (`config_name`, `config_value`) VALUES ('emailCron', '0 0 9 * * ?');

ALTER TABLE sys_config MODIFY COLUMN config_value varchar(1024) DEFAULT NOT NULL;

update `sys_config` set `config_value` = 'hatafan@webank.com;kxzhang@webank.com;andyzywang@webank.com;graysonzhang@webank.com;jiayumao@webank.com;wesleywang@webank.com;zonazhang@webank.com;alexdeng@webank.com' where `config_name` = 'emailTo';

