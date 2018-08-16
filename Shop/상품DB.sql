--상품일련번호 관리객체
create sequence seq_product_idx
increment by 1
start with 1;

--상품테이블
CREATE TABLE product	(
idx		 int primary key,						--일련번호
category	 varchar2(100)		Not Null,		--카테고리(컴퓨터/스포츠)
p_num		 varchar2(100)	unique	Not Null,	--상품번호(모델번호)
p_name		 varchar2(50)		Not Null,		--상품명
p_company    varchar2(50)		Not Null,		--제조사
p_price		 int				Not Null,		--가격(단가)
p_saleprice  int			Not Null,			--할인가
p_image_s	 varchar2(255)		Null,			--상품이미지(소)
p_image_l	 varchar2(255)		Null,			--상품이미지(대)
p_content	 clob	Not Null,					--상품설명
p_date		 date					Not Null	--등록일자		
) ;



insert into product values(seq_product_idx.nextVal,'sp003', 'RC-113',
'로체스 인라인','로체스',3200,1150,'pds1.jpg','pds1_z.jpg',
'바이오맥스 통풍 나일론-HGPU SHELL * 특수 충격 흡수 밑창 * 신발끈 메모리 버클 * 힐 락에 의한 신속한 신발끈 시스템 * 느린 메모리 포말에 의한 편안한 통풍성의 숨쉬는 라이너 * 쿨 통풍 시스템 * 통풍성의 인체공학적 신발밑창 * 손쉬운 엔트리 시스템(신기 편한 입구) * 몰디드 알루미늄 프레임 * 80mm 82a hyper dubbs 휠 * 강철 스페이서 * ABEC-5 베어링',sysdate);

insert into product values(seq_product_idx.nextVal,'ele002', 'vC-13',
'사니PDP-TV','사니',9200,4750,'pds4.jpg','pds4_z.jpg',
'질러~ 질러! 
무조건 질러봐~ 후회 하지 않아~~',sysdate);


select * from product;

commit









