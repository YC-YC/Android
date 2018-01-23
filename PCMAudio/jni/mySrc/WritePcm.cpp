/*
 * WritePcm.cpp
 *
 *  Created on: 2017-11-14
 *      Author: YC2
 */
#include "WritePcm.h"


#ifdef __cplusplus
extern "C" {
#endif

int WritePcm::openWritePcm(UINT card, UINT device, UINT channels, UINT rate,
		UINT bits) {
	struct pcm_config config;
	char *buffer;
	int size;
	int num_read;

	config.channels = channels;
	config.rate = rate;
	config.period_size = 128;
	config.period_count = 8;
	if (bits == 32)
		config.format = PCM_FORMAT_S32_LE;
	else if (bits == 16)
		config.format = PCM_FORMAT_S16_LE;
	config.start_threshold = 0;
	config.stop_threshold = 0;
	config.silence_threshold = 0;

	if (!isPcmPlayable(card, device, channels, rate, bits, config.period_size,
			config.period_count)) {
		return -1;
	}

	mPcm = pcm_open(card, device, PCM_OUT, &config);
	if (!mPcm || !pcm_is_ready(mPcm)) {
		LOGE("Unable to open PCM card %u,device %u (%s)\n", card, device, pcm_get_error(mPcm));
		return -1;
	}

	return pcm_frames_to_bytes(mPcm, pcm_get_buffer_size(mPcm));
}

int WritePcm::writeDataToPcm(const void *data, UINT count) {
	if (printCount > 100){
		printCount = 0;
		LOGI("writeDataToPcm len = %d", count);
	}
	else{
		printCount++;
	}
	 if (pcm_write(mPcm, data, count)) {
		 LOGE("Error playing sample\n");
	 }
	return 0;
}

void WritePcm::closeWritePcm() {
	if (mPcm != NULL){
		pcm_close(mPcm);
		mPcm = NULL;
	}
}

int WritePcm::isPcmPlayable(UINT card, UINT device, UINT channels, UINT rate,
		UINT bits, UINT period_size, UINT period_count) {
	struct pcm_params *params;
	int can_play;

	params = pcm_params_get(card, device, PCM_OUT);
	if (params == NULL) {
		LOGE("Unable to open PCM card %u, device %u.\n", card, device);
		return 0;
	}

	can_play = checkPcmParam(params, PCM_PARAM_RATE, rate, "Sample rate", "Hz");
	can_play &= checkPcmParam(params, PCM_PARAM_CHANNELS, channels, "Sample",
			" channels");
	can_play &= checkPcmParam(params, PCM_PARAM_SAMPLE_BITS, bits, "Bitrate",
			" bits");
	can_play &= checkPcmParam(params, PCM_PARAM_PERIOD_SIZE, period_size,
			"Period size", "Hz");
	can_play &= checkPcmParam(params, PCM_PARAM_PERIODS, period_count,
			"Period count", "Hz");

	pcm_params_free(params);

	return can_play;
}
int WritePcm::checkPcmParam(struct pcm_params *params, enum pcm_param param,
		UINT value, char *param_name, char *param_unit) {
	unsigned int min;
	unsigned int max;
	int is_within_bounds = 1;

	min = pcm_params_get_min(params, param);
	if (value < min) {
		LOGE("%s is %u%s, device only supports >= %u%s\n", param_name, value, param_unit, min, param_unit);
		is_within_bounds = 0;
	}

	max = pcm_params_get_max(params, param);
	if (value > max) {
		LOGE("%s is %u%s, device only supports <= %u%s\n", param_name, value, param_unit, max, param_unit);
		is_within_bounds = 0;
	}

	return is_within_bounds;
}
#ifdef __cplusplus
}
#endif
