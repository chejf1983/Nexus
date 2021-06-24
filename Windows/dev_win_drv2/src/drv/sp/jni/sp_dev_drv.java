/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drv.sp.jni;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 *
 * @author chejf
 */
public class sp_dev_drv {

    private static JSpectraAsenal _lib;
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
            _lib = JSpectraAsenal.INSTANCE;
            IsInit = true;
        }
    }

    private static String CreateDLLTempFile(String Filename, boolean clean) throws Exception {
        //System.out.println(System.getProperty("user.dir") + "\\jre\\bin");
//        File tmp = new File(System.getProperty("user.dir") + "\\jre\\bin");
//        if (tmp.exists()) {
//            tmp = new File(System.getProperty("user.dir") + "\\jre\\bin\\" + Filename);
//        } else {
//            tmp = new File(System.getProperty("user.dir") + "\\" + Filename);
//        }

        File tmp = new File(System.getProperty("user.dir") + "\\jre\\bin\\" + Filename);
        if (tmp.exists()) {
            tmp.delete();
        }

        tmp = new File(System.getProperty("user.dir") + "\\" + Filename);
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
        return _lib.SA_OpenSpectrometers() + 1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="搜索光谱仪串口"> 
    //返回找到几个设备。
    public static int OpenSpectrometersForSerial() {
        //驱动里返回-1没有找到设备，返回0找到1个设备，以此类推
        return _lib.SA_OpenSpectrometersForSerial() + 1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="关闭光谱仪"> 
    public static int CloseSpectrometers() {
        _lib.SA_CloseSpectrometers();
        return 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取驱动版本"> 
    public static String GetAPIVersion() {
        return _lib.SA_GetAPIVersion();
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
    public String GetSpectrometersName() {
        return _lib.SA_GetSpectrometersName(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取最大积分时间"> 
    public int GetMaxIntegrationTime() {
        return _lib.SA_GetMaxIntegrationTime(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取最小积分时间"> 
    public int GetMinIntegrationTime() {
        return _lib.SA_GetMinIntegrationTime(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取软件版本"> 
    public String GetSoftwareVersion() {
        return _lib.SA_GetSoftwareVersion(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取硬件版本"> 
    public String GetHardwareVersion() {
        byte value = _lib.SA_GetHardwareVersion(this.spectrometerIndex);

        return String.valueOf((char) value);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取光谱仪序列号"> 
    public String GetSerialNumber() {
        return _lib.SA_GetSerialNumber(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取生产日期"> 
    public String GetManufacturingDate() {
        return _lib.SA_GetManufacturingDate(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取返回的数据个数"> 
    public int GetSpectrometerPixelsNumber() {
        return _lib.SA_GetSpectrometerPixelsNumber(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取波长定标系数"> 
    public int GetWavelengthCalibrationCoefficients(double[] WavelengthCalibration) {
        return _lib.SA_GetWavelengthCalibrationCoefficients(this.spectrometerIndex, WavelengthCalibration);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="设置波长定标系数"> 
    public int SetWavelengthCalibrationCoefficients(double[] WavelengthCalibration) {
        return _lib.SA_SetWavelengthCalibrationCoefficients(this.spectrometerIndex, WavelengthCalibration);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="获取非线性系数"> 
    //获取非线性系数
    public int GetNonlinearCalibrationPixel(float[] pfNonlinearCalibAD, float[] pfNonlinearCalibCo) {
        float[] pfNonlinearCalibPixelOrWL = new float[8];
//        IntByReference len = new IntByReference(Native.getNativeSize(Integer.class));
        Pointer len = new Memory(Native.getNativeSize(Integer.class));
        len.setInt(0, -1);
        int ret = _lib.SA_GetNonlinearCalibrationPixel(spectrometerIndex, pfNonlinearCalibPixelOrWL, len, pfNonlinearCalibAD, pfNonlinearCalibCo);
        return ret < 0 ? ret : len.getInt(0);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="设置非线性系数"> 
    public int SetNonlinearCalibrationPixel(float fPixelValue, int ParaNum, float[] pfADValue, float[] pfCalibrationCo) {
        return _lib.SA_SetNonlinearCalibrationPixel(this.spectrometerIndex, fPixelValue, ParaNum, pfADValue, pfCalibrationCo);
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="光谱仪采集"> 
    // <editor-fold defaultstate="collapsed" desc="获取光谱仪数据"> 
    public int GetSpectum(double[] pdSpectumData) {
        Pointer len = new Memory(Native.getNativeSize(Integer.class));
        len.setInt(0, -1);
        int ret = _lib.SA_GetSpectum(this.spectrometerIndex, pdSpectumData, len);
        return ret < 0 ? ret : len.getInt(0);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="硬件触发操作接口"> 
//    public int GetSpectumHWTrigger(double[] pdSpectumData, int iTimeOut, int TriggerMode) {
//        Pointer len = new Memory(Native.getNativeSize(Integer.class));
//        len.setInt(0, -1);
//        int ret = _lib.SA_GetSpectumHWTrigger(this.spectrometerIndex, pdSpectumData, len, iTimeOut, TriggerMode);
//        return ret < 0 ? ret : len.getInt(0);
//    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="获取波长参数"> 
    public int GetWavelength(double[] pdWavelengthData) {
        Pointer len = new Memory(Native.getNativeSize(Integer.class));
        len.setInt(0, -1);
//        IntByReference len = new IntByReference();
//        len.setValue(0);
        int ret = _lib.SA_GetWavelength(this.spectrometerIndex, pdWavelengthData, len);
        return ret < 0 ? ret : len.getInt(0);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="非线性效准"> 
    public int NonlinearCalibration(double[] pbSpectum, double[] pbNewSpectum) {
        return _lib.SA_NonlinearCalibration(this.spectrometerIndex, pbSpectum, pbNewSpectum, pbNewSpectum.length);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="设置积分时间"> 
    public int SetIntegrationTime(int usec) {
        return _lib.SA_SetIntegrationTime(this.spectrometerIndex, usec);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="设置平均次数"> 
    public int SetAverageTimes(int AverageTimes) {
        return _lib.SA_SetAverageTimes(this.spectrometerIndex, AverageTimes);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="平滑窗口"> 
    public int SetWindow(int window) {
        //1-10;
        return _lib.SA_SetWindow(window);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="设置触发模式"> 
    public int SetSpectumTriggerMode(int TriggerMode) {
        return _lib.SA_SetSpectumTriggerMode(this.spectrometerIndex, TriggerMode);
    }
    // </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="脉冲氙灯相关操作接口"> 
    // <editor-fold defaultstate="collapsed" desc="脉冲氙灯参数设置"> 
    public int SetXenonFlashPara(int iPulseWidth, int IntervalTime, int iDelayTime, int PulseNumber) {
        return _lib.SA_SetXenonFlashPara(this.spectrometerIndex, iPulseWidth, IntervalTime, iDelayTime, PulseNumber);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="脉冲氙灯参数读取"> 
    public int GetXenonFlashPara(int[] rets) {
        if (rets.length < 4) {
            return -1;
        }
        Pointer[] ps = new Pointer[4];
        for (int i = 0; i < ps.length; i++) {
            ps[i] = new Memory(Native.getNativeSize(Integer.class));
        }
        int ret = _lib.SA_GetXenonFlashPara(this.spectrometerIndex, ps[0], ps[1], ps[2], ps[3]);
        for (int i = 0; i < ps.length; i++) {
            rets[i] = ps[i].getInt(0);
        }
        return ret;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="关闭氙灯"> 
    public int XenonFlashDisable() {
        return _lib.SA_XenonFlashDisable(this.spectrometerIndex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="打开氙灯"> 
    public int XenonFlashEnable() {
        return _lib.SA_XenonFlashEnable(this.spectrometerIndex);
    }
    // </editor-fold>
    // </editor-fold>

    //    // <editor-fold defaultstate="collapsed" desc="用户存储操作接口"> 
//    /* 用户存储操作接口 */
//    static native int SA_WriteUserMemory(int spectrometerIndex, int MEM, int Address, int length, byte[] UserData);
//
//    public enum MEMTYPE{
//        EIA,
//	NVPA,
//	VPA,
//	MDA,
//	SRA
//    }
//    
//    private int convertmem(MEMTYPE type){
//        switch(type){
//            case EIA:
//                return 0;
//            case NVPA:
//                return 1;
//            case VPA:
//                return 2;
//            case MDA:
//                return 3;
//            case SRA:
//                return 4;
//            default:
//                return 1;            
//        }
//    }
//    
//    public int WriteUserMemory(MEMTYPE mem, int Address, byte[] UserData) {
//        return SA_WriteUserMemory(this.spectrometerIndex, convertmem(mem), Address, UserData.length, UserData);
//    }
//
//    static native int SA_ReadUserMemory(int spectrometerIndex, int MEM, int Address,  int length, byte[] UserData);
//    
//    public int ReadUserMemory(MEMTYPE mem, int Address, byte[] UserData) {
//        return SA_ReadUserMemory(this.spectrometerIndex, convertmem(mem), Address, UserData.length, UserData);
//    }
//    // </editor-fold>
    // </editor-fold>
}
