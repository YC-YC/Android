/*
 * AudioIn.cpp
 *
 *  Created on: 2017-11-14
 *      Author: YC2
 */

#include "ReadPcm.h"
#include "asoundlib.h"
#include "CPublic.h"

int ReadPcm::initReadPcm(JavaVM *vm, JNIEnv *env, CPCHAR pName) {
	pJavaVm = vm;
	jclass classtmp = env->FindClass(pName);
	if (classtmp == NULL) {
		LOGE("CAudioIn init not find class CAudioIn");
		return 0;
	}
	javaClass = (jclass) env->NewGlobalRef(classtmp);
	env->DeleteLocalRef(classtmp);
	if (javaClass == NULL) {
		LOGE("activityClass is NULL");
		return 0;
	}

	methodwriteDataToJava = env->GetStaticMethodID(javaClass,
			"jniWriteAudioData", "([BI)V");
	if (methodwriteDataToJava == NULL) {
		LOGE("CAudioIn init not find method methodInit");
		return 0;
	}
	return 1;
}

void ReadPcm::deInitReadPcm(JNIEnv *env){
	pJavaVm = NULL;
	if (javaClass != NULL){
		env->DeleteGlobalRef(javaClass);
		javaClass = NULL;
	}
	methodwriteDataToJava = NULL;

}

int ReadPcm::openReadPcm(UINT card, UINT device, UINT channels, UINT rate,
		UINT bits) {
	struct pcm_config config;
	switch (bits) {
	case 32:
		config.format = PCM_FORMAT_S32_LE;
		break;
	case 24:
		config.format = PCM_FORMAT_S24_LE;
		break;
	case 16:
		config.format = PCM_FORMAT_S16_LE;
		break;
	default:
		LOGI("%d bits is not supported.\n", bits);
		return -1;
	}
	config.channels = channels;
	config.rate = rate;
//	config.period_size = 512;
	config.period_size = 128;
	config.period_count = 8;
	config.start_threshold = 0;
	config.stop_threshold = 0;
	config.silence_threshold = 0;
	mPcm = pcm_open(card, device, PCM_IN, &config);
	if (!mPcm || !pcm_is_ready(mPcm)) {
		LOGI("Unable to open PCM device (%s)\n", pcm_get_error(mPcm));
		return 0;
	}
//	LOGI("open pcm period_size = %d, period_count = %d\n", config.period_size, config.period_count);
	DWORD size = pcm_frames_to_bytes(mPcm, pcm_get_buffer_size(mPcm));
	mReadBufLen = size;
	pReadBuf = (char*) malloc(mReadBufLen);
	if (!pReadBuf) {
		LOGI("Unable to allocate %d bytes\n", size);
		free(pReadBuf);
		pcm_close(mPcm);
		mPcm = NULL;
		return 0;
	}
	return size;
}

int ReadPcm::readPcmData() {
	if (mPcm != NULL){
//		DWORD size = pcm_frames_to_bytes(mPcm, pcm_get_buffer_size(mPcm));
		if (!pcm_read(mPcm, pReadBuf, mReadBufLen)){
			writeDataToJava(pReadBuf, mReadBufLen);
			return mReadBufLen;
		}
	}

	return 0;
}

void ReadPcm::stopReadPcm(){
	if (pReadBuf != NULL){
		free(pReadBuf);
		pReadBuf = NULL;
	}
	if (mPcm != NULL){
		pcm_close(mPcm);
		mPcm = NULL;
	}
}

int ReadPcm::writeDataToJava(void *data, UINT count) {
	JNIEnv *myEnv = NULL;
	jint ret = 0;

	int type = CPublic::getEnv(pJavaVm, &myEnv);

	if (myEnv) {
		jbyteArray dataArray = myEnv->NewByteArray(count);
		myEnv->SetByteArrayRegion(dataArray, 0, count, (jbyte*)data);
		myEnv->CallStaticVoidMethod(javaClass, methodwriteDataToJava, dataArray, count);
	}

	CPublic::detachEnv(pJavaVm, type);
	return ret;
}

ReadPcm::~ReadPcm(){

}


