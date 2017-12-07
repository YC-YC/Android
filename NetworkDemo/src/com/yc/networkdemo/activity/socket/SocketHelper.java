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
	protected void onReadData(byte[] data, int len) {
		if (mRecDataLen + len > REC_BUFFER_LEN){
			mRecDataLen = 0;
			Log("clear old data");
		}
		System.arraycopy(data, 0, mRecData, mRecDataLen, len);
		mRecDataLen += len;
		Log("onReadData totalLen = " + mRecDataLen + ",data=" + ByteUtil.toString(mRecData, mRecDataLen));
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
		Log("startIndex = " + startIndex);
		if (startIndex >= 0){
			if (mRecDataLen > startIndex + PACKAGE_MIN_LEN){
				int pkgLen = (mRecData[startIndex+3]<<8)+mRecData[startIndex+2];
				int pkgSize = pkgLen + 4;
//				Log("pkgSize = " + pkgSize);
				if (mRecDataLen >= startIndex + pkgSize){
					byte[] pkg = new byte[pkgSize];
					System.arraycopy(mRecData, startIndex, pkg, 0, pkgSize);
//					Log("get one package");
					notifySocketPackage(pkg);
					mRecDataLen -= startIndex + pkgSize;
					System.arraycopy(mRecData, startIndex + pkgSize, mRecData, 0, mRecDataLen);
				}
				if (getStartIndex() >= 0){
					processRecData();
				}
			}
		}
		else{
			//clear data
			mRecDataLen = 0;
		}
	}

	/**
	 * 查找0x5D， 0x8E开头的位置
	 * @return
	 */
	private int getStartIndex() {
		//找出0x5D， 0x8E的位置
		for (int i = 0; i < mRecDataLen-1; i++){
			if (ByteUtil.byte2int(mRecData[i]) == 0x5D && ByteUtil.byte2int(mRecData[i+1]) == 0x8E){
				return i;
			}
		}
		//没有0x5D， 0x8E，找出最后一个字节为0x5D的位置
		if (mRecDataLen > 0 && ByteUtil.byte2int(mRecData[mRecDataLen-1]) == 0x5D){
			return mRecDataLen-1;
		}
		return -1;
	}
	
	private void notifySocketPackage(byte[] data){
		if (mOnSocketPackageListener != null){
			TBoxPackage pkg = new TBoxPackage(data);
			Log("get tbox package = " + pkg.toString());
			mOnSocketPackageListener.onPackage(pkg);
		}
	}
	
	public interface onSocketPackageListener{
		void onPackage(TBoxPackage data);
	}

}
