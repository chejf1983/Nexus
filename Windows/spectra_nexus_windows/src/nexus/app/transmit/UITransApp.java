package nexus.app.transmit;

import nexus.app.absorbe.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import nahon.comm.faultsystem.LogCenter;
import java.io.IOException;
import nahon.comm.event.NEvent;
import nexus.main.compent.FileDialogHelp;
import nexus.main.compent.ImageHelper;
import org.jfree.chart.ChartUtilities;
import sps.app.absorb.AbsApp;
import sps.app.absorb.RateData;
import sps.app.transmit.TrsApp;
import sps.dev.data.SSpectralDataPacket;
import sps.platform.SpectralPlatService;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jiche
 */
public class UITransApp extends javax.swing.JPanel {

    public UITransApp() {
        initComponents();

        //初始化表格和曲线
        this.InitTableAndChart();

        this.InitAppControl();

        this.InitLanguange();
    }

    // <editor-fold defaultstate="collapsed" desc="初始化表格和曲线"> 
    private SPPane sp_pane;
    private RatePane rate_pane;

    //初始化表格界面
    private void InitTableAndChart() {
        sp_pane = new SPPane();
        rate_pane = new RatePane();
        this.ChartTable_TabbedPane.add(this.sp_pane);
        this.ChartTable_TabbedPane.add(this.rate_pane);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="初始化语言"> 
    private void InitLanguange() {
        Button_SaveImage.setToolTipText("保存曲线图片");
        Button_SaveData.setToolTipText("导出Excel");
        Button_LoadData.setToolTipText("读取Excel数据");
        Button_AddSnapShot.setToolTipText("添加光谱副本");
        Button_DeletSnapShot.setToolTipText("删除光谱副本");
        ToggleButton_DataTable.setToolTipText("显示数据表格");

        Button_RefCollect.setToolTipText("测量参考光");
        ChartTable_TabbedPane.setTitleAt(0, "光谱曲线");
        ChartTable_TabbedPane.setTitleAt(1, "透过率度曲线");

        this.sp_pane.dataTablePane.SetTitle("光谱数据", "值");
        this.rate_pane.dataTablePane.SetTitle("透过率数据", "透过率");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="初始化App控制"> 
    private TrsApp commapp;

    private void InitAppControl() {
        commapp = SpectralPlatService.GetInstance().GetAppManager().GetTrsApp();
        
        SpectralPlatService.GetInstance().GetAppManager().TestEvent.RegeditListener((NEvent<Boolean> event) -> {
            //更新控制面板使能状态
            Button_RefCollect.setEnabled(!event.GetEvent());
        });
        
        this.commapp.TESTEVENT_CENTER.RegeditListener((NEvent<Integer> event) -> {
            if (event.GetEvent() == AbsApp.REFTEST) {
                sp_pane.UpdateRefData((SSpectralDataPacket) event.Info());
            }
            
            if (event.GetEvent() == AbsApp.DKTEST) {
                sp_pane.UpdateMainData((SSpectralDataPacket) event.Info());
            }
            
            if (event.GetEvent() == AbsApp.TESTDATA) {
                sp_pane.UpdateMainData(((RateData) event.Info()).testdata);
                rate_pane.UpdateMainData((RateData) event.Info());
            }
        });
    }

    // </editor-fold>
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DisplayArea = new javax.swing.JPanel();
        ChartTable_TabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        ToggleButton_DataTable = new javax.swing.JToggleButton();
        Button_DeletSnapShot = new javax.swing.JButton();
        Button_AddSnapShot = new javax.swing.JButton();
        Button_SaveData = new javax.swing.JButton();
        Button_SaveImage = new javax.swing.JButton();
        Button_RefCollect = new javax.swing.JButton();
        Button_LoadData = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(617, 406));

        DisplayArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout DisplayAreaLayout = new javax.swing.GroupLayout(DisplayArea);
        DisplayArea.setLayout(DisplayAreaLayout);
        DisplayAreaLayout.setHorizontalGroup(
            DisplayAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChartTable_TabbedPane)
        );
        DisplayAreaLayout.setVerticalGroup(
            DisplayAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChartTable_TabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
        );

