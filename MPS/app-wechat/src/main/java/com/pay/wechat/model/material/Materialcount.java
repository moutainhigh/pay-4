/**
 * 
 */
package com.pay.wechat.model.material;

/**
 * 素材总数
 * @author davis at 2016-07-04
 *
 */
public class Materialcount {

	//语音总数量
	private int voiceCount ;
	//视频总数量
	private int videoCount ;
	//图片总数量
	private int imageCount ;
	//图文总数量
	private int newsCount ;
	
	public int getVoiceCount() {
		return voiceCount;
	}

	public void setVoiceCount(int voiceCount) {
		this.voiceCount = voiceCount;
	}

	public int getVideoCount() {
		return videoCount;
	}

	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}

	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}

	public int getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(int newsCount) {
		this.newsCount = newsCount;
	}

	@Override
	public String toString() {
		return "Materialcount [voiceCount=" + voiceCount + ", videoCount=" + videoCount + ", imageCount=" + imageCount + ", newsCount=" + newsCount + "]";
	}
	
}
