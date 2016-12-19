/**
 * 
 */
package com.pay.wechat.message.resp;

/**
 * 响应音乐消息
 * @author PengJiangbo
 *
 */
public class MusicMessage extends BaseMessage {

	//音乐
	private Music music ;

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
	
	
}
