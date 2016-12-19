package com.pay.util;
import java.util.Scanner;  
/**
 *  当你输入信用卡号码的时候，有没有担心输错了而造成损失呢？其实可以不必这么担心， 
 * 因为并不是一个随便的信用卡号码都是合法的，它必须通过Luhn算法来验证通过。 
    该校验的过程： 
1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。 
2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，则将其减去9），再求和。 
3、将奇数位总和加上偶数位总和，结果应该可以被10整除。 
例如，卡号是：5432123456788881 
则奇数、偶数位（用红色标出）分布：5432123456788881 
奇数位和=35 
偶数位乘以2（有些要减去9）的结果：1 6 2 6 1 5 7 7，求和=35。 
最后35+35=70 可以被10整除，认定校验通过。 
 
请编写一个程序，从键盘输入卡号，然后判断是否校验通过。通过显示：“成功”，否则显示“失败”。 
比如，用户输入：356827027232780 
程序输出：成功 
 * @author PengJiangbo
 *
 */
public class BankCardCorrectValidUtil {  
    public static int calc(String s){  
        int odd = 0;  
        int even = 0;  
        int t = 0;  
        char[] c = s.toCharArray();  
        if(c.length%2==0){  // 如果位数为偶数个,则第一个数从偶数开始算起  
            for(int i=0;i<c.length;i++){  
                t = c[i]-'0';  
                if(i%2!=0){  
                    odd += t;  
                }else{       // 第一个数加入到偶数  
                    if(t*2>=10){  
                        even += t*2-9;  
                    }else{  
                        even += t*2;  
                    }  
                }  
            }  
        }else{       // 如果位数为奇数个,则第一个数从奇数开始算起  
            for(int i=0;i<c.length;i++){  
                t = c[i]-'0';  
                if(i%2==0){ // 第一个数加入到奇数  
                    odd += t;  
                }else{  
                    if(t*2>=10){  
                        even += t*2-9;  
                    }else{  
                        even += t*2;  
                    }  
                }  
            }  
        }  
        return odd+even;    // 返回奇数位总和加上偶数位总和  
    }  
    public static void main(String[] args){  
        Scanner scan = new Scanner(System.in);  
        System.out.print("输入卡号:");  
        String s = scan.nextLine();  
        if(calc(s)%10==0){  // 结果可以被10整除  
            System.out.println("成功");  
        }else{  
            System.out.println("失败");  
        }  
    }  
} 