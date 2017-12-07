/*
 * WritePcm.h
 *
 *  Created on: 2017-11-14
 *      Author: YC2
 */

#ifndef WRITEPCM_H_
#define WRITEPCM_H_

#include "comdef.h"
#include "asoundlib.h"

class WritePcm{
public:
	WritePcm(){
		mPcm = NULL;
		printCount = 0;
	}
	~WritePcm(){}
	int openWritePcm(UINT card, UINT device, UINT channels, UINT rate, UINT bits);
	int writeDataToPcm(const void *data, UINT count);
	void closeWritePcm();
private:
	int isPcmPlayable(UINT card, UINT device, UINT channels,
			UINT rate, UINT bits, UINT period_size,
			UINT period_count);
	int checkPcmParam(struct pcm_params *params, enum pcm_param param, UINT value,
	                 char *param_name, char *param_unit);
private:
	struct pcm *mPcm;
	int printCount;
};


#endif /* WRITEPCM_H_ */
