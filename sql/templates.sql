/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.19-123456
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 192.168.1.19:3306
 Source Schema         : templates

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 17/02/2021 10:53:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dictionaries
-- ----------------------------
DROP TABLE IF EXISTS `dictionaries`;
CREATE TABLE `dictionaries`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组',
  `order` int(2) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for model
-- ----------------------------
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `age` int(2) NOT NULL COMMENT '年龄',
  `sex` int(1) NOT NULL COMMENT '性别',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of model
-- ----------------------------
INSERT INTO `model` VALUES ('009a7647-94c9-48f4-9f7d-4c11e6339865', 'f', 1, 1, '2021-01-17 15:32:45', NULL, '1');
INSERT INTO `model` VALUES ('00a4f7a1-749e-4508-adb2-55b5b76142ca', 'e', 1, 1, '2021-01-17 15:31:46', NULL, '1');
INSERT INTO `model` VALUES ('6e07b6da-fb87-47b4-ae8d-fa82ee9a4f99', 'i', 1, 1, '2021-01-22 18:32:06', NULL, '111');
INSERT INTO `model` VALUES ('b2118cf5-a637-4906-a1ab-cc634454a6aa', 'd', 1, 1, '2021-01-17 15:31:09', NULL, '1');
INSERT INTO `model` VALUES ('b949bc30-e443-4eae-a375-d90c9fd04793', 'h', 1, 1, '2021-01-17 15:33:08', NULL, '1');
INSERT INTO `model` VALUES ('cf0597ac-58ef-44ee-9fdb-4891f1b9c0f2', 'g', 11, 1, '2021-01-17 15:32:54', NULL, '11');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'name',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('ea326528-650a-4307-b601-142f7fe716cb', '删除数据', '删除数据', '2021-02-18 22:05:31', NULL);
INSERT INTO `permission` VALUES ('f4213357-8c7d-4237-ac29-0d838f6ba6a7', '新增数据', '新增数据', '2021-02-23 22:05:35', NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'name',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('64b37fea-0e4f-49bf-b788-a1be52fc6d21', 'admin', '管理员', '2021-02-24 22:05:17', NULL);
INSERT INTO `role` VALUES ('a1f9bda4-db0f-4214-a8dd-ac33b3197dd2', 'teamMeb', '组员', '2021-02-25 22:05:21', NULL);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `role_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联角色',
  `permission_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联权限',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('ef8b2b69-37de-4bb6-98f5-46a46256cc54', '64b37fea-0e4f-49bf-b788-a1be52fc6d21', 'f4213357-8c7d-4237-ac29-0d838f6ba6a7', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_model
-- ----------------------------
DROP TABLE IF EXISTS `sys_model`;
CREATE TABLE `sys_model`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `parent_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父ID',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `navbar` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '导航栏名称',
  `icon_class` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标类名称',
  `url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址',
  `sort` int(2) NOT NULL DEFAULT 0 COMMENT '排序',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统模块表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_model
-- ----------------------------
INSERT INTO `sys_model` VALUES ('10339aaf-d26f-4d52-be81-9056ae2f8df8', '', '模板模块管理', '模板模块管理', NULL, '/templates', 100, '模板模块管理', '2021-02-06 20:11:41', '2021-02-06 20:11:41');
INSERT INTO `sys_model` VALUES ('83426a96-bda6-42d2-931a-8c19e25a7e6a', '9c2c95ee-38a4-4167-af34-c957c0d4f2cd', '用户系统模块管理', '系统模块管理', '', '/userSysModelManage', 11, '用户系统模块管理', '2021-02-06 22:39:11', '2021-02-06 22:39:11');
INSERT INTO `sys_model` VALUES ('8f459dbd-ffc6-4423-b84c-c8fee4a108d4', '9c2c95ee-38a4-4167-af34-c957c0d4f2cd', '用户基础信息管理', '基础信息管理', '', '/userBasicManage', 10, '', '2021-02-06 20:11:41', '2021-02-06 20:11:41');
INSERT INTO `sys_model` VALUES ('9999e044-35b2-48bb-b41e-e6a7b4cab464', 'a28d7764-1383-4d47-994b-afac6e19f4ec', '角色管理', '角色管理', NULL, '/roleManage', 100, '', '2021-02-06 20:11:41', '2021-02-06 20:11:41');
INSERT INTO `sys_model` VALUES ('9c2c95ee-38a4-4167-af34-c957c0d4f2cd', '', '用户管理', '用户管理', 'icon_1', '', 1, '用户管理', '2021-02-06 20:18:29', '2021-02-06 20:18:29');
INSERT INTO `sys_model` VALUES ('9e6113cd-9edd-44f4-a6f1-0076c4b37232', '9c2c95ee-38a4-4167-af34-c957c0d4f2cd', '用户角色管理', '角色管理', '', '/userRoleManage', 12, '', '2021-02-06 20:11:41', '2021-02-06 20:11:41');
INSERT INTO `sys_model` VALUES ('a28d7764-1383-4d47-994b-afac6e19f4ec', '', '访问控制', '访问控制', 'icon_2', '', 2, '访问控制', '2021-02-06 19:06:32', '2021-02-06 19:06:32');
INSERT INTO `sys_model` VALUES ('a8f41f09-fe60-4259-b833-16a8281ee44b', 'a28d7764-1383-4d47-994b-afac6e19f4ec', '权限管理', '权限管理', NULL, '/permissionManage', 100, '', '2021-02-06 20:11:41', '2021-02-06 20:11:41');
INSERT INTO `sys_model` VALUES ('d89f9f27-b6c1-416f-adbc-83bcf5ad3ad3', 'a28d7764-1383-4d47-994b-afac6e19f4ec', '角色权限管理', '角色权限管理', NULL, '/rolePermissionManage', 100, '', '2021-02-06 20:11:41', '2021-02-06 20:11:41');
INSERT INTO `sys_model` VALUES ('db79010a-e984-4856-9b75-4a4de75640f6', '', '系统管理', '系统管理', 'icon_3', '', 3, '系统管理', '2021-02-06 18:48:53', '2021-02-06 18:48:53');
INSERT INTO `sys_model` VALUES ('f7b32012-da82-489d-89e8-4c458a512e38', 'db79010a-e984-4856-9b75-4a4de75640f6', '系统模块管理', '系统模块管理', NULL, '/sysModelManage', 100, '', '2021-02-06 20:11:41', '2021-02-06 20:11:41');

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) NOT NULL DEFAULT 1,
  `order_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('0e1fb753-ee35-4b2e-be16-b1c44c65bf29', 'cyb', 'cyb', '64c8b1e43d8ba3115ab40bcea57f010b', '123', 'cyb', 1, NULL, 'F:\\Work\\IdeaWorkspace\\bysj\\SpringBootJSPWebTemp\\src\\main\\webapp\\/headportrait/boy.png', 'cyb', NULL, NULL);
INSERT INTO `user` VALUES ('5d544316-dcbc-4d57-921b-3bfabfe5a4b8', 'test', 'test', 'fc1709d0a95a6be30bc5926fdb7f22f4', '18220765414', '', NULL, '12345', 'F:\\Work\\IdeaWorkspace\\bysj\\SpringBootJSPWebTemp\\src\\main\\webapp\\/headportrait/girl.png', '', '2021-02-14 09:19:26', NULL);
INSERT INTO `user` VALUES ('9a068790-73c9-4f13-93a5-20ea7d9149a7', 'admin', 'admin', 'fc1709d0a95a6be30bc5926fdb7f22f4', '182208', 'test@163.com', 1, NULL, 'F:\\Work\\IdeaWorkspace\\bysj\\SpringBootJSPWebTemp\\src\\main\\webapp\\/headportrait/boy.png', 'hhhh', '2021-01-24 17:51:11', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联用户',
  `role_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联角色',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('17fac06b-a625-4523-b51e-a4a2c229b3f5', '75ff9add-24d2-4454-b540-f76cb94778b2', '64b37fea-0e4f-49bf-b788-a1be52fc6d21', NULL, NULL, NULL);
