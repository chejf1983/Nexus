/* DO NOT EDIT THIS FILE - it is machine generated */
#include "drv_sp_jni_sp_dev_drv.h"
#include "SpectraArsenal.h"

#pragma comment(lib, "SpectraArsenal.lib") 

#define DLL_SUCCESS 0
#define DLL_FAILE -1

/* Header for class drv_sp_jni_sp_dev_drv */
/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_OpenSpectrometers
 * Signature: ()I 搜索USB光谱仪
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1OpenSpectrometers
(JNIEnv * env, jclass){
	return SA_OpenSpectrometers();
}

/* Header for class drv_sp_jni_sp_dev_drv */
/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_OpenSpectrometers
 * Signature: ()I 搜索串口光谱仪
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1OpenSpectrometersForSerial
(JNIEnv * env, jclass){
	return SA_OpenSpectrometersForSerial();
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_CloseSpectrometers
 * Signature: ()I 关闭连接
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1CloseSpectrometers
(JNIEnv * env, jclass){
	SA_CloseSpectrometers();
	return DLL_SUCCESS;
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetAPIVersion
 * Signature: ()Ljava/lang/String 获取驱动版本
 */
JNIEXPORT jstring JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetAPIVersion
  (JNIEnv * env, jclass){
	char * api_version = SA_GetAPIVersion();
	return env->NewStringUTF(api_version);
}


/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetSpectrometersName
 * Signature: (I)Ljava/lang/String 获取光谱仪名称
 */
JNIEXPORT jstring JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetSpectrometersName
(JNIEnv * env, jclass, jint spectrometerIndex){
	char * sp_name = SA_GetSpectrometersName(spectrometerIndex);
	return env->NewStringUTF(sp_name);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetMaxIntegrationTime
 * Signature: (I)I 获取最大积分时间
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetMaxIntegrationTime
(JNIEnv * env, jclass, jint spectrometerIndex){
	return SA_GetMaxIntegrationTime(spectrometerIndex);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetMinIntegrationTime
 * Signature: (I)I 获取最小积分时间
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetMinIntegrationTime
(JNIEnv * env, jclass, jint spectrometerIndex){
	return SA_GetMinIntegrationTime(spectrometerIndex);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetSoftwareVersion
 * Signature: (I)Ljava/lang/String; 获取软件版本信息
 */
JNIEXPORT jstring JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetSoftwareVersion
(JNIEnv * env, jclass, jint spectrometerIndex){
	char * sp_name = SA_GetSoftwareVersion(spectrometerIndex);
	return env->NewStringUTF(sp_name);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetHardwareVersion
 * Signature: (I)Ljava/lang/String; 获取硬件版本信息
 */
JNIEXPORT jstring JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetHardwareVersion
(JNIEnv * env, jclass, jint spectrometerIndex){
	char sp_name = SA_GetHardwareVersion(spectrometerIndex);
	return env->NewStringUTF(&sp_name);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetSerialNumber
 * Signature: (I)Ljava/lang/String; 获取序列号
 */
JNIEXPORT jstring JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetSerialNumber
  (JNIEnv * env, jclass, jint spectrometerIndex){
	char * sp_name = SA_GetSerialNumber(spectrometerIndex);
	return env->NewStringUTF(sp_name);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetManufacturingDate
 * Signature: (I)Ljava/lang/String; 获取生产日期
 */
JNIEXPORT jstring JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetManufacturingDate
  (JNIEnv * env, jclass, jint spectrometerIndex){
	char * sp_name = SA_GetManufacturingDate(spectrometerIndex);
	return env->NewStringUTF(sp_name);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetSpectrometerPixelsNumber
 * Signature: (I)I 获取光谱仪采集点个数
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetSpectrometerPixelsNumber
  (JNIEnv * env, jclass, jint spectrometerIndex){
	return SA_GetSpectrometerPixelsNumber(spectrometerIndex);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetWavelengthCalibrationCoefficients
 * Signature: (I[D)I 获取波长效准系数
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetWavelengthCalibrationCoefficients
(JNIEnv * env, jclass, jint spectrometerIndex, jdoubleArray recBuffer){
	jdouble *tmp= (env)->GetDoubleArrayElements(recBuffer,0); 
	int ret = SA_GetWavelengthCalibrationCoefficients(spectrometerIndex,(double *)tmp);
	int rclen = DLL_FAILE;
	if(ret == SA_API_SUCCESS)
	{	
		rclen = 4;
		(env)->SetDoubleArrayRegion(recBuffer,0, rclen, tmp); 
	}
	(env)-> ReleaseDoubleArrayElements(recBuffer, tmp, 0);
	return rclen;
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_SetWavelengthCalibrationCoefficients
 * Signature: (I[D)I 设置波长效准系数
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1SetWavelengthCalibrationCoefficients
(JNIEnv * env, jclass, jint spectrometerIndex, jdoubleArray sendBuffer){
	jdouble *tmp= (env)->GetDoubleArrayElements(sendBuffer,0); 
	int ret = SA_SetWavelengthCalibrationCoefficients(spectrometerIndex,(double *)tmp);
	(env)-> ReleaseDoubleArrayElements(sendBuffer, tmp, 0);
	return ret;
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetNonlinearCalibrationPixel
 * Signature: (I[FI[F[F)I
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetNonlinearCalibrationPixel
  (JNIEnv * env, jclass, jint, jfloatArray, jint, jfloatArray, jfloatArray);

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_SetNonlinearCalibrationPixel
 * Signature: (IFI[F[F)I
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1SetNonlinearCalibrationPixel
  (JNIEnv * env, jclass, jint, jfloat, jint, jfloatArray, jfloatArray);

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetSpectum
 * Signature: (I[DI)I 软件触发获取光谱数据
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetSpectum
(JNIEnv * env, jclass, jint spectrometerIndex, jdoubleArray recBuffer, jint recBuffer_len){
	jdouble *tmp= (env)->GetDoubleArrayElements(recBuffer,0); 
	int rclen = DLL_FAILE;
	int ret = SA_GetSpectum(spectrometerIndex,(double *)tmp, &rclen);
	if(ret == SA_API_SUCCESS)
	{	
		(env)->SetDoubleArrayRegion(recBuffer,0, rclen, tmp); 
	}else{
		rclen = DLL_FAILE;
	}
	(env)-> ReleaseDoubleArrayElements(recBuffer, tmp, 0);
	return rclen;
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetSpectumHWTrigger
 * Signature: (I[DIII)I 硬件触发获取光谱数据
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetSpectumHWTrigger
(JNIEnv * env, jclass, jint spectrometerIndex, jdoubleArray recBuffer, jint recBuffer_len, jint timeout, jint triggermode){
	jdouble *tmp= (env)->GetDoubleArrayElements(recBuffer,0); 
	int rclen = DLL_FAILE;
	int ret = SA_GetSpectumHWTrigger(spectrometerIndex,(double *)tmp, &rclen, timeout, (TRIGGER_MODE)triggermode);
	if(ret == SA_API_SUCCESS)
	{	
		(env)->SetDoubleArrayRegion(recBuffer,0, rclen, tmp); 
	}else{
		rclen = DLL_FAILE;
	}
	(env)-> ReleaseDoubleArrayElements(recBuffer, tmp, 0);
	return rclen;
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_GetWavelength
 * Signature: (I[DI)I 获取波长数组
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1GetWavelength
(JNIEnv * env, jclass, jint spectrometerIndex, jdoubleArray recBuffer, jint recBuffer_len){
	jdouble *tmp= (env)->GetDoubleArrayElements(recBuffer,0); 
	int rclen = DLL_FAILE;
	int ret = SA_GetWavelength(spectrometerIndex,(double *)tmp, &rclen);
	if(ret == SA_API_SUCCESS)
	{	
		(env)->SetDoubleArrayRegion(recBuffer,0, rclen, tmp); 
	}else{
		rclen = DLL_FAILE;
	}
	(env)-> ReleaseDoubleArrayElements(recBuffer, tmp, 0);
	return rclen;
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_NonlinearCalibration
 * Signature: (I[D[DI)I 非线性校准
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1NonlinearCalibration
(JNIEnv * env, jclass, jint spectrometerIndex, jdoubleArray input_Buffer, jdoubleArray output_Buffer, jint buffer_len){
	jdouble *tmp_in= (env)->GetDoubleArrayElements(input_Buffer,0); 
	jdouble *tmp_out= (env)->GetDoubleArrayElements(output_Buffer,0); 

	int ret = SA_NonlinearCalibration(spectrometerIndex,(double *)tmp_in, (double *)tmp_out, buffer_len);
	if(ret == SA_API_SUCCESS)
	{	
		(env)->SetDoubleArrayRegion(output_Buffer,0, buffer_len, tmp_out); 
	}

	(env)-> ReleaseDoubleArrayElements(input_Buffer, tmp_in, 0);
	(env)-> ReleaseDoubleArrayElements(output_Buffer, tmp_out, 0);
	return ret;
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_SetIntegrationTime
 * Signature: (II)I设置积分时间
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1SetIntegrationTime
(JNIEnv * env, jclass, jint spectrometerIndex, jint time_us){
	return SA_SetIntegrationTime(spectrometerIndex, time_us);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_SetAverageTimes
 * Signature: (II)I设置平均次数
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1SetAverageTimes
  (JNIEnv * env, jclass, jint spectrometerIndex, jint avr){
	return SA_SetAverageTimes(spectrometerIndex, avr);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_SetSpectumTriggerMode
 * Signature: (II)I 设置触发模式
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1SetSpectumTriggerMode
  (JNIEnv * env, jclass, jint spectrometerIndex, jint mode){
	return SA_SetSpectumTriggerMode(spectrometerIndex, (TRIGGER_MODE)mode);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_SetXenonFlashPara
 * Signature: (IIIII)I 设置脉冲氙灯参数
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1SetXenonFlashPara
  (JNIEnv * env, jclass, jint spectrometerIndex, jint iPulseWidth, jint IntervalTime, jint iDelayTime, jint PulseNumber){
	return SA_SetXenonFlashPara(spectrometerIndex, iPulseWidth, IntervalTime, iDelayTime, PulseNumber);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_XenonFlashDisable
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1XenonFlashDisable
  (JNIEnv * env, jclass, jint spectrometerIndex){
	return SA_XenonFlashDisable(spectrometerIndex);
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_XenonFlashEnable
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1XenonFlashEnable
  (JNIEnv * env, jclass, jint spectrometerIndex){
	return SA_XenonFlashEnable(spectrometerIndex);
}

SPECTRAARSENAL_API int SA_WriteMemory(int spectrometerIndex, int MEM, int Address, int length, unsigned char * UserData);
SPECTRAARSENAL_API int SA_ReadMemory(int spectrometerIndex, int MEM, int Address, int length, unsigned char * UserData);
/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_WriteUserMemory
 * Signature: (III[B)I
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1WriteUserMemory
(JNIEnv * env, jclass, jint spectrometerIndex, jint MEM, jint address, jint length, jbyteArray recBuffer){
	jbyte *tmp= (env)->GetByteArrayElements(recBuffer,0); 
	int ret = SA_WriteMemory(spectrometerIndex, MEM, address, length, (unsigned char *)tmp);
	(env)-> ReleaseByteArrayElements(recBuffer, tmp, 0);
	return ret;
}

/*
 * Class:     drv_sp_jni_sp_dev_drv
 * Method:    SA_ReadUserMemory
 * Signature: (III[B)I
 */
JNIEXPORT jint JNICALL Java_drv_sp_jni_sp_1dev_1drv_SA_1ReadUserMemory
(JNIEnv * env, jclass, jint spectrometerIndex, jint MEM, jint address, jint length, jbyteArray recBuffer){
	jbyte *tmp= (env)->GetByteArrayElements(recBuffer,0); 
	int ret = SA_ReadMemory(spectrometerIndex, MEM, address, length, (unsigned char *)tmp);
	if(ret == SA_API_SUCCESS)
	{	
		(env)->SetByteArrayRegion(recBuffer,0, length, tmp); 
	}
	(env)-> ReleaseByteArrayElements(recBuffer, tmp, 0);
	return ret;
}