        ToggleButton_DataTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nexus/app/resources/datatable.png"))); // NOI18N
        ToggleButton_DataTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToggleButton_DataTableActionPerformed(evt);
            }
        });

        Button_DeletSnapShot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nexus/app/resources/delete_snap.png"))); // NOI18N
        Button_DeletSnapShot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_DeletSnapShotActionPerformed(evt);
            }
        });

        Button_AddSnapShot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nexus/app/resources/add_snap.png"))); // NOI18N
        Button_AddSnapShot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_AddSnapShotActionPerformed(evt);
            }
        });

        Button_SaveData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nexus/app/resources/xls_save.png"))); // NOI18N
        Button_SaveData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_SaveDataActionPerformed(evt);
            }
        });

        Button_SaveImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nexus/app/resources/bmp_save.png"))); // NOI18N
        Button_SaveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_SaveImageActionPerformed(evt);
            }
        });

        Button_RefCollect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nexus/app/resources/ref.png"))); // NOI18N
        Button_RefCollect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_RefCollectActionPerformed(evt);
            }
        });

        Button_LoadData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nexus/app/resources/excel_32px_load.png"))); // NOI18N
        Button_LoadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_LoadDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Button_SaveImage, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Button_SaveData, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Button_LoadData, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Button_AddSnapShot, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Button_DeletSnapShot, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(ToggleButton_DataTable, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(Button_RefCollect, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(278, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Button_LoadData, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_RefCollect, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_SaveImage, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_SaveData, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_AddSnapShot, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_DeletSnapShot, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ToggleButton_DataTable, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DisplayArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(DisplayArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void Button_SaveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_SaveImageActionPerformed
        try {
            File file = FileDialogHelp.GetFilePath(".png");
            if (file != null) {
                try (FileOutputStream bout = new java.io.FileOutputStream(file)) {
                    ChartUtilities.writeBufferedImageAsPNG(bout, ImageHelper.mergeImage(this.sp_pane.GetChartPanePNG(), this.rate_pane.GetChartPanePNG(), true));
                    bout.flush();
                }
            }
        } catch (IOException ex) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, "保存图片失败" + ex);
        }
    }//GEN-LAST:event_Button_SaveImageActionPerformed

    private void Button_SaveDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_SaveDataActionPerformed
        try {
            File file = FileDialogHelp.GetFilePath(".xls");
            if (file != null) {
                this.commapp.SaveToExcel(file.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "保存成功!");
            }
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, "保存记录失败!" + ex);
        }
    }//GEN-LAST:event_Button_SaveDataActionPerformed

    private void Button_AddSnapShotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_AddSnapShotActionPerformed
        this.commapp.AddSnapShot();
        this.sp_pane.UpdataChartSnapShort(this.commapp.GetSnapShots());
        this.rate_pane.UpdataChartSnapShort(this.commapp.GetSnapShots());
    }//GEN-LAST:event_Button_AddSnapShotActionPerformed

    private void Button_DeletSnapShotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_DeletSnapShotActionPerformed
        this.commapp.DelSnapShot();
        this.sp_pane.UpdataChartSnapShort(this.commapp.GetSnapShots());
        this.rate_pane.UpdataChartSnapShort(this.commapp.GetSnapShots());
    }//GEN-LAST:event_Button_DeletSnapShotActionPerformed

    private void ToggleButton_DataTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToggleButton_DataTableActionPerformed
        this.sp_pane.DisplayTable(ToggleButton_DataTable.isSelected());
        this.rate_pane.DisplayTable(ToggleButton_DataTable.isSelected());
    }//GEN-LAST:event_ToggleButton_DataTableActionPerformed

    private void Button_RefCollectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_RefCollectActionPerformed
        this.rate_pane.Clear();
        this.sp_pane.Clear();
        this.commapp.CollectReflectLight();
    }//GEN-LAST:event_Button_RefCollectActionPerformed

    private void Button_LoadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_LoadDataActionPerformed
        try {
            File file = FileDialogHelp.GetFilePath(".xls");
            if (file != null) {
                this.commapp.ReadExcel(file.getAbsolutePath());
                this.sp_pane.UpdataChartSnapShort(this.commapp.GetSnapShots());
                this.rate_pane.UpdataChartSnapShort(this.commapp.GetSnapShots());
                LogCenter.Instance().ShowMessBox(Level.INFO, "读取成功!");
            }
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, "读取成功记录失败!" + ex);
        }
    }//GEN-LAST:event_Button_LoadDataActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_AddSnapShot;
    private javax.swing.JButton Button_DeletSnapShot;
    private javax.swing.JButton Button_LoadData;
    private javax.swing.JButton Button_RefCollect;
    private javax.swing.JButton Button_SaveData;
    private javax.swing.JButton Button_SaveImage;
    private javax.swing.JTabbedPane ChartTable_TabbedPane;
    private javax.swing.JPanel DisplayArea;
    private javax.swing.JToggleButton ToggleButton_DataTable;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
