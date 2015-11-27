package com.haoxue.haoaccount.base;

import com.haoxue.haoaccount.R;

/**
 * 说明:常量
 * 作者:Luoyangs
 * 时间:2015-9-26
 */
public final class Constant {

	/**百度天气API*/
	public static final String WETHER_URL = "http://api.map.baidu.com/telematics/v3/weather";
	/**默认的数据格式*/
	public static final String DATA_TYPE = "json";
	/**百度天气API的ak*/
	public static final String BAIDU_AK = "kLy5hMmZTY2ORN1OgOQU3PAT";
	/**百度天气API的mcode*/
	public static final String BAIDU_MCODE = "83:43:03:20:2B:A9:84:A9:D6:10:2A:F2:BF:55:D1:16:F3:50:DC:EA;com.haoxue.haoaccount";
	
	/**是否为第一次登录*/
	public static final String IS_FIRST_LOGIN = "IS_FIRST_LOGIN";
	/**显示密码设置页面*/
	public static final String SHOW_PASS_SET = "SHOW_PASS_SET";
	/**显示记事分类图片页面*/
	public static int[] LISTVIEWIMG = new int[] { R.drawable.ic_category_40,
		R.drawable.ic_category_10, R.drawable.ic_category_20,
		R.drawable.ic_category_30, R.drawable.ic_category_45,
		R.drawable.ic_category_50, R.drawable.ic_category_55,
		R.drawable.ic_category_60, R.drawable.ic_category_65,
		R.drawable.ic_category_70, R.drawable.ic_category_80};
	/**颜色集*/
	public final static String[] FAVTEXTCOLOR = { "#ffdc8d3e", "#ffdccf57",
			"#ff6bdc60", "#ff6081dc", "#ffce8fdc", "#ff66bbff",
			"#ff99cc66", "#ff666699", "#ffcc9933", "#ffff9900",
			"#ffffcc00", "#ffff0033", "#ffff9966", "#ffccff00",
			"#ffcc3399", "#ffff6600", "#ff993366", "#ffcccc33",
			"#ff666633", "#ff009933", "#ff009966", "#ff006633",
			"#ff006600", "#ffcc0066", "#ff009999", "#ff333399", 
			"#ffcccc00", "#ff006633", "#ffff33cc", "#ffff0000" };
	
	public final class MSG{
		/**没有网络*/
		public static final int NO_NETWORK = 0x01;
		/**加载成功*/
		public static final int LOAD_OK = 0x02;
		/**加载失败*/
		public static final int LOAD_ERROR = 0x03;
		/**刷新成功*/
		public static final int FRESH_OK = 0x04;
		/**刷新失败*/
		public static final int FRESH_ERROR = 0x05;
	}
	
	/**支出账户*/
	public static final String DATA_OUT_ACCOUNT[] = {"现金账户","信用卡","金融账户","虚拟账户","负债账户","债权账户","投资账户"};
	/**支出账户子项*/
	public static final String DATA_OUT_ACCOUNT_SUB[][] = {
		{"现金(CNY)"},
		{"信用卡(CNY)"},
		{"银行卡(CNY)"},
		{"支付宝(CNY)"},
		{"应付款项(CNY)","生意借款(CNY)"},
		{"应收款项(CNY)"},
		{"基金账户(CNY)","余额宝(CNY)","股票账户(CNY)"},
	};
	/**转账出处*/
	public static final String DATA_ZA_SRC[] = {"现金(CNY)","信用卡(CNY)","银行卡(CNY)","支付宝(CNY)","应付款项(CNY)","生意借款(CNY)","应收款项(CNY)","基金账户(CNY)","余额宝(CNY)","股票账户(CNY)"};
	/**转账商家*/
	public static final String DATA_ZA_CUS[] = {"无商家/地点","其他","饭堂","银行","商场","超市","公交","淘宝","供应商","客户"};
	/**转账项目*/
	public static final String DATA_ZA_PRO[] = {"无项目","促销活动","优惠"};
	/**转账成员*/
	public static final String DATA_ZA_CMP[] = {"无成员","本人","家人","员工","BOSS"};
	
