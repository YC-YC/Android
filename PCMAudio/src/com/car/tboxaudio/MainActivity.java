package com.car.tboxaudio;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.car.tboxaudio.audio.AudioIn;
import com.car.tboxaudio.audio.AudioOut;
import com.car.tboxaudio.sender.CanSendCmdImpl;
import com.zhonghong.voicectrl.so.VoiceCtrlLib;

public class MainActivity extends Activity {
	
	private AudioIn mAudioIn = null;
	private AudioOut mAudioOut = null;
	VoiceCtrlLib voiceCtrlLib = new VoiceCtrlLib();
	private int adcVal = 47;
	private int workmode = VoiceCtrlLib.WORK_MODE_HOST;
	private int ctrlmode = VoiceCtrlLib.FUNC_MODE_PHONE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAudioIn = new AudioIn();
		mAudioOut = new AudioOut();
		voiceCtrlLib.initVoiceCtrl();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_quit) {
			moveTaskToBack(false);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.audio_in_init:
//			mAudioIn.initAudioIn(/*8000*/44100
//					, AudioFormat.CHANNEL_IN_STEREO/*AudioFormat.CHANNEL_IN_MONO*/
//					, AudioFormat.ENCODING_PCM_16BIT);
			mAudioIn.initAudioIn(8000
					, AudioFormat.CHANNEL_IN_MONO
					, AudioFormat.ENCODING_PCM_16BIT);
			break;
		case R.id.audio_in_start:
			mAudioIn.startRecord();
			break;
		case R.id.audio_in_stop:
			mAudioIn.stopRecord();
			break;
		case R.id.audio_out_init:
			mAudioOut.initAudioOut(AudioManager.STREAM_MUSIC
					, 8000
					, AudioFormat.CHANNEL_OUT_MONO
					, AudioFormat.ENCODING_PCM_16BIT);
			break;
		case R.id.audio_out_start:
			mAudioOut.startPlay();
			break;
		case R.id.audio_out_stop:
			mAudioOut.stopPlay();
			break;
		case R.id.callup_bcall:
			CanSendCmdImpl.getInstance().callupBCall();
			break;
		case R.id.hangup_bcall:
			CanSendCmdImpl.getInstance().hangupBCall();
			break;
		case R.id.on_phone:
			voiceCtrlLib.setVoiceWorkMode(workmode);
			voiceCtrlLib.setVoiceCtrlMode(ctrlmode);
			voiceCtrlLib.setVoiceCtrlDACValue(adcVal);
			break;
		case R.id.bt_workmode:
			workmode++;
			if (workmode > 3){
				workmode = 1;
			}
			voiceCtrlLib.setVoiceWorkMode(workmode);
			break;
		case R.id.bt_ctrlmode:
			ctrlmode++;
			if (ctrlmode > 3){
				ctrlmode = 0;
			}
			voiceCtrlLib.setVoiceCtrlMode(ctrlmode);
			break;
		case R.id.bt_inc_dac:
			adcVal++;
			if (adcVal > 63){
				adcVal = 63;
			}
			voiceCtrlLib.setVoiceCtrlDACValue(adcVal);
			break;
		case R.id.bt_dec_dac:
			adcVal--;
			if (adcVal < 0){
				adcVal = 0;
			}
			voiceCtrlLib.setVoiceCtrlDACValue(adcVal);
			break;
		default:
			break;
		}
	}
}
