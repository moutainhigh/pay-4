/**
 * 
 */
package com.pay.wechat.model;

import java.util.Arrays;

/**
 * 菜单
 * @author PengJiangbo
 *
 */
public class Menu {

	private Button[] button ;

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}

	@Override
	public String toString() {
		return "Menu [button=" + Arrays.toString(button) + "]";
	}

}
