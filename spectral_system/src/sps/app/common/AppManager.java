/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sps.app.common;

import java.util.HashMap;
import java.util.logging.Level;
import nahon.comm.event.NEventCenter;
import nahon.comm.faultsystem.LogCenter;
import sps.platform.SpectralPlatService;

/**
 *
 * @author Administrator
 */
public class AppManager {

    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager R() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    // <editor-fold defaultstate="collapsed" desc="app列表">  
    public CTestApp GetCurrentApp() {
        return currentApp;
    }

    private CTestApp currentApp = null;

    private HashMap<String, CTestApp> appmap = new HashMap();

    public void RegApp(String AppFlag, CTestApp app) {
        appmap.put(AppFlag, app);
    }

    public void SwitchApp(String AppFlag) {
        this.currentApp = appmap.get(AppFlag);
        if (this.currentApp == null) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, "未知模块" + AppFlag);
        }
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="执行测试">  
    public TimeConsume TimeFlag = new TimeConsume();
    private boolean is_running = false;

    public boolean IsRunning() {
        return this.is_running;
    }

    public void RunCommand(Runnable comd) {
        if (this.is_running) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, "系统正忙");
            return;
        }
        this.is_running = true;
        TestEvent.CreateEvent(is_running);
        //通知更新测试条件
        SpectralPlatService.GetInstance().GetThreadPools().submit(() -> {
            comd.run();
            is_running = false;
            TestEvent.CreateEvent(is_running);
        });
    }

    public NEventCenter<Boolean> TestEvent = new NEventCenter();
    // </editor-fold> 
}
