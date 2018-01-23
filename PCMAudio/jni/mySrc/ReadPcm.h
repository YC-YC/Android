/*
 * ReadPcm.h
 *
 *  Created on: 2017-11-14
 *      Author: YC2
 */

#ifndef READPCM_H_
#define READPCM_H_
#include <jni.h>
#include "comdef.h"

class ReadPcm{
public:
	ReadPcm():mPcm(NULL),pReadBuf(NULL),mReadBufLen(0){}
	int initReadPcm(JavaVM *vm, JNIEnv *env, CPCHAR pName);
	void deInitReadPcm(JNIEnv *env);
	int openReadPcm(UINT card, UINT device, UINT channels, UINT rate, UINT bits);
	int readPcmData();
	void stopReadPcm();
	~ReadPcm();
private:
	int writeDataToJava(void *data, UINT count);
private:
	JavaVM  *pJavaVm;
	jclass javaClass;
	jmethodID methodwriteDataToJava;
	struct pcm *mPcm;
	char* pReadBuf;
	int mReadBufLen;
};


#endif /* READPCM_H_ */
