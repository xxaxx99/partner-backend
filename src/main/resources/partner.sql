/*
 Navicat Premium Data Transfer

 Source Server         : testdb
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : partner

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 15/11/2023 21:34:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tagName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名称',
  `userId` bigint NULL DEFAULT NULL COMMENT '用户 id',
  `parentId` bigint NULL DEFAULT NULL COMMENT '父标签 id',
  `isParent` tinyint NULL DEFAULT NULL COMMENT '0 - 不是, 1 - 父标签',
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniIdx_tagName`(`tagName` ASC) USING BTREE,
  INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '队伍名称',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `maxNum` int NOT NULL DEFAULT 1 COMMENT '最大人数',
  `expireTime` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `userId` bigint NULL DEFAULT NULL COMMENT '用户id（队长 id）',
  `status` int NOT NULL DEFAULT 0 COMMENT '0 - 公开，1 - 私有，2 - 加密',
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '队伍' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES (18, '我来野！', '干就完事', 3, '2023-11-23 23:13:00', 2900018, 0, '', '2023-11-15 20:28:59', '2023-11-15 20:28:59', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号',
  `avatarUrl` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `gender` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `userStatus` int NOT NULL DEFAULT 0 COMMENT '状态 0 - 正常',
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `userRole` int NOT NULL DEFAULT 0 COMMENT '用户角色 0 - 普通用户 1 - 管理员',
  `tags` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签 json 列表',
  `profile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2900028 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('xxaxx', 2900018, 'xxaxx', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', '男', '5af0256cbbe8b59da7bc5587d28ca5c1', 'fawfawfawfaw', '@9awfawf@qq.com', 0, '2023-11-15 19:06:19', '2023-11-15 20:17:15', 0, 0, '[\"java\",\"大一\",\"男\"]', '大傻der一个');
INSERT INTO `user` VALUES ('张三', 2900019, 'zhangsan123', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', '男', 'hashed_password1', '1234567890', 'zhangsan@example.com', 0, '2023-01-01 12:00:00', '2023-11-15 20:17:15', 0, 0, '[\"Java\", \"大一\", \"男生\"]', '大一在读，热爱Java编程，喜欢打篮球。');
INSERT INTO `user` VALUES ('李四', 2900020, 'lisi456', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', '女', 'hashed_password2', '0987654321', 'lisi@example.com', 0, '2023-01-02 14:30:00', '2023-11-15 20:17:15', 0, 0, '[\"Python\", \"大一\", \"女生\"]', '计算机科学专业的大一学生，喜欢编写Python程序，爱好画画。');
INSERT INTO `user` VALUES ('王五', 2900021, 'wangwu789', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', '男', 'hashed_password3', '1112223333', 'wangwu@example.com', 0, '2023-01-03 10:45:00', '2023-11-15 20:18:00', 0, 0, '[\"C++\", \"大一\", \"男生\"]', '大一新生，对C++很感兴趣，热衷于学习编程。');
INSERT INTO `user` VALUES ('赵六', 2900022, 'zhaoliu101', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', '女', 'hashed_password4', '4445556666', 'zhaoliu@example.com', 0, '2023-01-04 08:20:00', '2023-11-15 20:18:00', 0, 0, '[\"Web开发\", \"大一\", \"女生\"]', '学习Web开发，对前端技术有浓厚兴趣。');
INSERT INTO `user` VALUES ('孙七', 2900023, 'sunqi202', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', '男', 'hashed_password5', '7778889999', 'sunqi@example.com', 0, '2023-01-05 16:10:00', '2023-11-15 20:18:00', 0, 0, '[\"数据科学\", \"大一\", \"男生\"]', '对数据科学领域充满好奇，正在学习相关知识。');
INSERT INTO `user` VALUES ('周八', 2900024, 'zhouba303', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', '女', 'hashed_password6', '5556667777', 'zhouba@example.com', 0, '2023-01-06 18:50:00', '2023-11-15 20:18:00', 0, 0, '[\"设计\", \"大一\", \"女生\"]', '设计专业的大一学生，喜欢创意设计和平面设计。');
INSERT INTO `user` VALUES ('吴九', 2900025, 'wujio404', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', '男', 'hashed_password7', '3334445555', 'wujio@example.com', 0, '2023-01-07 22:15:00', '2023-11-15 20:18:00', 0, 0, '[\"游戏开发\", \"大一\", \"男生\"]', '梦想成为一名游戏开发者，热爱游戏编程。');
INSERT INTO `user` VALUES ('郑十', 2900026, 'zhengshi505', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', '女', 'hashed_password8', '6667778888', 'zhengshi@example.com', 0, '2023-01-08 09:05:00', '2023-11-15 20:18:00', 0, 0, '[\"人工智能\", \"大一\", \"女生\"]', '对人工智能和机器学习领域充满兴趣，正在学习相关知识。');
INSERT INTO `user` VALUES ('叫我小李就好', 2900027, 'xiaoli', 'https://img.zcool.cn/community/04b4wf2hltswlkpp4qebcs3937.png', NULL, '5af0256cbbe8b59da7bc5587d28ca5c1', '6667778899', 'example,com', 0, '2023-11-15 20:25:22', '2023-11-15 20:27:46', 0, 0, '[\"人工智能\", \"大二\", \"男生\"]', '对人工智能和机器学习领域充满兴趣');

-- ----------------------------
-- Table structure for user_team
-- ----------------------------
DROP TABLE IF EXISTS `user_team`;
CREATE TABLE `user_team`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userId` bigint NULL DEFAULT NULL COMMENT '用户id',
  `teamId` bigint NULL DEFAULT NULL COMMENT '队伍id',
  `joinTime` datetime NULL DEFAULT NULL COMMENT '加入时间',
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户队伍关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_team
-- ----------------------------
INSERT INTO `user_team` VALUES (39, 2900018, 18, '2023-11-15 20:28:59', '2023-11-15 20:28:59', '2023-11-15 20:28:59', 0);

SET FOREIGN_KEY_CHECKS = 1;
