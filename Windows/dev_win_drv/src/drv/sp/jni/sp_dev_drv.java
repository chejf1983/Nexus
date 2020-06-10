/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drv.sp.jni;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 *
 * @author chejf
 */
public class sp_dev_drv {

    // <editor-fold defaultstate="collapsed" desc="静态接口"> 
    // <editor-fold defaultstate="collapsed" desc="驱动初始化"> 
    private static boolean IsInit = false;

    public static boolean IsInitLib() {
        return IsInit;
    }

    public static void InitLib(boolean clean) throws Exception {
        if (!IsInit) {
            System.load(CreateDLLTempFile("libusb0_x86.dll", clean));
            System.load(CreateDLLTempFile("USB_Driver.dll", clean));
            System.load(CreateDLLTempFile("SpectraArsenal.dll", clean));
            System.load(CreateDLLTempFile("sp_dev_dll.dll", clean));
            IsInit = true;
        }
    }

    private static String CreateDLLTempFile(String Filename, boolean clean) throws Exception {
        //System.out.println(System.getProperty("user.dir") + "\\jre\\bin");
        File tmp = new File(System.getProperty("user.dir") + "\\jre\\bin");
        if (tmp.exists()) {
            tmp = new File(System.getProperty("user.dir") + "\\jre\\bin\\" + Filename);
        } else {
            tmp = new File(System.getProperty("user.dir") + "\\" + Filename);
        }

        if (clean) {
            if (tmp.exists()) {
                tmp.delete();
            }
            CreateDLLTempFile(Filename, false);
        } else if (!tmp.exists()) {
            InputStream in = sp_dev_drv.class.getResourceAsStream("/Resource/" + Filename);
            FileOutputStream out = new FileOutputStream(tmp);

            int i;
            byte[] buf = new byte[1024];
            while ((i = in.read(buf)) != -1) {
                out.write(buf, 0, i);
            }

            in.close();
            out.close();
            System.out.println("create file:" + Filename);
        }

        // return tmp.getAbsoluteFile().getAbsolutePath();
        return tmp.getCanonicalPath();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="搜索光谱仪"> 
    //返回找到几个设备。
    public static int OpenSpectrometers() {
        //驱动里返回-1没有找到设备，返回0找到1个设备，以此类推
        return SA_OpenSpectrometers() + 1;
    }

    static native int SA_OpenSpectrometers();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="搜索光谱仪串口"> 
    //返回找到几个设备。
    public static int OpenSpectrometersForSerial() {
        //驱动里返回-1没有找到设备，返回0找到1个设备，以此类推
        return SA_OpenSpectrometersForSerial() + 1;
    }

    static native int SA_OpenSpectrometersForSerial();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="关闭光谱仪"> 
    static native int SA_CloseSpectrometers();

    public static int CloseSpectrometers() {
        return SA_CloseSpectrometers();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取驱动版本"> 
    static native String SA_GetAPIVersion();

    public static String GetAPIVersion() {
        return SA_GetAPIVersion();
    }
    // </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="光谱仪操作">     
    public sp_dev_drv(int spectrometerIndex) {
        this.spectrometerIndex = spectrometerIndex;
    }

    // <editor-fold defaultstate="collapsed" desc="光谱仪序列号，从0开始"> 
    //spectrometerIndex=设备序号从0开始
    private int spectrometerIndex;

    public int SPdevIndex() {
        return this.spectrometerIndex;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="光谱仪信息获取设置操作接口"> 
    // <editor-fold defaultstate="collapsed" desc="获取光谱仪名称"> 
    static native String SA_GetSpectrometersName(int spectrometerIndex);

    public String GetSpectrometersName() {
        return SA_GetSpectrometersName(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取最大积分时间"> 
    static native int SA_GetMaxIntegrationTime(int spectrometerIndex);

    public int GetMaxIntegrationTime() {
        return SA_GetMaxIntegrationTime(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取最小积分时间"> 
    static native int SA_GetMinIntegrationTime(int spectrometerIndex);

    public int GetMinIntegrationTime() {
        return SA_GetMinIntegrationTime(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取软件版本"> 
    static native String SA_GetSoftwareVersion(int spectrometerIndex);

    public String GetSoftwareVersion() {
        return SA_GetSoftwareVersion(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取硬件版本"> 
    static native String SA_GetHardwareVersion(int spectrometerIndex);

    public String GetHardwareVersion() {
        return SA_GetHardwareVersion(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取光谱仪序列号"> 
    static native String SA_GetSerialNumber(int spectrometerIndex);

    public String GetSerialNumber() {
        return SA_GetSerialNumber(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取生产日期"> 
    static native String SA_GetManufacturingDate(int spectrometerIndex);

    public String GetManufacturingDate() {
        return SA_GetManufacturingDate(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取返回的数据个数"> 
    static native int SA_GetSpectrometerPixelsNumber(int spectrometerIndex);

    public int GetSpectrometerPixelsNumber() {
        return SA_GetSpectrometerPixelsNumber(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取波长定标系数"> 
    static native int SA_GetWavelengthCalibrationCoefficients(int spectrometerIndex, double[] WavelengthCalibration);

    public int GetWavelengthCalibrationCoefficients(double[] WavelengthCalibration) {
        return SA_GetWavelengthCalibrationCoefficients(this.spectrometerIndex, WavelengthCalibration);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="设置波长定标系数"> 
    static native int SA_SetWavelengthCalibrationCoefficients(int spectrometerIndex, double[] WavelengthCalibration);

    public int SetWavelengthCalibrationCoefficients(double[] WavelengthCalibration) {
        return SA_SetWavelengthCalibrationCoefficients(this.spectrometerIndex, WavelengthCalibration);
    }
    // </editor-fold>

    //获取非线性系数
    static native int SA_GetNonlinearCalibrationPixel(int spectrometerIndex, float[] pfNonlinearCalibPixelOrWL, int piNonlinearCalibCoNumber, float[] pfNonlinearCalibAD, float[] pfNonlinearCalibCo);

    //设置非线性系数
    static native int SA_SetNonlinearCalibrationPixel(int spectrometerIndex, float fPixelValue, int ParaNum, float[] pfADValue, float[] pfCalibrationCo);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="光谱仪采集"> 
    // <editor-fold defaultstate="collapsed" desc="获取光谱仪数据"> 
    //获取光谱仪数据， 返回读取长度, pdSpectumData 数据缓存, pSpectumNumber 缓存区大小
    static native int SA_GetSpectum(int spectrometerIndex, double[] pdSpectumData, int pSpectumNumber);

    public int GetSpectum(double[] pdSpectumData) {
        return SA_GetSpectum(this.spectrometerIndex, pdSpectumData, pdSpectumData.length);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="硬件触发操作接口"> 
    /* 硬件触发操作接口 返回读取长度, pdSpectumData 数据缓存, pSpectumNumber 缓存区大小 iTimeOut 超时时间 int TriggerMode 硬件触发模式*/
    static native int SA_GetSpectumHWTrigger(int spectrometerIndex, double[] pdSpectumData, int pSpectumNumber, int iTimeOut, int TriggerMode);

    public int GetSpectumHWTrigger(double[] pdSpectumData, int iTimeOut, int TriggerMode) {
        return SA_GetSpectumHWTrigger(this.spectrometerIndex, pdSpectumData, pdSpectumData.length, iTimeOut, TriggerMode);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取波长参数"> 
    //获取波长参数, 返回读取长度,pdWavelengthData 数据缓存, pSpectumNumber 缓存区大小
    static native int SA_GetWavelength(int spectrometerIndex, double[] pdWavelengthData, int pSpectumNumber);

    public int GetWavelength(double[] pdWavelengthData) {
        return SA_GetWavelength(this.spectrometerIndex, pdWavelengthData, pdWavelengthData.length);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="非线性效准"> 
    //非线性效准
    static native int SA_NonlinearCalibration(int spectrometerIndex, double[] pbSpectum, double[] pbNewSpectum, int SpectumNumber);

    public int NonlinearCalibration(double[] pbSpectum, double[] pbNewSpectum) {
        return SA_NonlinearCalibration(this.spectrometerIndex, pbSpectum, pbNewSpectum, pbNewSpectum.length);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="设置积分时间"> 
    //设置积分时间 us
    static native int SA_SetIntegrationTime(int spectrometerIndex, int usec);

    public int SetIntegrationTime(int usec) {
        return SA_SetIntegrationTime(this.spectrometerIndex, usec);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="设置平均次数"> 
    //设置平均次数
    static native int SA_SetAverageTimes(int spectrometerIndex, int AverageTimes);

    public int SetAverageTimes(int AverageTimes) {
        return SA_SetAverageTimes(this.spectrometerIndex, AverageTimes);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="设置触发模式"> 
    //设置软件触发模式，默认软件同步触发模式 
    static native int SA_SetSpectumTriggerMode(int spectrometerIndex, int TriggerMode);

    public int SetSpectumTriggerMode(int TriggerMode) {
        return SA_SetSpectumTriggerMode(this.spectrometerIndex, TriggerMode);
    }
    // </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="脉冲氙灯相关操作接口"> 
    // <editor-fold defaultstate="collapsed" desc="脉冲氙灯参数设置"> 
    /* 脉冲氙灯相关操作接口 */
    static native int SA_SetXenonFlashPara(int spectrometerIndex, int iPulseWidth, int IntervalTime, int iDelayTime, int PulseNumber);

    public int SetXenonFlashPara(int iPulseWidth, int IntervalTime, int iDelayTime, int PulseNumber) {
        return SA_SetXenonFlashPara(this.spectrometerIndex, iPulseWidth, IntervalTime, iDelayTime, PulseNumber);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="脉冲氙灯参数读取"> 
    static native int SA_GetXenonFlashPara(int spectrometerIndex, int[] rets);

    public int GetXenonFlashPara(int[] rets) {
        return SA_GetXenonFlashPara(this.spectrometerIndex, rets);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="关闭氙灯"> 
    static native int SA_XenonFlashDisable(int spectrometerIndex);

    public int XenonFlashDisable() {
        return SA_XenonFlashDisable(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="打开氙灯"> 
    static native int SA_XenonFlashEnable(int spectrometerIndex);

    public int XenonFlashEnable() {
        return SA_XenonFlashEnable(this.spectrometerIndex);
    }
    // </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="用户存储操作接口"> 
    /* 用户存储操作接口 */
    static native int SA_WriteUserMemory(int spectrometerIndex, int MEM, int Address, int length, byte[] UserData);

    public enum MEMTYPE{
        EIA,
	NVPA,
	VPA,
	MDA,
	SRA
    }
    
    private int convertmem(MEMTYPE type){
        switch(type){
            case EIA:
                return 0;
            case NVPA:
                return 1;
            case VPA:
                return 2;
            case MDA:
                return 3;
            case SRA:
                return 4;
            default:
                return 1;            
        }
    }
    
    public int WriteUserMemory(MEMTYPE mem, int Address, byte[] UserData) {
        return SA_WriteUserMemory(this.spectrometerIndex, convertmem(mem), Address, UserData.length, UserData);
    }

    static native int SA_ReadUserMemory(int spectrometerIndex, int MEM, int Address,  int length, byte[] UserData);
    
    public int ReadUserMemory(MEMTYPE mem, int Address, byte[] UserData) {
        return SA_ReadUserMemory(this.spectrometerIndex, convertmem(mem), Address, UserData.length, UserData);
    }
    // </editor-fold>
    // </editor-fold>
}
