
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import AppPackage.AnimationClass;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import arduino.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 *
 * @author Nipuna
 */
public class SelectedMapExtended extends javax.swing.JFrame {

    private int positionMap[][] = {{40, 455}, {250, 455}, {460, 455},
    {40, 260}, {250, 260}, {460, 260},
    {40, 40}, {250, 40}, {460, 40}};

    private int tempvalue;
    private String serialReading = "5000";
    private int intArray[] = new int[9];
    private int arr1[] = new int[4];
    private int arr2[] = new int[4];
    private int arr3[] = new int[4];
    private int arr4[] = new int[4];
    private int count = 0;
    private int minVal = Integer.MAX_VALUE;
    private int posX = 0;
    private int posY = 0;

    private int val1x = 0;
    private int val2x = 0;
    private int val3x = 0;
    private int val4x = 0;

    private int val1y = 0;
    private int val2y = 0;
    private int val3y = 0;
    private int val4y = 0;

    private int sum1 = 0;
    private int sum2 = 0;
    private int sum3 = 0;
    private int sum4 = 0;

    private int rect1 = 20;
    private int rect2 = -20;

    private int sumArray[] = new int[4];
    private int minSum = Integer.MAX_VALUE;

    private int posRectifierX = 0;
    private int posRectifierY = 0;
    String input = JOptionPane.showInputDialog("Input COM port number");

    //Arduino object
    Arduino arduino = new Arduino("COM" + input, 9600);

    private Timer t;

