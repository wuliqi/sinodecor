CREATE TABLE `shop_good` (
  `good_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品标识',
  `good_title` varchar(256) DEFAULT NULL COMMENT '产品标题',
  `good_content` varchar(8192) DEFAULT NULL COMMENT '产品内容',
  `good_keyword` varchar(512) DEFAULT NULL COMMENT '产品关键词',
  `good_brief` varchar(512) DEFAULT NULL COMMENT '产品摘要',
  `begin_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `clicks` int(11) DEFAULT NULL COMMENT '点击次数',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `good_category` varchar(8) DEFAULT NULL COMMENT '产品分类',
  `is_stick` varchar(8) DEFAULT NULL COMMENT '是否置顶\n1:置顶\n0:不置顶',
  `approval_opinion` varchar(512) DEFAULT NULL COMMENT '审核意见',
  `approval_status` varchar(8) DEFAULT NULL COMMENT '产品状态\n0:未提交\n2：待审核\n4：未通过\n6：已通过',
  `approval_user_id` int(11) DEFAULT NULL COMMENT '审核人标识',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  PRIMARY KEY (`good_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品内容表';

CREATE TABLE `sys_advertisement` (
  `ad_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '广告标识',
  `ad_title` varchar(256) DEFAULT NULL COMMENT '广告标题',
  `ad_content` varchar(8192) DEFAULT NULL COMMENT '广告内容',
  `ad_keyword` varchar(512) DEFAULT NULL COMMENT '广告关键词',
  `ad_brief` varchar(512) DEFAULT NULL COMMENT '广告摘要',
  `is_valid` varchar(8) DEFAULT NULL COMMENT '是否有效',
  `ad_icon` varchar(512) DEFAULT NULL COMMENT '广告图标',
  `begin_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `clicks` int(11) DEFAULT NULL COMMENT '点击次数',
  `advertiser_id` int(11) DEFAULT NULL COMMENT '广告商标识',
  `is_stick` varchar(8) DEFAULT NULL COMMENT '是否置顶',
  `ad_type` varchar(8) DEFAULT NULL COMMENT '广告类型\n1:活动\n3：类型',
  PRIMARY KEY (`ad_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT=' 广告表:广告信息表，由后台加入广告信息，返回给用户时排除在用户广告关联关系表中的广告\n\n\n\n\n\n\n\n\n\n';

CREATE TABLE `sys_air_device` (
  `device_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备标识\n\n\n\n\n\n',
  `device_mac` varchar(32) DEFAULT NULL COMMENT '设备MAC',
  `device_name` varchar(64) DEFAULT NULL COMMENT '设备名称/车牌号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `device_model` varchar(64) DEFAULT NULL COMMENT '设备型号',
  `device_password` varchar(32) DEFAULT NULL COMMENT '设备出厂密码',
  `device_sn` varchar(32) DEFAULT NULL COMMENT '蓝牙设备序列号',
  `car_type` varchar(16) DEFAULT NULL COMMENT '汽车型号',
  `device_status` varchar(8) DEFAULT '1' COMMENT '设备状态：1：入库  3： 出库  5:已安装   7:已废弃',
  `warehouse_in_time` timestamp NULL DEFAULT NULL COMMENT '入库时间',
  `warehouse_out_time` timestamp NULL DEFAULT NULL COMMENT '出库时间',
  `install_time` timestamp NULL DEFAULT NULL COMMENT '安装时间，即绑定时间',
  `discard_time` timestamp NULL DEFAULT NULL COMMENT '废弃时间',
  `car_category` varchar(8) DEFAULT NULL COMMENT '汽车种类:\n2:出租车\n4：私家车',
  `device_mac2` varchar(32) DEFAULT NULL COMMENT '设备mac2(蓝牙2.0的mac地址)',
  `user_id` int(11) DEFAULT NULL COMMENT '销售人标识',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `sale_time` timestamp NULL DEFAULT NULL COMMENT '销售时间',
  `car_brand` varchar(16) DEFAULT NULL COMMENT '汽车品牌',
  `car_series` varchar(16) DEFAULT NULL COMMENT '汽车系列',
  `car_year` varchar(8) DEFAULT NULL COMMENT '汽车年份',
  `install_org_id` int(11) DEFAULT NULL COMMENT '所属安装门店',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `car_style` varchar(64) DEFAULT NULL COMMENT '汽车款式',
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=409 DEFAULT CHARSET=utf8 COMMENT='通风控制器设备基本信息表：MAC,设备名称出厂时录入后台，一一对应关系，可以修改设备名称为车牌号，但修改时需做唯一性验证';

CREATE TABLE `sys_car_schema` (
  `model_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '模式信息标识',
  `device_id` int(11) DEFAULT NULL COMMENT '设备标识',
  `people_number` varchar(8) DEFAULT NULL COMMENT '人数:\n1:1-2人\n2：3-5人',
  `device_model` varchar(8) DEFAULT NULL COMMENT '设备模式:\n  L1：节油 外循环-5秒   \n  L2：标准 内外循环时间不变     \n  L3：舒适 外循环+10秒   \n  L4：富氧 外循环+20秒',
  `setting_time` timestamp NULL DEFAULT NULL COMMENT '设定时间',
  `is_default` varchar(8) DEFAULT NULL COMMENT '默认模式:\n1:默认',
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='模式信息表:记录模式设置信息，可以获取最后一次配置的默认模式（可以APP直接记住）\n\n\n\n\n\n\n\n\n';

CREATE TABLE `sys_card` (
  `card_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '充值卡标识',
  `card_number` varchar(64) DEFAULT NULL COMMENT '充值卡号',
  `card_password` varchar(32) DEFAULT NULL COMMENT '密码',
  `card_type` varchar(8) DEFAULT NULL COMMENT '充值卡类型\n1：次卡\n3：月卡\n5：季卡\n7：半年卡\n9：年卡',
  `amount` float DEFAULT NULL COMMENT '金额',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `card_icon` varchar(256) DEFAULT NULL COMMENT '充值卡图标',
  `expiry_date` timestamp NULL DEFAULT NULL COMMENT '有效期',
  `card_status` varchar(8) DEFAULT NULL COMMENT '充值卡状态:1有效，0无效',
  `purchase_time` timestamp NULL DEFAULT NULL COMMENT '购买时间',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `device_id` int(11) DEFAULT NULL COMMENT '充值设备标识',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  PRIMARY KEY (`card_id`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8 COMMENT='充值卡表:充值卡号、密码提前录入后台数据库，状态为有效，充值后由更新状态（为无效），购买时间。购买时，根据充值卡类型查看';

CREATE TABLE `sys_card_type` (
  `card_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '卡类别标识',
  `card_type` varchar(8) DEFAULT NULL COMMENT '卡类别\n1：次卡\n3：月卡\n5：季卡\n7：半年卡\n9：年卡',
  `card_name` varchar(32) DEFAULT NULL COMMENT '卡类别名称',
  `amount` float DEFAULT NULL COMMENT '金额',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `card_title` varchar(128) DEFAULT NULL COMMENT '卡类别标题',
  PRIMARY KEY (`card_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='卡类别表:记录卡片类别信息表\n\n\n\n\n\n\n\n\n\n';

CREATE TABLE `sys_code` (
  `code_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代码类别标识',
  `code_name` varchar(64) DEFAULT NULL COMMENT '代码类别名称',
  `p_code` int(11) DEFAULT NULL COMMENT '代码类别父标识',
  `status` varchar(8) DEFAULT NULL COMMENT '状态  \n0：废弃\n\n1：启用',
  `type` varchar(8) DEFAULT NULL COMMENT '所属种类2, 过滤器品牌4, 过滤器型号6, 汽车品牌8, 汽车型号',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `descr` varchar(512) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  `code_value` varchar(64) DEFAULT NULL COMMENT '代码编码',
  PRIMARY KEY (`code_id`),
  UNIQUE KEY `code_id_UNIQUE` (`code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1411 DEFAULT CHARSET=utf8 COMMENT='代码类别表';

CREATE TABLE `sys_credit_card` (
  `user_id` int(11) NOT NULL COMMENT '用户标识',
  `total_credit` int(11) DEFAULT NULL COMMENT '积分总额',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分卡表:记录用户积分信息，用户标识做为主键，领取积分，总数增加，兑换积分，总数减少。积分只和账号关键\n\n';

CREATE TABLE `sys_credit_card_exchange_his` (
  `exchange_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '兑换标识',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `device_id` int(11) DEFAULT NULL COMMENT '设备标识',
  `exchange_time` timestamp NULL DEFAULT NULL COMMENT '兑换时间',
  `exchange_credit` int(11) DEFAULT NULL COMMENT '兑换积分数',
  `card_type` varchar(8) DEFAULT NULL COMMENT '兑换充值类型\n1：次卡\n3：月卡\n5：季卡\n7：半年卡\n9：年卡',
  `expiry_date` timestamp NULL DEFAULT NULL COMMENT '有效期',
  `card_id` int(11) DEFAULT NULL COMMENT '兑换充值卡标识',
  PRIMARY KEY (`exchange_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分兑换历史表:每次积分兑换时，往积分兑换历史表中插入一条记录，列表以兑换时间倒序排列\n\n\n';

CREATE TABLE `sys_device_ctrl` (
  `ctrl_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备远程控制标识',
  `device_name` varchar(64) DEFAULT NULL COMMENT '车牌号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `weichat_name` varchar(32) DEFAULT NULL COMMENT '微信昵称',
  `device_ctrl` varchar(45) DEFAULT NULL COMMENT '设备标识',
  `longitude` varchar(32) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(32) DEFAULT NULL COMMENT '纬度',
  `location_time` timestamp NULL DEFAULT NULL COMMENT '定位时间',
  `remark1` varchar(32) DEFAULT NULL COMMENT '出租车clientId',
  `remark2` varchar(32) DEFAULT NULL COMMENT '乘客端clientId',
  `status` varchar(8) DEFAULT NULL COMMENT '控制状态：\n1： 控制\n0：不控制',
  PRIMARY KEY (`ctrl_id`),
  UNIQUE KEY `ctrl_id_UNIQUE` (`ctrl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8 COMMENT='设备远程控制表';

CREATE TABLE `sys_device_recharge` (
  `recharge_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备充值标识',
  `device_id` int(11) DEFAULT NULL COMMENT '设备标识',
  `card_id` int(11) DEFAULT NULL COMMENT '充值卡标识',
  `card_type` varchar(8) DEFAULT NULL COMMENT '充值类型：\n0：免费\n1：次卡\n3：月卡\n5：季卡\n7：半年卡\n9：年卡',
  `begin_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `total_count` int(11) DEFAULT NULL COMMENT '总次数   ,仅次卡使用',
  `used_count` int(11) DEFAULT NULL COMMENT '已使用次数 ,仅次卡使用',
  `status` varchar(8) DEFAULT NULL COMMENT '状态',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '次卡使用一次开始时间',
  `finish_time` timestamp NULL DEFAULT NULL COMMENT '次卡使用一次生效结束时间',
  `paid_amount` float DEFAULT NULL COMMENT '实付金额',
  `charge_type` varchar(8) DEFAULT NULL COMMENT '充值方式：1：微信  3：支付宝  5：网银   7：卡充  9：后台',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织构',
  PRIMARY KEY (`recharge_id`)
) ENGINE=InnoDB AUTO_INCREMENT=385 DEFAULT CHARSET=utf8 COMMENT='设备充值表:记录设备充值的信息，每次充值时STATUS为有效，对于非次卡，当前系统时间大于结束时间时为无效，次卡，当部次';

CREATE TABLE `sys_device_recharge_his` (
  `recharge_his_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备充值历史标识',
  `order_num` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `device_id` int(11) DEFAULT NULL COMMENT '设备标识',
  `device_name` varchar(64) DEFAULT NULL,
  `card_id` int(11) DEFAULT NULL COMMENT '充值卡标识',
  `card_number` varchar(64) DEFAULT NULL COMMENT '充值卡号',
  `card_type` varchar(8) DEFAULT NULL COMMENT '充值类型\n1：次卡\n3：月卡\n5：季卡\n7：半年卡\n9：年卡',
  `charge_type` varchar(8) DEFAULT NULL COMMENT '充值方式：1：微信  3：支付宝  5：网银   7：卡充  9：后台',
  `paid_amount` float DEFAULT NULL COMMENT '金额',
  `begin_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `real_name` varchar(32) DEFAULT NULL COMMENT '操作人',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织构',
  PRIMARY KEY (`recharge_his_id`)
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_device_user_rela` (
  `dur_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联关系标识',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `device_id` int(11) DEFAULT NULL COMMENT '设备标识',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '绑定时间',
  PRIMARY KEY (`dur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=517 DEFAULT CHARSET=utf8 COMMENT='设备用户绑定表:用户与设备绑定关系，用户与设备为多对多的关系\n\n\n\n\n\n\n';

CREATE TABLE `sys_discount` (
  `discount_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '折扣标识',
  `card_discount` float DEFAULT NULL COMMENT '充值卡折扣',
  `air_device_discount` float DEFAULT NULL COMMENT '控制器折扣',
  `hepa_discount` float DEFAULT NULL COMMENT '过滤器折扣',
  `install_discount` float DEFAULT NULL COMMENT '安装费折扣',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '设置时间',
  `user_id` int(11) DEFAULT NULL COMMENT '操作人标识',
  PRIMARY KEY (`discount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_hepa` (
  `hepa_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '过滤器标识',
  `brand_code_id` int(11) DEFAULT NULL COMMENT '品牌标识',
  `model_code_id` int(11) DEFAULT NULL COMMENT '过滤器型号',
  `hepa_status` varchar(8) DEFAULT NULL COMMENT '过滤器状态\n2:过滤器入库\n4:过滤器出库\n6:新装过滤器\n8:升级过滤器',
  `hepa_number` int(11) DEFAULT NULL COMMENT '过滤器个数',
  `hepa_price` float DEFAULT NULL COMMENT '采购价格',
  `expiry_date` int(11) DEFAULT NULL COMMENT '有效期  X天',
  `should_price` float DEFAULT NULL COMMENT '应收价格',
  `real_price` float DEFAULT NULL COMMENT '实收价格',
  `install_time` timestamp NULL DEFAULT NULL COMMENT '安装时间',
  `device_name` varchar(32) DEFAULT NULL COMMENT '车牌号',
  `descr` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1:用作过滤器预定时间',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  `purchase_price` float DEFAULT NULL COMMENT '采购价格',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `sale_time` timestamp NULL DEFAULT NULL COMMENT '销售时间',
  `charge_type` varchar(45) DEFAULT NULL COMMENT '支付方式：\n1：微信  \n3：支付宝\n2:支付宝网页 \n4:汇款支付 \n5：网银   \n7：卡充  \n9：后台',
  `series_code_id` int(11) DEFAULT NULL COMMENT '过滤器汽车系列',
  `car_year` int(11) DEFAULT NULL COMMENT '汽车年份',
  PRIMARY KEY (`hepa_id`),
  UNIQUE KEY `hepa_id_UNIQUE` (`hepa_id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_hepa_his` (
  `hepa_his_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '过滤器标识',
  `hepa_id` int(11) DEFAULT NULL COMMENT '过滤器标识',
  `brand_code_id` int(11) DEFAULT NULL COMMENT '品牌标识',
  `model_code_id` int(11) DEFAULT NULL COMMENT '过滤器型号',
  `hepa_status` varchar(8) DEFAULT NULL COMMENT '\n过滤器状态\n2:过滤器入库\n\n4:过滤器出库\n\n6:新装过滤器\n\n8:升级过滤器',
  `hepa_number` int(11) DEFAULT NULL COMMENT '过滤器个数',
  `hepa_price` float DEFAULT NULL COMMENT '采购价格',
  `expiry_date` int(11) DEFAULT NULL COMMENT '有效期  X个月',
  `should_price` float DEFAULT NULL COMMENT '应收价格',
  `real_price` float DEFAULT NULL COMMENT '实收价格',
  `install_time` timestamp NULL DEFAULT NULL COMMENT '安装时间',
  `device_name` varchar(32) DEFAULT NULL COMMENT '车牌号',
  `descr` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  `purchase_price` float DEFAULT NULL COMMENT '采购价格',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `sale_time` timestamp NULL DEFAULT NULL COMMENT '销售时间',
  `charge_type` varchar(45) DEFAULT NULL COMMENT '支付方式：\n1：微信  \n3：支付宝\n2:支付宝网页 \n4:汇款支付 \n5：网银   \n7：卡充  \n9：后台',
  `series_code_id` int(11) DEFAULT NULL COMMENT '过滤器汽车系列',
  `car_year` int(11) DEFAULT NULL COMMENT '汽车年份',
  PRIMARY KEY (`hepa_his_id`),
  UNIQUE KEY `hepa_his_id_UNIQUE` (`hepa_his_id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_laser_particle_counter` (
  `lpc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '远程激光粒子计数器数标识',
  `aqi_epa` varchar(32) DEFAULT NULL COMMENT '环保局aqi',
  `aqi_epa_public_time` timestamp NULL DEFAULT NULL COMMENT '环保局aqi发布时间',
  `aqi_use` varchar(32) DEFAULT NULL COMMENT '美使馆aqi	',
  `aqi_use_public_time` timestamp NULL DEFAULT NULL COMMENT '美使馆aqi发布时间',
  `pm03out` int(11) DEFAULT NULL COMMENT '车外pm0.3',
  `pm05out` int(11) DEFAULT NULL COMMENT '车外pm0.5',
  `pm10out` int(11) DEFAULT NULL COMMENT '车外pm1.0',
  `pm25out` int(11) DEFAULT NULL COMMENT '车外pm2.5',
  `pm03in` int(11) DEFAULT NULL COMMENT '车内pm0.3',
  `pm05in` int(11) DEFAULT NULL COMMENT '车内pm0.5',
  `pm10in` int(11) DEFAULT NULL COMMENT '车内pm1.0',
  `pm25in` int(11) DEFAULT NULL COMMENT '车内pm2.5',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `user_name` varchar(32) DEFAULT NULL COMMENT '采集人姓名',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '采集时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  PRIMARY KEY (`lpc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3742 DEFAULT CHARSET=utf8 COMMENT='AirBest远程激光粒子计数器数据表									\n					\n';

CREATE TABLE `sys_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志标识',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `login_name` varchar(32) DEFAULT NULL COMMENT '登录名',
  `log_type` varchar(8) DEFAULT NULL COMMENT '日志类型\n1：后台\n3：app',
  `ip_address` varchar(32) DEFAULT NULL COMMENT 'IP地址',
  `op_content` varchar(1024) DEFAULT NULL COMMENT '操作内容',
  `op_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `terminal_type` varchar(32) DEFAULT NULL COMMENT '终端型号',
  `localization` varchar(256) DEFAULT NULL COMMENT '地址定位（经纬度）',
  `remark1` varchar(32) DEFAULT NULL COMMENT '扩展字段1',
  `remark2` varchar(64) DEFAULT NULL COMMENT '扩展字段2',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47087 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL COMMENT '本实体记录的唯一标识。',
  `menu_code` varchar(16) NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(256) DEFAULT NULL COMMENT '菜单名称',
  `menu_title` varchar(32) DEFAULT NULL COMMENT '菜单标题',
  `menu_pid` int(11) DEFAULT NULL COMMENT '直接上级菜单的菜单标识。',
  `menu_folder_flag` varchar(8) DEFAULT NULL COMMENT '是否菜单夹，是：菜单夹；否：菜单项。',
  `menu_sort` varchar(8) DEFAULT NULL COMMENT '菜单对应的资源的类别。01 组件 02 HTML 03 报表',
  `menu_represent` varchar(256) DEFAULT NULL COMMENT '记录菜单链接资源的名称。',
  `sort_no` int(4) DEFAULT NULL COMMENT '在同级中的排列顺序的序号，用自然数标识，如，1、2、3。',
  `menu_level` int(4) DEFAULT NULL COMMENT '菜单级别',
  `menu_path` varchar(256) DEFAULT NULL COMMENT '菜单路径',
  `isactive` varchar(8) DEFAULT NULL COMMENT '是否可用标识 0：无效  1：有效',
  `user_id` varchar(32) DEFAULT NULL COMMENT '菜单添加人员',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '菜单添加时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '菜单的备注',
  `menu_icon` varchar(16) DEFAULT NULL COMMENT '菜单图标',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 保存菜单定义的一些基本信息。本实体主要包括菜单标识、菜单名称、菜单标题、上级菜单标识、是否菜单夹等属性。';

CREATE TABLE `sys_message` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息标识',
  `msg_title` varchar(256) DEFAULT NULL COMMENT '消息标题',
  `msg_content` varchar(2048) DEFAULT NULL COMMENT '消息内容',
  `msg_type` varchar(8) DEFAULT NULL COMMENT '消息类型\n2：系统消息\n4：活动消息\n6：推送消息\n\n9:短信',
  `msg_create_time` timestamp NULL DEFAULT NULL COMMENT '消息创建时间',
  `msg_expiry_time` timestamp NULL DEFAULT NULL COMMENT '失效时间',
  `is_stick` varchar(8) DEFAULT NULL COMMENT '短信类型\n1:表示行业信息  3:表示营销信息\n',
  `user_id` int(11) DEFAULT NULL COMMENT '发布人标识',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7173 DEFAULT CHARSET=utf8 COMMENT='消息表:记录系统消息及其他类型的消息表\n\n\n\n\n\n';

CREATE TABLE `sys_message_send` (
  `msg_send_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息发送标识',
  `msg_id` int(11) DEFAULT NULL COMMENT '消息标识',
  `receiver_id` int(11) DEFAULT NULL COMMENT '消息接收者标识',
  `sender_id` int(11) DEFAULT NULL COMMENT '消息发送者标识',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '消息创建时间',
  `status` varchar(8) DEFAULT NULL COMMENT '消息状态\n0：未查看\n1：已查看\n4：删除		',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  PRIMARY KEY (`msg_send_id`)
) ENGINE=InnoDB AUTO_INCREMENT=306 DEFAULT CHARSET=utf8 COMMENT='消息发送表';

CREATE TABLE `sys_order` (
  `order_id` varchar(32) NOT NULL COMMENT '订单号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '订单时间',
  `device_name` varchar(64) DEFAULT NULL COMMENT '车牌号',
  `card_type` varchar(8) DEFAULT NULL COMMENT '充值卡类型\n1：次卡\n3：月卡\n5：季卡\n7：半年卡\n9：年卡',
  `amount` float DEFAULT NULL COMMENT '充值金额',
  `status` varchar(45) DEFAULT NULL COMMENT '付款状态\n1:已付款\n0:未付款\n3:审核中	\n4:审核通过',
  `charge_type` varchar(8) DEFAULT NULL COMMENT '支付方式\n1：微信  \n3：支付宝\n2:支付宝网页 \n4:汇款支付 \n5：网银   \n7：卡充  \n9：后台',
  `paid_time` timestamp NULL DEFAULT NULL COMMENT '付款时间',
  `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `account_flag` varchar(8) DEFAULT NULL COMMENT '是否到账：\n1：已到账\n0：未到账',
  `source` varchar(8) DEFAULT NULL COMMENT '来源:\n0:官网；\n2:空气卫士app android\n4:空气卫士app iOS\n6:微信商城',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录订单信息表';

CREATE TABLE `sys_org` (
  `org_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '本实体记录的唯一标识，创建组织机构的唯一标识。',
  `org_no` varchar(16) DEFAULT NULL COMMENT '机构、单位等编码',
  `org_pid` int(11) DEFAULT NULL COMMENT '直属上级机构编码。',
  `org_name` varchar(256) DEFAULT NULL COMMENT '机构单位详细的名称。',
  `abbr` varchar(128) DEFAULT NULL COMMENT '机构名称简写,如北京简写bj。',
  `org_type` varchar(8) DEFAULT NULL COMMENT '所属品牌：具体类型根据标准代码维护为依据。',
  `user_id` int(11) DEFAULT NULL COMMENT '负责人',
  `mobile` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(8) DEFAULT NULL COMMENT '传真',
  `sys_type` varchar(8) DEFAULT '01' COMMENT '组织机构的一个系统来源方向：01 后台管理系统\n具体系统来源根据标准代码维护为依据。',
  `sort_no` int(4) DEFAULT '1' COMMENT '在同级中的排列顺序的序号，用自然数阿拉伯数字标识，如，1、2、3。',
  `isactive` varchar(8) DEFAULT '1' COMMENT '描述该机构是否有效的一个状态标识  0：无效  1：有效',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='组织机构单位的基本信息。本实体主要包括机构标识、机构名称、上级机构标识、机构类型等属性。';

CREATE TABLE `sys_remit` (
  `remit_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '汇款标识',
  `order_id` varchar(16) DEFAULT NULL COMMENT '订单号',
  `login_name` varchar(32) DEFAULT NULL COMMENT '登录名',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '提交汇款信息时间',
  `bank_name` varchar(128) DEFAULT NULL COMMENT '汇款银行',
  `remit_type` varchar(8) DEFAULT NULL COMMENT '汇款种类\n4：银行汇款\n6：银行转账',
  `remit_number` varchar(64) DEFAULT NULL COMMENT '汇款单号/银行卡号',
  `device_id` int(11) DEFAULT NULL COMMENT '设备标识',
  `device_name` varchar(64) DEFAULT NULL COMMENT '车牌号',
  `card_type` varchar(8) DEFAULT NULL COMMENT '服务类型',
  `amount` float DEFAULT NULL COMMENT '汇款金额',
  `status` varchar(8) DEFAULT NULL COMMENT '审核状态\n0:待审核\n1：审核通过 \n4:已删除',
  `audit_time` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(32) DEFAULT NULL COMMENT '备用字段2',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  PRIMARY KEY (`remit_id`),
  UNIQUE KEY `remit_id_UNIQUE` (`remit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='汇款信息表';

CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '本实体记录的唯一标识。',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `user_id` int(11) DEFAULT NULL COMMENT '创建该角色的操作人员，即系统登录用户标识',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `isactive` varchar(8) DEFAULT NULL COMMENT '描述该角色是否可用的一个状态标识   0：无效  1：有效',
  `role_desc` varchar(256) DEFAULT NULL COMMENT '角色描述。',
  `remark1` varchar(32) DEFAULT NULL COMMENT '备用字段',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='系统角色的信息。角色是一组资源权限项的集合，这一组资源权限项可以被一起授予或回收。本实体主要包括角色标识、角色名称';

CREATE TABLE `sys_role_menu_rela` (
  `rm_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `role_id` int(11) DEFAULT NULL COMMENT '角色标识。',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单标识。',
  `menu_code` varchar(16) DEFAULT NULL COMMENT '菜单编码',
  `user_id` int(11) DEFAULT NULL COMMENT '操作人员',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`rm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1574 DEFAULT CHARSET=utf8 COMMENT='用于记录菜单功能信息与角色信息之间关系的信息，本实体主要包括菜单角色关系标识、菜单唯一标识、角色唯一标识等属性';

CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户标识',
  `login_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登录名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `last_change_pwd_time` timestamp NULL DEFAULT NULL COMMENT '最后修改密码时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `signature` varchar(512) DEFAULT NULL COMMENT '签名',
  `photo_icon_url` varchar(512) DEFAULT NULL COMMENT '头像',
  `birthday` timestamp NULL DEFAULT NULL COMMENT '生日',
  `sex` varchar(8) DEFAULT NULL COMMENT '性别',
  `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `car_type` varchar(64) DEFAULT NULL COMMENT '汽车型号',
  `country` varchar(32) DEFAULT NULL COMMENT '所属国家',
  `city` varchar(32) DEFAULT NULL COMMENT '所在省/市',
  `district` varchar(32) DEFAULT NULL COMMENT '所在区/县',
  `car_category` varchar(8) DEFAULT NULL COMMENT '汽车种类:\n2:出租车\n4：私家车',
  `org_id` int(11) DEFAULT NULL COMMENT '所属机构',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `user_type` varchar(8) DEFAULT NULL COMMENT '用户类型\n1：后台\n3：app',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=523 DEFAULT CHARSET=utf8 COMMENT='用户表:用户基本信息，通过APP端注册、修改维护，登录名称需做唯一性验证。';

CREATE TABLE `sys_user_advertisement_rela` (
  `uar_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户广告关联标识\n',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `ad_id` int(11) DEFAULT NULL COMMENT '广告标识',
  `is_valid` varchar(8) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`uar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户广告关联关系表:广告点击后往用户广告关联关系表中增加一条记录\n\n\n\n\n';

CREATE TABLE `sys_user_binding` (
  `ub_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '绑定标识',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `open_id` varchar(64) DEFAULT NULL COMMENT '第三方登录账号唯一标识',
  `binding_type` varchar(8) DEFAULT NULL COMMENT '绑定类型\n\n2:个推出租车\n4/8:个推私家车（4:个推私家车appstore  8:个推私家车企业版 主要为IOS区分，android为4）',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '绑定时间',
  `os_type` varchar(16) DEFAULT NULL COMMENT '操作系统列型\nandroid:安卓\nios:苹果',
  PRIMARY KEY (`ub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8 COMMENT='用户绑定表：第三方登录绑定表，暂时不用';

CREATE TABLE `sys_user_gps_info` (
  `gps_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地地信息标识',
  `longitude` varchar(32) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(32) DEFAULT NULL COMMENT '纬度',
  `location_time` timestamp NULL DEFAULT NULL COMMENT '定位时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  PRIMARY KEY (`gps_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33566 DEFAULT CHARSET=utf8 COMMENT='用户GPS信息表:每次用户启动APP时，记录GPS信息，APP与蓝牙设备无连接时，记录GPS信息';

CREATE TABLE `sys_user_role_rela` (
  `user_role_ref_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色关联关系标识。',
  `user_id` int(11) DEFAULT NULL COMMENT '用户标识',
  `role_id` int(11) DEFAULT NULL COMMENT '角色标识。',
  `op_user_id` int(11) DEFAULT NULL COMMENT '操作人员标识',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建操作时间',
  PRIMARY KEY (`user_role_ref_id`)
) ENGINE=InnoDB AUTO_INCREMENT=342 DEFAULT CHARSET=utf8 COMMENT='系统用户与角色的多对多关系。本实体主要包括系统用户与角色对照标识等属性。';

CREATE TABLE `sys_weather` (
  `weather_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '天气标识',
  `aqi` varchar(32) DEFAULT NULL COMMENT 'aqi指数',
  `co` varchar(32) DEFAULT NULL COMMENT '一氧化碳值',
  `no2` varchar(32) DEFAULT NULL COMMENT '二氧化氮值',
  `o3` varchar(32) DEFAULT NULL COMMENT '臭氧值',
  `so2` varchar(32) DEFAULT NULL COMMENT '二氧化硫值',
  `pm10` varchar(32) DEFAULT NULL COMMENT 'PM10值',
  `pm25` varchar(32) DEFAULT NULL COMMENT 'PM2.5值',
  `quality` varchar(32) DEFAULT NULL COMMENT '空气质量',
  `humidity` varchar(32) DEFAULT NULL COMMENT '湿度',
  `temperature` varchar(32) DEFAULT NULL COMMENT '温度',
  `weather_text` varchar(32) DEFAULT NULL COMMENT '天气文本',
  `wind_scale` varchar(32) DEFAULT NULL COMMENT '风级',
  `us_embassy_aqi` varchar(32) DEFAULT NULL COMMENT '美大使馆aqi值',
  `think_page_weather` varchar(4096) DEFAULT NULL COMMENT '心知天气JSON文本',
  `last_update` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
  `remark1` varchar(32) DEFAULT NULL COMMENT '城市编号',
  `remark2` varchar(512) DEFAULT NULL COMMENT '城市名称',
  `user_id` int(11) DEFAULT NULL,
  `us_embassy_aqi_time` timestamp NULL DEFAULT NULL COMMENT '美国大使馆天气预报发布时间',
  `us_embassy_aqi_pm25` varchar(32) DEFAULT NULL COMMENT '美使馆pm25 µg/m³',
  `forecast` varchar(2048) DEFAULT NULL COMMENT '一周天气预报json',
  `weather_code` varchar(8) DEFAULT NULL COMMENT '天气图标编码',
  PRIMARY KEY (`weather_id`)
) ENGINE=InnoDB AUTO_INCREMENT=977987 DEFAULT CHARSET=utf8 COMMENT='天气表\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n';

CREATE TABLE `wx_fenxiang` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `wecha_id` varchar(60) DEFAULT '',
  `addtime` varchar(20) DEFAULT '',
  `num` int(11) DEFAULT NULL COMMENT '分享次数',
  `fid` int(11) DEFAULT '1' COMMENT '活动id',
  `token` varchar(40) DEFAULT NULL,
  `tel` varchar(11) DEFAULT NULL COMMENT '手机号',
  `chexing` varchar(200) DEFAULT NULL COMMENT '车型车系',
  `lintime` varchar(20) DEFAULT NULL COMMENT '领取时间',
  `org_id` int(11) DEFAULT '5' COMMENT '门店(默认5)',
  `status` varchar(8) DEFAULT NULL COMMENT '状态:  0:未处理  1:已处理',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=269 DEFAULT CHARSET=utf8;

CREATE TABLE `wx_loading_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '预约安装标识',
  `wxid` int(11) DEFAULT NULL COMMENT '微信预约id',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `tel` varchar(13) DEFAULT NULL COMMENT '电话',
  `addtime` timestamp NULL DEFAULT NULL COMMENT '下单时间',
  `goods_name` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `book_num` int(11) DEFAULT NULL COMMENT '商品数量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '总价格',
  `order_status` int(11) DEFAULT NULL COMMENT '订单状态（0未处理，1失败，2成功）',
  `hid` int(11) DEFAULT NULL COMMENT '订单类型（1套餐，2预约装车，3备品备件）',
  `remarks` varchar(250) DEFAULT NULL COMMENT '用户备注',
  `companyid` int(11) DEFAULT NULL COMMENT '门店id（1）',
  `starttime` varchar(64) DEFAULT NULL COMMENT '安装时间',
  `catid` varchar(32) DEFAULT NULL COMMENT '品牌',
  `catid2` varchar(32) DEFAULT NULL COMMENT '系列',
  `catid3` varchar(32) DEFAULT NULL COMMENT '型号',
  `nianfen` int(11) DEFAULT NULL COMMENT '年份',
  `pre_type` varchar(8) DEFAULT NULL COMMENT '预约类型\n1：微信\n3：网站',
  `remark` varchar(1024) DEFAULT NULL COMMENT '客服务处理备注',
  `car_style` varchar(64) DEFAULT NULL COMMENT '汽车款式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='预约安装信息表';

CREATE TABLE `wx_lottery_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wxid` int(11) DEFAULT NULL COMMENT '微信抽奖唯一id',
  `lid` int(11) DEFAULT NULL COMMENT '活动id(1大转盘)',
  `usenums` int(1) DEFAULT '0' COMMENT '用户使用次数',
  `wecha_id` varchar(60) DEFAULT NULL COMMENT '微信唯一识别码',
  `islottery` int(1) DEFAULT NULL COMMENT '是否中奖（0未中，1中奖）',
  `tel` varchar(15) DEFAULT NULL COMMENT '电话',
  `sn` varchar(13) DEFAULT NULL COMMENT '中奖后序列号',
  `time` timestamp NULL DEFAULT NULL COMMENT '中奖时间',
  `prize` varchar(50) DEFAULT '' COMMENT '已中奖项（1-6中奖，其余都未中奖）',
  `sendstutas` int(11) DEFAULT '0' COMMENT '是否已发奖品（0未发，1已发）',
  `sendtime` timestamp NULL DEFAULT NULL COMMENT '发奖时间',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `prizename` varchar(32) DEFAULT NULL COMMENT '奖品名称',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

CREATE TABLE `wx_xiche` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `token` varchar(60) DEFAULT NULL,
  `wecha_id` varchar(60) DEFAULT NULL,
  `sn` varchar(16) DEFAULT NULL COMMENT '专属编码',
  `truename` varchar(60) DEFAULT NULL COMMENT '姓名',
  `tel` varchar(11) DEFAULT NULL COMMENT '手机号',
  `status` varchar(8) DEFAULT NULL COMMENT '0未处理，1处理',
  `chexin` varchar(200) DEFAULT NULL COMMENT '车型+微信号',
  `uptime` varchar(20) DEFAULT NULL COMMENT '修改时间',
  `org_id` int(11) DEFAULT NULL COMMENT '所属组织机构',
  `source` varchar(32) DEFAULT NULL COMMENT '来源',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=727 DEFAULT CHARSET=utf8;
