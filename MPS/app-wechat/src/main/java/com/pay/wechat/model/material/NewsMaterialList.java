/**
 * 
 */
package com.pay.wechat.model.material;

import java.util.List;

/**
 * 获取图文素材列表类
 * @author davis at 2016-07-05
 *
 */
public class NewsMaterialList {

	//该类型的素材的总数
	private int total_count ;
	//本次调用获取的素材的数量
	private int item_count ;
	//返回多图文素材对象
	private List<NewsMaterial> item ;
		
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int getItem_count() {
		return item_count;
	}

	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}
	public List<NewsMaterial> getItem() {
		return item;
	}
	public void setItem(List<NewsMaterial> item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "NewsMaterialList [total_count=" + total_count + ", item_count=" + item_count + ", item=" + item + "]";
	}
	
}
