package com.pay.base.common.annotation;

public class AnnotationUsage {
    @AnnotationTest("hello")   
         public void method()   
         {   
             System.out.println("usage of annotation");   
         }   
    
         public static void main(String[] args)   
        {   
            AnnotationUsage au = new AnnotationUsage();        
            au.method();   
        }   


 
}
