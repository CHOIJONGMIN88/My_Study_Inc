package service;

import dao.Test2Dao;
import dao.TestDao;

public class TestServiceImpl implements TestService {

	TestDao dao1;
	Test2Dao dao2;

	public TestDao getDao1() {
		return dao1;
	}

	public void setDao1(TestDao dao1) {
		this.dao1 = dao1;
	}

	public Test2Dao getDao2() {
		return dao2;
	}

	public void setDao2(Test2Dao dao2) {
		this.dao2 = dao2;
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
//		long start = System.currentTimeMillis();
		System.out.println("--�޼ҵ� ����: Test Service test()ȣ��--");
//
		try {
			Thread.sleep(1234);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		long end = System.currentTimeMillis();
//
//		System.out.println("����ð� " + (end - start) + "�и�������");

	}

	@Override
	public void test2() {
		// TODO Auto-generated method stub
	//	long start = System.currentTimeMillis();
		System.out.println("--�޼ҵ� ���� : TestService Test2()ȣ��--");

		try {
			Thread.sleep(1234);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
//		long end = System.currentTimeMillis();
//
//		System.out.println("����ð� " + (end - start) + "�и�������");

	}

}
