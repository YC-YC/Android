/**
 * 
 */
package com.yc.networkdemo.activity.socket;

import java.util.Arrays;

/**
 * @author YC2
 * @time 2017-11-23 下午4:18:18
 * TODO:
 */
public class TBoxPackage {
	
	private static final int PACKAGE_MIN_LEN = 13;

	private int headerIdentifier = 0x8E5D;
	private int headerLength;
	private int headerCount;
	private boolean headerAskFlag;
	private int headerMsgType;
	private int headerErrorCode = 0;
	
	private int bodyVersion;
	private int bodyEncryption;
	private int bodyErrorCode;
	private int bodyTid;
	private int bodySid;
	private int bodyDataLen;
	private int bodyCmd;
	private byte[] bodyData;
	
	private int tail;
	
	
	
	/**
	 * @param headerCount
	 * @param headerAskFlag
	 * @param headerMsgType
	 * @param bodyCmd
	 * @param bodyData
	 */
	public TBoxPackage(int headerCount, boolean headerAskFlag,
			int headerMsgType, int bodyCmd, byte[] bodyData) {
		this(0x8E5D, headerCount, headerAskFlag, headerMsgType, 0, 0, 0, 0, 0x60, 0x36, bodyCmd, bodyData);
	}

	/**
	 * @param headerIdentifier
	 * @param headerCount
	 * @param headerAskFlag
	 * @param headerMsgType
	 * @param headerErrorCode
	 * @param bodyVersion
	 * @param bodyEncryption
	 * @param bodyErrorCode
	 * @param bodyTid
	 * @param bodySid
	 * @param bodyCmd
	 * @param bodyData
	 */
	public TBoxPackage(int headerIdentifier, int headerCount,
			boolean headerAskFlag, int headerMsgType, int headerErrorCode,
			int bodyVersion, int bodyEncryption, int bodyErrorCode,
			int bodyTid, int bodySid, int bodyCmd, byte[] bodyData) {
		super();
		this.headerIdentifier = headerIdentifier;
		this.headerCount = headerCount;
		this.headerAskFlag = headerAskFlag;
		this.headerMsgType = headerMsgType;
		this.headerErrorCode = headerErrorCode;
		this.bodyVersion = bodyVersion;
		this.bodyEncryption = bodyEncryption;
		this.bodyErrorCode = bodyErrorCode;
		this.bodyTid = bodyTid;
		this.bodySid = bodySid;
		this.bodyCmd = bodyCmd;
		this.bodyData = bodyData;
		
		this.bodyDataLen = (bodyData == null)? 0: bodyData.length;
		this.headerLength = PACKAGE_MIN_LEN+bodyDataLen;
	}

	TBoxPackage(byte[] data){
		headerIdentifier = (byte2int(data[0])<<8) + byte2int(data[1]);
		headerLength = (byte2int(data[2])<<8) + byte2int(data[3]);
		headerCount = byte2int(data[4]);
		headerAskFlag = ((data[5]&0x01) > 0) ? true:false;
		headerMsgType = (byte2int(data[5])>>1)&0x07;
		headerErrorCode = (byte2int(data[5])>>4)&0x0F;
		
		bodyVersion = byte2int(data[6]);
		bodyEncryption = byte2int(data[7])&0x0F;
		bodyErrorCode = (byte2int(data[7])>>4)&0x0F;
		bodyTid = byte2int(data[8]);
		bodySid = byte2int(data[9]);
		bodyDataLen = byte2int(data[10]);
		bodyCmd = byte2int(data[11]);
		if (bodyDataLen > 0){
			bodyData = new byte[bodyDataLen];
			System.arraycopy(data, 12, bodyData, 0, bodyDataLen);
			tail = byte2int(data[12+bodyDataLen]);
		}
		else{
			tail = byte2int(data[12]);
		}
	}
	