	public final class PSV{
		/**相机图片*/
		public static final int REQUEST_CODE_PIC = 0x01;
		/**添加录制音频*/
		public static final int REQUEST_CODE_SOD = 0x02;
		/**添加录制视频*/
		public static final int REQUEST_CODE_VIO = 0x03;
	}
	
	public final class DB{
		/**数据库名*/
		public static final String HAOACCOUNT_DB_NAME = "HAOACCOUNT_DB_NAME";
		
		/**用户表名*/
		public static final String USER_TABLE_NAME = "USER_TABLE";
		/**收入表名*/
		public static final String INCOME_TABLE_NAME = "INCOME_TABLE";
		/**支出表名*/
		public static final String OUTCOME_TABLE_NAME = "OUTCOME_TABLE";
		/**预算表名*/
		public static final String PREPAY_TABLE_NAME = "PREPAY_TABLE";
		/**总类别表名*/
		public static final String TYPE_TABLE_NAME = "TYPE_TABLE";
		/**父类别表名*/
		public static final String PTYPE_TABLE_NAME = "PTYPE_TABLE";
		/**子类别表名*/
		public static final String CTYPE_TABLE_NAME = "CTYPE_TABLE";
		/**消息表名*/
		public static final String MSG_TABLE_NAME = "MSG_TABLE";
		/**积分表*/
		public static final String FAVOR_TABLE_NAME = "FAVOR_TABLE";
		/**签到表*/
		public static final String SIGN_TABLE_NAME = "SIGN_TABLE";
		/**记事本类别表*/
		public static final String NOTE_TYPE_TABLE_NAME = "NOTE_TYPE_TABLE";
		/**记事本表*/
		public static final String NOTE_TABLE_NAME = "NOTE_TABLE";
		/**记事本附件表*/
		public static final String ATTACH_TABLE_NAME = "ATTACH_TABLE";
		
		/**获取定制类别*/
		public static final String GET_TYPE ="select name from TYPE_TABLE where own=1";
		/**获取父类别*/
		public static final String GET_PTYPE_BY_TYPE ="select name,img from PTYPE_TABLE where type=?";
		/**依据父类别获取子类别*/
		public static final String GET_CTYPE_BY_PTYPE = "select c.name,c.img from CTYPE_TABLE c,PTYPE_TABLE p where c.type=? and p.id = c.ptype and p.name = ?";
		
		/**添加预算*/
		public static final String INSERT_PREPAY = "insert into PREPAY_TABLE(UserId,num,pay,type,ptype,ctype,froms,use,info,year,month) values(?,?,?,?,?,?,?,?,?,?,?)";
		/**判断二级预算是否存在*/
		public static final String HAS_PREPAY_TWO = "select count(*) from PREPAY_TABLE where type = 3 and ptype = ? and ctype = 0";
		/**更新二级预算中的预算值*/
		public static final String SET_PREPAY_TWO_NUM = "update PREPAY_TABLE set num = ? where type = 3 and ptype = ? and ctype = 0";
		/**读取二级预算中的预算和支出*/
		public static final String GET_PREPAY_TWO = "select num,pay from PREPAY_TABLE where type = 3 and ptype = ? and ctype = 0";
		
		/**判断三级预算是否存在*/
		public static final String HAS_PREPAY_THREE = "select count(*) from PREPAY_TABLE where ctype =?";
		/**更新三级预算中的预算值*/
		public static final String SET_PREPAY_THREE_NUM = "update PREPAY_TABLE set num = ? where ctype = ?";
		/**读取三级预算中的预算和支出*/
		public static final String GET_PREPAY_THREE = "select num,pay from PREPAY_TABLE where ctype = ?";
		
		/**电话判断用户是否存在*/
		public static final String HAS_USER_PHONE = "select id from USER_TABLE where phone=?";
		/**邮箱判断用户是否存在*/
		public static final String HAS_USER_EMAIL = "select id from USER_TABLE where email=?";
		/**手机登陆*/
		public static final String LOG_USER_PHONE = "select id from USER_TABLE where phone=? and password=?";
		/**邮箱登陆*/
		public static final String LOG_USER_EMAIL = "select id from USER_TABLE where email=? and password=?";
		
