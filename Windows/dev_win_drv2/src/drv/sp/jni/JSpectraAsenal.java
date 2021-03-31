/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drv.sp.jni;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

/**
 *
 * @author chejf
 */
public interface JSpectraAsenal extends StdCallLibrary {

    JSpectraAsenal INSTANCE = (JSpectraAsenal) Native.load("SpectraArsenal", JSpectraAsenal.class);

    int SA_OpenSpectrometers();

    int SA_OpenSpectrometersForSerial();

    void SA_CloseSpectrometers();

    String SA_GetAPIVersion();

    // <editor-fold defaultstate="collapsed" desc="设备信息信息"> 
    String SA_GetSoftwareVersion(int spectrometerIndex);
    byte SA_GetHardwareVersion(int spectrometerIndex);
    String SA_GetSerialNumber(int spectrometerIndex);
    String SA_GetManufacturingDate(int spectrometerIndex);
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="基本设置"> 
    String SA_GetSpectrometersName(int spectrometerIndex);

    int SA_GetMaxIntegrationTime(int spectrometerIndex);

    int SA_GetMinIntegrationTime(int spectrometerIndex);
    
    int SA_GetSpectrometerPixelsNumber(int spectrometerIndex);
    
    int SA_GetWavelengthCalibrationCoefficients(int spectrometerIndex, double[] WavelengthCalibration);
    
    int SA_SetWavelengthCalibrationCoefficients(int spectrometerIndex, double[] WavelengthCalibration);
    
    int SA_GetNonlinearCalibrationPixel(int spectrometerIndex, float[] pfNonlinearCalibPixelOrWL, Pointer piNonlinearCalibCoNumber, float[] pfNonlinearCalibAD, float[] pfNonlinearCalibCo);
    
    int SA_SetNonlinearCalibrationPixel(int spectrometerIndex, float fPixelValue, int ParaNum, float[] pfADValue, float[] pfCalibrationCo);
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="光谱仪采集"> 
    //获取光谱仪数据， 返回读取长度, pdSpectumData 数据缓存, pSpectumNumber 缓存区大小
    int SA_GetSpectum(int spectrometerIndex, double[] pdSpectumData, Pointer pSpectumNumber);
    /* 硬件触发操作接口 返回读取长度, pdSpectumData 数据缓存, pSpectumNumber 缓存区大小 iTimeOut 超时时间 int TriggerMode 硬件触发模式*/
//    int SA_GetSpectumHWTrigger(int spectrometerIndex, double[] pdSpectumData, Pointer pSpectumNumber, int iTimeOut, int TriggerMode);
    //获取波长参数, 返回读取长度,pdWavelengthData 数据缓存, pSpectumNumber 缓存区大小
    int SA_GetWavelength(int spectrometerIndex, double[] pdWavelengthData, Pointer pSpectumNumber);
    //非线性效准
    int SA_NonlinearCalibration(int spectrometerIndex, double[] pbSpectum, double[] pbNewSpectum, int SpectumNumber);
    //设置积分时间 us
    int SA_SetIntegrationTime(int spectrometerIndex, int usec);
    //设置平均次数
    int SA_SetAverageTimes(int spectrometerIndex, int AverageTimes);
    //设置软件触发模式，默认软件同步触发模式 
    int SA_SetSpectumTriggerMode(int spectrometerIndex, int TriggerMode);
    // </editor-fold>   

    // <editor-fold defaultstate="collapsed" desc="脉冲氙灯相关操作接口"> 
    /* 脉冲氙灯相关操作接口 */
    int SA_SetXenonFlashPara(int spectrometerIndex, int iPulseWidth, int IntervalTime, int iDelayTime, int PulseNumber);
    int SA_GetXenonFlashPara(int spectrometerIndex, Pointer iPulseWidth, Pointer IntervalTime, Pointer iDelayTime, Pointer PulseNumber);
    int SA_XenonFlashDisable(int spectrometerIndex);
    int SA_XenonFlashEnable(int spectrometerIndex);
    // </editor-fold>
}
