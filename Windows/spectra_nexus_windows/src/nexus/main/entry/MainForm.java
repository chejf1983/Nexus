/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nexus.main.entry;

import java.awt.CardLayout;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import nahon.comm.event.NEvent;
import nahon.comm.exl2.xlsTable_R;
import nahon.comm.faultsystem.LogCenter;
import nahon.comm.file2.FileReader2;
import nexus.app.absorbe.UIAbsorbeApp;

import nexus.app.stander.UIStanderApp;
import nexus.app.transmit.UITransApp;
import nexus.device.manager.LeftPane;
import nexus.main.compent.AboutDialog;
import sps.app.common.AppManager;
import sps.control.manager.SpDevManager;
import sps.dev.data.SSCollectConfig;
import sps.platform.SpectralPlatService;
import sps.platform.SystemConfig;

/**
 *
 * @author jiche
 */
public class MainForm extends javax.swing.JFrame {

    public static MainForm instance;

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        LogCenter.Instance().RegisterFaultEvent((NEvent<Level> event) -> {
            JOptionPane.showMessageDialog(MainForm.this, event.Info().toString());
        });
        //初始化界面
        this.InitUI();

        //触发语言
        this.RegistItemLanguage();

        //全屏
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // <editor-fold defaultstate="collapsed" desc="UI初始化"> 
    private void InitUI() {
        this.setLocationRelativeTo(null);

        this.SetFormIcon();

        this.InitDeviceArea();

        //初始化
        this.InitApplication();

        this.InitTailPane();

        MainForm.instance = this;
    }

    private void SetFormIcon() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        java.net.URL disurl = MainForm.class.getResource("/nexus/main/resource/SN.png");
        java.awt.Image image = tk.createImage(disurl);
        this.setIconImage(image);

