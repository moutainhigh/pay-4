package com.pay.base.common.util.matrixcard;

import java.util.Calendar;
import java.util.Date;

import com.pay.util.RandomUtil;
import com.pay.base.common.helper.matrixcard.MatrixCardCfg;

public class MatrixCardUtil {

	public static int[] convertPosToNumber(String position) {
		int x0 = position.toUpperCase().charAt(0) - 'A' + 1;
		int y0 = Integer.valueOf(position.substring(1));
		int [] returnValue = new int[]{x0, y0};
		return returnValue;
	}
	
	public static Date getBindLastValidDate(Date beginDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(beginDate);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + MatrixCardCfg.BIND_DATE_LINE);
		return c.getTime(); 
	}
	
	public static String[] getRandomCoord(){
		String[] coords = new String[3];
		int length = MatrixCardCfg.ROWS * MatrixCardCfg.COLS;
		int cnt  = 0;
		while(cnt<3){
			
			int pos = RandomUtil.random(1, length);
			char x = (char)((int)'A' + (pos-1)/MatrixCardCfg.COLS);
			int y = (pos-1) % MatrixCardCfg.COLS + 1;
			StringBuffer coord = new StringBuffer();
			coord.append(x).append(y);
			
			boolean beExists = false;
			
			for(int j=0;j<cnt;j++){
				if(coord.toString().equals(coords[j])){
					beExists = true;
				}
			}
			
			if(!beExists){
				coords[cnt] = coord.toString();
				cnt++;
			}
		}
		
		
		return coords;
	}
	
	public static String generateValidateCode() {
		int digit = 6;
        return RandomUtil.random(digit);
	}
	
	public static String createMatrixCardData(){
		
		int[] randLen = {1,2,2,2,2,2,3,3,3,3,3};
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<MatrixCardCfg.COLS*MatrixCardCfg.ROWS; i++){
			if(i != 0){
				sb.append(",");
			}
			int j = RandomUtil.random(0,9);
			switch(randLen[j]){
				case 1:
					sb.append(RandomUtil.random(0,9));
					break;
				case 2:
					sb.append(RandomUtil.random(10,99));
					break;
				default:
					sb.append(RandomUtil.random(100,999));
			}
						             
		}
		return sb.toString();
		
	}
}
