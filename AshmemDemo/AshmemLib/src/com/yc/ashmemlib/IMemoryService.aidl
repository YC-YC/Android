package com.yc.ashmemlib;

interface IMemoryService {

   /**
	 * 获取服务端Ashmem的文件描述符
	 * @return
	 */
	 ParcelFileDescriptor getFileDescriptor();
}