    public SelectedMapExtended() {
        initComponents();
        arduino.openConnection();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dotPointer.setVisible(true);
        serialValues();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dotPointer = new javax.swing.JLabel();
        mapLabel = new javax.swing.JLabel();
        backgroundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dotPointer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dotPointer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reddot16.png"))); // NOI18N
        dotPointer.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                dotPointerMouseDragged(evt);
            }
        });
        dotPointer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dotPointerMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dotPointerMouseReleased(evt);
            }
        });
        getContentPane().add(dotPointer, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, 40, 40));

        mapLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mapLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/map3.png"))); // NOI18N
        mapLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mapLabelMouseDragged(evt);
            }
        });
        mapLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mapLabelMousePressed(evt);
            }
        });
        getContentPane().add(mapLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 480, 490));

        backgroundLabel.setBackground(new java.awt.Color(102, 102, 102));
        backgroundLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backgroundLabel.setOpaque(true);
        backgroundLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                backgroundLabelMouseDragged(evt);
            }
        });
        backgroundLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                backgroundLabelMousePressed(evt);
            }
        });
        getContentPane().add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 537, 536));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dotPointerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dotPointerMouseDragged

    }//GEN-LAST:event_dotPointerMouseDragged

    private void dotPointerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dotPointerMousePressed

    }//GEN-LAST:event_dotPointerMousePressed

    private void mapLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mapLabelMousePressed

    }//GEN-LAST:event_mapLabelMousePressed

    private void mapLabelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mapLabelMouseDragged

    }//GEN-LAST:event_mapLabelMouseDragged
    //arduino serial connection. while 5000 is printed at serial monitor the label move function will not work
    private void serialValues() {
        System.out.println("Start");
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                while (!serialReading.equals("1000")) {
                    serialReading = arduino.serialRead();
                    serialReading = serialReading.trim();

                    System.out.println(serialReading);
                    String strArray[] = serialReading.split(",");

                    if (strArray[0].trim().contains("AA")) {
                        int i = 1;
                        while (!strArray[i].trim().equals("BB")) {
                            boolean isDigit = false;
                            char[] chars = strArray[i].toCharArray();
                            for (char c : chars) {
                                if (Character.isDigit(c)) {
                                    isDigit = true;
                                }
                            }
                            if (isDigit) {
                                tempvalue = Integer.parseInt(strArray[i]);
                                //System.out.println(i + " " + tempvalue);
                                Thread.sleep(100);
                                publish(tempvalue);
                            }
                            i++;
                        }
                    }
                    /*
                    boolean isdigit = false;
                    char[] chars = serialReading.toCharArray();
                    for (char c : chars) {
                        if (Character.isDigit(c)) {
                            isdigit = true;
                        }
                    }
                    if (isdigit) {
                        tempvalue = Integer.parseInt(serialReading);
                        publish(tempvalue);
                    }*/
                }
                return true;
            }

            protected void done() {
                boolean status;
                try {
                    status = get();
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                }
            }

            @Override
            protected void process(List<Integer> chunks) {
                int mostRecentValue = chunks.get(chunks.size() - 1);
                System.out.println("Most Recent Value: "+mostRecentValue);
                setPosition(mostRecentValue);
            }
        };

        worker.execute();

    }

    void setPosition(int val) {
        intArray[count] = val;
        count++;
        if (count == 9) {
            val1x = 0;
            val1y = 0;
            val2x = 0;
            val2y = 0;
            val3x = 0;
            val3y = 0;
            val4x = 0;
            val4y = 0;

            int a = intArray[0];
            int b = intArray[1];
            int c = intArray[2];
            int d = intArray[3];
            int e = intArray[4];
            int f = intArray[5];
            int g = intArray[6];
            int h = intArray[7];
            int l = intArray[8];

            arr1[0] = 0;//a;
            arr1[1] = 1;//b;
            arr1[2] = 4;//e;
            arr1[3] = 3;//d;

            arr2[0] = 1;//b;
            arr2[1] = 2;//c;
            arr2[2] = 5;//f;
            arr2[3] = 4;//e;

            arr3[0] = 3;//d;
            arr3[1] = 4;//e;
            arr3[2] = 7;//h;
            arr3[3] = 6;//g;

            arr4[0] = 4;//d;
            arr4[1] = 5;//f;
            arr4[2] = 8;//l;
            arr4[3] = 7;//h;

            minVal = a;
            int index = 0;
            for (int i = 0; i < intArray.length; i++) {
                if (minVal > intArray[i]) {
                    index = i;
                    minVal = intArray[i];
                }
            }
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] == index) {
                    val1x = 20;
                    val1y = -20;
                }
                sum1 += intArray[arr1[i]];
            }
            for (int i = 0; i < arr2.length; i++) {
                if (arr2[i] == index) {
                    val2x = -20;
                    val2y = -20;
                }
                sum2 += intArray[arr2[i]];
            }

            for (int i = 0; i < arr3.length; i++) {
                if (arr3[i] == index) {
                    val3x = 20;
                    val3y = 20;
                }
                sum3 += intArray[arr3[i]];
            }

            for (int i = 0; i < arr4.length; i++) {
                if (arr4[i] == index) {
                    val4x = -20;
                    val4y = 20;
                }
                sum4 += intArray[arr4[i]];
            }

            sumArray[0] = sum1;
            sumArray[1] = sum2;
            sumArray[2] = sum3;
            sumArray[3] = sum4;

            minSum = sum1;
            int sumIndex = 0;
            for (int i = 0; i < sumArray.length; i++) {
                if (minSum > sumArray[i]) {
                    minSum = sumArray[i];
                    sumIndex = i;
                }
            }

            posRectifierX = val1x + val2x + val3x + val4x;
            posRectifierY = val1y + val2y + val3y + val4y;

            if (Math.abs(posRectifierX) == 0 && Math.abs(posRectifierY) == 0) {
                if (sumIndex == 1) {
                    posRectifierX += -20;
                    posRectifierY += 20;
                } else if (sumIndex == 2) {
                    posRectifierX += 20;
                    posRectifierY += 20;
                } else if (sumIndex == 3) {
                    posRectifierX += -20;
                    posRectifierY += -20;
                } else if (sumIndex == 4) {
                    posRectifierX += 20;
                    posRectifierY += -20;
                }
            } else if (Math.abs(posRectifierX) == 0) {
                if (sumIndex == 1) {
                    posRectifierX += -20;
                } else if (sumIndex == 2) {
                    posRectifierX += 20;
                } else if (sumIndex == 3) {
                    posRectifierX += -20;
                } else if (sumIndex == 4) {
                    posRectifierX += 20;
                }
            } else if (Math.abs(posRectifierY) == 0) {
                if (sumIndex == 1) {
                    posRectifierY += 20;
                } else if (sumIndex == 2) {
                    posRectifierY += 20;
                } else if (sumIndex == 3) {
                    posRectifierY += -20;
                } else if (sumIndex == 4) {
                    posRectifierY += -20;
                }
            }
            if (posRectifierX == -40) {
                posRectifierX += 20;
            } else if (posRectifierX == 40) {
                posRectifierX -= 20;
            } else if (posRectifierY == -40) {
                posRectifierY += 20;
            } else if (posRectifierY == 40) {
                posRectifierY -= 20;
            }
            //System.out.println(posRectifierX + " " + posRectifierY);
            switch (index) {
                case 0:
                    posX = positionMap[0][0] + posRectifierX;
                    posY = positionMap[0][1] + posRectifierY;
                    break;
                case 1:
                    posX = positionMap[1][0] + posRectifierX;
                    posY = positionMap[1][1] + posRectifierY;
                    break;
                case 2:
                    posX = positionMap[2][0] + posRectifierX;
                    posY = positionMap[2][1] + posRectifierY;
                    break;
                case 3:
                    posX = positionMap[3][0] + posRectifierX;
                    posY = positionMap[3][1] + posRectifierY;
                    break;
                case 4:
                    posX = positionMap[4][0] + posRectifierX;
                    posY = positionMap[4][1] + posRectifierY;
                    break;
                case 5:
                    posX = positionMap[5][0] + posRectifierX;
                    posY = positionMap[5][1] + posRectifierY;
                    break;
                case 6:
                    posX = positionMap[6][0] + posRectifierX;
                    posY = positionMap[6][1] + posRectifierY;
                    break;
                case 7:
                    posX = positionMap[7][0] + posRectifierX;
                    posY = positionMap[7][1] + posRectifierY;
                    break;
                case 8:
                    posX = positionMap[8][0] + posRectifierX;
                    posY = positionMap[8][1] + posRectifierY;
                    break;
            }
            t = new Timer(2, new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    //dotPointer.setVisible(true);
                    dotPointer.setBounds(posX, posY, 40, 40);
                }
            });
            t.start();
            t.setRepeats(false);
            count = 0;

        }
    }

    //start shortest path finding and serial connection when the icon is dropped
    private void dotPointerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dotPointerMouseReleased

    }//GEN-LAST:event_dotPointerMouseReleased

    //background options
    private void backgroundLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backgroundLabelMousePressed

    }//GEN-LAST:event_backgroundLabelMousePressed

    private void backgroundLabelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backgroundLabelMouseDragged

    }//GEN-LAST:event_backgroundLabelMouseDragged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SelectedMapExtended.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectedMapExtended.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectedMapExtended.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectedMapExtended.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelectedMapExtended().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JLabel dotPointer;
    private javax.swing.JLabel mapLabel;
    // End of variables declaration//GEN-END:variables
}
