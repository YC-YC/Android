/**
 * 
 */
package com.example.testbackview;

import android.graphics.Point;
import android.graphics.PointF;


/**
 * 几何工具类(也可灵活使用pathMeasure来获取)
 * @author YC
 * @time 2017-8-31 下午3:12:28
 * TODO:
 */
public class GeometricUtil {
	
	/**
	 * 计算某点绕中心点旋转一个角度后的坐标
	 * @param old
	 * @param center
	 * @param angle
	 * @return
	 */
	public static Point calcNewPoint(Point old, Point center, float angle) {  
	    // calc arc   
	    float l = (float) ((angle * Math.PI) / 180);  
	      
	    //sin/cos value  
	    float cosv = (float) Math.cos(l);  
	    float sinv = (float) Math.sin(l);  
	  
	    // calc new point  
	    float newX = (float) ((old.x - center.x) * cosv - (old.y - center.y) * sinv + center.x);  
	    float newY = (float) ((old.x - center.x) * sinv + (old.y - center.y) * cosv + center.y);  
	    return new Point((int) newX, (int) newY);  
	}  

	
	/**
	 * 计算圆周上指定距离所扫过的角度
	 * @param disc 直线距离
	 * @param radio
	 * @return
	 */
	public static double calcDistAngle(double dist, double radio){
		return arc2Angle(2*Math.asin(dist/(2*radio)));
	}
	
	/**
	 * 计算圆周上指定弧线长度所扫过的角度
	 * @param arcDist 弧线距离
	 * @param radio
	 * @return
	 */
	public static double calcArcDistAngle(double arcDist, double radio){
		return arcDist*180.0f/(Math.PI*radio);
	}
	
	/**
	 * 弧度转角度
	 * @param arc
	 * @return
	 */
	public static double arc2Angle(double arc){
		return arc*180/Math.PI;
	}
	
	/**
	 * 角度转弧度
	 * @param angle
	 * @return
	 */
	public static double angle2Arc(double angle){
		return (Math.PI*angle/180);
	}
	
	/**
	 * 获取圆上某个角度上的点坐标
	 * @param center
	 * @param radio
	 * @param angle:逆时针方向
	 * @return
	 */
	public static Point getPointOnCircle(Point center, float radio, float angle){
		int x = (int) (radio*Math.cos(Math.PI*angle/180)) + center.x;
		int y = (int) (radio*Math.sin(Math.PI*angle/180)) + center.y;
		return new Point(x, y);
	}
	
	/**
	 * 已知一个点，求方向角度上指定长度的点坐标
	 * @param start 起始坐标
	 * @param dist 距离
	 * @param angle 方向角度
	 * @param bToUp true为向y正方向值，false为y负方向值
	 * @return
	 */
	public static PointF getPointOnDistAndAngle(PointF start, float dist, float angle, boolean bToUp){
		double x = 0, y = 0;
		double tan = Math.tan(Math.PI*angle/180);
		if (bToUp){
			if (tan > 0){
				x = (dist/Math.sqrt(tan*tan+1) + start.x);
			}
			else{
				x = (-dist/Math.sqrt(tan*tan+1) + start.x);
			}
		}
		else{
			if (tan > 0){
				x = (-dist/Math.sqrt(tan*tan+1) + start.x);
			}
			else{
				x = (dist/Math.sqrt(tan*tan+1) + start.x);
			}
		}
		y = ((x-start.x)*tan + start.y);
		return new PointF((float)x, (float)y);
	}
	
	/**
	 * 计算Bezier曲线上的点坐标	
	 * bezier = (1-t)(1-t)*start + 2*t*(1-t)*ctrl + t*t*end
	 * @param start
	 * @param end
	 * @param ctrl
	 * @param t
	 * @return
	 */
	public static Point getBezierPoint(Point start, Point end, Point ctrl, float t) {
        int x = (int) ((1-t)*(1-t)*start.x + 2*t*(1-t)*ctrl.x + t*t*end.x);
        int y = (int) ((1-t)*(1-t)*start.y + 2*t*(1-t)*ctrl.y + t*t*end.y);
        return new Point(x, y);
    }
	
}
