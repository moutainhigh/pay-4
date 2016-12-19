<%@ page contentType="image/jpeg" pageEncoding="UTF-8"
	import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"%>
<%
	//在内存中创建图象
	int width = 70, height = 22;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//获取图形上下文
	Graphics g = image.getGraphics();
	//设定背景色
	g.setColor(new Color(0xDCDCDC));
	g.fillRect(0, 0, width, height);
	//画边框
	g.setColor(Color.black);
	g.drawRect(0, 0, width - 1, height - 1);
	//随机产生的认证码(4位数字)
	String rand=request.getParameter("code");
	//将认证码显示到图象中
	g.setColor(Color.blue);
	g.setFont(new Font("Atlantic Inline", Font.PLAIN, 18));
	String str = rand.substring(0, 1);
	g.drawString(str, 4, 17);
	str = rand.substring(1, 2);
	g.drawString(str, 16, 15);
	str = rand.substring(2, 3);
	g.drawString(str, 28, 18);
	str = rand.substring(3, 4);
	g.drawString(str, 40, 15);
	str = rand.substring(4);
	g.drawString(str, 52, 15);
	//随机产生88个干扰点，使图象中的认证码不易被其它程序探测到
	Random random = new Random();
	for (int i = 0; i < 20; i++) {
		g.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
				(int) (Math.random() * 255)));
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		g.drawLine(x, y, x + 5, y + 5);
	}
	//图象生效
	g.dispose();
	//将认证码存入SESSION
	session.setAttribute("validateCode", rand.toString());
	//输出图象到页面
	ImageIO.write(image, "JPEG", response.getOutputStream());
	out.clear();
	out = pageContext.pushBody();
%>