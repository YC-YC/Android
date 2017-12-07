LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

#链接日志模块
LOCAL_LDLIBS    := -lm -llog -landroid

#LOCAL_CFLASG += -pie -fPIE
#LOCAL_LDFLAGS += -pie -fPIE
LOCAL_C_INCLUDES := $(LOCAL_PATH)/include \
					$(LOCAL_PATH)/pcm/sound \
					$(LOCAL_PATH)/pcm/tinyalsa \
					$(LOCAL_PATH)/mySrc	

LOCAL_MODULE    := PcmAudio

APP_SRCS :=  pcm/*.c \
			mySrc/*.cpp \
			PcmAudio.cpp 
			
LOCAL_SRC_FILES := $(foreach F, $(APP_SRCS), $(addprefix $(dir $(F)),$(notdir $(wildcard $(LOCAL_PATH)/$(F)))))

include $(BUILD_SHARED_LIBRARY)
