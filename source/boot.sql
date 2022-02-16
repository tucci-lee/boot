/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : boot

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 16/02/2022 09:38:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log_login
-- ----------------------------
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
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
-- Records of log_login
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for log_operate
-- ----------------------------
DROP TABLE IF EXISTS `log_operate`;
CREATE TABLE `log_operate` (
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
-- Records of log_operate
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=1153 DEFAULT CHARSET=utf8mb4 COMMENT='角色与资源关联表';

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_res` VALUES (1, 112650695508754432, 112063924660076544);
INSERT INTO `sys_role_res` VALUES (2, 112650695508754432, 112064419969630208);
INSERT INTO `sys_role_res` VALUES (3, 112650695508754432, 112064548923506688);
INSERT INTO `sys_role_res` VALUES (4, 112650695508754432, 112065408583860224);
INSERT INTO `sys_role_res` VALUES (5, 112650695508754432, 112304735788204032);
INSERT INTO `sys_role_res` VALUES (6, 112650695508754432, 112304837034508288);
INSERT INTO `sys_role_res` VALUES (7, 112650695508754432, 112304921109331968);
INSERT INTO `sys_role_res` VALUES (8, 112650695508754432, 112305002013261824);
INSERT INTO `sys_role_res` VALUES (9, 112650695508754432, 112766904753455104);
INSERT INTO `sys_role_res` VALUES (10, 112650695508754432, 113296483343663104);
INSERT INTO `sys_role_res` VALUES (11, 112650695508754432, 113296609537687552);
INSERT INTO `sys_role_res` VALUES (12, 112650695508754432, 113296742077693952);
INSERT INTO `sys_role_res` VALUES (13, 112650695508754432, 113296815033417728);
INSERT INTO `sys_role_res` VALUES (14, 112650695508754432, 113296905349365760);
INSERT INTO `sys_role_res` VALUES (15, 112650695508754432, 113297095770767360);
INSERT INTO `sys_role_res` VALUES (16, 112650695508754432, 113297279363842048);
INSERT INTO `sys_role_res` VALUES (17, 112650695508754432, 113297348314005504);
INSERT INTO `sys_role_res` VALUES (18, 112650695508754432, 113297416005877760);
INSERT INTO `sys_role_res` VALUES (19, 112650695508754432, 113297479004323840);
INSERT INTO `sys_role_res` VALUES (20, 112650695508754432, 113297703277953024);
INSERT INTO `sys_role_res` VALUES (21, 112650695508754432, 113297768126087168);
INSERT INTO `sys_role_res` VALUES (22, 112650695508754432, 113297841698373632);
INSERT INTO `sys_role_res` VALUES (23, 112650695508754432, 113297895578402816);
INSERT INTO `sys_role_res` VALUES (24, 112650695508754432, 113326873903104000);
INSERT INTO `sys_role_res` VALUES (25, 112650695508754432, 113327217995415552);
INSERT INTO `sys_role_res` VALUES (26, 112650695508754432, 113328033569439744);
INSERT INTO `sys_role_res` VALUES (27, 112650695508754432, 113329345681948672);
INSERT INTO `sys_role_res` VALUES (28, 112650695508754432, 113329497700302848);
INSERT INTO `sys_role_res` VALUES (29, 112650695508754432, 113329647516647424);
INSERT INTO `sys_role_res` VALUES (30, 112650695508754432, 113329855000477696);
INSERT INTO `sys_role_res` VALUES (31, 112650695508754432, 113329976727568384);
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

SET FOREIGN_KEY_CHECKS = 1;
