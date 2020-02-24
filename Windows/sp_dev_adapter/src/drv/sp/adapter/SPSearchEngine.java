/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drv.sp.adapter;

import drv.sp.jni.sp_dev_drv;
import sps.control.manager.ISPDevSearch;
import sps.control.manager.ISpDevice;

/**
 *
 * @author chejf
 */
public class SPSearchEngine implements ISPDevSearch {

    @Override
    public ISpDevice[] SearchDevice() {
        //判断是否初始化
        if (sp_dev_drv.IsInitLib()) {
            //搜索设备
            int dev_num = sp_dev_drv.OpenSpectrometers();
            if(dev_num == 0){
                sp_dev_drv.OpenSpectrometersForSerial();
            }
            ISpDevice[] ret = new ISpDevice[dev_num];
            //创建适配器
            for (int i = 0; i < ret.length; i++) {
                ret[i] = new SPDeviceAdp(new sp_dev_drv(i));
            }
            return ret;
        }
        System.out.println("驱动没有初始化");
        return new ISpDevice[0];
    }

    @Override
    public String InitDriver() throws Exception {
        sp_dev_drv.InitLib();
        return "光谱仪驱动版本:" + sp_dev_drv.GetAPIVersion();
    }
}
