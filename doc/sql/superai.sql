/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80023 (8.0.23)
 Source Host           : localhost:3306
 Source Schema         : superai

 Target Server Type    : MySQL
 Target Server Version : 80023 (8.0.23)
 File Encoding         : 65001

 Date: 24/06/2025 10:33:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_record
-- ----------------------------
DROP TABLE IF EXISTS `chat_record`;
CREATE TABLE `chat_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `knowledge_id` bigint NOT NULL COMMENT '知识库id',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题',
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回答',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for knowledge_base
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_base`;
CREATE TABLE `knowledge_base`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '知识库名称',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '-- 知识库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for knowledge_file
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_file`;
CREATE TABLE `knowledge_file`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `knowledge_id` bigint NOT NULL COMMENT '知识库ID',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `file_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件路径',
  `file_size` bigint NOT NULL COMMENT '文件大小',
  `chunk_count` int NOT NULL COMMENT '块数量',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `knowledge_id`(`knowledge_id` ASC) USING BTREE,
  CONSTRAINT `knowledge_file_ibfk_1` FOREIGN KEY (`knowledge_id`) REFERENCES `knowledge_base` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for knowledge_file_metadata
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_file_metadata`;
CREATE TABLE `knowledge_file_metadata`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `file_id` bigint NOT NULL COMMENT '知识库文件id',
  `summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总结',
  `keywords` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '关键字',
  `entities` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '实体',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `file_id`(`file_id` ASC) USING BTREE,
  CONSTRAINT `knowledge_file_metadata_ibfk_1` FOREIGN KEY (`file_id`) REFERENCES `knowledge_file` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识文件元数据表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
