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
				+ "name varchar(60),type int,ptype int,state int)";
		
		/**收入表创建语句*/
		public static final String INCOME_TABLE_CREATE = "create table INCOME_TABLE(id integer primary key autoincrement, "
				+ "userId int,num float,type int, ptype int,ctype int,from varchar(200),save varchar(200), info text,date varchar(30));";
		/**支出表创建语句*/
		public static final String OUTCOME_TABLE_CREATE = "create table OUTCOME_TABLE(id integer primary key autoincrement, "
				+ "userId int,num float,type int, ptype int,ctype int,from varchar(200),use varchar(200), info text,date varchar(30));";
		/**预算表创建语句*/
		public static final String PREPAY_TABLE_CREATE = "create table PREPAY_TABLE(id integer primary key autoincrement, "
				+ "userId int,num float,type int, ptype int,ctype int,from varchar(200),use varchar(200), info text,date varchar(30));";
	}
}