		/**判断消息是否存在*/
		public static final String HAS_MSG_BY_TYPE = "select count(*) from MSG_TABLE where type =?";
		/**类别读取消息*/
		public static final String GET_MSG_BY_TYPE = "select id,title,state from MSG_TABLE where type =? order by createTime";
		/**ID读取消息*/
		public static final String GET_MSG_BY_ID = "select title,content,type,state,createTime from MSG_TABLE where id =?";
		
		/**判断积分是否存在*/
		public static final String HAS_FAV = "select count(*) from FAVOR_TABLE";
		/**读取积分*/
		public static final String GET_FAV = "select id,name,favcount,deadline from FAVOR_TABLE";
		
		/**判断签到是否存在*/
		public static final String HAS_SIGN = "select count(*) from SIGN_TABLE";
		/**读取最新一条签到*/
		public static final String GET_TOP_SIGN_BY_USERID = "select curfav,sumfav from SIGN_TABLE where userId =? order by id desc limit 1 offset 0";
		/**读取签到*/
		public static final String GET_SIGN = "select id,curfav,sumfav,subfav,createTime from SIGN_TABLE order by createTime desc";
		
		/**判断附件是否存在*/
		public static final String HAS_ATTACH = "select count(*) from ATTACH_TABLE where type = ?";
		/**判断记事分类是否存在*/
		public static final String HAS_NOTE_TYPE = "select count(*) from NOTE_TYPE_TABLE";
		/**读取记事分类*/
		public static final String GET_NOTE_TYPE = "select id,title from NOTE_TYPE_TABLE where userId = 0 or userId = ?";
		/**类别读取记事*/
		public static final String GET_NOTE_BY_TYPE = "select id,title,createTime,updateTime from NOTE_TABLE where type =?";
		/**删除记事*/
		public static final String DEL_NOTE = "delete from NOTE_TABLE where id =?";
		
	}
}