	public byte[] toSocketData(){
		int totalLen = PACKAGE_MIN_LEN+bodyDataLen;
		byte[] data = new byte[totalLen];
		data[0] = (byte) (headerIdentifier>>8);
		data[1] = (byte) headerIdentifier;
		data[2] = (byte) (headerLength>>8);
		data[3] = (byte) headerLength;
		data[4] = (byte) headerCount;
		byte tmp = 0;
		tmp |= headerAskFlag?0x01:0x00;
		tmp |= headerMsgType<<1;
		tmp |= headerErrorCode<<4;
		data[5] = tmp;
		
		data[6] = (byte) bodyVersion;
		tmp = 0;
		tmp |= bodyEncryption;
		tmp |= bodyErrorCode<<4;
		data[7] = tmp;
		data[8] = (byte) bodyTid;
		data[9] = (byte) bodySid;
		data[10] = (byte) bodyDataLen;
		data[11] = (byte) bodyCmd;
		if (bodyDataLen > 0){
			System.arraycopy(bodyData, 0, data, 12, bodyDataLen);
		}
		data[totalLen -1] = getXor(data, 8, totalLen - 2);
		return data;
	}
	
	private byte getXor(byte[] data, int startIndex, int endIndex){
		byte result = data[startIndex];
		for (int i = startIndex+1; i <= endIndex; i++){
			result = (byte) (result ^ data[i]);
		}
		return result;
	}
	
	private int byte2int(byte data){
		return ByteUtil.byte2int(data);
	}
	

	/**
	 * @return the headerIdentifier
	 */
	public int getHeaderIdentifier() {
		return headerIdentifier;
	}

	/**
	 * @return the headerLength
	 */
	public int getHeaderLength() {
		return headerLength;
	}

	/**
	 * @return the headerCount
	 */
	public int getHeaderCount() {
		return headerCount;
	}

	/**
	 * @return the headerAskFlag
	 */
	public boolean isHeaderAskFlag() {
		return headerAskFlag;
	}

	/**
	 * @return the headerMsgType
	 */
	public int getHeaderMsgType() {
		return headerMsgType;
	}

	/**
	 * @return the headerErrorCode
	 */
	public int getHeaderErrorCode() {
		return headerErrorCode;
	}

	/**
	 * @return the bodyVersion
	 */
	public int getBodyVersion() {
		return bodyVersion;
	}

	/**
	 * @return the bodyEncryption
	 */
	public int getBodyEncryption() {
		return bodyEncryption;
	}

	/**
	 * @return the bodyErrorCode
	 */
	public int getBodyErrorCode() {
		return bodyErrorCode;
	}

	/**
	 * @return the bodyTid
	 */
	public int getBodyTid() {
		return bodyTid;
	}

	/**
	 * @return the bodySid
	 */
	public int getBodySid() {
		return bodySid;
	}

	/**
	 * @return the bodyDataLen
	 */
	public int getBodyDataLen() {
		return bodyDataLen;
	}

	/**
	 * @return the bodyCmd
	 */
	public int getBodyCmd() {
		return bodyCmd;
	}

	/**
	 * @return the bodyData
	 */
	public byte[] getBodyData() {
		return bodyData;
	}

	/**
	 * @return the tail
	 */
	public int getTail() {
		return tail;
	}

	@Override
	public String toString() {
		return "TBoxPackage [headerIdentifier=" + headerIdentifier
				+ ", headerLength=" + headerLength + ", headerCount="
				+ headerCount + ", headerAskFlag=" + headerAskFlag
				+ ", headerMsgType=" + headerMsgType + ", headerErrorCode="
				+ headerErrorCode + ", bodyVersion=" + bodyVersion
				+ ", bodyEncryption=" + bodyEncryption + ", bodyErrorCode="
				+ bodyErrorCode + ", bodyTid=" + bodyTid + ", bodySid="
				+ bodySid + ", bodyDataLen=" + bodyDataLen + ", bodyCmd="
				+ bodyCmd + ", bodyData=" + Arrays.toString(bodyData)
				+ ", tail=" + tail + "]";
	}
	
}
