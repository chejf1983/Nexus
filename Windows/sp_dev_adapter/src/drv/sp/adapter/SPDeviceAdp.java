/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drv.sp.adapter;

import sps.drv.filter.SPDataWindowAverage;
import drv.sp.jni.sp_dev_drv;
import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;
import sps.control.manager.ISpDevice;
import sps.dev.data.SSCollectConfig;
import sps.dev.data.SSCollectPar;
import sps.dev.data.SSConfigItem;
import sps.dev.data.SSLinearParameter;
import sps.dev.data.SSPDevInfo;
import sps.dev.data.SSWaveCaculatePar;
import sps.dev.data.SSpectralDataPacket;
import sps.dev.data.SSpectralPar;
import sps.drv.filter.BattwoseFilter;
import sps.drv.filter.Theleastsquaremethod;

/**
 *
 * @author chejf
 */
public class SPDeviceAdp implements ISpDevice {

    private final sp_dev_drv dev_drv;

    public SPDeviceAdp(sp_dev_drv dev_drv) {
        this.dev_drv = dev_drv;
    }

    // <editor-fold defaultstate="collapsed" desc="设备控制接口"> 
    private final ReentrantLock dev_lock = new ReentrantLock();
    private boolean is_canceled = false;

    @Override
    public void Open() throws Exception {
        dev_lock.lock();
    }

    @Override
    public boolean IsOpened() {
        return dev_lock.isLocked();
    }

    @Override
    public void close() throws Exception {
        if (dev_lock.isLocked()) {
            is_canceled = false;
            dev_lock.unlock();
        }
    }

    @Override
    public boolean IsCmdCancled() {
        return is_canceled;
    }

