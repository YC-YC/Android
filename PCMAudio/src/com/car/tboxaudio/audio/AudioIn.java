/**
 * 
 */
package com.car.tboxaudio.audio;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.SystemClock;
import android.util.Log;

import com.car.pcm.PcmJniBC;
import com.car.tboxaudio.data.C;
import com.car.tboxaudio.utils.Debug;
import com.car.tboxaudio.utils.FileUtils;

/**
 * 录音
 * 
 * @author YC2
 * @time 2017-11-10 上午9:53:27 TODO:
 */
public class AudioIn implements Runnable {

	private static final String AUDIO_IN_ORIGINAL = "/mnt/sdcard/tbox/audioIn_original";

	private static final String TAG = "TBox_AudioIn";

	private AudioRecord audioInput = null;
	/** 从录音机获取到的数据 */
	private byte[] mPcmReadBuffer = null;

	private static final int MIN_PCM_BUF_LEN = 2 * 1024;
	/** 请求读 */
	private boolean bReqRead = false;

	public AudioIn() {

	}

	/**
	 * 初始化录音机
	 * 
	 * @param sampleRate
	 *            : inHZ
	 * @param channal
	 *            : AudioFormat.CHANNEL_IN_XX
	 * @param audioFormat
	 *            : AudioFormat.ENCODING_PCM_XX
	 */
	public void initAudioIn(int sampleRate, int channal, int audioFormat) {
		int pcmReadBufferLen = AudioRecord.getMinBufferSize(sampleRate,
				channal, audioFormat);
		Log("getMinBufferSize = " + pcmReadBufferLen);
		if (pcmReadBufferLen < MIN_PCM_BUF_LEN) {
			pcmReadBufferLen = MIN_PCM_BUF_LEN;
		}
		mPcmReadBuffer = new byte[pcmReadBufferLen];
		audioInput = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
				channal, audioFormat, pcmReadBufferLen);
	}

	/**
	 * 开始录音
	 */
	public void startRecord() {
		if (audioInput != null) {
			try {
				audioInput.startRecording();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		bReqRead = true;
		new Thread(this, "tbox_audio_in").start();
	}

	/**
	 * 停止录音
	 */
	public void stopRecord() {
		bReqRead = false;
		try {
			if (null != audioInput) {
				audioInput.stop();
				audioInput.release();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO 获取录音机数据线程
		// int len = AudioJniBC.openPcmOut(0, C.PCM_DEVICE,
		// C.PcmChannals.CHANNEL_STEREO, 41000, C.PcmBits.BIT_16);
		int len = PcmJniBC.openPcmOut(C.PCM_CARD, C.PCM_DEVICE,
				C.PcmChannals.CHANNEL_MONO, 8000, C.PcmBits.BIT_16);
		if (len > 0) {
			// int printCount = 0;
			while (true) {
				if (!bReqRead) {
					Log("thread stop read pcm");
					break;
				}
				if (audioInput == null) {
					Log("audioInput == null");
					continue;
				}
				int readCnt = audioInput.read(mPcmReadBuffer, 0,
						mPcmReadBuffer.length);
				if (readCnt <= 0) {
					Log("read data lenght = 0");
					sleep(2);
					continue;
				}
				PcmJniBC.writeDataToPcmOut(mPcmReadBuffer, readCnt);
				if (Debug.isSaveAudioIn2File()) {
					FileUtils.saveByteToFile(mPcmReadBuffer, readCnt,
							AUDIO_IN_ORIGINAL);
				}
			}
		} else {
			Log("openPcmOut error");
		}
		PcmJniBC.closePcmOut();
	}

	private void Log(String msg) {
		Log.i(TAG, msg);
	}

	private void sleep(long ms) {
		SystemClock.sleep(ms);
	}
}
