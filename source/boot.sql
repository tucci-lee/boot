/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : boot

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 23/02/2022 14:54:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '部门名称',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级id，0是顶级部门',
  `seq` int(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `manager` varchar(20) NOT NULL COMMENT '部门管理人',
  `manager_phone` varchar(11) NOT NULL COMMENT '管理人手机',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_name` (`name`),
  KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表，一个用户只能绑定一个部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (24169813928574976, '总部', 0, 0, 'Tucci', '13333333333', 1644560698135, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL,
  `username` varchar(10) NOT NULL COMMENT '登录账号',
  `os` varchar(20) NOT NULL COMMENT '操作系统',
  `browser` varchar(20) NOT NULL COMMENT '浏览器',
  `ip` varchar(15) NOT NULL COMMENT 'ip地址',
  `create_time` bigint(13) NOT NULL COMMENT '登录时间',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '登录状态\n0-失败\n1-成功',
  `message` varchar(1000) DEFAULT NULL COMMENT '信息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_username` (`username`),
  KEY `idx_ip` (`ip`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_login_log` VALUES (25975012993269760, 'admin', 'Mac OS X', 'Chrome 9', '39.65.235.63', 1644991091131, 1, '');
INSERT INTO `sys_login_log` VALUES (26409697430994944, 'admin', 'Windows 10', 'Chrome 9', '121.225.44.3', 1645094727976, 1, '');
COMMIT;

-- ----------------------------
-- Table structure for sys_login_version
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_version`;
CREATE TABLE `sys_login_version` (
  `uid` bigint(20) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '每次修改密码，锁定等都会+1',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户版本号，根据当前的版本号判断当前用户登录是否过期。比如：修改密码、用户锁定、用户删除，都会将当前的版本号加1，如果当前登录的用户信息低于数据库的版本号，说明登录过期';

-- ----------------------------
-- Records of sys_login_version
-- ----------------------------
BEGIN;
INSERT INTO `sys_login_version` VALUES (1, 0, 1609852591000, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log` (
  `id` bigint(20) NOT NULL,
  `username` varchar(20) NOT NULL COMMENT '操作人账号',
  `ip` varchar(15) NOT NULL COMMENT '用户ip',
  `url` varchar(1000) NOT NULL COMMENT '请求URL',
  `method` varchar(1000) NOT NULL COMMENT '执行方法',
  `params` text COMMENT '参数',
  `result` text COMMENT '执行成功后的返回信息',
  `description` varchar(20) NOT NULL COMMENT '描述',
  `error_message` text COMMENT '执行失败后的异常信息',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `status` tinyint(1) NOT NULL COMMENT '执行状态\n0-失败\n1-成功',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_status` (`status`),
  KEY `idx_username` (`username`),
  KEY `idx_ip` (`ip`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_operate_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '资源名称',
  `type` int(1) NOT NULL COMMENT '类型\n1-菜单\n2-权限',
  `url` varchar(100) DEFAULT NULL COMMENT 'url',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级id，0是顶级目录',
  `res_char` varchar(50) DEFAULT NULL COMMENT '资源字符',
  `seq` int(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_type` (`type`),
  KEY `idx_is_deleted` (`is_deleted`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源';

-- ----------------------------
-- Records of sys_res
-- ----------------------------
BEGIN;
INSERT INTO `sys_res` VALUES (28174904473944064, '定时任务', 1, '/monitor/task', 113329647516647424, NULL, 0, 1645515586131, NULL, 0);
INSERT INTO `sys_res` VALUES (28175098825408512, '定时任务查询', 2, NULL, 28174904473944064, 'task:list', 0, 1645515632468, NULL, 0);
INSERT INTO `sys_res` VALUES (28516794918502401, '定时任务添加', 2, NULL, 28174904473944064, 'task:add', 0, 1645597099165, NULL, 0);
INSERT INTO `sys_res` VALUES (28516877999276032, '定时任务修改', 2, NULL, 28174904473944064, 'task:edit', 0, 1645597118973, NULL, 0);
INSERT INTO `sys_res` VALUES (28516935251525632, '定时任务删除', 2, NULL, 28174904473944064, 'task:delete', 0, 1645597132623, NULL, 0);
INSERT INTO `sys_res` VALUES (28517012728709120, '定时任务状态', 2, NULL, 28174904473944064, 'task:editStatus', 0, 1645597151095, NULL, 0);
INSERT INTO `sys_res` VALUES (28517103883517953, '定时任务执行', 2, NULL, 28174904473944064, 'task:start', 0, 1645597172828, NULL, 0);
INSERT INTO `sys_res` VALUES (112063924660076544, '系统管理', 1, NULL, 0, NULL, 98, 1610292596000, 1644341004058, 0);
INSERT INTO `sys_res` VALUES (112064419969630208, '资源管理', 1, '/sys/res', 112063924660076544, NULL, 2, 1610292714000, NULL, 0);
INSERT INTO `sys_res` VALUES (112064548923506688, '角色管理', 1, '/sys/role', 112063924660076544, NULL, 1, 1610292745000, NULL, 0);
INSERT INTO `sys_res` VALUES (112065408583860224, '用户管理', 1, '/sys/user', 112063924660076544, NULL, 0, 1610292949000, NULL, 0);
INSERT INTO `sys_res` VALUES (112304735788204032, '资源查询', 2, NULL, 112064419969630208, 'sys:res:list', 0, 1610350010000, NULL, 0);
INSERT INTO `sys_res` VALUES (112304837034508288, '资源添加', 2, NULL, 112064419969630208, 'sys:res:add', 0, 1610350034000, NULL, 0);
INSERT INTO `sys_res` VALUES (112304921109331968, '资源修改', 2, NULL, 112064419969630208, 'sys:res:edit', 0, 1610350054000, NULL, 0);
INSERT INTO `sys_res` VALUES (112305002013261824, '资源删除', 2, NULL, 112064419969630208, 'sys:res:delete', 0, 1610350073000, NULL, 0);
INSERT INTO `sys_res` VALUES (112766904753455104, '部门管理', 1, '/sys/dept', 112063924660076544, NULL, 3, 1610460199683, NULL, 0);
INSERT INTO `sys_res` VALUES (113296483343663104, '用户查询', 2, NULL, 112065408583860224, 'sys:user:list', 0, 1610586461058, NULL, 0);
INSERT INTO `sys_res` VALUES (113296609537687552, '用户添加', 2, NULL, 112065408583860224, 'sys:user:add', 0, 1610586491144, NULL, 0);
INSERT INTO `sys_res` VALUES (113296742077693952, '用户修改', 2, NULL, 112065408583860224, 'sys:user:edit', 0, 1610586522744, NULL, 0);
INSERT INTO `sys_res` VALUES (113296815033417728, '用户删除', 2, NULL, 112065408583860224, 'sys:user:delete', 0, 1610586540138, NULL, 0);
INSERT INTO `sys_res` VALUES (113296905349365760, '用户锁定', 2, NULL, 112065408583860224, 'sys:user:editLock', 0, 1610586561671, NULL, 0);
INSERT INTO `sys_res` VALUES (113297095770767360, '密码修改', 2, NULL, 112065408583860224, 'sys:user:editPassword', 0, 1610586607071, 1644910091737, 0);
INSERT INTO `sys_res` VALUES (113297279363842048, '角色查询', 2, NULL, 112064548923506688, 'sys:role:list', 0, 1610586650843, NULL, 0);
INSERT INTO `sys_res` VALUES (113297348314005504, '角色添加', 2, NULL, 112064548923506688, 'sys:role:add', 0, 1610586667282, NULL, 0);
INSERT INTO `sys_res` VALUES (113297416005877760, '角色修改', 2, NULL, 112064548923506688, 'sys:role:edit', 0, 1610586683421, NULL, 0);
INSERT INTO `sys_res` VALUES (113297479004323840, '角色删除', 2, NULL, 112064548923506688, 'sys:role:delete', 0, 1610586698441, NULL, 0);
INSERT INTO `sys_res` VALUES (113297703277953024, '部门查询', 2, NULL, 112766904753455104, 'sys:dept:list', 0, 1610586751912, NULL, 0);
INSERT INTO `sys_res` VALUES (113297768126087168, '部门添加', 2, NULL, 112766904753455104, 'sys:dept:add', 0, 1610586767373, NULL, 0);
INSERT INTO `sys_res` VALUES (113297841698373632, '部门修改', 2, NULL, 112766904753455104, 'sys:dept:edit', 0, 1610586784914, NULL, 0);
INSERT INTO `sys_res` VALUES (113297895578402816, '部门删除', 2, NULL, 112766904753455104, 'sys:dept:delete', 0, 1610586797761, NULL, 0);
INSERT INTO `sys_res` VALUES (113326873903104000, '日志管理', 1, NULL, 0, NULL, 99, 1610593706732, 1644341016577, 0);
INSERT INTO `sys_res` VALUES (113327217995415552, '登陆日志', 1, '/log/login', 113326873903104000, NULL, 0, 1610593788770, NULL, 0);
INSERT INTO `sys_res` VALUES (113328033569439744, '操作日志', 1, '/log/operate', 113326873903104000, NULL, 0, 1610593983218, 1644909981122, 0);
INSERT INTO `sys_res` VALUES (113329345681948672, '登录日志查询', 2, NULL, 113327217995415552, 'log:login:list', 0, 1610594296049, 1644910063283, 0);
INSERT INTO `sys_res` VALUES (113329497700302848, '操作日志查询', 2, NULL, 113328033569439744, 'log:operate:list', 0, 1610594332293, 1644910056332, 0);
INSERT INTO `sys_res` VALUES (113329647516647424, '监控管理', 1, NULL, 0, NULL, 97, 1610594368012, 1644506254576, 0);
INSERT INTO `sys_res` VALUES (113329855000477696, 'Druid监控', 1, '/monitor/druid', 113329647516647424, NULL, 0, 1610594417480, 1644910131574, 0);
INSERT INTO `sys_res` VALUES (113329976727568384, 'Druid监控查询', 2, NULL, 113329855000477696, 'monitor:druid:view', 0, 1610594446502, 1644910126589, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL,
  `role_char` varchar(20) DEFAULT NULL COMMENT '角色字符',
  `name` varchar(20) NOT NULL COMMENT '角色名',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (112650695508754432, 'root', '系统管理员', '', 1610432493240, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `res_id` bigint(20) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_role_res` (`role_id`,`res_id`) USING BTREE,
  KEY `idx_res_id` (`res_id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1241 DEFAULT CHARSET=utf8mb4 COMMENT='角色与资源关联表';

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_res` VALUES (1204, 112650695508754432, 28174904473944064);
INSERT INTO `sys_role_res` VALUES (1205, 112650695508754432, 28175098825408512);
INSERT INTO `sys_role_res` VALUES (1206, 112650695508754432, 28516794918502401);
INSERT INTO `sys_role_res` VALUES (1207, 112650695508754432, 28516877999276032);
INSERT INTO `sys_role_res` VALUES (1208, 112650695508754432, 28516935251525632);
INSERT INTO `sys_role_res` VALUES (1209, 112650695508754432, 28517012728709120);
INSERT INTO `sys_role_res` VALUES (1210, 112650695508754432, 28517103883517953);
INSERT INTO `sys_role_res` VALUES (1213, 112650695508754432, 112063924660076544);
INSERT INTO `sys_role_res` VALUES (1226, 112650695508754432, 112064419969630208);
INSERT INTO `sys_role_res` VALUES (1221, 112650695508754432, 112064548923506688);
INSERT INTO `sys_role_res` VALUES (1214, 112650695508754432, 112065408583860224);
INSERT INTO `sys_role_res` VALUES (1227, 112650695508754432, 112304735788204032);
INSERT INTO `sys_role_res` VALUES (1228, 112650695508754432, 112304837034508288);
INSERT INTO `sys_role_res` VALUES (1229, 112650695508754432, 112304921109331968);
INSERT INTO `sys_role_res` VALUES (1230, 112650695508754432, 112305002013261824);
INSERT INTO `sys_role_res` VALUES (1231, 112650695508754432, 112766904753455104);
INSERT INTO `sys_role_res` VALUES (1215, 112650695508754432, 113296483343663104);
INSERT INTO `sys_role_res` VALUES (1216, 112650695508754432, 113296609537687552);
INSERT INTO `sys_role_res` VALUES (1217, 112650695508754432, 113296742077693952);
INSERT INTO `sys_role_res` VALUES (1218, 112650695508754432, 113296815033417728);
INSERT INTO `sys_role_res` VALUES (1219, 112650695508754432, 113296905349365760);
INSERT INTO `sys_role_res` VALUES (1220, 112650695508754432, 113297095770767360);
INSERT INTO `sys_role_res` VALUES (1222, 112650695508754432, 113297279363842048);
INSERT INTO `sys_role_res` VALUES (1223, 112650695508754432, 113297348314005504);
INSERT INTO `sys_role_res` VALUES (1224, 112650695508754432, 113297416005877760);
INSERT INTO `sys_role_res` VALUES (1225, 112650695508754432, 113297479004323840);
INSERT INTO `sys_role_res` VALUES (1232, 112650695508754432, 113297703277953024);
INSERT INTO `sys_role_res` VALUES (1233, 112650695508754432, 113297768126087168);
INSERT INTO `sys_role_res` VALUES (1234, 112650695508754432, 113297841698373632);
INSERT INTO `sys_role_res` VALUES (1235, 112650695508754432, 113297895578402816);
INSERT INTO `sys_role_res` VALUES (1236, 112650695508754432, 113326873903104000);
INSERT INTO `sys_role_res` VALUES (1237, 112650695508754432, 113327217995415552);
INSERT INTO `sys_role_res` VALUES (1239, 112650695508754432, 113328033569439744);
INSERT INTO `sys_role_res` VALUES (1238, 112650695508754432, 113329345681948672);
INSERT INTO `sys_role_res` VALUES (1240, 112650695508754432, 113329497700302848);
INSERT INTO `sys_role_res` VALUES (1203, 112650695508754432, 113329647516647424);
INSERT INTO `sys_role_res` VALUES (1211, 112650695508754432, 113329855000477696);
INSERT INTO `sys_role_res` VALUES (1212, 112650695508754432, 113329976727568384);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` bigint(20) NOT NULL,
  `username` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `is_lock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定\n0-未锁定\n1-锁定',
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属部门id',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `idx_deptid` (`dept_id`) USING BTREE,
  KEY `idx_is_lock` (`is_lock`),
  KEY `idx_is_deleted` (`is_deleted`),
  KEY `idx_username` (`username`) USING BTREE,
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$B4zf3NseTLRLBTJkxd7/juY.7HFMXmcsC8xjBXRfTHr7Plw72ElBK', '13333333333', 'tucci@163.com', 0, '管理员', '系统管理员', 24169813928574976, 1609852591000, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_uid_role_id` (`uid`,`role_id`) USING BTREE,
  KEY `uk_uid_role` (`uid`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='用户关联的角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 112650695508754432);
COMMIT;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '任务名称',
  `class_name` varchar(200) NOT NULL COMMENT '类名',
  `cron` varchar(100) NOT NULL COMMENT 'cron表达式',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '任务状态\n0-未启动\n1-启动',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_is_deleted` (`is_deleted`),
  KEY `idx_name` (`name`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';

-- ----------------------------
-- Records of task
-- ----------------------------
BEGIN;
INSERT INTO `task` VALUES (28180210469830657, '测试', 'com.tuccicode.boot.app.task.job.TestJob', '*/5 * * * * ?', 0, NULL, 1645516851181, 1645596218393, 0);
COMMIT;

-- ----------------------------
-- Table structure for task_log
-- ----------------------------
DROP TABLE IF EXISTS `task_log`;
CREATE TABLE `task_log` (
  `id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL COMMENT '定时任务id',
  `status` int(1) DEFAULT NULL COMMENT '运行状态\nnull-运行中\n0-失败\n1-成功',
  `message` text COMMENT '运行信息',
  `start_time` bigint(13) NOT NULL COMMENT '开始时间',
  `run_time` bigint(13) DEFAULT NULL COMMENT '运行时间',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of task_log
-- ----------------------------
BEGIN;
INSERT INTO `task_log` VALUES (28512568767152128, 28180210469830657, 1, '', 1645596091538, 110, 1645596091573);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