    @Override
    public void Cancel() {
        is_canceled = true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="采集控制"> 
    private final BattwoseFilter batfilter = new BattwoseFilter();

    @Override
    public SSpectralDataPacket CollectData() throws Exception {
        //读取波长数据
        double[] data = new double[this.sp_par.nodeNumber];
        double[] wave = new double[wave_array.length];
        if (this.dev_drv.GetSpectum(data) < 0) {
            throw new Exception("数据采集失败!");
        }

        //扣暗电流
        if (this.collect_config.dk_enable && this.dk_data.length == data.length) {
            for (int i = 0; i < data.length; i++) {
                data[i] -= this.dk_data[i];
            }
        }

        //非线性修正
        if (this.collect_config.linearEnable) {
            this.dev_drv.NonlinearCalibration(data, wave);
            System.arraycopy(wave, 0, data, 0, data.length);
        }

        //窗口平滑
        if (this.collect_config.window > 1) {
            SPDataWindowAverage.WindowAverage(data, this.collect_config.window);
        }

        System.arraycopy(this.wave_array, 0, wave, 0, wave.length);
        SSpectralDataPacket ret_data = new SSpectralDataPacket(wave, data);

        //巴特沃斯滤波
        if (((byte) this.collect_config.filterKey & SSCollectConfig.BattwoseKey) > 0) {
            ret_data.data.datavalue = batfilter.Filter(ret_data.data.datavalue);
        }

        //最小二乘平滑
        if (((byte) this.collect_config.filterKey & SSCollectConfig.SquareKey) > 0) {
            Theleastsquaremethod.smooth(ret_data.data.pixelIndex, ret_data.data.datavalue, 5);
        }

        return ret_data;
    }

    // <editor-fold defaultstate="collapsed" desc="采集参数">  
    private SSCollectPar collect_par = new SSCollectPar(-1, 0, 0, SSCollectPar.SoftMode);

    @Override
    public SSCollectPar GetCollectPar() {
        return new SSCollectPar(collect_par);
    }

    @Override
    public void SetCollectPar(SSCollectPar par) throws Exception {
        if (this.collect_par.EqualTo(par)) {
            return;
        }
        this.dev_drv.SetAverageTimes(par.averageTime);
        this.dev_drv.SetIntegrationTime((int) (par.integralTime * 1000));
        this.collect_par = new SSCollectPar(par);
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="采集配置">  
    private SSCollectConfig collect_config = new SSCollectConfig(false, true, false, SSCollectConfig.NoneKey, 1);

    @Override
    public SSCollectConfig GetCollectCoinfig() {
        return collect_config;
    }

    @Override
    public void SetCollectConfig(SSCollectConfig par) throws Exception {
        this.collect_config = par;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="按电流操作"> 
    //暗电流
    private double[] dk_data = new double[0];

    @Override
    public SSpectralDataPacket DKModify() throws Exception {
        double[] data = new double[this.sp_par.nodeNumber];
        double[] wave = new double[wave_array.length];
        System.arraycopy(this.wave_array, 0, wave, 0, wave.length);
        if (this.dev_drv.GetSpectum(data) < 0) {
            throw new Exception("数据采集失败!");
        }
        this.dk_data = data;
        return new SSpectralDataPacket(wave, data);
    }

    @Override
    public void ClearDKData() {
        dk_data = new double[0];
    }
    // </editor-fold> 
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="参数设置"> 
    //波长系数
    private double[] wave_array;

    private void initWaveLen() throws Exception {
        this.wave_array = new double[this.sp_par.nodeNumber];
        if (this.dev_drv.GetWavelength(wave_array) < 0) {
            throw new Exception("获取波长数据失败!");
        }
        for (int i = 0; i < this.wave_array.length; i++) {
            if (Double.isFinite(this.wave_array[i])) {
                this.wave_array[i] = new BigDecimal(this.wave_array[i]).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            }else{
                this.wave_array[i] = i + 1;
            }
        }
    }

    @Override
    public void InitDevice() throws Exception {
        dev_info = new SSPDevInfo();
        dev_info.index = this.dev_drv.SPdevIndex();
        dev_info.eia = dev_info.new SSEquipmentInfo();

        //获取EIA
        dev_info.eia.Hardversion = this.dev_drv.GetHardwareVersion();
        dev_info.eia.DeviceName = this.dev_drv.GetSpectrometersName();
        dev_info.eia.SoftwareVersion = this.dev_drv.GetSoftwareVersion();
        dev_info.eia.BuildSerialNum = this.dev_drv.GetSerialNumber();
        dev_info.eia.BuildDate = this.dev_drv.GetManufacturingDate();

        sp_par = new SSpectralPar();
        //读取光谱个数
        this.sp_par.nodeNumber = this.dev_drv.GetSpectrometerPixelsNumber();
        if (this.sp_par.nodeNumber < 0) {
            throw new Exception("获取光数据个数失败!");
        }
        this.sp_par.minIntegralTime = this.dev_drv.GetMinIntegrationTime();
        this.sp_par.maxIntegralTime = this.dev_drv.GetMaxIntegrationTime();
        //读取波长数据
        this.initWaveLen();

        //初始化巴特沃斯滤波器
        this.batfilter.InitBattwoseWindow(this.sp_par.nodeNumber, this.sp_par.nodeNumber / 10, 16);

        this.initConfig();
    }

    // <editor-fold defaultstate="collapsed" desc="设备信息">  
    private SSPDevInfo dev_info;

    @Override
    public SSPDevInfo GetDevInfo() {
        return dev_info;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="光谱仪基本参数"> 
    private SSpectralPar sp_par;

    @Override
    public SSpectralPar GetSpectralPar() {
        return this.sp_par;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="波长定标系数"> 
    @Override
    public SSWaveCaculatePar GetWaveParameter() {
        SSWaveCaculatePar cal_par = new SSWaveCaculatePar();
        double[] c_array = new double[4];
        this.dev_drv.GetWavelengthCalibrationCoefficients(c_array);
        cal_par.C0 = c_array[0];
        cal_par.C1 = c_array[1];
        cal_par.C2 = c_array[2];
        cal_par.C3 = c_array[3];
        return cal_par;
    }

    @Override
    public void SetWaveParameter(SSWaveCaculatePar par) throws Exception {
        this.dev_drv.SetWavelengthCalibrationCoefficients(new double[]{par.C0, par.C1, par.C2, par.C3});
        this.initWaveLen();
    }
    // </editor-fold> 

    @Override
    public SSLinearParameter GetLinearPar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetLinearPar(SSLinearParameter par) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="外设控制"> 
    @Override
    public void EnableExtern(boolean value) throws Exception {
        if (value) {
            this.dev_drv.XenonFlashEnable();
        } else {
            this.dev_drv.XenonFlashDisable();
        }
    }

    private SSConfigItem[] config = new SSConfigItem[0];
    public static String[] CONFIG_STRING = new String[]{"脉冲宽度", "积分时间", "延时", "脉冲次数"};
    public static String[] CONFIG_UNIT = new String[]{"", "ms", "ms", ""};
    private int x_pars[] = new int[4];
//    private int pluswidth = 0;
//    private int IntervalTime = 0;
//    private int DelayTime = 0;
//    private int PulseNumber = 0;

    private void initConfig() {
        this.dev_drv.GetXenonFlashPara(x_pars);
        config = new SSConfigItem[x_pars.length];
        for (int i = 0; i < config.length; i++) {
            config[i] = SSConfigItem.CreateRWItem(CONFIG_STRING[i], x_pars[i] + "", CONFIG_UNIT[i]);
        }
    }

    @Override
    public SSConfigItem[] GetExternConfig() {
        return config;
    }

    @Override
    public void SetExcternConfig(SSConfigItem[] config) throws Exception {

        for (SSConfigItem item : config) {
            if (item.data_name.contentEquals(CONFIG_STRING[0])) {
                x_pars[0] = Integer.valueOf(item.value);
            }
            if (item.data_name.contentEquals(CONFIG_STRING[1])) {
                x_pars[1] = Integer.valueOf(item.value);
            }
            if (item.data_name.contentEquals(CONFIG_STRING[2])) {
                x_pars[2] = Integer.valueOf(item.value);
            }
            if (item.data_name.contentEquals(CONFIG_STRING[3])) {
                x_pars[3] = Integer.valueOf(item.value);
            }
        }

        this.dev_drv.SetXenonFlashPara(x_pars[0], x_pars[1], x_pars[2], x_pars[3]);
    }
    // </editor-fold> 
}
