/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_car_pcm_PcmJniBC */

#ifndef _Included_com_car_pcm_PcmJniBC
#define _Included_com_car_pcm_PcmJniBC
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    initPcmIn
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_car_pcm_PcmJniBC_initPcmIn
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    openPcmIn
 * Signature: (IIIII)I
 */
JNIEXPORT jint JNICALL Java_com_car_pcm_PcmJniBC_openPcmIn
  (JNIEnv *, jclass, jint, jint, jint, jint, jint);

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    readPcmIn
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_car_pcm_PcmJniBC_readPcmIn
  (JNIEnv *, jclass);

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    stopPcmIn
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_pcm_PcmJniBC_stopPcmIn
  (JNIEnv *, jclass);

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    deInitPcmIn
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_pcm_PcmJniBC_deInitPcmIn
  (JNIEnv *, jclass);

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    openPcmOut
 * Signature: (IIIII)I
 */
JNIEXPORT jint JNICALL Java_com_car_pcm_PcmJniBC_openPcmOut
  (JNIEnv *, jclass, jint, jint, jint, jint, jint);

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    writeDataToPcmOut
 * Signature: ([BI)I
 */
JNIEXPORT jint JNICALL Java_com_car_pcm_PcmJniBC_writeDataToPcmOut
  (JNIEnv *, jclass, jbyteArray, jint);

/*
 * Class:     com_car_pcm_PcmJniBC
 * Method:    closePcmOut
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_pcm_PcmJniBC_closePcmOut
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
