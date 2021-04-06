/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : imooc-oa

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 23/03/2020 17:27:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for adm_department
-- ----------------------------
DROP TABLE IF EXISTS `adm_department`;
CREATE TABLE `adm_department`
(
    `department_id`   bigint(20)                                                   NOT NULL AUTO_INCREMENT,
    `department_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adm_department
-- ----------------------------
INSERT INTO `adm_department`
VALUES (1, '总裁办');
INSERT INTO `adm_department`
VALUES (2, '研发部');
INSERT INTO `adm_department`
VALUES (3, '市场部');

-- ----------------------------
-- Table structure for adm_employee
-- ----------------------------
DROP TABLE IF EXISTS `adm_employee`;
CREATE TABLE `adm_employee`
(
    `employee_id`   bigint(20)                                                   NOT NULL AUTO_INCREMENT,
    `name`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `department_id` bigint(20)                                                   NOT NULL,
    `title`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `level`         int(255)                                                     NOT NULL,
    PRIMARY KEY (`employee_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adm_employee
-- ----------------------------
INSERT INTO `adm_employee`
VALUES (1, '张晓涛', 1, '总经理', 8);
INSERT INTO `adm_employee`
VALUES (2, '齐紫陌', 2, '部门经理', 7);
INSERT INTO `adm_employee`
VALUES (3, '王美美', 2, '高级研发工程师', 6);
INSERT INTO `adm_employee`
VALUES (4, '宋彩妮', 2, '研发工程师', 5);
INSERT INTO `adm_employee`
VALUES (5, '欧阳峰', 2, '初级研发工程师', 4);
INSERT INTO `adm_employee`
VALUES (6, '张世豪', 3, '部门经理', 7);
INSERT INTO `adm_employee`
VALUES (7, '王子豪', 3, '大客户经理', 6);
INSERT INTO `adm_employee`
VALUES (8, '段峰', 3, '客户经理', 5);
INSERT INTO `adm_employee`
VALUES (9, '章雪峰', 3, '客户经理', 4);
INSERT INTO `adm_employee`
VALUES (10, '李莉', 3, '见习客户经理', 3);

-- ----------------------------
-- Table structure for sys_node
-- ----------------------------
DROP TABLE IF EXISTS `sys_node`;
CREATE TABLE `sys_node`
(
    `node_id`   bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '节点编号',
    `node_type` int(255)                                                      NOT NULL COMMENT '节点类型 1-模块 2-功能',
    `node_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '节点名称',
    `url`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '功能地址',
    `node_code` int(255)                                                      NOT NULL COMMENT '节点编码,用于排序',
    `parent_id` bigint(20)                                                    NULL DEFAULT NULL COMMENT '上级节点编号',
    PRIMARY KEY (`node_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_node
-- ----------------------------
INSERT INTO `sys_node`
VALUES (1, 1, '行政审批', NULL, 1000000, NULL);
INSERT INTO `sys_node`
VALUES (2, 2, '通知公告', ' ', 1000001, 1);
INSERT INTO `sys_node`
VALUES (3, 2, '请假申请', NULL, 1000002, 1);
INSERT INTO `sys_node`
VALUES (4, 2, '请假审批', NULL, 1000003, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`          bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '角色编号',
    `role_description` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色描述',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '业务岗角色');
INSERT INTO `sys_role`
VALUES (2, '管理岗角色');

-- ----------------------------
-- Table structure for sys_role_node
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_node`;
CREATE TABLE `sys_role_node`
(
    `rn_id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) NOT NULL,
    `node_id` bigint(20) NOT NULL,
    PRIMARY KEY (`rn_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_node
-- ----------------------------
INSERT INTO `sys_role_node`
VALUES (1, 1, 1);
INSERT INTO `sys_role_node`
VALUES (2, 1, 2);
INSERT INTO `sys_role_node`
VALUES (3, 1, 3);
INSERT INTO `sys_role_node`
VALUES (4, 2, 1);
INSERT INTO `sys_role_node`
VALUES (5, 2, 2);
INSERT INTO `sys_role_node`
VALUES (6, 2, 3);
INSERT INTO `sys_role_node`
VALUES (7, 2, 4);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`
(
    `ru_id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) NOT NULL,
    `user_id` int(11)    NOT NULL,
    PRIMARY KEY (`ru_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user`
VALUES (1, 2, 1);
INSERT INTO `sys_role_user`
VALUES (2, 2, 2);
INSERT INTO `sys_role_user`
VALUES (3, 1, 3);
INSERT INTO `sys_role_user`
VALUES (4, 1, 4);
INSERT INTO `sys_role_user`
VALUES (5, 1, 5);
INSERT INTO `sys_role_user`
VALUES (6, 2, 6);
INSERT INTO `sys_role_user`
VALUES (7, 1, 7);
INSERT INTO `sys_role_user`
VALUES (8, 1, 8);
INSERT INTO `sys_role_user`
VALUES (9, 1, 9);
INSERT INTO `sys_role_user`
VALUES (10, 1, 10);
INSERT INTO `sys_role_user`
VALUES (11, 1, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`     bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '用户编号',
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
    `password`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `employee_id` bigint(20)                                                   NOT NULL COMMENT '员工编号',
    `salt`        int(255)                                                     NOT NULL COMMENT '盐值',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 'm8', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 1, 188);
INSERT INTO `sys_user`
VALUES (2, 't7', 'dcfa022748271dccf5532c834e98ad08', 2, 189);
INSERT INTO `sys_user`
VALUES (3, 't6', '76ce11f8b004e8bdc8b0976b177c620d', 3, 190);
INSERT INTO `sys_user`
VALUES (4, 't5', '11f04f04054772bc1a8fdc41e70c7977', 4, 191);
INSERT INTO `sys_user`
VALUES (5, 't4', '8d7713848189a8d5c224f94f65d18b06', 5, 192);
INSERT INTO `sys_user`
VALUES (6, 's7', '044214e86e07d96c97de79a2222188cd', 6, 193);
INSERT INTO `sys_user`
VALUES (7, 's6', 'ecbd2f592ee65838328236d06ce35252', 7, 194);
INSERT INTO `sys_user`
VALUES (8, 's5', '846ecc83bba8fe420adc38b39f897201', 8, 195);
INSERT INTO `sys_user`
VALUES (9, 's4', 'c1e523cd2daa02f6cf4b98b2f26585fd', 9, 196);
INSERT INTO `sys_user`
VALUES (10, 's3', '89e89f369e07634fbb2286efffb9492b', 10, 197);

SET FOREIGN_KEY_CHECKS = 1;
