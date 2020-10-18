package model.dao;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class Createlmage {
	private static final int WIDTH = 100;// 设置验证码图片宽度
	private static final int HEIGHT = 30;// 设置验证码图片高度
	private static final int LENGTH = 4;// 设置验证码长度
	private static final int LINECOUNT = 6;// 干扰线的数目
	
	//验证码的字符库，去掉不变识别的o0I1l字符
	private static final String str = "23456789"
			+ "ABCDEFGHJKMNPQRSTUVWXYZ" + "abcdefghjkmnpqrstuvwxyz";
	
	private static Random random = new Random();
	
	//通过随机数取字符库中的字符组合成4位验证码
	public String createCode() {
		String code = "";
		for (int i = 0; i < LENGTH; i++) {
			//nextInt(n)生成一个随机的int值，改值介于[0，n)的区间，也就是0到n之间的随机int值，包含0而不包含n
			char c = str.charAt(random.nextInt(str.length()));
			
			code = code + c;
		}
		return code;
	}
	//获取不同颜色
	public Color getColor() {
		return new Color(random.nextInt(255),random.nextInt(255),
		random.nextInt(255));
	}
	public Font getFont() {
		return new Font("黑体", Font.BOLD, 23);
	}
	public void drawChar(Graphics g, String code) {
		g.setFont(getFont());
		for (int i = 0; i < LENGTH; i++) {
			char c = code.charAt(i);
			g.setColor(getColor());
			g.drawString(c + "", 20 * i + 10, 25);
		}
	}
	//绘制随机线
	public void drawLine(Graphics g) {
		int x = random.nextInt(WIDTH);
		int y = random.nextInt(HEIGHT);
		int x1 = random.nextInt(13);
		int y1 = random.nextInt(15);
		g.setColor(getColor());
		g.drawLine(x, y, x + x1, y + y1);
	}
	//绘制验证码图片
	public BufferedImage CreateImage(String code) {
		//获取画笔
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = image.getGraphics();
		//设置背景颜色并绘制矩形背景
		g.setColor(new Color(200,200,200));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//验证码绘制
		drawChar(g, code);
		//随机线的绘制
		for (int i = 0; i < LINECOUNT; i++) {
			drawLine(g);
		}
		//绘制图片
		g.dispose();
		//返回图片
			return image;
		}
	}




