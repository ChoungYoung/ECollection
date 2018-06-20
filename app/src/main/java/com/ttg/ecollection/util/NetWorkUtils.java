package com.ttg.ecollection.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetWorkUtils {
	private static final int NETERROR = 0;// 无网络
	private static final int CMWAP = 1;
	private static final int CMNET = 2;
	private static final int WIFY = 3;
	private static final int U3GNET = 4;
	private static final int U3GWAP = 5;
	private static final int UNINET = 6;
	private static final int UNIWAP = 7;
	private static final int CTNET = 8;
	private static final int CTWAP = 9;
	private static final int OTHER = 10;

	private static final String[] STRNETMODE = { "error", "cmwap", "cmnet",
			"wify", "3gnet", "3gwap", "uninet", "uniwap", "ctnet", "ctwap",
			"other" };
	
	
	// 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap
	private static int getActiveNet(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager != null) {
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			if (null != networkinfo) {
				String net = networkinfo.getExtraInfo();// 获取网络类型
				if (net != null)// wifi的值为空，这里判断下
				{
					String lownet = net.toLowerCase();
					if (lownet.contains("cmwap")) {
						return CMWAP;
					} else if (lownet.contains("cmnet") ) {
						return CMNET;
					} else if (lownet.contains("3gnet") ) {
						return U3GNET;
					} else if (lownet.contains("3gwap") ) {
						return U3GWAP;
					} else if (lownet.contains("uninet") ) {
						return UNINET;
					} else if (lownet.contains("uniwap") ) {
						return UNIWAP;
					} else if (lownet.contains("ctnet") ) {
						return CTNET;
					} else if (lownet.contains("ctwap") ) {
						return CTWAP;
					} else {
						return OTHER;
					}
				} else {
					return WIFY;
				}
			}
		}

		return NETERROR;
	}

	private static final String wap="wap";
	private static final String net="net";
	private static final String wify="wify";
	public static String getCurNetType(Context context)
	{
		String rt = null;
		switch(getActiveNet(context))
		{
		case WIFY:
			rt = wify;
			break;
		case U3GWAP:
		case UNIWAP:
		case CMWAP:
		case CTWAP:
			rt = wap;
			break;
		case CMNET:
		case UNINET:
		case U3GNET:
		case CTNET:
			rt = net;
			break;
		default:
			String proxyHost = android.net.Proxy.getDefaultHost();
			if (proxyHost != null) {
				rt = wap;
			}
			else
			{
				rt = net;
			}
			break;
		}
		
		
		return rt;
	}
	
	
	// 判断当前网络是否畅通
	public static boolean isNetAvailable(Context context, boolean ifcheck) {
		if(context==null)
			return false;
		if (ifcheck) {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity == null) {

			} else {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							if (null != info[i].getExtraInfo()) {
								// //暂时cmwap联网不支持
								// if(info[i].getExtraInfo().toLowerCase().indexOf("cmwap")>=0)
								// {
								//
								// }
								// else
								return true;
							} else {
								return true;
							}

						}
					}
				}
			}
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 检查网络延时
	 * 
	 * @param ipString
	 */
	public static void checkNetDelay(final String ipString,
			final OnNetStateListner listener) {
		try {
			// ping ip地址
			Process p = Runtime.getRuntime().exec("ping -c 1 -w 5 " + ipString);
			int status = p.waitFor();

			InputStream input = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			String str = buffer.toString();
			if (str.contains("avg")) {
				int i = str.indexOf("/", 20);
				int j = str.indexOf(".", i);
				String delay = str.substring(i + 1, j);
				// delay = delay + "ms";


				String delayTime = delay.split("=")[1].replace(" ", "");

				if (listener != null) {
					if (Integer.parseInt(delayTime) < 5000) {
						listener.onNetStateCheck(0, delayTime);
					} else {
						listener.onNetStateCheck(1, delayTime);
					}
				}

			} else {

				if (listener != null) {
					listener.onNetStateCheck(1, "5000");
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
		}
	}

	public interface OnNetStateListner {
		void onNetStateCheck(int state, String delay);
	}
}
