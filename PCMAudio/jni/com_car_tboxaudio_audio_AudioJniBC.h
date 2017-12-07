/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_car_tboxaudio_audio_AudioJniBC */

#ifndef _Included_com_car_tboxaudio_audio_AudioJniBC
#define _Included_com_car_tboxaudio_audio_AudioJniBC
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_car_tboxaudio_audio_AudioJniBC
 * Method:    initPcmIn
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_tboxaudio_audio_AudioJniBC_initPcmIn
  (JNIEnv *, jclass);

/*
 * Class:     com_car_tboxaudio_audio_AudioJniBC
 * Method:    openPcmIn
 * Signature: (IIIII)I
 */
JNIEXPORT jint JNICALL Java_com_car_tboxaudio_audio_AudioJniBC_openPcmIn
  (JNIEnv *, jclass, jint, jint, jint, jint, jint);

/*
 * Class:     com_car_tboxaudio_audio_AudioJniBC
 * Method:    readPcmIn
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_car_tboxaudio_audio_AudioJniBC_readPcmIn
  (JNIEnv *, jclass);

/*
 * Class:     com_car_tboxaudio_audio_AudioJniBC
 * Method:    stopPcmIn
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_tboxaudio_audio_AudioJniBC_stopPcmIn
  (JNIEnv *, jclass);

/*
 * Class:     com_car_tboxaudio_audio_AudioJniBC
 * Method:    deInitPcmIn
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_tboxaudio_audio_AudioJniBC_deInitPcmIn
  (JNIEnv *, jclass);

/*
 * Class:     com_car_tboxaudio_audio_AudioJniBC
 * Method:    openPcmOut
 * Signature: (IIIII)I
 */
JNIEXPORT jint JNICALL Java_com_car_tboxaudio_audio_AudioJniBC_openPcmOut
  (JNIEnv *, jclass, jint, jint, jint, jint, jint);

/*
 * Class:     com_car_tboxaudio_audio_AudioJniBC
 * Method:    writeDataToPcmOut
 * Signature: ([BI)I
 */
JNIEXPORT jint JNICALL Java_com_car_tboxaudio_audio_AudioJniBC_writeDataToPcmOut
  (JNIEnv *, jclass, jbyteArray, jint);

/*
 * Class:     com_car_tboxaudio_audio_AudioJniBC
 * Method:    closePcmOut
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_car_tboxaudio_audio_AudioJniBC_closePcmOut
  (JNIEnv *, jclass);

/*
 * Class:     com_car_tboxaudio_audio_AudioJniBC
 * Method:    jniWriteAudioData
 * Signature: ([BI)V
 */
JNIEXPORT void JNICALL Java_com_car_tboxaudio_audio_AudioJniBC_jniWriteAudioData
  (JNIEnv *, jclass, jbyteArray, jint);

#ifdef __cplusplus
}
#endif
#endif