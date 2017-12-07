package com.car.tboxaudio.utils;

import android.os.SystemProperties;
import android.util.Log;

/**
 * 调试
 * @author YC2
 * @time 2017-11-10 下午2:50:25
 * TODO:
 */
public final class Debug {

	private static boolean DEBUG = false;

	public static void init() {
		DEBUG = "true".equals(SystemProperties.get("tbox_audio_debug", "false"));
	}

	public static void LogAudioIn(String tag, String msg) {
		if (DEBUG && "true".equals(SystemProperties.get("tbox_audio_log_audioin", "false"))) {
			Log.i(tag, msg);
		}
	} 

	public static boolean isSaveAudioIn2File() {
		return /*DEBUG && */"true".equals(SystemProperties.get("tbox_audio_save_audioin", "false"));
	}

}
