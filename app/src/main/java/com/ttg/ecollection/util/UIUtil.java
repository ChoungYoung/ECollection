package com.ttg.ecollection.util;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ttg.ecollection.R;


/**ui显示相关工具类*/
public class UIUtil {
    private static Toast mToast;
    /**弹出toast*/
    public static void showToast(Context context , String str){
    	if(context==null||str==null)
    		return;
    	LayoutInflater inflater = ((Activity) context).getLayoutInflater();  
    	View view = inflater.inflate(R.layout.toast,null);
    	TextView text= view.findViewById(R.id.text);
    	text.setText(str);
        if (null != mToast){
            mToast.cancel();//关闭吐司显示
        }

        mToast = Toast.makeText(context, str,Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(view);


//        mToast.setMargin(UIUtil.dp2px(context, 20), UIUtil.dp2px(context, 20));
        mToast.show();
    }
    
    /**
     * 把两个位图覆盖合成为一个位图，左右拼接
     * @param leftBitmap 
     * @param rightBitmap 
     * @param isBaseMax 是否以宽度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_LR(Bitmap leftBitmap, Bitmap rightBitmap, boolean isBaseMax) {

        if (leftBitmap == null || leftBitmap.isRecycled() 
                || rightBitmap == null || rightBitmap.isRecycled()) {
            return null;
        }
        int height = 0; // 拼接后的高度，按照参数取大或取小
        if (isBaseMax) {
            height = leftBitmap.getHeight() > rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        } else {
            height = leftBitmap.getHeight() < rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        }

        // 缩放之后的bitmap
        Bitmap tempBitmapL = leftBitmap;
        Bitmap tempBitmapR = rightBitmap;

        if (leftBitmap.getHeight() != height) {
            tempBitmapL = Bitmap.createScaledBitmap(leftBitmap, (int)(leftBitmap.getWidth()*1f/leftBitmap.getHeight()*height), height, false);
        } else if (rightBitmap.getHeight() != height) {
            tempBitmapR = Bitmap.createScaledBitmap(rightBitmap, (int)(rightBitmap.getWidth()*1f/rightBitmap.getHeight()*height), height, false);
        }

        // 拼接后的宽度
        int width = tempBitmapL.getWidth() + tempBitmapR.getWidth();

        // 定义输出的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 缩放后两个bitmap需要绘制的参数
        Rect leftRect = new Rect(0, 0, tempBitmapL.getWidth(), tempBitmapL.getHeight());
        Rect rightRect  = new Rect(0, 0, tempBitmapR.getWidth(), tempBitmapR.getHeight());

        // 右边图需要绘制的位置，往右边偏移左边图的宽度，高度是相同的
        Rect rightRectT  = new Rect(tempBitmapL.getWidth(), 0, width, height);

        canvas.drawBitmap(tempBitmapL, leftRect, leftRect, null);
        canvas.drawBitmap(tempBitmapR, rightRect, rightRectT, null);
        return bitmap;
    }
    
    /**
    *
    * @param x 图像的宽度
    * @param y 图像的高度
    * @param image 源图片
    * @param outerRadiusRat 圆角的大小
    * @return 圆角图片
    */
   public static Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
       //根据源文件新建一个darwable对象
       Drawable imageDrawable = new BitmapDrawable(image);

       // 新建一个新的输出图片
       Bitmap output = Bitmap.createBitmap(x, y, Config.ARGB_8888);
       Canvas canvas = new Canvas(output);

       // 新建一个矩形
       RectF outerRect = new RectF(0, 0, x, y);

       // 产生一个红色的圆角矩形
       Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
       paint.setColor(Color.RED);
       canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

       // 将源图片绘制到这个圆角矩形上
       paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
       imageDrawable.setBounds(0, 0, x, y);
       canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
       imageDrawable.draw(canvas);
       canvas.restore();

       return output;
   }
  

    /**返回系统语言类型*/
    public static String getLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage().toLowerCase(Locale.ENGLISH);
        String country = locale.getCountry().toLowerCase(Locale.ENGLISH);
        if ("zh".endsWith(language))
        	if ("cn".equals(country)) {
        		return "zh-ch";
			}
            if ("tw".endsWith(country)) {
				return "zh-tw";
			}
            if ("hk".equals(country)) {
				return "zh-hk";
			}
            if ("mo".equals(country)) {
            	return "zh-mo";
            }
        else if("en".equals(language)){
        	if ("us".equals(country)) {
				return "en-us";
			}
        	if ("uk".equals(country)) {
        		return "en-uk";
			}
        	return "en";
        }else if ("ko".equals(language)) {
			if ("kr".equals(country)) {
				return "ko-kr";
			}
		}else if ("ja".equals(language)) {
			return "ja-jp";
		}
        	
        return language+"-"+country;
    }
    
    /**判断某个界面是否在前台*/
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
	public static String getForeground(Context context, String className) {
		if (context == null || TextUtils.isEmpty(className)) {
			return null;
		}

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
		if (list != null && list.size() > 0) {
			ComponentName cpn = list.get(0).topActivity;
//			if (className.equals(cpn.getClassName())) {
				return cpn.getClassName();
//			}
		}

		return null;
	}
    
	 public static boolean isAppOnForeground(Context context) {
	        // 首先获取activity管理器和当前进程的包名
	        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	        String packageName = context.getPackageName();

	        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
	                .getRunningAppProcesses();
	        if (appProcesses == null)
	            return false;

	       // 遍历当前运行的所有进程，查看本进程是否是前台进程，如果不是就代表当前应用进入了后台，执行相关关闭操作
	        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
	            // The name of the process that this object is associated with.
	            if (appProcess.processName.equals(packageName)
	                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
	                return true;
	            }
	        }

	        return false;
	    }
	
	
    /**dp转px*/
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**sp转px*/
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**px转dp*/
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**px转sp*/
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**获得屏幕高度*/
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**获得屏幕宽度*/
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**获得状态栏的高度*/
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**获取当前屏幕截图，包含状态栏*/
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**获取当前屏幕截图，不包含状态栏*/
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

}
