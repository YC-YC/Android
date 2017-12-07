/*
 * AudioManager.h
 *
 *  Created on: 2017-11-14
 *      Author: YC2
 */

#ifndef AUDIOMANAGER_H_
#define AUDIOMANAGER_H_

#include <jni.h>
#include "ReadPcm.h"
#include "WritePcm.h"

class AudioManager {
public:
	void initAudioIn(JavaVM *vm, JNIEnv *env, CPCHAR pName);
	void deInitAudioIn(JNIEnv *env);
	int openAudioIn(UINT card, UINT device, UINT channels, UINT rate, UINT bits);
	int readAudioIn();
	void stopAudioIn();

	int openAudioOut(UINT card, UINT device, UINT channels, UINT rate, UINT bits);
	int writeDataToPcm(const void *data, UINT count);
	void closeAudioOut();
private:
	ReadPcm* pReadPcm;
	WritePcm* pWritePcm;

};

#endif /* AUDIOMANAGER_H_ */
