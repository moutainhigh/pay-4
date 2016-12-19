package com.pay.txncore.aop;

public class TestBean2 {

	public void doException(){
		System.out.println(2/0);
	}
	
	public void buyBook(Book book){
		if("忧伤".equals(book.getName())){
			System.out.println(3/0);
		}
	}
}