        String flag = SpectralPlatService.GetInstance().GetConfig().getProperty(SystemConfig.InternalFlag, "1");
        if (Integer.valueOf(flag) == 1) {
            this.setTitle("SpectralNexus-Internal");
        } else {
            this.setTitle("SpectralNexus");
        }

    }

    private LeftPane devmanager = new LeftPane();

    private void InitDeviceArea() {
        this.SplitPane_main.setLeftComponent(devmanager);
        this.SplitPane_main.setResizeWeight(0);
        this.SplitPane_main.setOneTouchExpandable(false);
        this.SplitPane_main.setEnabled(false);

    }

    // <editor-fold defaultstate="collapsed" desc="Application MenutItem Init"> 
    private CardLayout applicationAreaLayout = new CardLayout();

    public JRadioButtonMenuItem RegAppUI(String name, IAppUI pane) {
        JRadioButtonMenuItem item = new JRadioButtonMenuItem(name);
        Menu_Application.add(item);
        ApplicationGroup.add(item);

        ApplicationArea.add(name, pane);
        AppManager.R().RegApp(name, pane.getApp());
        item.addChangeListener((javax.swing.event.ChangeEvent evt) -> {
            if (item.isSelected()) {
                applicationAreaLayout.show(ApplicationArea, name);
                AppManager.R().SwitchApp(name);
            }
        });
        return item;
    }

    private void addExtModel(){
        try (FileReader2 fr = FileReader2.OpenFile("./model.cfg")) {
            while (true) {
                xlsTable_R tb = fr.FindeNextTable();
                if (tb == null) {
                    return;
                }
                if (tb.rows.size() != 3) {
                    continue;
                }

                URL url = new File("./lib/" + tb.rows.get(0)[0] + ".jar").toURI().toURL();
                URLClassLoader loader = new URLClassLoader(new URL[]{url});
                Class c = loader.loadClass(tb.rows.get(2)[0]);
                IAppUI app = (IAppUI) c.newInstance();
                RegAppUI(tb.rows.get(1)[0], app);
            }
        } catch (Exception ex) {
            LogCenter.Instance().SendFaultReport(Level.SEVERE, ex);
        }
    }

    private void InitApplication() {
        AppManager.R().TestEvent.RegeditListener((NEvent<Boolean> event) -> {
            //更新控制面板使能状态
            Menu_Application.setEnabled(!event.GetEvent());
        });

        this.Menu_Application.setEnabled(false);
        this.ApplicationArea.setLayout(applicationAreaLayout);

        RegAppUI("原始光谱", new UIStanderApp()).setSelected(true);
        RegAppUI("透射率", new UITransApp());
        RegAppUI("吸光度", new UIAbsorbeApp());
        addExtModel();
        Menu_Application.setEnabled(true);
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Main尾部初始化"> 
    private void InitTailPane() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Label_SystemTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        }, 0, 1000);

        //显示时间
        AppManager.R().TimeFlag.TimeConsumeEvent.RegeditListener((NEvent<Long> event) -> {
            java.awt.EventQueue.invokeLater(() -> {
                Label_time.setText("耗时" + ":" + (double) event.GetEvent() / 1000000 + "ms");
            });
        });

        AppManager.R().TestEvent.RegeditListener((NEvent<Boolean> event) -> {
            java.awt.EventQueue.invokeLater(() -> {
                ProgressBar.setIndeterminate(event.GetEvent());
            });
        });
    }
    // </editor-fold> 
    // </editor-fold> 

    private void RegistItemLanguage() {
//        this.LanguageGroup.add(this.MenuItem_Chinese);
//        this.LanguageGroup.add(this.MenuItem_English);
//        this.LanguageGroup.add(this.MenuItem_Japanese);
//
//        switch (LanguageHelper.getIntance().GetLanguage()) {
//            case Chinese:
//                this.MenuItem_Chinese.setSelected(true);
//                break;
//            case English:
//                this.MenuItem_English.setSelected(true);
//                break;
//            case Japanese:
//                this.MenuItem_Japanese.setSelected(true);
//                break;
//        }
//                MainForm.this.MenuItem_Chinese.setText("MenuItem_Chinese"));
//                MainForm.this.MenuItem_English.setText("MenuItem_English"));
//                MainForm.this.MenuItem_Japanese.setText("MenuItem_Japanese"));

        MainForm.this.Menu_Application.setText("应用");
//                MainForm.this.MenuItem_TNP.setText("MenuItem_TNP"));
//                MainForm.this.MenuItem_SynCalibrate.setText("MenuItem_SynCalibrate"));

        MainForm.this.Menu_File.setText("开始");
//                MainForm.this.Menu_Language.setText("Menu_Language"));
        MainForm.this.MenuItem_Exit.setText("退出");

        MainForm.this.Menu_config.setText("设置");
        MainForm.this.MenuItem_Filter.setText("滤波开关");

        MainForm.this.Menu_Help.setText("帮助");
//                MainForm.this.MenuItem_SerialNum.setText("MenuItem_SerialNum"));
        MainForm.this.MenuItem_About.setText("关于");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LanguageGroup = new javax.swing.ButtonGroup();
        ApplicationGroup = new javax.swing.ButtonGroup();
        SplitPane_main = new javax.swing.JSplitPane();
        DeviceArea = new javax.swing.JPanel();
        ApplicationArea1 = new javax.swing.JPanel();
        ApplicationArea = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Label_SystemTime = new javax.swing.JLabel();
        Label_time = new javax.swing.JLabel();
        ProgressBar = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        Menu_File = new javax.swing.JMenu();
        MenuItem_Exit = new javax.swing.JMenuItem();
        Menu_Application = new javax.swing.JMenu();
        Menu_config = new javax.swing.JMenu();
        MenuItem_Filter = new javax.swing.JCheckBoxMenuItem();
        Menu_Help = new javax.swing.JMenu();
        MenuItem_About = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1100, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        SplitPane_main.setResizeWeight(1.0);

        DeviceArea.setPreferredSize(new java.awt.Dimension(100, 555));

        javax.swing.GroupLayout DeviceAreaLayout = new javax.swing.GroupLayout(DeviceArea);
        DeviceArea.setLayout(DeviceAreaLayout);
        DeviceAreaLayout.setHorizontalGroup(
            DeviceAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );
        DeviceAreaLayout.setVerticalGroup(
            DeviceAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );

        SplitPane_main.setLeftComponent(DeviceArea);

        javax.swing.GroupLayout ApplicationAreaLayout = new javax.swing.GroupLayout(ApplicationArea);
        ApplicationArea.setLayout(ApplicationAreaLayout);
        ApplicationAreaLayout.setHorizontalGroup(
            ApplicationAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 882, Short.MAX_VALUE)
        );
        ApplicationAreaLayout.setVerticalGroup(
            ApplicationAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ApplicationArea1Layout = new javax.swing.GroupLayout(ApplicationArea1);
        ApplicationArea1.setLayout(ApplicationArea1Layout);
        ApplicationArea1Layout.setHorizontalGroup(
            ApplicationArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ApplicationArea1Layout.createSequentialGroup()
                .addComponent(ApplicationArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        ApplicationArea1Layout.setVerticalGroup(
            ApplicationArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ApplicationArea1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(ApplicationArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SplitPane_main.setRightComponent(ApplicationArea1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Label_SystemTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Label_SystemTime.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(Label_time, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(Label_SystemTime, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Label_SystemTime, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .addComponent(Label_time, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(ProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        Menu_File.setText("File");

        MenuItem_Exit.setText("Exit");
        MenuItem_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_ExitActionPerformed(evt);
            }
        });
        Menu_File.add(MenuItem_Exit);

        jMenuBar1.add(Menu_File);

        Menu_Application.setText("Application");
        jMenuBar1.add(Menu_Application);

        Menu_config.setText("设置");

        MenuItem_Filter.setText("滤波使能");
        MenuItem_Filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_FilterActionPerformed(evt);
            }
        });
        Menu_config.add(MenuItem_Filter);

        jMenuBar1.add(Menu_config);

        Menu_Help.setText("Help");

        MenuItem_About.setText("关于");
        MenuItem_About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_AboutActionPerformed(evt);
            }
        });
        Menu_Help.add(MenuItem_About);

        jMenuBar1.add(Menu_Help);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SplitPane_main, javax.swing.GroupLayout.DEFAULT_SIZE, 1234, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SplitPane_main, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuItem_AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_AboutActionPerformed
        new AboutDialog(this, true).setVisible(true);
    }//GEN-LAST:event_MenuItem_AboutActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // SpectralPlatService.Instance.Close();
    }//GEN-LAST:event_formWindowClosing

    private void MenuItem_FilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_FilterActionPerformed
        if (MenuItem_Filter.isSelected()) {
            SpDevManager.R().TestConfig.collect_config.filterKey = SSCollectConfig.BattwoseKey;
        } else {
            SpDevManager.R().TestConfig.collect_config.filterKey = SSCollectConfig.NoneKey;
        }
    }//GEN-LAST:event_MenuItem_FilterActionPerformed

    private void MenuItem_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_MenuItem_ExitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ApplicationArea;
    private javax.swing.JPanel ApplicationArea1;
    private javax.swing.ButtonGroup ApplicationGroup;
    private javax.swing.JPanel DeviceArea;
    private javax.swing.JLabel Label_SystemTime;
    private javax.swing.JLabel Label_time;
    private javax.swing.ButtonGroup LanguageGroup;
    private javax.swing.JMenuItem MenuItem_About;
    private javax.swing.JMenuItem MenuItem_Exit;
    private javax.swing.JCheckBoxMenuItem MenuItem_Filter;
    private javax.swing.JMenu Menu_Application;
    private javax.swing.JMenu Menu_File;
    private javax.swing.JMenu Menu_Help;
    private javax.swing.JMenu Menu_config;
    private javax.swing.JProgressBar ProgressBar;
    private javax.swing.JSplitPane SplitPane_main;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
