/**
 * 
 */
package com.yc.networkdemo.activity.socket;

/**
 * @author YC2
 * @time 2017-11-23 下午2:36:44
 * TODO:
 */
public class SocketHelper extends ISocket {

	private static final int REC_BUFFER_LEN = 2*1024;
	private static final int PACKAGE_MIN_LEN = 13;
	
	private onSocketPackageListener mOnSocketPackageListener;
	
	private byte[] mRecData = null;
	private int mRecDataLen = 0;
	
	@Override
	public synchronized void openSocket(String host, int port) {
		super.openSocket(host, port);
		mRecData = new byte[REC_BUFFER_LEN];
		mRecDataLen = 0;
	}
	
	@Override
	protected void onReadData(char[] data, int len) {
		byte[] bytes = ByteUtil.getBytes(data, len);
		System.arraycopy(bytes, 0, mRecData, mRecDataLen, len);
		mRecDataLen += len;
		processRecData();
	}

	public void registerOnSocketPackageListener(onSocketPackageListener listener){
		mOnSocketPackageListener = listener;
	}
	
	public void unregisterOnSocketPackageListener(){
		mOnSocketPackageListener = null;
	}
	
	/**
	 * 处理接收到的数据
	 */
	private void processRecData() {
		final int startIndex = getStartIndex();
		if (startIndex >= 0){
			if (mRecDataLen > startIndex + PACKAGE_MIN_LEN){
				int pkgLen = (mRecData[startIndex+2]<<8)+mRecData[startIndex+3];
				int pkgSize = pkgLen + 4;
				if (mRecDataLen >= startIndex + pkgSize){
					byte[] pkg = new byte[pkgSize];
					System.arraycopy(mRecData, startIndex, pkg, 0, pkgSize);
					notifySocketPackage(pkg);
					mRecDataLen -= startIndex + pkgSize;
					System.arraycopy(mRecData, startIndex + pkgSize, mRecData, 0, mRecDataLen);
				}
			}
		}
		else{
			//clear data
			mRecDataLen = 0;
		}
	}

	/**
	 * 查找0x8E， 0x5D开头的位置
	 * @return
	 */
	private int getStartIndex() {
		//找出0x8E， 0x5D的位置
		for (int i = 0; i < mRecDataLen-1; i++){
			if (ByteUtil.byte2int(mRecData[i]) == 0x8E && mRecData[i+1] == 0x5D){
				return i;
			}
		}
		//没有0x8E， 0x5D，找出最后一个字节为0x8E的位置
		if (mRecDataLen > 0 && ByteUtil.byte2int(mRecData[mRecDataLen-1]) == 0x8E){
			return mRecDataLen-1;
		}
		return -1;
	}
	
	private void notifySocketPackage(byte[] data){
		if (mOnSocketPackageListener != null){
			TBoxPackage pkg = new TBoxPackage(data);
			mOnSocketPackageListener.onPackage(pkg);
		}
	}
	
	public interface onSocketPackageListener{
		void onPackage(TBoxPackage data);
	}

}