INSERT INTO `user_role` VALUES ('2cbe47af-c738-4384-a49b-a336eef7d0c0', '0e1fb753-ee35-4b2e-be16-b1c44c65bf29', 'a1f9bda4-db0f-4214-a8dd-ac33b3197dd2', NULL, NULL, NULL);
INSERT INTO `user_role` VALUES ('3ce5b3a4-bff8-4a9f-a5ff-1fd336ed39e7', '75ff9add-24d2-4454-b540-f76cb94778b2', 'a1f9bda4-db0f-4214-a8dd-ac33b3197dd2', NULL, NULL, NULL);
INSERT INTO `user_role` VALUES ('66b331f6-b853-4481-9a68-0c7895c4ac02', '191a2c91-9ef1-4be5-9607-818e968ddad1', '64b37fea-0e4f-49bf-b788-a1be52fc6d21', NULL, NULL, NULL);
INSERT INTO `user_role` VALUES ('7be29c77-70c8-4844-8a8e-f08150578859', '191a2c91-9ef1-4be5-9607-818e968ddad1', 'a1f9bda4-db0f-4214-a8dd-ac33b3197dd2', NULL, NULL, NULL);
INSERT INTO `user_role` VALUES ('c297c7dc-d25d-466e-84df-80256c019584', '9a068790-73c9-4f13-93a5-20ea7d9149a7', '64b37fea-0e4f-49bf-b788-a1be52fc6d21', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user_sys_model
-- ----------------------------
DROP TABLE IF EXISTS `user_sys_model`;
CREATE TABLE `user_sys_model`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联用户',
  `sys_model_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联系统模块',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户系统模块表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_sys_model
-- ----------------------------
INSERT INTO `user_sys_model` VALUES ('17fac06b-a625-4523-b51e-a4a2c229b3f5', '75ff9add-24d2-4454-b540-f76cb94778b2', '64b37fea-0e4f-49bf-b788-a1be52fc6d21', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('2cbe47af-c738-4384-a49b-a336eef7d0c0', '0e1fb753-ee35-4b2e-be16-b1c44c65bf29', 'a1f9bda4-db0f-4214-a8dd-ac33b3197dd2', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('3ce5b3a4-bff8-4a9f-a5ff-1fd336ed39e7', '75ff9add-24d2-4454-b540-f76cb94778b2', 'a1f9bda4-db0f-4214-a8dd-ac33b3197dd2', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('54934e35-632e-4afc-b9d2-7a673c947294', '9a068790-73c9-4f13-93a5-20ea7d9149a7', '9999e044-35b2-48bb-b41e-e6a7b4cab464', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('5dc74f09-51c7-4940-9fc7-72e8ef22aec5', '9a068790-73c9-4f13-93a5-20ea7d9149a7', 'f7b32012-da82-489d-89e8-4c458a512e38', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('66b331f6-b853-4481-9a68-0c7895c4ac02', '191a2c91-9ef1-4be5-9607-818e968ddad1', '64b37fea-0e4f-49bf-b788-a1be52fc6d21', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('7077e09c-5708-4ddb-add6-195924e901ec', '9a068790-73c9-4f13-93a5-20ea7d9149a7', '9e6113cd-9edd-44f4-a6f1-0076c4b37232', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('7be29c77-70c8-4844-8a8e-f08150578859', '191a2c91-9ef1-4be5-9607-818e968ddad1', 'a1f9bda4-db0f-4214-a8dd-ac33b3197dd2', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('86e570ee-84cc-49be-920c-285e11812657', '9a068790-73c9-4f13-93a5-20ea7d9149a7', '83426a96-bda6-42d2-931a-8c19e25a7e6a', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('9c51d682-85ea-4371-871e-84bf8dd4f9a4', '0e1fb753-ee35-4b2e-be16-b1c44c65bf29', '10339aaf-d26f-4d52-be81-9056ae2f8df8', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('a75297e5-fd24-41ae-8974-14cc40873c66', '9a068790-73c9-4f13-93a5-20ea7d9149a7', '8f459dbd-ffc6-4423-b84c-c8fee4a108d4', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('b3016b4e-3fd4-43f3-b604-60d577be8672', '9a068790-73c9-4f13-93a5-20ea7d9149a7', 'db79010a-e984-4856-9b75-4a4de75640f6', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('c297c7dc-d25d-466e-84df-80256c019584', '9a068790-73c9-4f13-93a5-20ea7d9149a7', '64b37fea-0e4f-49bf-b788-a1be52fc6d21', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('d61bc880-e893-43fb-a1c3-bf2a4acd2784', '9a068790-73c9-4f13-93a5-20ea7d9149a7', 'd89f9f27-b6c1-416f-adbc-83bcf5ad3ad3', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('e448f2d0-ce53-43e8-b88d-85c84914360b', '9a068790-73c9-4f13-93a5-20ea7d9149a7', '9c2c95ee-38a4-4167-af34-c957c0d4f2cd', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('ea9ce385-54f3-4b46-87b6-71252d3ce7fb', '9a068790-73c9-4f13-93a5-20ea7d9149a7', 'a8f41f09-fe60-4259-b833-16a8281ee44b', NULL, NULL, NULL);
INSERT INTO `user_sys_model` VALUES ('f96d7e71-8e59-4f9a-8a1c-8aed236d71d1', '9a068790-73c9-4f13-93a5-20ea7d9149a7', 'a28d7764-1383-4d47-994b-afac6e19f4ec', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
