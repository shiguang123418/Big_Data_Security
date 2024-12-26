/*
 Navicat Premium Data Transfer

 Source Server         : 百度云
 Source Server Type    : MySQL
 Source Server Version : 80040
 Source Host           : 180.76.54.75:3306
 Source Schema         : y2

 Target Server Type    : MySQL
 Target Server Version : 80040
 File Encoding         : 65001

 Date: 26/12/2024 03:06:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `id` int NOT NULL,
  `permission` int NOT NULL DEFAULT 3,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `user_id1` FOREIGN KEY (`id`) REFERENCES `user_dict` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES (4, -3);
INSERT INTO `permissions` VALUES (6, 3);
INSERT INTO `permissions` VALUES (8, 3);
INSERT INTO `permissions` VALUES (9, 3);
INSERT INTO `permissions` VALUES (11, -3);
INSERT INTO `permissions` VALUES (12, -3);
INSERT INTO `permissions` VALUES (13, 3);
INSERT INTO `permissions` VALUES (14, 3);
INSERT INTO `permissions` VALUES (15, 3);
INSERT INTO `permissions` VALUES (16, 3);
INSERT INTO `permissions` VALUES (17, 3);
INSERT INTO `permissions` VALUES (18, 3);
INSERT INTO `permissions` VALUES (19, 3);
INSERT INTO `permissions` VALUES (20, 3);
INSERT INTO `permissions` VALUES (21, 3);
INSERT INTO `permissions` VALUES (22, 3);
INSERT INTO `permissions` VALUES (23, 3);
INSERT INTO `permissions` VALUES (24, 3);
INSERT INTO `permissions` VALUES (25, 3);
INSERT INTO `permissions` VALUES (26, 3);
INSERT INTO `permissions` VALUES (27, 3);
INSERT INTO `permissions` VALUES (28, 3);
INSERT INTO `permissions` VALUES (29, 3);
INSERT INTO `permissions` VALUES (30, 3);
INSERT INTO `permissions` VALUES (31, 3);
INSERT INTO `permissions` VALUES (32, 3);
INSERT INTO `permissions` VALUES (33, 3);
INSERT INTO `permissions` VALUES (34, 3);
INSERT INTO `permissions` VALUES (35, 3);
INSERT INTO `permissions` VALUES (36, 3);
INSERT INTO `permissions` VALUES (37, 3);
INSERT INTO `permissions` VALUES (38, 3);
INSERT INTO `permissions` VALUES (39, 3);
INSERT INTO `permissions` VALUES (40, 3);
INSERT INTO `permissions` VALUES (41, 3);
INSERT INTO `permissions` VALUES (42, 3);
INSERT INTO `permissions` VALUES (44, 3);
INSERT INTO `permissions` VALUES (45, 3);
INSERT INTO `permissions` VALUES (46, 3);
INSERT INTO `permissions` VALUES (47, 3);
INSERT INTO `permissions` VALUES (48, 3);
INSERT INTO `permissions` VALUES (49, 3);
INSERT INTO `permissions` VALUES (50, 3);
INSERT INTO `permissions` VALUES (51, 3);
INSERT INTO `permissions` VALUES (52, 2);
INSERT INTO `permissions` VALUES (53, 3);
INSERT INTO `permissions` VALUES (54, 3);
INSERT INTO `permissions` VALUES (55, 3);
INSERT INTO `permissions` VALUES (56, 3);
INSERT INTO `permissions` VALUES (57, 3);
INSERT INTO `permissions` VALUES (58, 3);
INSERT INTO `permissions` VALUES (59, 3);
INSERT INTO `permissions` VALUES (60, 3);
INSERT INTO `permissions` VALUES (61, 3);
INSERT INTO `permissions` VALUES (62, 3);
INSERT INTO `permissions` VALUES (63, 3);
INSERT INTO `permissions` VALUES (64, 3);
INSERT INTO `permissions` VALUES (65, 3);
INSERT INTO `permissions` VALUES (66, 3);
INSERT INTO `permissions` VALUES (67, 3);
INSERT INTO `permissions` VALUES (68, 3);
INSERT INTO `permissions` VALUES (69, 3);
INSERT INTO `permissions` VALUES (70, 3);
INSERT INTO `permissions` VALUES (71, 3);
INSERT INTO `permissions` VALUES (72, 3);
INSERT INTO `permissions` VALUES (73, 3);
INSERT INTO `permissions` VALUES (74, 3);
INSERT INTO `permissions` VALUES (75, 3);
INSERT INTO `permissions` VALUES (76, 3);
INSERT INTO `permissions` VALUES (77, 3);
INSERT INTO `permissions` VALUES (78, 3);
INSERT INTO `permissions` VALUES (79, 3);
INSERT INTO `permissions` VALUES (80, 3);
INSERT INTO `permissions` VALUES (81, 3);
INSERT INTO `permissions` VALUES (82, 3);
INSERT INTO `permissions` VALUES (83, 3);
INSERT INTO `permissions` VALUES (84, 3);
INSERT INTO `permissions` VALUES (85, 3);
INSERT INTO `permissions` VALUES (86, 3);
INSERT INTO `permissions` VALUES (87, 3);
INSERT INTO `permissions` VALUES (88, 3);
INSERT INTO `permissions` VALUES (89, 3);
INSERT INTO `permissions` VALUES (90, 3);
INSERT INTO `permissions` VALUES (91, 3);
INSERT INTO `permissions` VALUES (92, 3);
INSERT INTO `permissions` VALUES (93, 3);
INSERT INTO `permissions` VALUES (94, 3);
INSERT INTO `permissions` VALUES (95, 3);
INSERT INTO `permissions` VALUES (96, 3);
INSERT INTO `permissions` VALUES (97, 3);
INSERT INTO `permissions` VALUES (98, 3);
INSERT INTO `permissions` VALUES (99, 3);
INSERT INTO `permissions` VALUES (100, 3);
INSERT INTO `permissions` VALUES (131, 1);
INSERT INTO `permissions` VALUES (133, 2);
INSERT INTO `permissions` VALUES (134, 2);
INSERT INTO `permissions` VALUES (135, 3);
INSERT INTO `permissions` VALUES (136, 3);
INSERT INTO `permissions` VALUES (137, 3);
INSERT INTO `permissions` VALUES (138, 3);
INSERT INTO `permissions` VALUES (139, 3);
INSERT INTO `permissions` VALUES (142, 3);

-- ----------------------------
-- Table structure for user_dict
-- ----------------------------
DROP TABLE IF EXISTS `user_dict`;
CREATE TABLE `user_dict`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT 0,
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `idcard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 153 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_dict
-- ----------------------------
INSERT INTO `user_dict` VALUES (4, '常安琪3', 'ac2@gmail.com', 'female', 12, '15847599809', '745793395437321756', '中国北京市朝阳区三里屯路266号35号楼');
INSERT INTO `user_dict` VALUES (6, '谭云熙', 'tyunxi@outlook.com', 'male', 60, '17495789918', '497910357888638842', '中国深圳福田区深南大道968号华润大厦9室');
INSERT INTO `user_dict` VALUES (8, '秦璐', 'qinlu@yahoo.com', 'female', 68, '17274955816', '218848135581568691', '中国中山京华商圈华夏街121号6室');
INSERT INTO `user_dict` VALUES (9, '龚詩涵', 'sgo10@icloud.com', 'male', 18, '14865233813', '281660317142055954', '中国中山天河区大信商圈大信南路227号19号楼');
INSERT INTO `user_dict` VALUES (11, '梁子韬', 'liangz1019@gmail.com', 'female', 36, '12210159672', '171537372423021475', '中国中山天河区大信商圈大信南路969号华润大厦6室');
INSERT INTO `user_dict` VALUES (12, '张致远', 'zhiyuanzhang1963@outlook.com', 'female', 31, '18787004270', '910848182695152413', '中国北京市東城区東直門內大街206号34栋');
INSERT INTO `user_dict` VALUES (13, '姚睿', 'yrui@gmail.com', 'female', 73, '14405895528', '985669427948196723', '中国北京市東城区東直門內大街673号19号楼');
INSERT INTO `user_dict` VALUES (14, '沈安琪', 'shenanq@outlook.com', 'male', 9, '15931860611', '306105162319247281', '中国深圳罗湖区田贝一路747号37楼');
INSERT INTO `user_dict` VALUES (15, '邓安琪', 'anqid1980@mail.com', 'male', 11, '14358200733', '700481666266687210', '中国北京市西城区复兴门内大街701号华润大厦25室');
INSERT INTO `user_dict` VALUES (16, '武嘉伦', 'jwu@gmail.com', 'male', 16, '19430171581', '517074909038064588', '中国北京市東城区東直門內大街277号37室');
INSERT INTO `user_dict` VALUES (17, '吴晓明', 'wux1205@hotmail.com', 'male', 81, '10474794237', '673870167802346064', '中国广州市白云区机场路棠苑街五巷693号27室');
INSERT INTO `user_dict` VALUES (18, '蒋子韬', 'ziji1115@icloud.com', 'female', 83, '14963369108', '948737140406528156', '中国广州市白云区小坪东路271号12室');
INSERT INTO `user_dict` VALUES (19, '谢秀英', 'xiuying1983@outlook.com', 'female', 87, '15815563736', '370553653737160151', '中国中山天河区大信商圈大信南路874号31楼');
INSERT INTO `user_dict` VALUES (20, '薛岚', 'xuelan@outlook.com', 'female', 12, '12015698048', '569504496241920666', '中国北京市東城区東直門內大街70号48楼');
INSERT INTO `user_dict` VALUES (21, '戴詩涵', 'dais102@mail.com', 'male', 45, '15977422540', '646309646594123919', '中国深圳罗湖区清水河一路968号华润大厦23室');
INSERT INTO `user_dict` VALUES (22, '程子韬', 'chengzitao@outlook.com', 'female', 72, '10278923551', '264917457622879558', '中国东莞珊瑚路55号46栋');
INSERT INTO `user_dict` VALUES (23, '曹子韬', 'zitaocao@outlook.com', 'male', 21, '12171527970', '851250101910221080', '中国深圳福田区深南大道913号24室');
INSERT INTO `user_dict` VALUES (24, '严杰宏', 'jiehyan@yahoo.com', 'male', 21, '18304974973', '310030023934265287', '中国上海市浦东新区橄榄路827号11号楼');
INSERT INTO `user_dict` VALUES (25, '王震南', 'zhennan929@outlook.com', 'male', 27, '12961575450', '617087173644738642', '中国上海市黄浦区淮海中路908号42室');
INSERT INTO `user_dict` VALUES (26, '杨晓明', 'yang1988@mail.com', 'female', 10, '19390528437', '741545279817260047', '中国中山紫马岭商圈中山五路61号25室');
INSERT INTO `user_dict` VALUES (27, '赵秀英', 'zhaoxi@gmail.com', 'female', 42, '12537550082', '233959042180783849', '中国广州市白云区机场路棠苑街五巷700号华润大厦13室');
INSERT INTO `user_dict` VALUES (28, '杜睿', 'ruidu120@outlook.com', 'female', 59, '10181123697', '821269212203805467', '中国深圳福田区深南大道405号40楼');
INSERT INTO `user_dict` VALUES (29, '孟宇宁', 'mengy109@gmail.com', 'male', 22, '18360889653', '352041205531028767', '中国中山京华商圈华夏街494号50栋');
INSERT INTO `user_dict` VALUES (30, '常睿', 'changru@icloud.com', 'male', 16, '15295862497', '252135852317485270', '中国成都市成华区玉双路6号229号7栋');
INSERT INTO `user_dict` VALUES (31, '龚秀英', 'xiuyinggon10@outlook.com', 'female', 8, '19173710514', '137401373165489298', '中国上海市闵行区宾川路871号11楼');
INSERT INTO `user_dict` VALUES (32, '许璐', 'luxu@gmail.com', 'female', 30, '19096239135', '534741538056252114', '中国广州市白云区机场路棠苑街五巷85号41号楼');
INSERT INTO `user_dict` VALUES (33, '袁致远', 'yuzhiyu@outlook.com', 'female', 7, '11450318660', '740789005373201784', '中国深圳罗湖区蔡屋围深南东路612号38楼');
INSERT INTO `user_dict` VALUES (34, '彭宇宁', 'penyuning@mail.com', 'female', 33, '17701665775', '971651609498260717', '中国广州市越秀区中山二路209号15号楼');
INSERT INTO `user_dict` VALUES (35, '范嘉伦', 'jialfan@icloud.com', 'male', 79, '10749364371', '856733863818248111', '中国上海市浦东新区健祥路413号12号楼');
INSERT INTO `user_dict` VALUES (36, '谢云熙', 'xieyun99@mail.com', 'female', 1, '18122199862', '156399975410737272', '中国广州市白云区机场路棠苑街五巷172号39号楼');
INSERT INTO `user_dict` VALUES (37, '钱岚', 'qianlan@outlook.com', 'male', 26, '15635420155', '833107087025708165', '中国上海市浦东新区健祥路763号34号楼');
INSERT INTO `user_dict` VALUES (38, '彭秀英', 'xiuying6@hotmail.com', 'male', 51, '15561566246', '963392677280204436', '中国东莞东泰五街3号华润大厦20室');
INSERT INTO `user_dict` VALUES (39, '江詩涵', 'jshihan42@outlook.com', 'female', 88, '19267812448', '159267421356660188', '中国北京市延庆区028县道146号47楼');
INSERT INTO `user_dict` VALUES (40, '彭璐', 'penglu@hotmail.com', 'female', 58, '15021271145', '610525648810261451', '中国上海市徐汇区虹桥路271号16号楼');
INSERT INTO `user_dict` VALUES (41, '范云熙', 'yunxf531@outlook.com', 'female', 93, '16597812583', '945453151649039393', '中国深圳罗湖区蔡屋围深南东路126号华润大厦38室');
INSERT INTO `user_dict` VALUES (42, '谭詩涵', 'tshiha@icloud.com', 'male', 75, '16522302381', '658488034171281859', '中国广州市越秀区中山二路120号21号楼');
INSERT INTO `user_dict` VALUES (44, '潘睿', 'pan1018@gmail.com', 'male', 88, '15791129780', '308882470945557707', '中国深圳福田区景田东一街562号华润大厦49室');
INSERT INTO `user_dict` VALUES (45, '邱宇宁', 'qiyuning2@gmail.com', 'male', 79, '18739081490', '420812036777633210', '中国深圳龙岗区布吉镇西环路374号31楼');
INSERT INTO `user_dict` VALUES (46, '贺子异', 'heziyi@outlook.com', 'male', 20, '17680524053', '417954848278315310', '中国北京市房山区岳琉路467号10室');
INSERT INTO `user_dict` VALUES (47, '薛云熙', 'yxue1025@hotmail.com', 'female', 4, '11156973762', '362192968381474224', '中国深圳龙岗区布吉镇西环路618号25号楼');
INSERT INTO `user_dict` VALUES (48, '戴子异', 'zd1949@gmail.com', 'male', 98, '14642858063', '514167157017092122', '中国广州市越秀区中山二路488号华润大厦28室');
INSERT INTO `user_dict` VALUES (49, '熊睿', 'ruixio@hotmail.com', 'female', 86, '15716820952', '641586051287388028', '中国广州市白云区机场路棠苑街五巷439号48栋');
INSERT INTO `user_dict` VALUES (50, '贾子异', 'jiazi@icloud.com', 'male', 29, '13275192262', '507911002563521040', '中国成都市锦江区人民南路四段679号9室');
INSERT INTO `user_dict` VALUES (51, '蔡晓明', 'xiaomingca@gmail.com', 'female', 55, '18587091707', '387494016647245317', '中国北京市房山区岳琉路400号20号楼');
INSERT INTO `user_dict` VALUES (52, '郝嘉伦', 'jihao75@gmail.com', 'female', 72, '13790636173', '710598274209524159', '中国上海市闵行区宾川路154号2楼');
INSERT INTO `user_dict` VALUES (53, '向詩涵', 'xiang46@icloud.com', 'female', 6, '17713708058', '651138610754813720', '中国北京市朝阳区三里屯路656号35号楼');
INSERT INTO `user_dict` VALUES (54, '阎晓明', 'yxiaoming@outlook.com', 'female', 93, '15098153561', '464718341367891505', '中国广州市白云区小坪东路238号50室');
INSERT INTO `user_dict` VALUES (55, '韩震南', 'zhennan80@icloud.com', 'female', 39, '18087429908', '943979814968671528', '中国北京市东城区东单王府井东街283号华润大厦38室');
INSERT INTO `user_dict` VALUES (56, '常睿', 'chang7@hotmail.com', 'male', 83, '11910027031', '537424908159113231', '中国北京市西城区复兴门内大街508号7栋');
INSERT INTO `user_dict` VALUES (57, '姚安琪', 'yaoanqi@icloud.com', 'male', 34, '15419092031', '573169145731271630', '中国中山紫马岭商圈中山五路930号42楼');
INSERT INTO `user_dict` VALUES (58, '吴杰宏', 'wujie@outlook.com', 'male', 88, '12931689384', '160226087787844068', '中国北京市东城区东单王府井东街603号13号楼');
INSERT INTO `user_dict` VALUES (59, '吴秀英', 'wuxiuying@icloud.com', 'male', 78, '10462412788', '127798461669524345', '中国成都市成华区玉双路6号714号13楼');
INSERT INTO `user_dict` VALUES (60, '沈安琪', 'anqis@outlook.com', 'female', 91, '17272645855', '568009839611130154', '中国北京市房山区岳琉路187号20号楼');
INSERT INTO `user_dict` VALUES (61, '崔宇宁', 'cy124@gmail.com', 'female', 75, '16903187240', '322799157260324419', '中国中山紫马岭商圈中山五路400号29楼');
INSERT INTO `user_dict` VALUES (62, '段子韬', 'ziduan@hotmail.com', 'male', 83, '12329217581', '925964991621005790', '中国东莞环区南街二巷156号27栋');
INSERT INTO `user_dict` VALUES (63, '邹嘉伦', 'jialuz@gmail.com', 'male', 23, '16204548914', '138390983292501636', '中国中山乐丰六路921号6栋');
INSERT INTO `user_dict` VALUES (64, '杜震南', 'zhennand@outlook.com', 'female', 94, '14759113675', '500992752721652611', '中国北京市西城区西長安街363号8栋');
INSERT INTO `user_dict` VALUES (65, '顾震南', 'zhennang1230@gmail.com', 'female', 89, '10611680697', '646368822247538141', '中国深圳福田区深南大道827号45号楼');
INSERT INTO `user_dict` VALUES (66, '姚睿', 'ry8@hotmail.com', 'female', 12, '17154422417', '708856527882398301', '中国北京市西城区复兴门内大街647号17楼');
INSERT INTO `user_dict` VALUES (67, '钱岚', 'lanqia@icloud.com', 'female', 70, '14653339417', '270232552361200110', '中国上海市闵行区宾川路47号21楼');
INSERT INTO `user_dict` VALUES (68, '贾晓明', 'jia1@outlook.com', 'female', 28, '17167524089', '208708566368465529', '中国北京市西城区复兴门内大街827号33栋');
INSERT INTO `user_dict` VALUES (69, '何嘉伦', 'hej@mail.com', 'female', 19, '18781041715', '594180990783732071', '中国上海市黄浦区淮海中路235号47号楼');
INSERT INTO `user_dict` VALUES (70, '孟岚', 'mengla@icloud.com', 'female', 47, '14849486696', '346129581470796350', '中国成都市锦江区红星路三段402号华润大厦35室');
INSERT INTO `user_dict` VALUES (71, '廖晓明', 'xiaoming5@outlook.com', 'female', 88, '17842593863', '744799328866567952', '中国广州市越秀区中山二路334号49号楼');
INSERT INTO `user_dict` VALUES (72, '唐岚', 'tanla@icloud.com', 'male', 92, '15209642733', '372431227365377904', '中国深圳龙岗区学园一巷726号20室');
INSERT INTO `user_dict` VALUES (73, '吴安琪', 'anqi1104@outlook.com', 'female', 47, '16346595256', '221060149382007693', '中国成都市成华区二仙桥东三路762号6栋');
INSERT INTO `user_dict` VALUES (74, '汪子韬', 'zitawan@gmail.com', 'female', 77, '18466286127', '918266862273144223', '中国深圳罗湖区清水河一路487号49号楼');
INSERT INTO `user_dict` VALUES (75, '冯晓明', 'feng57@mail.com', 'male', 40, '10352125959', '743548959315703806', '中国上海市闵行区宾川路793号华润大厦24室');
INSERT INTO `user_dict` VALUES (76, '孔璐', 'lukong715@hotmail.com', 'female', 44, '10753631335', '622469891403947408', '中国东莞珊瑚路770号20号楼');
INSERT INTO `user_dict` VALUES (77, '武岚', 'wulan10@hotmail.com', 'female', 79, '11728637774', '454886521623079101', '中国广州市天河区天河路944号华润大厦15室');
INSERT INTO `user_dict` VALUES (78, '汤子异', 'tang79@outlook.com', 'female', 88, '12366694466', '441712026726885519', '中国成都市锦江区红星路三段967号30室');
INSERT INTO `user_dict` VALUES (79, '孙云熙', 'yunxi72@icloud.com', 'male', 69, '16565397364', '950629099713189234', '中国深圳龙岗区学园一巷314号36室');
INSERT INTO `user_dict` VALUES (80, '尹震南', 'zheyin@gmail.com', 'male', 95, '15732264345', '663898634463924373', '中国深圳龙岗区布吉镇西环路389号18室');
INSERT INTO `user_dict` VALUES (81, '孔璐', 'lu17@icloud.com', 'female', 11, '19562130027', '567109766773746738', '中国东莞环区南街二巷135号28号楼');
INSERT INTO `user_dict` VALUES (82, '李宇宁', 'yuningli1979@mail.com', 'female', 87, '13770171407', '764494212106706943', '中国广州市白云区小坪东路312号华润大厦37室');
INSERT INTO `user_dict` VALUES (83, '梁嘉伦', 'jialun61@icloud.com', 'female', 0, '19136483623', '921084010727142754', '中国广州市白云区小坪东路331号华润大厦38室');
INSERT INTO `user_dict` VALUES (84, '冯宇宁', 'yuning2@outlook.com', 'female', 31, '17639179463', '392448099981500341', '中国深圳福田区景田东一街569号23室');
INSERT INTO `user_dict` VALUES (85, '黎震南', 'lizhenn@mail.com', 'female', 31, '11888570255', '477440307066385678', '中国深圳福田区景田东一街555号6楼');
INSERT INTO `user_dict` VALUES (86, '于晓明', 'xiaomingy42@yahoo.com', 'male', 44, '11713949992', '842209095150527156', '中国上海市浦东新区橄榄路711号华润大厦24室');
INSERT INTO `user_dict` VALUES (87, '姚睿', 'ruyao@icloud.com', 'male', 11, '16806995403', '597072972348775712', '中国上海市浦东新区橄榄路477号35室');
INSERT INTO `user_dict` VALUES (88, '宋致远', 'songz222@mail.com', 'male', 69, '16607702261', '300276417211440604', '中国成都市成华区双庆路942号10楼');
INSERT INTO `user_dict` VALUES (89, '蒋子异', 'zij96@hotmail.com', 'male', 37, '13370434653', '416760365639837555', '中国东莞坑美十五巷567号30栋');
INSERT INTO `user_dict` VALUES (90, '丁震南', 'zhennand109@gmail.com', 'male', 1, '12725760780', '620768628535218203', '中国北京市房山区岳琉路531号50号楼');
INSERT INTO `user_dict` VALUES (91, '陆睿', 'lurui613@outlook.com', 'female', 93, '19774754490', '306382744040558221', '中国上海市黄浦区淮海中路192号华润大厦19室');
INSERT INTO `user_dict` VALUES (92, '郭嘉伦', 'guo723@icloud.com', 'female', 50, '11210553855', '145141326641055653', '中国广州市越秀区中山二路850号12楼');
INSERT INTO `user_dict` VALUES (93, '唐詩涵', 'shihan921@icloud.com', 'male', 77, '18392228655', '979765593396999443', '中国北京市西城区西長安街213号华润大厦39室');
INSERT INTO `user_dict` VALUES (94, '潘震南', 'pzhennan@hotmail.com', 'male', 46, '18817225546', '472979250435829190', '中国上海市徐汇区虹桥路306号华润大厦2室');
INSERT INTO `user_dict` VALUES (95, '林震南', 'lin64@gmail.com', 'male', 31, '11585701899', '359828069111383097', '中国北京市東城区東直門內大街915号华润大厦36室');
INSERT INTO `user_dict` VALUES (96, '钱子韬', 'zitao6@icloud.com', 'male', 19, '18750503837', '460377755572800137', '中国广州市越秀区中山二路587号5室');
INSERT INTO `user_dict` VALUES (97, '李晓明', 'lxi46@gmail.com', 'female', 18, '15453527406', '932694249564559006', '中国深圳罗湖区清水河一路177号40栋');
INSERT INTO `user_dict` VALUES (98, '马安琪', 'maan@hotmail.com', 'male', 17, '14313774564', '546779969902185289', '中国深圳龙岗区学园一巷501号华润大厦28室');
INSERT INTO `user_dict` VALUES (99, '崔睿', 'rui201@icloud.com', 'female', 54, '18881832938', '447189396533528725', '中国北京市延庆区028县道984号23楼');
INSERT INTO `user_dict` VALUES (100, '严璐', 'yanlu@outlook.com', 'male', 69, '10803789398', '725144803655081633', '中国中山紫马岭商圈中山五路312号32楼');
INSERT INTO `user_dict` VALUES (131, '1', '1@qq.com', 'male', 12, '11111111111', '111111111111111111', '河南省许昌市建安区河南农业大学');
INSERT INTO `user_dict` VALUES (133, '2', '2@qq.com', 'female', 231, '22222222222', '222222222222222222', '河南省许昌市建安区\n河南农业大学');
INSERT INTO `user_dict` VALUES (134, '3', '3@qq.com', 'male', 123, '33333333333', '333333333333333333', '河南省许昌市建安区\n河南农业大学');
INSERT INTO `user_dict` VALUES (135, '4', '4@qq.com', 'male', 12, '44444444444', '1545341534631', 'dsfsdfgfdhgdfh');
INSERT INTO `user_dict` VALUES (136, '5', '5@qq.com', 'male', 5, '55555555555', '5416415341534153', 'dfsfdsfdsgdfg');
INSERT INTO `user_dict` VALUES (137, '6', '6@qq.com', 'male', 12, '66666666666', '412156123435153', '河南省许昌市建安区\n河南农业大学');
INSERT INTO `user_dict` VALUES (138, '7', '7@qq.com', 'male', 312, '77777777777', '143243254352345', 'dsfdsdfsdgdsg');
INSERT INTO `user_dict` VALUES (139, '8', '8@qq.com', 'male', 4152, '88888888888', '143243254352345', '河南省许昌市建安区\n河南农业大学');
INSERT INTO `user_dict` VALUES (142, '9', '9@qq.com', 'male', 15, '99999999999', '412156123435153', '河南省许昌市建安区\n河南农业大学');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account` ASC) USING BTREE,
  CONSTRAINT `user_id` FOREIGN KEY (`id`) REFERENCES `user_dict` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 154 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (4, '44', 'i8hss0yFbGYuSudx8jIPZRfqHolTabNeBa4tdF/OBcfQ97IcO2s1n/uJoKOGB3Y8B8L1GMeaoVJDxe98fO+dCw==');
INSERT INTO `users` VALUES (131, '1', 'jU07zLKfb9LBM6qe4jGL9UKGDJi+6pi/L10Uj2up5lpWKCPWGClxe9qA19YcY8gsaeLQFaSYs02jBJ3azUsTPQ==');
INSERT INTO `users` VALUES (133, '2', 'fjoV1z44x6qBwB6rlEpbP/wqBOFPROX4MI/hAdjGOH1Gc0431Imq84ikucpdMZ3ePTzu62CHwWZTfXvwyWsrig==');
INSERT INTO `users` VALUES (134, '3', 'D5+dhSMvNRr/658OUYLth9HUc0XaIMOQcNqIUZapFaeeEClmTTE8GYdPL/CGq/7N6OgyiR3EHDL6B/t114Hv/Q==');
INSERT INTO `users` VALUES (135, '4', 'ZZK1KJu5Ud/lpi3evc81+sILuqGNoUlPBeM5zc2M03XFr1ZdoGN6YE9fyl4IslfCkmT0yK+gVummnBBXWcbK5g==');
INSERT INTO `users` VALUES (136, '5', 'by1ekzViVepwV1dWTHmlYGPMj4XvHtqYjCppv1pRjGAwMrq8yZXBzGhKgvihEXKkLTPyN0bD8xwIP0M+XDEGPg==');
INSERT INTO `users` VALUES (137, '6', 'm1vISgIOjIY9zTVSmrmKGKRi+5jlc1utDTMKbxvH9VN88bPhrNDZ0XoD93r6q2MQSnGLMmmmPpRbuBMH6TLWNA==');
INSERT INTO `users` VALUES (138, '7', 'gxIW5lDUP+02CNiEj2Ghn5S/9mzXGaSvKm/Bqi38L8tmTnxIbGF99HjjcWpsennDaDn/n8OBb/tZmFpdjVWhoQ==');
INSERT INTO `users` VALUES (139, '8', 'jheSJEKplkmboYZSZY5sH4JktbuAHS0f+L8GZsH8ncv5gZS3t4Rv9ir+Z1VySOMCzMnPs9nvmSU6ZmS/7usH6g==');
INSERT INTO `users` VALUES (142, '9', 'BSfUu5WDEbz4uVamKnbbSHeCzosPCln0ODoXJ10E0BJbuRgUAJTkg4zXXpsVslJct0izraquu1MhYKrngGE5ng==');

SET FOREIGN_KEY_CHECKS = 1;
