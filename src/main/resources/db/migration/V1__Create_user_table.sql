create table User
			(
			id int auto_increment,
			ACCOUNT_ID VARCHAR(100),
			NAME VARCHAR(50),
			TOKEN CHAR(36),
			GMT_CREAT BIGINT,
			GMT_MODIFIED BIGINT,
			constraint User_pk
			primary key (id)
			)
