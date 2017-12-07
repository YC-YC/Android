#include <jni.h>
#include "AudioManager.h"

#ifdef __cplusplus
extern "C" {
#endif

static JavaVM *pJavaVm = NULL;
static AudioManager gAudioManager;

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    initPcmIn
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_car_pcm_PcmJniBC_initPcmIn
  (JNIEnv *env, jclass, jstring className){
	const char *name = env->GetStringUTFChars(className, NULL);
	gAudioManager.initAudioIn(pJavaVm, env, name);
	env->ReleaseStringUTFChars(className, name);
}

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    openPcmIn
 * Signature: (IIIII)I
 */
JNIEXPORT jint JNICALL Java_com_car_pcm_PcmJniBC_openPcmIn
  (JNIEnv *, jclass, jint card, jint device, jint channels, jint rate, jint bits){
	return gAudioManager.openAudioIn(card, device, channels, rate, bits);
}


/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    readPcmIn
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_car_pcm_PcmJniBC_readPcmIn
  (JNIEnv *, jclass){
	return gAudioManager.readAudioIn();
}

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    stopPcmIn
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_pcm_PcmJniBC_stopPcmIn
  (JNIEnv *, jclass){
	gAudioManager.stopAudioIn();
}

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    deInitPcmIn
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_pcm_PcmJniBC_deInitPcmIn
  (JNIEnv *env, jclass){
	gAudioManager.deInitAudioIn(env);
}

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    openPcmOut
 * Signature: (IIIII)I
 */
JNIEXPORT jint JNICALL Java_com_car_pcm_PcmJniBC_openPcmOut
  (JNIEnv *, jclass, jint card, jint device, jint channels, jint rate, jint bits){
	return gAudioManager.openAudioOut(card, device, channels, rate, bits);
}

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    writeDataToPcmOut
 * Signature: ([BI)I
 */
JNIEXPORT jint JNICALL Java_com_car_pcm_PcmJniBC_writeDataToPcmOut
  (JNIEnv *env, jclass, jbyteArray jData, jint count){

	int result = 0;
	jbyte* bytedata = env->GetByteArrayElements(jData, 0);
	jsize  oldsize = env->GetArrayLength(jData);
	BYTE* data = (BYTE*)bytedata;
	result = gAudioManager.writeDataToPcm(data, oldsize);
	if (bytedata != NULL){
		env->ReleaseByteArrayElements(jData, bytedata, 0);
	}
	return result;
}

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    closePcmOut
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_pcm_PcmJniBC_closePcmOut
  (JNIEnv *, jclass){
	gAudioManager.closeAudioOut();
}





jint JNI_OnLoad(JavaVM* vm, void *reserved)
{
	JNIEnv* env = NULL;
	jint result = -1;

	pJavaVm = vm;
	if(vm->GetEnv((void**)&env, JNI_VERSION_1_6) != JNI_OK) {
		LOGE("JNI_OnLoad jni fail");
  		return -1;
  	}

	LOGI("JNI_OnLoad jni ok");
	return JNI_VERSION_1_6;
}

void JNI_OnUnload(JavaVM* vm, void* reserved){
	pJavaVm = NULL;
	LOGI("JNI_OnUnload ok");
}

#ifdef __cplusplus
}
#endif
