package com.car.tboxaudio.sender;

import com.incall.proxy.can.CanManager;


/**
 * 命令发送
 * @author YC
 * @time 2017-8-24 下午5:15:58
 * TODO:
 */
public class CanSendCmdImpl implements ICanSendCmd{
	private static CanSendCmdImpl sAvmSendCmdImpl;
	
	private CanSendCmdImpl() {
	}

	public static CanSendCmdImpl getInstance() {
		if (sAvmSendCmdImpl == null) {
			sAvmSendCmdImpl = new CanSendCmdImpl();
		}
		return sAvmSendCmdImpl;
	}
	
	private void sendCanData(int moduleId, int index, int length, int what){
		try {
			CanManager.getInstance().sendCanData(moduleId, index, length, what);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void clearCanData(int moduleId, int index, int length, int what){
		try {
			CanManager.getInstance().setCanBufData(moduleId, index, length, what);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callupBCall() {
		sendCanData(0x2F8, 60, 2, 0x01);
	}

	@Override
	public void hangupBCall() {
		sendCanData(0x2F8, 60, 2, 0x02);
	}
}
