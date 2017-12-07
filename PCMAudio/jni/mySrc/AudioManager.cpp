/*
 * AudioManager.cpp
 *
 *  Created on: 2017-11-14
 *      Author: YC2
 */
#include "AudioManager.h"

void AudioManager::initAudioIn(JavaVM *vm, JNIEnv *env, CPCHAR pName) {
	LOGI("initAudioIn");
	pReadPcm = new ReadPcm();
	pReadPcm->initReadPcm(vm, env, pName);
}

void AudioManager::deInitAudioIn(JNIEnv *env) {
	LOGI("deInitAudioIn");
	pReadPcm->deInitReadPcm(env);
	delete pReadPcm;
	pReadPcm = NULL;
}

int AudioManager::openAudioIn(UINT card, UINT device, UINT channels, UINT rate,
		UINT bits) {
	LOGI("openAudioIn");
	if (pReadPcm != NULL) {
		return pReadPcm->openReadPcm(card, device, channels, rate, bits);
	}
	LOGE("pReadPcm is null");
	return 0;
}

int AudioManager::readAudioIn() {
//	LOGI("startAudioIn");
	if (pReadPcm != NULL){
		return pReadPcm->readPcmData();
	}
	LOGE("pReadPcm is null");
	return 0;
}

void AudioManager::stopAudioIn() {
	LOGI("stopAudioIn");
	if (pReadPcm != NULL){
		pReadPcm->stopReadPcm();
	}
	else{
		LOGE("pReadPcm is null");
	}
}

int AudioManager::openAudioOut(UINT card, UINT device, UINT channels, UINT rate, UINT bits){
	LOGI("openAudioOut");
	if (pWritePcm == NULL){
		pWritePcm = new WritePcm();
	}
	return pWritePcm->openWritePcm(card, device, channels, rate, bits);
}

int AudioManager::writeDataToPcm(const void *data, DWORD count){
	if (pWritePcm != NULL){
		return pWritePcm->writeDataToPcm(data, count);
	}
	return -1;
}

void AudioManager::closeAudioOut(){
	LOGI("closeAudioOut");
	if (pWritePcm != NULL){
		pWritePcm->closeWritePcm();
		delete pWritePcm;
		pWritePcm = NULL;
	}
}
