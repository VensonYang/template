/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.26-log : Database - template
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`template` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `template`;

/*Table structure for table `t_department` */

DROP TABLE IF EXISTS `t_department`;

CREATE TABLE `t_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` char(100) DEFAULT NULL COMMENT '名称',
  `pid` int(11) DEFAULT NULL COMMENT '上级部门',
  `deptNo` char(100) DEFAULT NULL COMMENT '部门编号',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` char(50) DEFAULT NULL COMMENT '创建人',
  `status` char(50) DEFAULT NULL COMMENT '状态',
  `memo` char(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_department` */

insert  into `t_department`(`id`,`name`,`pid`,`deptNo`,`createTime`,`creator`,`status`,`memo`) values (1,'行政部',NULL,'1','2016-07-18 15:30:11','admin','1',NULL),(2,'开发部',NULL,'2','2016-07-18 15:30:19','admin','1',NULL);

/*Table structure for table `t_file` */

DROP TABLE IF EXISTS `t_file`;

CREATE TABLE `t_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fileDesc` text COMMENT '描述',
  `fileName` char(200) NOT NULL COMMENT '文件名称',
  `fileType` char(100) DEFAULT NULL COMMENT '文件类型',
  `fileSize` int(11) DEFAULT NULL COMMENT '文件大小',
  `filePath` char(200) NOT NULL COMMENT '文件路径',
  `createor` int(11) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `memo` char(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `createor` (`createor`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

/*Data for the table `t_file` */

insert  into `t_file`(`id`,`fileDesc`,`fileName`,`fileType`,`fileSize`,`filePath`,`createor`,`createTime`,`memo`) values (53,NULL,'试卷模板样式__历史中考.doc','application/msword',706560,'upload/attachment/20160624/0ddd0221-95a4-46bc-91d9-f3e5d8acb175-1466760733416.doc',1,'2016-06-24 17:32:13',NULL),(54,NULL,'试卷模板样式__历史中考.doc','application/msword',710656,'upload/attachment/20160624/1ffb10a7-6c8a-4f63-b868-5f7ac5ab041a-1466764151939.doc',1,'2016-06-24 18:29:12',NULL);

/*Table structure for table `t_news` */

DROP TABLE IF EXISTS `t_news`;

CREATE TABLE `t_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` char(50) NOT NULL COMMENT '新闻标题',
  `publisher` char(10) NOT NULL COMMENT '发布者',
  `newtypeID` int(11) NOT NULL COMMENT '新闻类型',
  `content` text NOT NULL COMMENT '新闻内容',
  `source` char(50) DEFAULT NULL COMMENT '新闻来源',
  `imageUrl` char(100) DEFAULT NULL COMMENT '新闻图片',
  `summary` varchar(1000) DEFAULT NULL COMMENT '新闻摘要',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `status` char(2) NOT NULL COMMENT '新闻状态',
  PRIMARY KEY (`id`),
  KEY `FK_Relationship_1` (`newtypeID`) USING BTREE,
  CONSTRAINT `FKiyoraw37my2hcr93dhok32ohh` FOREIGN KEY (`newtypeID`) REFERENCES `t_news_type` (`id`),
  CONSTRAINT `t_news_ibfk_1` FOREIGN KEY (`newtypeID`) REFERENCES `t_news_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='新闻基本信息表';

/*Data for the table `t_news` */

insert  into `t_news`(`id`,`title`,`publisher`,`newtypeID`,`content`,`source`,`imageUrl`,`summary`,`createTime`,`modifyTime`,`status`) values (1,'跟着习近平去植树','admin',2,'<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size: 14px;\">春回大地，草长莺飞，又到了植树造林的大好时节。党和国家领导人</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/5/xijinping\" target=\"_blank\"><span style=\"font-size: 14px;\">习近平</span></a><span style=\"font-size: 14px;\">、</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/2/likeqiang\" target=\"_blank\"><span style=\"font-size: 14px;\">李克强</span></a><span style=\"font-size: 14px;\">、</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/12/zhangdejiang\" target=\"_blank\"><span style=\"font-size: 14px;\">张德江</span></a><span style=\"font-size: 14px;\">、</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/21/yuzhengsheng\" target=\"_blank\"><span style=\"font-size: 14px;\">俞正声</span></a><span style=\"font-size: 14px;\">、</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/39/liuyunshan\" target=\"_blank\"><span style=\"font-size: 14px;\">刘云山</span></a><span style=\"font-size: 14px;\">、</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/54/wangqishan\" target=\"_blank\"><span style=\"font-size: 14px;\">王岐山</span></a><span style=\"font-size: 14px;\">、</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/61/zhanggaoli\" target=\"_blank\"><span style=\"font-size: 14px;\">张高丽</span></a><span style=\"font-size: 14px;\">等5日上午参加了首都义务植树活动。这，已经成为每年的惯例了。</span></p><p><span style=\"font-size: 14px;\">你知道吗，习总书记可不只是每年就种这么一次树。前几天访问捷克期间，他还在布拉格拉尼庄园和捷克总统泽曼一同种下了一株来自中国的银杏树苗。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;前人栽树，后人乘凉。植树造林，是每个公民的法定义务，不仅有利于保护环境，也可以锻炼身体，还能够寄托人们对美好未来的祝福，承载人与人之间、国与国之间的长久友谊。</span></p><p><span style=\"font-size: 14px;\">既然有这么多好处，就让我们趁着大好春光、走向原野郊区，跟着总书记一起去植树吧！</span></p><p><span style=\"font-size: 14px;\"><strong>总书记在哪里植过树？</strong></span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2012年12月8日上午，广东省深圳市莲花山公园</span></p><p style=\"text-align: center;\"><img alt=\"新华社记者兰红光摄\" src=\"http://y0.ifengimg.com/cmpp/2016/04/06/08/4e0e845a-b3d5-4645-8fd8-ed9eefac3c0f_size240_w486_h600.jpg\"/><br/></p><p><br/></p><p><br/></p>','凤凰网',NULL,'跟着习总书记去植树','2016-04-06 14:47:51',NULL,'1'),(2,' 朱小丹：促进珠三角与粤东西北协调发展','admin',2,'<p style=\"margin-top: 5px;\"><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹</span><span style=\"font-size: 14px;\">出席省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议强调</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5日上午，省政府召开省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议。省长朱小丹出席会议并讲话。省委常委、常务副省长</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/478/xushaohua\" target=\"_blank\"><span style=\"font-size: 14px;\">徐少华</span></a><span style=\"font-size: 14px;\">主持会议。副省长许瑞生、邓海光、袁宝成，广州市市长温国辉、深圳市市长许勤出席会议。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹指出，一年来，各市、各部门认真贯彻落实省委、省政府的决策部署，深入实施《珠三角规划纲要》和促进粤东西北振兴发展战略，珠三角发展质量不断提升，粤东西北发展后劲不断增强，全省区域发展差异系数由2010年的0.68调整为2015年的0.66。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹指出，面对新形势新任务新挑战，在更高层次上推进珠三角优化发展和粤东西北振兴发展，必须牢牢把握经济发展新常态的阶段性特征，坚定不移践行\n新发展理念。在珠三角优化发展方面，珠三角地区作为我省实现“双支撑”、“双中高”的“压舱石”、“顶梁柱”，要更好地发挥对全省乃至全国发展大局的支撑\n引领作用。加快推进珠三角国家自主创新示范区建设，形成分工互补的“1+1+7”区域创新格局；坚持主动减量、调整存量、引导增量“三管齐下”，找准“稳\n增长”与“调结构”的结合点和平衡点，全力推进供给侧结构性改革攻坚；</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深入推进重点领域改革攻坚，持续抓好各项改革试点工作；推进珠三角城市群一体化发展，加快打造世界级城市群建设，增强在全国乃至全球的集聚辐射功能\n和综合竞争力；高标准抓好广东自贸试验区建设，更加积极参与“一带一路”建设；率先推进基本公共服务均等化和社会保障城乡一体化。在粤东西北振兴发展方\n面，粤东西北各市要对照全省到2018年率先全面建成小康社会的目标，努力在加快发展中调整结构，不断提高经济发展内生动力。加快推进重大基础设施建设同\n步推进重点项目落地、传统优势产业转型和产业园区提质增效，协调推进以人为本的新型城镇化，大力推进县域经济发展，坚决打赢脱贫攻坚战，坚定推进绿色低碳\n循环发展。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹强调，珠三角、粤东西北两个区域要按照优势互补的协调发展理念要求，促进珠三角与粤东西北地区联动融合、协调发展，推进产业链跨区域对接延伸，继续坚定不移地抓好“双转移”，统筹推进全面对口帮扶。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会议还审议了《实施珠三角规划纲要2016年重点工作任务》、《促进粤东西北地区振兴发展2016年重点工作任务》、《实施珠三角规划纲要2015\n年度工作评估考核方案》、《促进粤东西北地区振兴发展2015年度工作评估考核方案》、《珠三角地区对口帮扶粤东西北地区2014-2016年工作评估考\n核工作方案》等文稿。</span></p><p><span style=\"font-size: 14px;\"><br/></span></p>','凤凰网',NULL,'促进珠三角与粤东西北联动融合协调发展','2016-04-06 14:50:47',NULL,'1'),(3,'98.1%网民拥护习近平总书记提出的中国治网主张','admin',2,'<p style=\"margin-top: 5px;\"><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹</span><span style=\"font-size: 14px;\">出席省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议强调</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5日上午，省政府召开省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议。省长朱小丹出席会议并讲话。省委常委、常务副省长</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/478/xushaohua\" target=\"_blank\"><span style=\"font-size: 14px;\">徐少华</span></a><span style=\"font-size: 14px;\">主持会议。副省长许瑞生、邓海光、袁宝成，广州市市长温国辉、深圳市市长许勤出席会议。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱\n小丹指出，一年来，各市、各部门认真贯彻落实省委、省政府的决策部署，深入实施《珠三角规划纲要》和促进粤东西北振兴发展战略，珠三角发展质量不断提升，\n粤东西北发展后劲不断增强，全省区域发展差异系数由2010年的0.68调整为2015年的0.66。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹指出，面对新形势新任务新挑战，在更高层次上推进珠三角优化发展和粤东西北振兴发展，必须牢牢把握经济发展新常态的阶段性特征，坚定不移践行\n新发展理念。在珠三角优化发展方面，珠三角地区作为我省实现“双支撑”、“双中高”的“压舱石”、“顶梁柱”，要更好地发挥对全省乃至全国发展大局的支撑\n引领作用。加快推进珠三角国家自主创新示范区建设，形成分工互补的“1+1+7”区域创新格局；坚持主动减量、调整存量、引导增量“三管齐下”，找准“稳\n增长”与“调结构”的结合点和平衡点，全力推进供给侧结构性改革攻坚；</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深入推进重点领域改革攻坚，持续抓好各项改革试点工作；推进珠三角城市群一体化发展，加快打造世界级城市群建设，增强在全国乃至全球的集聚辐射功能\n和综合竞争力；高标准抓好广东自贸试验区建设，更加积极参与“一带一路”建设；率先推进基本公共服务均等化和社会保障城乡一体化。在粤东西北振兴发展方\n面，粤东西北各市要对照全省到2018年率先全面建成小康社会的目标，努力在加快发展中调整结构，不断提高经济发展内生动力。加快推进重大基础设施建设同\n步推进重点项目落地、传统优势产业转型和产业园区提质增效，协调推进以人为本的新型城镇化，大力推进县域经济发展，坚决打赢脱贫攻坚战，坚定推进绿色低碳\n循环发展。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹强调，珠三角、粤东西北两个区域要按照优势互补的协调发展理念要求，促进珠三角与粤东西北地区联动融合、协调发展，推进产业链跨区域对接延伸，继续坚定不移地抓好“双转移”，统筹推进全面对口帮扶。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会议还审议了《实施珠三角规划纲要2016年重点工作任务》、《促进粤东西北地区振兴发展2016年重点工作任务》、《实施珠三角规划纲要2015\n年度工作评估考核方案》、《促进粤东西北地区振兴发展2015年度工作评估考核方案》、《珠三角地区对口帮扶粤东西北地区2014-2016年工作评估考\n核工作方案》等文稿。</span></p><p><span style=\"font-size: 14px;\"><br/></span></p><p><br/></p>','凤凰网',NULL,NULL,'2016-03-03 15:49:23','2016-04-06 14:53:31','1'),(4,'2015政府工作如何，国务院交成绩单了','admin',2,'<p style=\"margin-top: 5px;\"><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹</span><span style=\"font-size: 14px;\">出席省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议强调</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5日上午，省政府召开省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议。省长朱小丹出席会议并讲话。省委常委、常务副省长</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/478/xushaohua\" target=\"_blank\"><span style=\"font-size: 14px;\">徐少华</span></a><span style=\"font-size: 14px;\">主持会议。副省长许瑞生、邓海光、袁宝成，广州市市长温国辉、深圳市市长许勤出席会议。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱\n小丹指出，一年来，各市、各部门认真贯彻落实省委、省政府的决策部署，深入实施《珠三角规划纲要》和促进粤东西北振兴发展战略，珠三角发展质量不断提升，\n粤东西北发展后劲不断增强，全省区域发展差异系数由2010年的0.68调整为2015年的0.66。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹指出，面对新形势新任务新挑战，在更高层次上推进珠三角优化发展和粤东西北振兴发展，必须牢牢把握经济发展新常态的阶段性特征，坚定不移践行\n新发展理念。在珠三角优化发展方面，珠三角地区作为我省实现“双支撑”、“双中高”的“压舱石”、“顶梁柱”，要更好地发挥对全省乃至全国发展大局的支撑\n引领作用。加快推进珠三角国家自主创新示范区建设，形成分工互补的“1+1+7”区域创新格局；坚持主动减量、调整存量、引导增量“三管齐下”，找准“稳\n增长”与“调结构”的结合点和平衡点，全力推进供给侧结构性改革攻坚；</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深入推进重点领域改革攻坚，持续抓好各项改革试点工作；推进珠三角城市群一体化发展，加快打造世界级城市群建设，增强在全国乃至全球的集聚辐射功能\n和综合竞争力；高标准抓好广东自贸试验区建设，更加积极参与“一带一路”建设；率先推进基本公共服务均等化和社会保障城乡一体化。在粤东西北振兴发展方\n面，粤东西北各市要对照全省到2018年率先全面建成小康社会的目标，努力在加快发展中调整结构，不断提高经济发展内生动力。加快推进重大基础设施建设同\n步推进重点项目落地、传统优势产业转型和产业园区提质增效，协调推进以人为本的新型城镇化，大力推进县域经济发展，坚决打赢脱贫攻坚战，坚定推进绿色低碳\n循环发展。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹强调，珠三角、粤东西北两个区域要按照优势互补的协调发展理念要求，促进珠三角与粤东西北地区联动融合、协调发展，推进产业链跨区域对接延伸，继续坚定不移地抓好“双转移”，统筹推进全面对口帮扶。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会议还审议了《实施珠三角规划纲要2016年重点工作任务》、《促进粤东西北地区振兴发展2016年重点工作任务》、《实施珠三角规划纲要2015\n年度工作评估考核方案》、《促进粤东西北地区振兴发展2015年度工作评估考核方案》、《珠三角地区对口帮扶粤东西北地区2014-2016年工作评估考\n核工作方案》等文稿。</span></p><p><span style=\"font-size: 14px;\"><br/></span></p><p><br/></p>','凤凰网',NULL,NULL,'2016-03-03 15:52:59','2016-04-06 14:53:47','1'),(5,'楼继伟：中国有充足政策工具保障中高速增长','admin',2,'<p style=\"margin-top: 5px;\"><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹</span><span style=\"font-size: 14px;\">出席省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议强调</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5日上午，省政府召开省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议。省长朱小丹出席会议并讲话。省委常委、常务副省长</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/478/xushaohua\" target=\"_blank\"><span style=\"font-size: 14px;\">徐少华</span></a><span style=\"font-size: 14px;\">主持会议。副省长许瑞生、邓海光、袁宝成，广州市市长温国辉、深圳市市长许勤出席会议。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱\n小丹指出，一年来，各市、各部门认真贯彻落实省委、省政府的决策部署，深入实施《珠三角规划纲要》和促进粤东西北振兴发展战略，珠三角发展质量不断提升，\n粤东西北发展后劲不断增强，全省区域发展差异系数由2010年的0.68调整为2015年的0.66。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹指出，面对新形势新任务新挑战，在更高层次上推进珠三角优化发展和粤东西北振兴发展，必须牢牢把握经济发展新常态的阶段性特征，坚定不移践行\n新发展理念。在珠三角优化发展方面，珠三角地区作为我省实现“双支撑”、“双中高”的“压舱石”、“顶梁柱”，要更好地发挥对全省乃至全国发展大局的支撑\n引领作用。加快推进珠三角国家自主创新示范区建设，形成分工互补的“1+1+7”区域创新格局；坚持主动减量、调整存量、引导增量“三管齐下”，找准“稳\n增长”与“调结构”的结合点和平衡点，全力推进供给侧结构性改革攻坚；</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深入推进重点领域改革攻坚，持续抓好各项改革试点工作；推进珠三角城市群一体化发展，加快打造世界级城市群建设，增强在全国乃至全球的集聚辐射功能\n和综合竞争力；高标准抓好广东自贸试验区建设，更加积极参与“一带一路”建设；率先推进基本公共服务均等化和社会保障城乡一体化。在粤东西北振兴发展方\n面，粤东西北各市要对照全省到2018年率先全面建成小康社会的目标，努力在加快发展中调整结构，不断提高经济发展内生动力。加快推进重大基础设施建设同\n步推进重点项目落地、传统优势产业转型和产业园区提质增效，协调推进以人为本的新型城镇化，大力推进县域经济发展，坚决打赢脱贫攻坚战，坚定推进绿色低碳\n循环发展。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹强调，珠三角、粤东西北两个区域要按照优势互补的协调发展理念要求，促进珠三角与粤东西北地区联动融合、协调发展，推进产业链跨区域对接延伸，继续坚定不移地抓好“双转移”，统筹推进全面对口帮扶。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会议还审议了《实施珠三角规划纲要2016年重点工作任务》、《促进粤东西北地区振兴发展2016年重点工作任务》、《实施珠三角规划纲要2015\n年度工作评估考核方案》、《促进粤东西北地区振兴发展2015年度工作评估考核方案》、《珠三角地区对口帮扶粤东西北地区2014-2016年工作评估考\n核工作方案》等文稿。</span></p><p><span style=\"font-size: 14px;\"><br/></span></p><p><br/></p>','凤凰网',NULL,NULL,'2016-03-03 16:02:13','2016-04-06 14:53:56','1'),(6,'安理会通过最严厉对朝鲜制裁决议','admin',2,'<p style=\"margin-top: 5px;\"><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹</span><span style=\"font-size: 14px;\">出席省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议强调</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5日上午，省政府召开省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议。省长朱小丹出席会议并讲话。省委常委、常务副省长</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/478/xushaohua\" target=\"_blank\"><span style=\"font-size: 14px;\">徐少华</span></a><span style=\"font-size: 14px;\">主持会议。副省长许瑞生、邓海光、袁宝成，广州市市长温国辉、深圳市市长许勤出席会议。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱\n小丹指出，一年来，各市、各部门认真贯彻落实省委、省政府的决策部署，深入实施《珠三角规划纲要》和促进粤东西北振兴发展战略，珠三角发展质量不断提升，\n粤东西北发展后劲不断增强，全省区域发展差异系数由2010年的0.68调整为2015年的0.66。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹指出，面对新形势新任务新挑战，在更高层次上推进珠三角优化发展和粤东西北振兴发展，必须牢牢把握经济发展新常态的阶段性特征，坚定不移践行\n新发展理念。在珠三角优化发展方面，珠三角地区作为我省实现“双支撑”、“双中高”的“压舱石”、“顶梁柱”，要更好地发挥对全省乃至全国发展大局的支撑\n引领作用。加快推进珠三角国家自主创新示范区建设，形成分工互补的“1+1+7”区域创新格局；坚持主动减量、调整存量、引导增量“三管齐下”，找准“稳\n增长”与“调结构”的结合点和平衡点，全力推进供给侧结构性改革攻坚；</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深入推进重点领域改革攻坚，持续抓好各项改革试点工作；推进珠三角城市群一体化发展，加快打造世界级城市群建设，增强在全国乃至全球的集聚辐射功能\n和综合竞争力；高标准抓好广东自贸试验区建设，更加积极参与“一带一路”建设；率先推进基本公共服务均等化和社会保障城乡一体化。在粤东西北振兴发展方\n面，粤东西北各市要对照全省到2018年率先全面建成小康社会的目标，努力在加快发展中调整结构，不断提高经济发展内生动力。加快推进重大基础设施建设同\n步推进重点项目落地、传统优势产业转型和产业园区提质增效，协调推进以人为本的新型城镇化，大力推进县域经济发展，坚决打赢脱贫攻坚战，坚定推进绿色低碳\n循环发展。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹强调，珠三角、粤东西北两个区域要按照优势互补的协调发展理念要求，促进珠三角与粤东西北地区联动融合、协调发展，推进产业链跨区域对接延伸，继续坚定不移地抓好“双转移”，统筹推进全面对口帮扶。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会议还审议了《实施珠三角规划纲要2016年重点工作任务》、《促进粤东西北地区振兴发展2016年重点工作任务》、《实施珠三角规划纲要2015\n年度工作评估考核方案》、《促进粤东西北地区振兴发展2015年度工作评估考核方案》、《珠三角地区对口帮扶粤东西北地区2014-2016年工作评估考\n核工作方案》等文稿。</span></p><p><span style=\"font-size: 14px;\"><br/></span></p><p><br/></p>','新华社',NULL,NULL,'2016-03-03 16:58:37','2016-04-06 14:54:06','1'),(7,'习近平参加黑龙江团审议 了解煤企现状(图)','admin',2,'<p style=\"margin-top: 5px;\"><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹</span><span style=\"font-size: 14px;\">出席省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议强调</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5日上午，省政府召开省实施《珠三角规划纲要》领导小组和省促进粤东西北地区振兴发展协调领导小组会议。省长朱小丹出席会议并讲话。省委常委、常务副省长</span><a style=\"font-size: 14px; text-decoration: underline;\" href=\"http://renwuku.news.ifeng.com/index/detail/478/xushaohua\" target=\"_blank\"><span style=\"font-size: 14px;\">徐少华</span></a><span style=\"font-size: 14px;\">主持会议。副省长许瑞生、邓海光、袁宝成，广州市市长温国辉、深圳市市长许勤出席会议。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱\n小丹指出，一年来，各市、各部门认真贯彻落实省委、省政府的决策部署，深入实施《珠三角规划纲要》和促进粤东西北振兴发展战略，珠三角发展质量不断提升，\n粤东西北发展后劲不断增强，全省区域发展差异系数由2010年的0.68调整为2015年的0.66。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹指出，面对新形势新任务新挑战，在更高层次上推进珠三角优化发展和粤东西北振兴发展，必须牢牢把握经济发展新常态的阶段性特征，坚定不移践行\n新发展理念。在珠三角优化发展方面，珠三角地区作为我省实现“双支撑”、“双中高”的“压舱石”、“顶梁柱”，要更好地发挥对全省乃至全国发展大局的支撑\n引领作用。加快推进珠三角国家自主创新示范区建设，形成分工互补的“1+1+7”区域创新格局；坚持主动减量、调整存量、引导增量“三管齐下”，找准“稳\n增长”与“调结构”的结合点和平衡点，全力推进供给侧结构性改革攻坚；</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深入推进重点领域改革攻坚，持续抓好各项改革试点工作；推进珠三角城市群一体化发展，加快打造世界级城市群建设，增强在全国乃至全球的集聚辐射功能\n和综合竞争力；高标准抓好广东自贸试验区建设，更加积极参与“一带一路”建设；率先推进基本公共服务均等化和社会保障城乡一体化。在粤东西北振兴发展方\n面，粤东西北各市要对照全省到2018年率先全面建成小康社会的目标，努力在加快发展中调整结构，不断提高经济发展内生动力。加快推进重大基础设施建设同\n步推进重点项目落地、传统优势产业转型和产业园区提质增效，协调推进以人为本的新型城镇化，大力推进县域经济发展，坚决打赢脱贫攻坚战，坚定推进绿色低碳\n循环发展。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;朱小丹强调，珠三角、粤东西北两个区域要按照优势互补的协调发展理念要求，促进珠三角与粤东西北地区联动融合、协调发展，推进产业链跨区域对接延伸，继续坚定不移地抓好“双转移”，统筹推进全面对口帮扶。</span></p><p><span style=\"font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会议还审议了《实施珠三角规划纲要2016年重点工作任务》、《促进粤东西北地区振兴发展2016年重点工作任务》、《实施珠三角规划纲要2015\n年度工作评估考核方案》、《促进粤东西北地区振兴发展2015年度工作评估考核方案》、《珠三角地区对口帮扶粤东西北地区2014-2016年工作评估考\n核工作方案》等文稿。</span></p><p><span style=\"font-size: 14px;\"><br/></span></p><p><br/></p>','凤凰网',NULL,NULL,'2016-03-07 14:35:06','2016-04-06 14:54:17','1');

/*Table structure for table `t_news_type` */

DROP TABLE IF EXISTS `t_news_type`;

CREATE TABLE `t_news_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL COMMENT '新闻类型名称',
  `status` char(2) NOT NULL COMMENT '状态 0:启用 1:禁用',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `creator` int(11) NOT NULL COMMENT '创建者',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `sort` int(11) NOT NULL COMMENT '排序',
  `memo` char(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='新闻类型';

/*Data for the table `t_news_type` */

insert  into `t_news_type`(`id`,`name`,`status`,`createTime`,`modifyTime`,`creator`,`modifier`,`sort`,`memo`) values (2,'社会','1','2016-03-03 15:07:49',NULL,0,NULL,1,NULL),(3,'财经','1','2016-03-03 15:08:00',NULL,0,NULL,2,NULL),(4,'军事','1','2016-03-03 15:08:13',NULL,0,NULL,3,NULL),(5,'科技','1','2016-03-03 15:08:26',NULL,0,NULL,4,NULL),(6,'国际','1','2016-03-03 17:17:05',NULL,0,NULL,6,NULL),(7,'汽车','1','2016-03-03 17:17:23',NULL,0,NULL,7,NULL);

/*Table structure for table `t_priviledges` */

DROP TABLE IF EXISTS `t_priviledges`;

CREATE TABLE `t_priviledges` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` char(20) DEFAULT NULL COMMENT '名称',
  `url` char(100) NOT NULL COMMENT '权限控制地址',
  `status` char(2) NOT NULL COMMENT '是否可用',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `pid` int(11) DEFAULT NULL COMMENT '父节点',
  `icon` char(25) DEFAULT NULL COMMENT '图标',
  `creator` int(11) DEFAULT NULL COMMENT '创建者(对应userId)',
  `memo` char(100) DEFAULT NULL COMMENT '备注',
  `isParent` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='用户权限信息表';

/*Data for the table `t_priviledges` */

insert  into `t_priviledges`(`id`,`name`,`url`,`status`,`createTime`,`modifyTime`,`pid`,`icon`,`creator`,`memo`,`isParent`) values (2,'权限管理','system/priviledges.html','1','2015-07-21 10:18:01','2015-09-09 14:24:53',5,'fa-key',1,NULL,NULL),(3,'用户管理','system/user.html','1','2015-09-09 14:52:49','2015-09-09 14:54:39',5,'fa-users',1,NULL,NULL),(4,'角色管理','system/role.html','1','2016-02-26 11:40:00',NULL,5,'fa-user',1,NULL,NULL),(5,'系统维护','#','1','2016-02-26 11:46:30',NULL,0,'fa-desktop',1,NULL,NULL),(9,'新闻维护','#','1','2016-03-01 13:39:36',NULL,0,'fa-newspaper-o',1,NULL,NULL),(10,'新闻类型维护','news/news_type.html','1','2016-03-01 13:40:51','2016-03-31 17:33:41',9,'fa-arrows',1,NULL,NULL),(11,'新闻管理','news/news.html','1','2016-03-01 13:41:22','2016-03-31 17:29:11',9,'fa-cog',1,NULL,NULL),(12,'百度一下','http://www.baidu.com','1','2016-03-23 14:04:44','2016-03-31 17:24:18',13,'fa-search',1,NULL,NULL),(13,'友情链接','#','1','2016-03-23 15:02:56','2016-03-31 17:28:14',0,'fa-link',1,NULL,NULL),(14,'凤凰网','http://www.ifeng.com/','1','2016-03-25 10:28:43','2016-03-31 17:28:43',13,' fa-info',1,NULL,NULL),(15,'通用模块','#','1','2016-03-25 14:08:34','2016-05-19 18:43:21',0,' fa-globe',1,NULL,NULL),(16,'打印_导出_导入','universal/print_export_import.html','1','2016-03-25 14:08:53','2016-03-31 17:27:46',15,'fa-print',1,NULL,NULL),(17,'上传下载','universal/netdisk.html','1','2016-03-29 16:27:10','2016-03-31 17:27:22',15,'fa-upload',1,NULL,NULL),(18,'头像上传','universal/crop.html','1','2016-03-30 10:11:51','2016-03-31 17:27:00',15,'fa-image (alias)',1,NULL,NULL),(19,'百度图表','universal/echarts.html','1','2016-03-31 11:14:00','2016-03-31 17:26:19',15,'fa-line-chart',1,NULL,NULL),(20,'报表模块','universal/jasper.html','1','2016-04-01 09:05:24','2016-04-01 09:06:29',15,'fa-sliders',1,NULL,NULL);

/*Table structure for table `t_priviledges_matrix` */

DROP TABLE IF EXISTS `t_priviledges_matrix`;

CREATE TABLE `t_priviledges_matrix` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleID` int(11) NOT NULL COMMENT '角色ID',
  `priviledgesID` int(11) NOT NULL COMMENT '权限ID',
  `iscreate` tinyint(1) NOT NULL COMMENT '是否可创建',
  `isdelete` tinyint(1) NOT NULL COMMENT '是否可删除',
  `ismodify` tinyint(1) NOT NULL COMMENT '是否可修改',
  `isselect` tinyint(1) NOT NULL COMMENT '是否可查询',
  `isprint` tinyint(1) NOT NULL COMMENT '是否可打印',
  `isimport` tinyint(1) NOT NULL COMMENT '是否可导入',
  `isexport` tinyint(1) NOT NULL COMMENT '是否可导出',
  `memo` char(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_75` (`roleID`) USING BTREE,
  KEY `FK_Reference_76` (`priviledgesID`) USING BTREE,
  CONSTRAINT `FK962y6m8qvrr54kfhlq41hr86s` FOREIGN KEY (`priviledgesID`) REFERENCES `t_priviledges` (`id`),
  CONSTRAINT `FKfg0macdh1clpqu6nqjsrdse55` FOREIGN KEY (`roleID`) REFERENCES `t_role` (`id`),
  CONSTRAINT `t_priviledges_matrix_ibfk_1` FOREIGN KEY (`priviledgesID`) REFERENCES `t_priviledges` (`id`),
  CONSTRAINT `t_priviledges_matrix_ibfk_2` FOREIGN KEY (`roleID`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='权限矩阵信息表';

/*Data for the table `t_priviledges_matrix` */

insert  into `t_priviledges_matrix`(`id`,`roleID`,`priviledgesID`,`iscreate`,`isdelete`,`ismodify`,`isselect`,`isprint`,`isimport`,`isexport`,`memo`) values (2,1,2,1,1,1,1,1,1,1,NULL),(3,1,3,1,1,1,1,1,1,1,NULL),(4,1,4,1,1,1,1,1,1,1,NULL),(6,1,10,1,1,1,1,1,1,1,'auto'),(7,1,11,1,1,1,1,1,1,1,'auto'),(9,1,12,1,1,1,1,1,1,1,'auto'),(10,1,12,1,1,1,1,1,1,1,'auto'),(11,1,14,1,1,1,1,1,1,1,'auto'),(14,1,16,1,1,1,1,1,1,1,'auto'),(15,1,17,1,1,1,1,1,1,1,'auto'),(16,1,18,1,1,1,1,1,1,1,'auto'),(17,1,19,1,1,1,1,1,1,1,'auto'),(18,1,20,1,1,1,1,1,1,1,'auto'),(60,5,10,1,1,1,1,1,1,1,'auto'),(61,5,11,1,1,1,1,1,1,1,'auto'),(62,5,12,1,1,1,1,1,1,1,'auto'),(63,5,14,1,1,1,1,1,1,1,'auto'),(64,5,16,1,1,1,1,1,1,1,'auto');

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` char(20) NOT NULL COMMENT '角色名称',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `status` char(2) DEFAULT NULL COMMENT '状态(0：有效 1：无效)',
  `memo` char(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户角色信息表';

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`name`,`createTime`,`modifyTime`,`status`,`memo`) values (1,'admin','2015-06-29 14:19:00','2016-02-29 08:51:45','1','admin'),(5,'user','2016-05-09 10:54:53','2016-05-18 18:36:49','1','用户角色'),(9,'4','2016-05-20 10:11:57',NULL,'sd',NULL);

/*Table structure for table `t_role_priviledges` */

DROP TABLE IF EXISTS `t_role_priviledges`;

CREATE TABLE `t_role_priviledges` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `priviledgesID` int(11) NOT NULL COMMENT '权限ID',
  `roleID` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `Relationship_15_FK` (`roleID`) USING BTREE,
  KEY `FK_Reference_74` (`priviledgesID`) USING BTREE,
  CONSTRAINT `FKfdx8t1gedy9i18lsrpij0pqd8` FOREIGN KEY (`priviledgesID`) REFERENCES `t_priviledges` (`id`),
  CONSTRAINT `FKiipffrvmkqrms5nga3948k2x9` FOREIGN KEY (`roleID`) REFERENCES `t_role` (`id`),
  CONSTRAINT `t_role_priviledges_ibfk_1` FOREIGN KEY (`priviledgesID`) REFERENCES `t_priviledges` (`id`),
  CONSTRAINT `t_role_priviledges_ibfk_2` FOREIGN KEY (`roleID`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COMMENT='角色权限对应关系表';

/*Data for the table `t_role_priviledges` */

insert  into `t_role_priviledges`(`id`,`priviledgesID`,`roleID`) values (2,2,1),(3,3,1),(4,4,1),(5,5,1),(34,9,1),(35,10,1),(36,11,1),(38,12,1),(39,13,1),(40,12,1),(41,14,1),(45,15,1),(46,16,1),(47,17,1),(48,18,1),(49,19,1),(50,20,1),(115,9,5),(116,10,5),(117,11,5),(118,13,5),(119,12,5),(120,14,5),(121,15,5),(122,16,5);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userName` char(20) NOT NULL COMMENT '用户名称',
  `sex` char(4) NOT NULL COMMENT '性别',
  `userAccount` char(32) NOT NULL COMMENT '账号',
  `password` char(32) NOT NULL COMMENT '登陆密码',
  `deptId` int(11) DEFAULT NULL COMMENT '用户部门',
  `status` char(2) DEFAULT NULL COMMENT '状态(0：有效 1：无效)',
  `creator` int(11) DEFAULT NULL COMMENT '创建者(对应userId)',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `modifier` int(1) DEFAULT NULL COMMENT '修改人',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `headImage` char(150) DEFAULT NULL COMMENT '用户头像',
  `memo` char(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userAccount` (`userAccount`),
  KEY `deptId` (`deptId`),
  CONSTRAINT `t_user_ibfk_1` FOREIGN KEY (`deptId`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userName`,`sex`,`userAccount`,`password`,`deptId`,`status`,`creator`,`createTime`,`modifier`,`modifyTime`,`headImage`,`memo`) values (1,'admin','男','9999','e10adc3949ba59abbe56e057f20f883e',NULL,'1',-1,'2015-12-12 16:25:33',0,NULL,'2e8d751d-f76c-461b-87f1-129f2b1030bc-1463708314739.jpg',NULL),(15,'test','男','6666','e10adc3949ba59abbe56e057f20f883e',NULL,'1',14,'2016-05-09 15:15:26',14,'2016-05-09 17:51:57',NULL,NULL);

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) DEFAULT NULL,
  `roleID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Relationship_FK_1` (`userID`),
  KEY `Relationship_FK_2` (`roleID`),
  CONSTRAINT `FK_t_user_role_relationship_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_t_user_role_relationship_ibfk_2` FOREIGN KEY (`roleID`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`id`,`userID`,`roleID`) values (1,1,1),(23,15,5);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
