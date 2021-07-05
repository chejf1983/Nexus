/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sps.platform;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import nahon.comm.faultsystem.LogCenter;
import sps.app.common.AppManager;
import sps.control.manager.SpDevManager;
import sps.control.manager.ISPDevSearch;

/**
 *
 * @author jiche
 */
public class SpectralPlatService {

    private static SpectralPlatService Instance = new SpectralPlatService();

    private SpectralPlatService() {
    }

    public static SpectralPlatService GetInstance() {
        return Instance;
    }

    public static void SetDriver(ISPDevSearch drv) {
        SpDevManager.SetSPDevDriver(drv);
    }

    public void InitPlatForm() throws Exception {

        //初始化LOG路径
        LogCenter.Instance().SetLogPath("./log");

        //初始化系统配置
        this.GetConfig().ReadFromFile();
    }

    private SystemConfig config;

    public SystemConfig GetConfig() {
        if (this.config == null) {
            this.config = new SystemConfig();
        }
        return this.config;
    }

    public void Close() {
        config.SaveToFile();
    }

    //线程池
    private ExecutorService systemthreadpool;

    public ExecutorService GetThreadPools() {
        //初始化进程池
        if (this.systemthreadpool == null) {
            this.systemthreadpool = Executors.newFixedThreadPool(50);
        }
//        System.out.println("当前激活线程:" + ((ThreadPoolExecutor) systemthreadpool).getActiveCount());
        return this.systemthreadpool;
    }
}
