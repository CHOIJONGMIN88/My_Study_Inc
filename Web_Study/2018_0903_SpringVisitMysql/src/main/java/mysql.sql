create table visit(

idx int auto_increment,
name varchar(100) not null,
content varchar(2000) not null,
pwd varchar(100) not null,
ip varchar(100) not null,
regdate datetime  ,
constraint pk_visit_idx primary key(idx)
);




insert into visit(name,content,pwd,ip,regdate) values('����Ʈ��','�ȳ��ϼ���. ��������������ȣȣȣ�����������������������������ٰ�����������⸶����������������������������','1234','127.0.0.1',now());
insert into visit(name,content,pwd,ip,regdate)  values('�̱浿','�ȳȳȳ�','1234','127.0.0.1',now());

commit;


update visit set name ='SCP���',content='1231231234',pwd='1234',regdate=sysdate where idx=6;
select * from visit;
delete from visit;