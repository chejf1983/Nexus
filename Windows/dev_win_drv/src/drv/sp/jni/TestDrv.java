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
            sp_dev_drv.InitLib(true);
            System.out.println(sp_dev_drv.GetAPIVersion());

            int dev_num = sp_dev_drv.OpenSpectrometers();

            if (dev_num == 0) {
                sp_dev_drv.OpenSpectrometersForSerial();
            }

            System.out.println("搜索到设备:" + dev_num);

            if (dev_num > 0) {
                sp_dev_drv drv = new sp_dev_drv(0);
                System.out.println(drv.GetSpectrometersName());

                int[] a = new int[4];
                drv.GetXenonFlashPara(a);
                System.out.println(a[0] + ":" + a[1] + ":" + a[2] + ":" + a[3]);
            
       
//                double[] c_par = new double[4];
//                drv.GetWavelengthCalibrationCoefficients(c_par);
//                
//                System.out.println(c_par[0]);
//                
//                byte[] testb = new byte[40];
//                //drv.WriteUserMemory(0, 0, testb);
//               // for(int i = 0; i < testb.length; i++)
//                //System.out.print(testb[i] + " ");
//                //System.out.println();
//                int ret = drv.ReadUserMemory(MEMTYPE.EIA, 0, testb);                
//                System.out.println("结果:" + ret);
//                for(int i = 0; i < testb.length; i++)
//                System.out.print(testb[i] + " ");
            }

        } catch (Exception ex) {
            Logger.getLogger(TestDrv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
