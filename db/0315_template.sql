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
  CONSTRAINT `t_news_ibfk_1` FOREIGN KEY (`newtypeID`) REFERENCES `t_news_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='新闻基本信息表';

/*Data for the table `t_news` */

insert  into `t_news`(`id`,`title`,`publisher`,`newtypeID`,`content`,`source`,`imageUrl`,`summary`,`createTime`,`modifyTime`,`status`) values (2,'98.1%网民拥护习近平总书记提出的中国治网主张','admin',4,'<p><strong>98.1%网民拥护习近平总书记提出的中国治网主张</strong></p><p>2015年12月，以“互联互通，共享共治，构建网络空间命运共同体”为主题的第二届世界互联网大会<a href=\"http://app.travel.ifeng.com/scenery_detail-15164.html\" target=\"_blank\">乌镇</a>峰\n会在浙江乌镇召开，习近平总书记出席大会开幕式并做了主旨演讲,详细阐述了互联网发展的重大意义和深远影响，提出了推进全球互联网治理体系变革“四项原\n则”和构建网络空间命运共同体“五点主张”。“四项原则”，即“尊重网络主权、维护和平安全、促进开放合作、构建良好秩序”。“五个主张”，即“加快全球\n网络基础设施建设，促进互联互通；打造网上文化交流共享平台，促进交流互鉴；推动网络经济创新发展，促进共同繁荣；保障网络安全，促进有序发展；构建互联\n网治理体系，促进公平正义”。</p><p>本次调查显示，98.1%网民对习总书记提出的全球互联网建设和治理的中国主张表示拥护。</p><p><strong>9成以上网民积极评价网络国际合作</strong></p><p>习总书记在世界互联网大会开幕式的演讲中强调，要促进网络开放合作，搭建更多沟通合作平台，让更多国家和人民搭乘信息时代的快车、共享互联网发展成果。</p><p>据\n零点研究咨询集团《2015年政府外交工作满意度测评》报告显示，第二届世界互联网大会与第八届中美互联网论坛在“网民印象最深刻的2015年外交瞬间排\n行榜”中，位列第二与第三，仅次于有多国领导人参加的“纪念中国人民抗日战争暨世界反法西斯战争胜利70周年阅兵式”。调查显示，高达98.3%的网民认\n为世界互联网大会乌镇峰会对中国互联网发展具有积极影响，98.1%网民认为中美互联网论坛对中国互联网发展具有积极影响。</p><p><strong>9成以上网民支持针对网络安全专门立法</strong></p><p>习\n近平总书记在主旨演讲中明确表示，网络空间不是“法外之地”，要坚持依法治网、依法办网、依法上网，让互联网在法治轨道上健康运行。网民对习总书记的这一\n表述表现出高度认同。调查显示，98.1%的网民支持对网络安全专门立法。“网络安全”和“网络信息传播规范”位居网民最期待加强立法的网络内容前两位，\n其比例分别为74%和71.1%。<span class=\"ifengLogo\"><a href=\"http://www.ifeng.com/\" target=\"_blank\"><img src=\"http://y2.ifengimg.com/a/2015/0708/icon_logo.gif\"/></a></span></p><p><br/></p>','凤凰网',NULL,NULL,'2016-03-03 15:49:23',NULL,'1'),(3,'2015政府工作如何，国务院交成绩单了','admin',2,'<p>2015政府工作到底怎么样？铁路投资、新建改建农村公路、城镇新增就业、保障性安居工程……哪些是你关心的？这些目标完成的怎么样？在2016年两会来临之际，让我们一起来看政府的成绩单。<img style=\"width: 206px; height: 163px;\" alt=\"bridge.jpg\" src=\"http://localhost:8080/DITemplate/upload/news/content/20160303/f21dbe3a-3e78-4d74-a2f2-4fcdb0901f3f.jpg\" title=\"\" height=\"163\" width=\"206\"/><br/></p>','凤凰网',NULL,NULL,'2016-03-03 15:52:59',NULL,'1'),(5,'楼继伟：中国有充足政策工具保障中高速增长','admin',3,'<p>凤凰财经讯 中国两会正在进行当中，中央领导及各部委部长的言行备受关注。财政部部长楼继伟接受财新采访时表示，目前中国有充足的政策工具和巨大的改革空间保障中高速增长。</p><p>就具体政策而言，楼继伟称，中国现在赤字率不到3%，马斯特里赫特条约3%的红线标准并不是太客观。即使按照这个标准，中国的债务率也不是特别高。</p><p>中国财政赤字率将提高到多少，目前有多种不同的的观点，大部分学者建议提高到3%。5日，李克强做政府工作报告将提出具体目标。</p><p>此外，楼继伟表示，PPP、政府引导基金加杠杆，必须做到风险可控。目前的确<a href=\"http://car.auto.ifeng.com/series/2087/\" target=\"_blank\"><span style=\"color:#004276\">发现</span></a>，有的PPP、基金，变相是债，因为承诺了很多的回报，但这些情况我们是在规范的。</p><p><img style=\"width: 416px; height: 233px;\" alt=\"bridge.jpg\" src=\"http://localhost:8080/DITemplate/upload/news/content/20160303/3cefdcf5-3571-4037-aef4-25392bfe87e8.jpg\" title=\"\" height=\"233\" width=\"416\"/></p>','凤凰网',NULL,NULL,'2016-03-03 16:02:13','2016-03-03 17:42:00','1'),(6,'安理会通过最严厉对朝鲜制裁决议','admin',2,'<p>原标题：简讯：联合国安理会通过涉朝鲜决议</p><p>新华社联合国3月2日电（记者倪红梅顾震球）联合国安理会2日一致通过决议，决定实施一系列制裁措施遏制朝鲜的核、导开发计划，并呼吁恢复六方会谈。&nbsp;</p><p>决议说，安理会谴责朝鲜无视安理会相关决议，于1月6日进行的核试验及2月7日使用<a href=\"http://ent.ifeng.com/movie/special/dandao/\" target=\"_blank\">弹道</a>导弹技术从事发射活动。&nbsp;</p><p>为遏制朝鲜的核、导开发计划，决议出台一系列措施，包括要求各国禁止向朝鲜运送可能用于核、导计划的物品，收紧对朝鲜的武器禁运措施，冻结可能与核、导计划有关的<a href=\"http://auto.ifeng.com/news/finance/\" target=\"_blank\">金融</a>资产等。决议强调，有关措施无意对朝鲜平民造成不利的人道主义后果或对在朝鲜开展援助活动的工作产生不利影响。&nbsp;</p><p>决议重申维护朝鲜半岛和整个东北亚的和平与稳定至关重要，表示安理会承诺以和平、外交和政治方式解决这一局势，欢迎安理会成员及其他国家为通过对话实现和平及全面解决朝鲜核问题提供便利，不采取任何可能加剧紧张的行动。&nbsp;</p><p>决议重申对六方会谈的支持，呼吁恢复六方会谈，重申支持六方在2005年9月19日共同声明中阐述的承诺等。&nbsp;</p><p>朝鲜1月6日宣布进行核试验。这是朝鲜自2006年以来进行的第四次核试验。2月7日，朝鲜用远程火箭发射一颗卫星。联合国安理会随后发表媒体声明，谴责朝鲜使用弹道导弹技术从事发射活动，表示将迅速通过新决议，采取重要举措应对。（完）</p><p><strong>各方表态：</strong></p><p><strong>中国：<span style=\"font-size: 13.63636302948px;\">提半岛无核化和停和机制转换双轨并进思路</span></strong></p><p>中国外交部长王毅3月2日当天在与俄罗斯外长拉夫罗夫（Sergei&nbsp;Lavrov）通电话时说，解决半岛核问题的最终出路还是要回到对话谈判的轨道上来。为此，中方提出了半岛无核化和停和机制转换双轨并进思路，愿与俄方就此加强合作。</p><p>中国常驻联合国代表刘结一2号在安理会发言时指出，当天通过的对朝新决议，应成为政治解决朝鲜半岛核问题的新起点和“铺路石”。刘结一也表明，中方反对在朝鲜半岛部署萨德反导系统，指出这损害中国和地区其他国家安全利益，与维护半岛和平的目的背道而驰。&nbsp;</p><p>刘结一表示，当前朝鲜半岛局势高度复杂敏感，各方更要保持冷静，发挥外交智慧，中方希望有关各方与中方相向而行，始终从维护半岛和东北亚地区和平稳定的大局出发，为早日实现半岛长治久安发挥积极和建设性作用。&nbsp;</p><p><strong>俄罗斯：不能窒息朝鲜经济 与中方合作</strong></p><p>俄罗斯常驻联合国代表丘尔金表示，由于朝鲜进行了核试验和弹道导弹发射，破坏了国际社会的和平与稳定，安理会实施了一整套非常严酷的制裁，制止朝鲜的核相关战略物资供资渠道。然而，决议不应该用作窒息朝鲜经济。俄方呼吁尽快恢复六方会谈。</p><p>中国外交部长王毅，2号与俄罗斯外长拉夫罗夫通电话。王毅表示，中俄就安理会通过对朝新决议进行了有效沟通，就阻止朝鲜进一步发展核导计划、避免半岛生战生乱、维护中俄正当权益，形成重要共识，体现了中俄战略协作的高水平。&nbsp;</p><p>拉夫罗夫表示，俄方高度评价俄中就安理会新涉朝决议进行的密切协调，愿与中方继续保持沟通，维护核不扩散体系权威，保持半岛和平稳定。</p><p><strong>美国：朝鲜必须放弃危险项目</strong></p><p>美国务卿克里（John Kerry）也在制裁案通过后发表声明，表示欢迎。克里说，联合国的决议反应了国际社会应对朝鲜持续违背国际义务和承诺的问题的坚定和统一的决心。克里还说，新的决议是联合国20年来实施的最严厉的制裁。</p><p>美国总统奥巴马随后也发表声明说，国际社会一致向平壤发出了一个简单的信号：朝鲜必须放弃那些危险的项目，为它的人民选择一条更好的道路。</p><p>美国常驻联合国代表鲍尔（Samantha Power）表示，朝鲜利用的弹道导弹技术是在奴役它的人民的基础上进行的。我们要求朝鲜停止在这个道路上继续走下去。今天的决议是帮助朝鲜人民争取他们的人权，保障他们的生活质量。</p><p><strong>法国：吁朝遵守国际义务 恢复对话</strong></p><p>作为联合国五常之一以及欧盟重要国家的法国，呼吁朝鲜遵守国际义务，恢复对话的道路，实现朝鲜半岛，最终的安全与和平。</p><p>法\n国常驻联合国代表德拉特在决议通过后表示，朝鲜违反安理会决议，对国际区域和平安全构成了威胁。这次通过的制裁决议最终目的是恢复朝鲜半岛无核化。欧盟代\n表也表示会积极参与对朝鲜的制裁行动，并加大单边制裁朝鲜的力度。有学者认为，法国作为美国的盟国，会积极配合美国的制裁态度。&nbsp;</p><p>法国媒体也特别关注了中国在这次通过决议中所发挥的作用，但分析认为，介于中国和朝鲜的密切往来，法国虽然会积极支持制裁朝鲜决议，但另一方面也会对此持谨慎态度。&nbsp;</p><p><strong>解读新闻热点、呈现敏感事件、更多独家分析，尽在凤凰网微信（ID：ifeng-news），欢迎关注。</strong><span class=\"ifengLogo\"><a href=\"http://www.ifeng.com/\" target=\"_blank\"><img src=\"http://y2.ifengimg.com/a/2015/0708/icon_logo.gif\"/></a></span></p><p><br/></p>','新华社',NULL,NULL,'2016-03-03 16:58:37',NULL,'1'),(7,'李克强与全国政协经济界、农业界委员座谈','admin',6,'<p>京华时报：【<a href=\"http://renwuku.news.ifeng.com/index/detail/2/likeqiang\" target=\"_blank\">李克强</a>总理到昆泰酒店与政协委员座谈】李克强总理下午3点来到望京昆泰酒店，与全国政协经济界、农业界委员联组会现场委员座谈。在李克强总理步入二楼走廊时，数十名记者向总理大声问好，李克强总理听到后，停下脚步，向记者们挥手致意，随后步入会议室。京华时报记者陈艳<span class=\"ifengLogo\"><a href=\"http://www.ifeng.com/\" target=\"_blank\"><img src=\"http://y2.ifengimg.com/a/2015/0708/icon_logo.gif\"/></a></span></p><p class=\"iphone_none\">[责任编辑：宇文杰 PN051]</p><p><img src=\"http://h2.ifengimg.com/0f56ee67a4c375c2/2013/1106/indeccode.png\" style=\"width:86px; height:86px; float:left; margin-right:14px;\" class=\"js_wx_qrcod\"/>\n &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p style=\" line-height:24px; color:#666; font-size:12px; width:auto; padding-top:12px;\">用微信扫描二维码<br/>分享至好友和朋友圈</p><p class=\"p01 ss_none\"><br/></p><p><img style=\"width: 724px; height: 434px;\" alt=\"bridge.jpg\" src=\"http://localhost:8080/DITemplate/upload/news/content/20160304/18838f83-ddea-42de-9770-d17a555518fa.jpg\" title=\"\" height=\"434\" width=\"724\"/></p>','trte','upload/news/content/20160304/4e46713b-e2fd-4e0f-b43b-a0c736288451.jpg','fsd','2016-03-04 15:56:51','2016-03-04 15:57:15','1'),(8,'习近平参加黑龙江团审议 了解煤企现状(图)','admin',2,'<p style=\"text-align:center\"><img alt=\"1f001318a250f66_w800_h645.jpg\" src=\"http://localhost:8080/DITemplate/upload/news/content/20160307/95a8348d-1d1c-437b-9aa7-db713649dae6.jpg\" title=\"\"/></p><p>【习总书记关心黑龙江煤炭产业发展】黑龙江代表团审议时，省委书记<a href=\"http://renwuku.news.ifeng.com/index/detail/55/wangxiankui\" target=\"_blank\">王宪魁</a>谈到坚决打好龙煤集团改革生存攻坚战。<strong>习近平详细了解龙煤集团生产经营，询问煤炭产量、采煤条件、设备机械化等情况。习近平仔细听取来自煤炭企业代表的发言，记下关于黑龙江煤炭资源枯竭型城市经济解困和转型发展的建议，希望企业面向市场、面向技术创新，增强企业发展活力和原生力。</strong><br/></p><p>【习近平叮嘱黑龙江要保护好湿地】听到<a href=\"http://renwuku.news.ifeng.com/index/detail/48/luhao\" target=\"_blank\">陆昊</a>代表谈到黑龙江扎龙、三江、珍宝岛湿地等是<a href=\"http://travel.ifeng.com/\" target=\"_blank\">旅游</a>热点,总书记说，一定要保护好湿地。习近平十分关心黑龙江转换发展动能的情况。当代表们谈到大学生创业、奶制品业发展等情况，总书记不时插话询问具体情况。</p><p>【黑\n龙江团代表向总书记表达农民心声，习近平连问农业生产】来自黑龙江省桦南县梨树乡和平村的孙斌代表发言时，代表农民感谢总书记对“三农”的关心支持。习近\n平问，桦南县是不是以种植业为主，玉米分级收购优质玉米能占多大比重，产量上有没有区别，机械化程度如何。孙斌一一作答，并说农民深知国家粮食安全至关重\n要，一定加倍努力，种好地、多打粮，希望总书记百忙之中到黑龙江来考察。习近平说，黑龙江农业很重要，将来去了肯定要看农业。</p><p>【习近平托\n代表转达对赫哲族群众的祝愿】在黑龙江代表团审议时，赫哲族80后代表刘蕾说，在兴边富民工程等政策帮扶下，赫哲族群众生活就像乌苏里船歌中唱的一样走上\n了幸福路。总书记说，乌苏里船歌我们早就耳熟能详。他关心地问，现在还有多少人靠打鱼为生、江里的鱼还多不多。总书记强调，在发展道路上要发挥好制度优\n势，人数较少民族也都要奔小康，一个也不能少。</p><p>【习近平：国有企业发展既要“借东风”，也要激发内生动力】习近平7日上午参加黑龙江省代\n表团审议时，听到王波代表说到老工业基地结构性改革，他强调说，现在是市场经济，哪里有优势，哪里要素齐备，哪里就具有集聚的优势。对国有企业发展，政府\n的作用更多体现在支持、扶持、杠杆作用，但没有现存的“金娃娃”摆在那里。在这种情况下，国有企业要深化改革，要“借东风”，激发内生动力，在竞争中增强\n实力。要抓好东北老工业基地振兴各项政策的落实。</p><p>【习近平：着力打造全面振兴好环境】在7日上午参加黑龙江省代表团审议时，习近平听到陆\n昊代表介绍黑龙江省简政放权的情况，他接连询问：“下放还是取消？”“下放的能接得住吗？”习近平强调说，要深入推进依法治国，着力打造全面振兴好环境。\n法治是一种基本思维方式和工作方式，法治化环境最能聚人聚财、最有利于发展。要提高领导干部运用法治思维和法治方式开展工作、解决问题、推动发展的能力，\n引导广大群众自觉守法、遇事找法、解决问题靠法，深化基层依法治理，让依法办事蔚然成风。</p><p>【习近平：全面振兴决心不能动摇，工作不能松\n劲】在7日上午参加黑龙江省代表团审议时，习近平谈到东北老工业基地振兴。他强调说，要瞄准方向、保持定力、一以贯之、久久为功，急躁是不行的，浮躁更不\n行。要向高新技术成果产业化要发展，向选好用好各方面人才要发展，临渊羡鱼不如退而结网。发展不能守株待兔、固步自封，要在市场环境下、竞争中求发展。要\n扬长避短、扬长克短、扬长补短，向经济建设这个中心聚焦发力，打好发展组合拳，奋力走出全面振兴新路子。</p><p>【习近平：冰天雪地也是金山银\n山】在7日上午参加黑龙江省代表团审议时，上甘岭林业局工人高永谈到了大小兴安岭停伐转型情况，习近平问道：“当地林业工人转型，由木材采伐就地转成生态\n保护的工人，难度大吗？比例有多高？”有代表回答说，大约占到50%的比例，会继续加大转型力度。习近平表示，要加强生态文明建设，划定生态保护红线，为\n可持续发展留足空间，为子孙后代留下天蓝地绿水清的家园。绿水青山是金山银山，黑龙江的冰天雪地也是金山银山。</p><p><br/></p>','凤凰网','upload/news/content/20160307/acfb5aa7-eff1-422a-932d-4c5d8731360b.jpg','3月7日上午，中共中央总书记、国家主席、中央军委主席习近平来到十二届全国人大四次会议黑龙江代表团参加审议。','2016-03-07 14:35:06',NULL,'1'),(9,' 李克强寄望广东：为国家做更多贡献','admin',4,'<p style=\"text-align: center;\">原标题：敢为人先 勇挑重担 为国家做更多贡献</p><p>&nbsp;&nbsp;&nbsp;&nbsp;<img style=\"width: 667px; height: 466px;\" alt=\"1f001318a250f66_w800_h645.jpg\" src=\"http://localhost:8080/DITemplate/upload/news/content/20160310/64c3c85c-8e33-4fed-82e9-19d1c5d84a37.jpg\" title=\"\" height=\"466\" width=\"667\"/></p><p><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;李克强参加广东代表团审议政府工作报告寄望广东——敢为人先勇挑重担为国家做更多贡献</strong></p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;广州日报讯9日上午，中共中央政治局常委、国务院总理李克强来到十二届全国人大四次会议广东代表团参加审议。</p><p>中共中央政治局委员、省委书记<a href=\"http://renwuku.news.ifeng.com/index/detail/25/huchunhua\" target=\"_blank\">胡春华</a>主持代表团全体会议。<a href=\"http://renwuku.news.ifeng.com/index/detail/485/zhuxiaodan\" target=\"_blank\">朱小丹</a>、许勤、<a href=\"http://book.ifeng.com/lianzai/detail_2010_04/12/520207_4.shtml\" target=\"_blank\">钟南山</a>、曾香桂、陈建华、范冬萍等代表围绕改革创新、环境治理、农民工融入城市、就业创业等问题先后发言。李克强与大家深入交流。</p><p>李\n克强说，广东是国家发展的重要支撑，是改革开放的“排头兵”。面对今年复杂形势，希望广东敢为人先、勇挑重担。一要走在促进“双中高”前列。持续推进结构\n性改革尤其是供给侧结构性改革，进一步简政放权、放管结合、优化服务，打破束缚社会创造力的条条框框，积极参与国际竞争与合作，依靠改革开放保持经济平稳\n较快发展。二要大力发展新经济。打造大众创业、万众创新平台，促进覆盖一、二、三产业的新技术、新业态、新模式成长，围绕“补短板”和传统产业升级扩大有\n效投资，更多依靠人力人才资源促进新旧动能加快转换。三要进一步把改革发展的出发点和落脚点放在民生上。促进就业特别是大学生就业，为农民工提供基本公共\n服务，科学有效应对雾霾等环境污染，提高人民健康水平。</p><p>胡春华在主持会议时表示，李克强总理在讲话中充分肯定了我省经济社会发展取得的成绩，对今后的改革发展提出了明确要求、寄予了殷切期望，使我们深受鼓舞和鞭策。我们要深入学习贯彻<a href=\"http://renwuku.news.ifeng.com/index/detail/5/xijinping\" target=\"_blank\">习近平</a>总\n书记系列重要讲话精神，进一步增强政治意识、大局意识、核心意识、看齐意识，落实好全国“两会”精神和李克强总理的指示要求，奋发有为、扎实工作，保持经\n济运行在合理区间，加快新旧动能转换，形成创新型经济格局，加大民生保障力度，让人民群众更好地共享发展成果，不断开创广东工作新局面。</p><p>国务委员、国务院秘书长杨晶，国务院常务副秘书长肖捷，国家发展改革委主任<a href=\"http://renwuku.news.ifeng.com/index/detail/74/xushaoshi\" target=\"_blank\">徐绍史</a>，工业和信息化部部长<a href=\"http://renwuku.news.ifeng.com/index/detail/91/miaowei\" target=\"_blank\">苗圩</a>，住房城乡建设部部长<a href=\"http://renwuku.news.ifeng.com/index/detail/281/chenzhenggao\" target=\"_blank\">陈政高</a>，商务部部长<a href=\"http://renwuku.news.ifeng.com/index/detail/143/gaohucheng\" target=\"_blank\">高虎城</a>，国家统计局局长宁吉喆等参加审议。 <br/></p><p><br/></p>','凤凰网','upload/news/content/20160310/421342c1-e138-4511-aed3-46287383b218.jpg',NULL,'2016-03-10 14:34:54','2016-03-10 14:35:24','1');

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='新闻类型';

/*Data for the table `t_news_type` */

insert  into `t_news_type`(`id`,`name`,`status`,`createTime`,`modifyTime`,`creator`,`modifier`,`sort`,`memo`) values (2,'社会','1','2016-03-03 15:07:49',NULL,0,NULL,1,NULL),(3,'财经','1','2016-03-03 15:08:00',NULL,0,NULL,2,NULL),(4,'军事','1','2016-03-03 15:08:13',NULL,0,NULL,3,NULL),(5,'科技','1','2016-03-03 15:08:26',NULL,0,NULL,4,NULL),(6,'国际','1','2016-03-03 17:17:05',NULL,0,NULL,6,NULL),(7,'汽车','1','2016-03-03 17:17:23',NULL,0,NULL,7,NULL),(8,'娱乐','1','2016-03-03 17:17:46',NULL,0,NULL,5,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='用户权限信息表';

/*Data for the table `t_priviledges` */

insert  into `t_priviledges`(`id`,`name`,`url`,`status`,`createTime`,`modifyTime`,`pid`,`icon`,`creator`,`memo`,`isParent`) values (2,'权限管理','../system/priviledges.html','1','2015-07-21 10:18:01','2015-09-09 14:24:53',5,'fa-key',NULL,NULL,0),(3,'用户管理','../system/user.html','1','2015-09-09 14:52:49','2015-09-09 14:54:39',5,'fa-users',NULL,NULL,0),(4,'角色管理','../system/role.html','1','2016-02-26 11:40:00',NULL,5,'fa-user',NULL,NULL,0),(5,'系统维护','#','1','2016-02-26 11:46:30',NULL,0,'fa-desktop',NULL,NULL,1),(9,'新闻维护','#','1','2016-03-01 13:39:36',NULL,0,'fa-newspaper-o',NULL,NULL,NULL),(10,'新闻类型维护','../news/news_type.html','1','2016-03-01 13:40:51',NULL,9,'fa-sliders',NULL,NULL,NULL),(11,'新闻管理','../news/news.html','1','2016-03-01 13:41:22',NULL,9,'fa-sliders',NULL,NULL,NULL);

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
  CONSTRAINT `t_priviledges_matrix_ibfk_1` FOREIGN KEY (`priviledgesID`) REFERENCES `t_priviledges` (`id`),
  CONSTRAINT `t_priviledges_matrix_ibfk_2` FOREIGN KEY (`roleID`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='权限矩阵信息表';

/*Data for the table `t_priviledges_matrix` */

insert  into `t_priviledges_matrix`(`id`,`roleID`,`priviledgesID`,`iscreate`,`isdelete`,`ismodify`,`isselect`,`isprint`,`isimport`,`isexport`,`memo`) values (2,1,2,1,1,1,1,1,1,1,NULL),(3,1,3,1,1,1,1,1,1,1,NULL),(4,1,4,1,1,1,1,1,1,1,NULL),(6,1,10,1,1,1,1,1,1,1,'auto'),(7,1,11,1,1,1,1,1,1,1,'auto');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户角色信息表';

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`name`,`createTime`,`modifyTime`,`status`,`memo`) values (1,'admin','2015-06-29 14:19:00','2016-02-29 08:51:45','1','admin'),(2,'user','2016-02-26 16:16:35','2016-02-29 09:30:02','1',NULL),(4,'test','2016-02-29 17:22:47',NULL,'1',NULL);

/*Table structure for table `t_role_priviledges` */

DROP TABLE IF EXISTS `t_role_priviledges`;

CREATE TABLE `t_role_priviledges` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `priviledgesID` int(11) NOT NULL COMMENT '权限ID',
  `roleID` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `Relationship_15_FK` (`roleID`) USING BTREE,
  KEY `FK_Reference_74` (`priviledgesID`) USING BTREE,
  CONSTRAINT `t_role_priviledges_ibfk_1` FOREIGN KEY (`priviledgesID`) REFERENCES `t_priviledges` (`id`),
  CONSTRAINT `t_role_priviledges_ibfk_2` FOREIGN KEY (`roleID`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='角色权限对应关系表';

/*Data for the table `t_role_priviledges` */

insert  into `t_role_priviledges`(`id`,`priviledgesID`,`roleID`) values (2,2,1),(3,3,1),(4,4,1),(5,5,1),(24,5,2),(26,3,2),(28,5,2),(29,5,4),(34,9,1),(35,10,1),(36,11,1);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userName` char(20) NOT NULL COMMENT '用户名称',
  `sex` char(4) NOT NULL COMMENT '性别',
  `userAccount` char(32) NOT NULL COMMENT '账号',
  `password` char(32) NOT NULL COMMENT '登陆密码',
  `status` char(2) DEFAULT NULL COMMENT '状态(0：有效 1：无效)',
  `creator` int(11) DEFAULT NULL COMMENT '创建者(对应userId)',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `modifier` int(1) DEFAULT NULL COMMENT '修改人',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `headImage` int(11) DEFAULT NULL COMMENT '用户头像',
  `memo` char(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userAccount` (`userAccount`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userName`,`sex`,`userAccount`,`password`,`status`,`creator`,`createTime`,`modifier`,`modifyTime`,`headImage`,`memo`) values (1,'admin','男','9999','e10adc3949ba59abbe56e057f20f883e','1',-1,'2015-12-12 16:25:33',0,NULL,NULL,NULL),(3,'test','男','8888','e10adc3949ba59abbe56e057f20f883e','1',1,'2016-02-26 16:18:05',1,'2016-03-01 09:43:03',NULL,NULL),(4,'saly','女','2222','e10adc3949ba59abbe56e057f20f883e','1',1,'2016-02-29 08:35:49',1,'2016-03-01 14:34:55',NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`id`,`userID`,`roleID`) values (1,1,1),(5,3,2),(10,4,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