/**
 * 建表语句

CREATE TABLE USER_TABLE(id integer primary key autoincrement,phone varchar(20),email varchar(50),password varchar(50),imageUri varchar(50), name varchar(60),nice varchar(60),age int default 18,star varchar(10), sex varchar(6),likes varchar(160),info text,score int default 0,state int default 0,createTime DATETIME DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),updateTime DATETIME);

CREATE TABLE TYPE_TABLE(id integer primary key autoincrement,name varchar(60),own int default 0,state int);

create table PTYPE_TABLE(id integer primary key autoincrement,name varchar(60),img varchar(60),type int,state int);

create table CTYPE_TABLE(id integer primary key autoincrement, name varchar(60),img varchar(60),type int,ptype int,state int);

CREATE TABLE INCOME_TABLE(id integer primary key autoincrement, userId int,num float default 0,pay float default 0,type int, ptype int,ctype int,froms varchar(200),save varchar(200), info text,date DATETIME DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')));

CREATE TABLE OUTCOME_TABLE(id integer primary key autoincrement, userId int,num float default 0,type int, ptype int,ctype int,froms varchar(200),use varchar(200), info text,date DATETIME DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')));

CREATE TABLE PREPAY_TABLE(id integer primary key autoincrement, userId int,num float default 0,pay float default 0,type int, ptype int,ctype int,froms varchar(200),use varchar(200), info text,year int,month int,date DATETIME DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')));

CREATE TABLE MSG_TABLE(id integer primary key autoincrement, title nvarchar(500),content text,type int,state int default 0,createTime DATETIME DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')));

CREATE TABLE FAVOR_TABLE(id integer primary key autoincrement, name varchar(20),favcount int default 0,deadline int default 0);

CREATE TABLE SIGN_TABLE(id integer primary key autoincrement, userId int default -1,curfav int default 0,sumfav int default 0,subfav int default 0,createTime DATETIME DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')));

insert into TYPE_TABLE(name,state) values('收入',0);
insert into TYPE_TABLE(name,state) values('支出',0);
insert into TYPE_TABLE(name,state) values('预算',0);
insert into TYPE_TABLE(name,state) values('借贷',0);
insert into TYPE_TABLE(name,state) values('班费',0);

select * from TYPE_TABLE;

insert into PTYPE_TABLE(name,img,type,state) values('职业收入','i0',1,0);
insert into PTYPE_TABLE(name,img,type,state) values('其他收入','v0',1,0);

insert into PTYPE_TABLE(name,img,type,state) values('衣服饰品','c0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('食品酒水','f0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('居家业务','h0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('行业交通','t0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('交流通讯','pp0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('休闲娱乐','y0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('学习进修','s0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('人情往来','g0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('医疗保健','b0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('经融保险','j0',2,0);
insert into PTYPE_TABLE(name,img,type,state) values('其他杂项','o0',2,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('工资收入','i0',1,1,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('利息收入','i1',1,1,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('加班收入','i2',1,1,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('奖金收入','i3',1,1,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('投资收入','i4',1,1,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('兼职收入','i5',1,1,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('礼金收入','v1',1,2,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('中奖收入','v2',1,2,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('意外来钱','v3',1,2,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('经营所得','v4',1,2,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('衣服裤子','c1',2,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('鞋帽包包','c2',2,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('化妆饰品','c3',2,3,0);


insert into CTYPE_TABLE(name,img,type,ptype,state) values('早午晚餐','f1',2,4,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('烟酒茶水','f2',2,4,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('水果零食','f3',2,4,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('日常用品','h1',2,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('水电煤气','h2',2,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('房租','h3',2,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('业务管理','h4',2,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('维修保养','h5',2,5,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('公共交通','t1',2,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('打出租车','t2',2,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('私家车','t3',2,6,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('座机费','pp1',2,7,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('手机费','pp2',2,7,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('上网费','pp3',2,7,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('邮寄费','pp4',2,7,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('运动健身','y1',2,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('腐败聚会','y2',2,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('休闲玩乐','y3',2,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('宠物宝贝','y4',2,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('旅游度假','y5',2,8,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('书报杂志','s1',2,9,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('培训教学','s2',2,9,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('数码装备','s3',2,9,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('送礼请客','g1',2,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('孝敬家长','g2',2,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('换人钱物','g3',2,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('慈善捐助','g4',2,10,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('药品费','b1',2,11,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('保健费','b2',2,11,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('美容费','b3',2,11,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('医疗费','b4',2,11,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('银行手续','j1',2,12,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('投资亏损','j2',2,12,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('按揭还款','j3',2,12,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('消费税收','j4',2,12,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('利息支出','j5',2,12,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('赔偿罚款','j6',2,12,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('其他支出','o1',2,13,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('意外丢失','o2',2,13,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('烂账损失','o3',2,13,0);

insert into PTYPE_TABLE(name,img,type,state) values('衣服饰品','c0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('食品酒水','f0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('居家业务','h0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('行业交通','t0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('交流通讯','pp0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('休闲娱乐','y0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('学习进修','s0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('人情往来','g0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('医疗保健','b0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('经融保险','j0',3,0);
insert into PTYPE_TABLE(name,img,type,state) values('其他杂项','o0',3,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('衣服裤子','c1',3,14,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('鞋帽包包','c2',3,14,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('化妆饰品','c3',3,14,0);


insert into CTYPE_TABLE(name,img,type,ptype,state) values('早午晚餐','f1',3,15,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('烟酒茶水','f2',3,15,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('水果零食','f3',3,15,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('日常用品','h1',3,16,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('水电煤气','h2',3,16,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('房租','h3',3,16,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('业务管理','h4',3,16,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('维修保养','h5',3,16,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('公共交通','t1',3,17,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('打出租车','t2',3,17,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('私家车','t3',3,17,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('座机费','pp1',3,18,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('手机费','pp2',3,18,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('上网费','pp3',3,18,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('邮寄费','pp4',3,18,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('运动健身','y1',3,19,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('腐败聚会','y2',3,19,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('休闲玩乐','y3',3,19,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('宠物宝贝','y4',3,19,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('旅游度假','y5',3,19,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('书报杂志','s1',3,20,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('培训教学','s2',3,20,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('数码装备','s3',3,20,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('送礼请客','g1',3,21,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('孝敬家长','g2',3,21,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('换人钱物','g3',3,21,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('慈善捐助','g4',3,21,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('药品费','b1',3,22,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('保健费','b2',3,22,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('美容费','b3',3,22,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('医疗费','b4',3,22,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('银行手续','j1',3,23,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('投资亏损','j2',3,23,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('按揭还款','j3',3,23,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('消费税收','j4',3,23,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('利息支出','j5',3,23,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('赔偿罚款','j6',3,23,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('其他支出','o1',3,24,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('意外丢失','o2',3,24,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('烂账损失','o3',3,24,0);

insert into INCOME_TABLE(UserId,num,pay,type,ptype,ctype,froms,save,info)
values(0,200,20,1,1,4,'xdkfj','hhhh','kdhfkjfxldfixdjfixdfkd');

insert into OUTCOME_TABLE(UserId,num,type,ptype,ctype,froms,use,info)
values(0,200,2,5,27,'xdkfj','hhhh','kdhfkjfxldfixdjfixdfkd');

insert into PREPAY_TABLE(UserId,num,pay,type,ptype,ctype,froms,use,info,year,month)
values(0,1200,230,3,19,74,'xdkfj','几款是恐惧的女生','s收到了开发商',2015,10);

insert into MSG_TABLE(title,content,type) values ('这是系统的第11个消息','由源码可以看会被传进当的位置使用view.performClick()触发点击事件。',0);
insert into MSG_TABLE(title,content,type) values ('这是用户的第1个消息','由源码可以看出，e在event.ch事件，则后续hListene点击事件。',1);

insert into FAVOR_TABLE(name,favcount,deadline) values('1',5,100);
insert into FAVOR_TABLE(name,favcount,deadline) values('2',10,200);
insert into FAVOR_TABLE(name,favcount,deadline) values('3',10,300);
insert into FAVOR_TABLE(name,favcount,deadline) values('4',15,500);
insert into FAVOR_TABLE(name,favcount,deadline) values('5',15,700);
insert into FAVOR_TABLE(name,favcount,deadline) values('6',15,900);
insert into FAVOR_TABLE(name,favcount,deadline) values('7',20,1200);
insert into FAVOR_TABLE(name,favcount,deadline) values('8',20,1400);
insert into FAVOR_TABLE(name,favcount,deadline) values('9',20,1600);
insert into FAVOR_TABLE(name,favcount,deadline) values('10',20,1800);
insert into FAVOR_TABLE(name,favcount,deadline) values('11',25,2200);
insert into FAVOR_TABLE(name,favcount,deadline) values('12',25,2400);
insert into FAVOR_TABLE(name,favcount,deadline) values('13',25,2600);
insert into FAVOR_TABLE(name,favcount,deadline) values('14',25,2800);
insert into FAVOR_TABLE(name,favcount,deadline) values('15',25,3000);
insert into FAVOR_TABLE(name,favcount,deadline) values('16',30,3500);
insert into FAVOR_TABLE(name,favcount,deadline) values('17',30,3700);
insert into FAVOR_TABLE(name,favcount,deadline) values('18',30,3900);
insert into FAVOR_TABLE(name,favcount,deadline) values('19',30,4100);
insert into FAVOR_TABLE(name,favcount,deadline) values('20',30,4300);
insert into FAVOR_TABLE(name,favcount,deadline) values('21',30,4500);
insert into FAVOR_TABLE(name,favcount,deadline) values('22',35,5000);
insert into FAVOR_TABLE(name,favcount,deadline) values('23',35,5200);
insert into FAVOR_TABLE(name,favcount,deadline) values('24',35,5400);
insert into FAVOR_TABLE(name,favcount,deadline) values('25',35,5600);
insert into FAVOR_TABLE(name,favcount,deadline) values('26',35,5800);
insert into FAVOR_TABLE(name,favcount,deadline) values('27',35,6000);
insert into FAVOR_TABLE(name,favcount,deadline) values('28',35,6200);
insert into FAVOR_TABLE(name,favcount,deadline) values('29',40,6800);
insert into FAVOR_TABLE(name,favcount,deadline) values('30',40,7000);

 */
