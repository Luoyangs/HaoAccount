package com.haoxue.haoaccount.base;

/**
 * 说明:常量
 * 作者:Luoyangs
 * 时间:2015-9-26
 */
public final class Constant {

	/**是否为第一次登录*/
	public static final String IS_FIRST_LOGIN = "IS_FIRST_LOGIN";
	/**显示密码设置页面*/
	public static final String SHOW_PASS_SET = "SHOW_PASS_SET";
	
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
		
		/**用户表创建语句*/
		public static final String USER_TABLE_CREATE = "create table USER_TABLE(id integer primary key autoincrement, "
				+ "name varchar(60),password varchar(60),email varchar(30),birth varchar(30),like varchar(260),address " 
				+ "varchar(160),work varchar(60),state int)";
		/**总类别表创建语句*/
		public static final String TYPE_TABLE_CREATE = "create table TYPE_TABLE(id integer primary key autoincrement, "
				+ "name varchar(60),state int)";
		/**父类别表创建语句*/
		public static final String PTYPE_TABLE_CREATE = "create table PTYPE_TABLE(id integer primary key autoincrement, "
				+ "name varchar(60),type int,state int)";
		public static final String CTYPE_TABLE_CREATE = "create table CTYPE_TABLE(id integer primary key autoincrement, "
				+ "name varchar(60),ptype int,state int)";
		
		/**收入表创建语句*/
		public static final String INCOME_TABLE_CREATE = "create table INCOME_TABLE(id integer primary key autoincrement, "
				+ "userId int,num float,type int, ptype int,ctype int,froms varchar(200),save varchar(200), info text,date varchar(30));";
		/**支出表创建语句*/
		public static final String OUTCOME_TABLE_CREATE = "create table OUTCOME_TABLE(id integer primary key autoincrement, "
				+ "userId int,num float,type int, ptype int,ctype int,froms varchar(200),use varchar(200), info text,date varchar(30));";
		/**预算表创建语句*/
		public static final String PREPAY_TABLE_CREATE = "create table PREPAY_TABLE(id integer primary key autoincrement, "
				+ "userId int,num float,type int, ptype int,ctype int,froms varchar(200),use varchar(200), info text,date varchar(30));";
	}
}

/**
 * 建表语句

create table USER_TABLE(id integer primary key autoincrement, name varchar(60),password varchar(60),email varchar(30),birth varchar(30), like varchar(260),address varchar(160),work varchar(60),state int);

create table TYPE_TABLE(id integer primary key autoincrement,name varchar(60),state int);

create table PTYPE_TABLE(id integer primary key autoincrement,name varchar(60),img varchar(60),type int,state int);

create table CTYPE_TABLE(id integer primary key autoincrement, name varchar(60),img varchar(60),type int,ptype int,state int);

create table INCOME_TABLE(id integer primary key autoincrement, userId int,num float,type int, ptype int,ctype int,froms varchar(200),save varchar(200), info text,date varchar(30));

create table OUTCOME_TABLE(id integer primary key autoincrement, userId int,num float,type int, ptype int,ctype int,froms varchar(200),use varchar(200), info text,date varchar(30));

create table PREPAY_TABLE(id integer primary key autoincrement, userId int,num float,type int, ptype int,ctype int,froms varchar(200),use varchar(200), info text,date varchar(30));

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

select * from CTYPE_TABLE;

insert into CTYPE_TABLE(name,img,type,ptype,state) values('衣服裤子','c1',2,1,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('鞋帽包包','c2',2,1,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('化妆饰品','c3',2,1,0);


insert into CTYPE_TABLE(name,img,type,ptype,state) values('早午晚餐','f1',2,2,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('烟酒茶水','f2',2,2,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('水果零食','f3',2,2,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('日常用品','h1',2,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('水电煤气','h2',2,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('房租','h3',2,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('业务管理','h4',2,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('维修保养','h5',2,3,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('公共交通','t1',2,4,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('打出租车','t2',2,4,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('私家车','t3',2,4,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('座机费','pp1',2,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('手机费','pp2',2,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('上网费','pp3',2,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('邮寄费','pp4',2,5,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('运动健身','y1',2,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('腐败聚会','y2',2,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('休闲玩乐','y3',2,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('宠物宝贝','y4',2,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('旅游度假','y5',2,6,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('书报杂志','s1',2,7,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('培训教学','s2',2,7,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('数码装备','s3',2,7,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('送礼请客','g1',2,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('孝敬家长','g2',2,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('换人钱物','g3',2,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('慈善捐助','g4',2,8,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('药品费','b1',2,9,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('保健费','b2',2,9,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('美容费','b3',2,9,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('医疗费','b4',2,9,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('银行手续','j1',2,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('投资亏损','j2',2,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('按揭还款','j3',2,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('消费税收','j4',2,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('利息支出','j5',2,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('赔偿罚款','j6',2,10,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('其他支出','o1',2,11,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('意外丢失','o2',2,11,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('烂账损失','o3',2,11,0);

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

insert into CTYPE_TABLE(name,img,type,ptype,state) values('衣服裤子','c1',3,1,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('鞋帽包包','c2',3,1,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('化妆饰品','c3',3,1,0);


insert into CTYPE_TABLE(name,img,type,ptype,state) values('早午晚餐','f1',3,2,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('烟酒茶水','f2',3,2,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('水果零食','f3',3,2,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('日常用品','h1',3,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('水电煤气','h2',3,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('房租','h3',3,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('业务管理','h4',3,3,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('维修保养','h5',3,3,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('公共交通','t1',3,4,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('打出租车','t2',3,4,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('私家车','t3',3,4,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('座机费','pp1',3,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('手机费','pp2',3,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('上网费','pp3',3,5,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('邮寄费','pp4',3,5,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('运动健身','y1',3,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('腐败聚会','y2',3,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('休闲玩乐','y3',3,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('宠物宝贝','y4',3,6,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('旅游度假','y5',3,6,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('书报杂志','s1',3,7,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('培训教学','s2',3,7,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('数码装备','s3',3,7,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('送礼请客','g1',3,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('孝敬家长','g2',3,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('换人钱物','g3',3,8,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('慈善捐助','g4',3,8,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('药品费','b1',3,9,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('保健费','b2',3,9,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('美容费','b3',3,9,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('医疗费','b4',3,9,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('银行手续','j1',3,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('投资亏损','j2',3,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('按揭还款','j3',3,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('消费税收','j4',3,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('利息支出','j5',3,10,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('赔偿罚款','j6',3,10,0);

insert into CTYPE_TABLE(name,img,type,ptype,state) values('其他支出','o1',3,11,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('意外丢失','o2',3,11,0);
insert into CTYPE_TABLE(name,img,type,ptype,state) values('烂账损失','o3',3,11,0);

 */
