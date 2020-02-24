/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drv.sp.jni;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chejf
 */
public class TestDrv {

    public static void main(String... args) {
        try {
            sp_dev_drv.InitLib();
            System.out.println(sp_dev_drv.GetAPIVersion());

            int dev_num = sp_dev_drv.OpenSpectrometers();
            
            if(dev_num == 0){
                sp_dev_drv.OpenSpectrometersForSerial();
            }

            System.out.println("搜索到设备:" + dev_num);

            if (dev_num > 0) {
                sp_dev_drv drv = new sp_dev_drv(0);
                System.out.println(drv.GetSpectrometersName());
                
                double[] c_par = new double[4];
                drv.GetWavelengthCalibrationCoefficients(c_par);
                
                System.out.println(c_par[0]);
            }
        } catch (Exception ex) {
            Logger.getLogger(TestDrv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
