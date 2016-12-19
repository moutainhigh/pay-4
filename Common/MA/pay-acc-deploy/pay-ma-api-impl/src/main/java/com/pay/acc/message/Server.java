package com.pay.acc.message;

/**
 * 目标服务器配置
 * @author jerry
 *
 */
public class Server {
	
	private String ip;
	
	private int port;
	
	private String url;
	
	public Server(){
		
	}
	
	public Server(String ip,int port){
		this.ip = ip;
		this.port = port;
	}
	
	public Server(String url){
		this.url = url;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
