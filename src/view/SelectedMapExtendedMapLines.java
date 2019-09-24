
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 *
 * @author Nipuna
 */
public class SelectedMapExtendedMapLines extends javax.swing.JFrame {

    private int positionMap[][] = {{40, 455}, {250, 455}, {460, 455},
    {40, 260}, {250, 260}, {460, 260},
    {40, 40}, {250, 40}, {460, 40}};
    private int diagonalMap_1[][] = {{200, 200}, {300, 200}, {200, 300}, {300, 300}};// *
    private int diagonalMap_2[][] = {{150, 350}, {350, 350}, {150, 150}, {350, 150}};//   *
    private int diagonalMap_3[][] = {{100, 100}, {400, 100}, {100, 400}, {400, 400}};//     *

    private int diagonalMap_4[][] = {{200, 100}, {300, 100}, {100, 200}, {100, 300}, {200, 400}, {300, 400}, {400, 300}, {400, 200}};//*
    //private int diagonalMap_5[][]={{},{},{}};//     *
    private int tempvalue;
    //private String serialReading = "5000";
    private String content = "5000";
    private int intArray[] = new int[9];
    private int convertValues[] = new int[9];
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
    private int sumIndex = 0;
    private int previousPoint;

    private int posRectifierX = 0;
    private int posRectifierY = 0;

    private int minimumValIndex[] = new int[6];
    private int minimumValCount = 0;
    private boolean minimumValBoolean = false;

    private int firstCount = 0;
    private Timer t;

    public SelectedMapExtendedMapLines() {
        initComponents();
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
        getContentPane().add(dotPointer, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 265, 30, 30));

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

    private void serialValues() {
        System.out.println("Start");
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                while (!content.equals("10000")) {
                    try {
                        URL url = new URL("http://192.168.8.114");
                        URLConnection con = url.openConnection();
                        InputStream is = con.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String concatLine = null;
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            concatLine += (line);
                        }
                        int divIndex = concatLine.indexOf("<div");
                        divIndex = concatLine.indexOf(">", divIndex);
                        int endDivIndex = concatLine.indexOf("</div>", divIndex);
                        content = concatLine.substring(divIndex + 1, endDivIndex);
                        System.out.println(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String strArray[] = content.split(",");

                    if (strArray[0].trim().contains("A") && strArray[strArray.length - 1].trim().equals("BB") && strArray.length == 11 && content.trim().length() == 32) {
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
                                Thread.sleep(100);
                                publish(tempvalue);
                            } else {
                                if (strArray[i].trim().equals("**")) {
                                    tempvalue = 99;
                                    Thread.sleep(100);
                                    publish(tempvalue);
                                }
                            }
                            i++;
                        }
                    }
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
                setPositionBased(mostRecentValue);
            }
        };

        worker.execute();

    }

    private void convertValuesCommon() {
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] >= 0 && intArray[i] <= 4) {
                convertValues[i] = 2;
            } else if (intArray[i] >= 5 && intArray[i] <= 9) {
                convertValues[i] = 7;
            } else if (intArray[i] >= 10 && intArray[i] <= 14) {
                convertValues[i] = 12;
            } else if (intArray[i] >= 15 && intArray[i] <= 19) {
                convertValues[i] = 17;
            } else if (intArray[i] >= 20 && intArray[i] <= 24) {
                convertValues[i] = 22;
            } else if (intArray[i] >= 25 && intArray[i] <= 29) {
                convertValues[i] = 27;
            } else if (intArray[i] >= 30 && intArray[i] <= 34) {
                convertValues[i] = 32;
            } else if (intArray[i] >= 35 && intArray[i] <= 39) {
                convertValues[i] = 37;
            } else if (intArray[i] >= 40 && intArray[i] <= 44) {
                convertValues[i] = 42;
            } else if (intArray[i] >= 45 && intArray[i] <= 49) {
                convertValues[i] = 47;
            } else if (intArray[i] >= 50 && intArray[i] <= 54) {
                convertValues[i] = 52;
            } else if (intArray[i] >= 55 && intArray[i] <= 59) {
                convertValues[i] = 57;
            } else if (intArray[i] >= 60 && intArray[i] <= 64) {
                convertValues[i] = 62;
            } else if (intArray[i] >= 65 && intArray[i] <= 69) {
                convertValues[i] = 67;
            } else if (intArray[i] >= 70 && intArray[i] <= 74) {
                convertValues[i] = 72;
            } else if (intArray[i] >= 75 && intArray[i] <= 79) {
                convertValues[i] = 77;
            } else if (intArray[i] >= 80 && intArray[i] <= 84) {
                convertValues[i] = 82;
            } else if (intArray[i] >= 85 && intArray[i] <= 89) {
                convertValues[i] = 87;
            } else if (intArray[i] >= 90 && intArray[i] <= 94) {
                convertValues[i] = 92;
            } else if (intArray[i] >= 95 && intArray[i] <= 99) {
                convertValues[i] = 97;
            }
        }
    }

    private void posSelector() {
        if (((convertValues[arr1[0]] == convertValues[arr1[1]])
                && (convertValues[arr1[1]] == convertValues[arr1[2]])
                && (convertValues[arr1[2]] == convertValues[arr1[3]])
                && (convertValues[arr1[3]] == convertValues[arr1[0]])) && sumIndex == 1) {
            posX = diagonalMap_2[0][0];
            posY = diagonalMap_2[0][1];
        } else if (((convertValues[arr2[0]] == convertValues[arr2[1]])
                && (convertValues[arr2[1]] == convertValues[arr2[2]])
                && (convertValues[arr2[2]] == convertValues[arr2[3]])
                && (convertValues[arr2[3]] == convertValues[arr2[0]])) && sumIndex == 2) {
            posX = diagonalMap_2[1][0];
            posY = diagonalMap_2[1][1];
        } else if (((convertValues[arr3[0]] == convertValues[arr3[1]])
                && (convertValues[arr3[1]] == convertValues[arr3[2]])
                && (convertValues[arr3[2]] == convertValues[arr3[3]])
                && (convertValues[arr3[3]] == convertValues[arr3[0]])) && sumIndex == 3) {
            posX = diagonalMap_2[2][0];
            posY = diagonalMap_2[2][1];
        } else if (((convertValues[arr4[0]] == convertValues[arr4[1]])
                && (convertValues[arr4[1]] == convertValues[arr4[2]])
                && (convertValues[arr4[2]] == convertValues[arr4[3]])
                && (convertValues[arr4[3]] == convertValues[arr4[0]])) && sumIndex == 4) {
            posX = diagonalMap_2[3][0];
            posY = diagonalMap_2[3][1];
        }
    }

    private void setPositionBased(int val) {
        intArray[count] = val;
        count++;
        if (count == 9) {
            sum1 = 0;
            sum2 = 0;
            sum3 = 0;
            sum4 = 0;

            val1x = 0;
            val1y = 0;
            val2x = 0;
            val2y = 0;
            val3x = 0;
            val3y = 0;
            val4x = 0;
            val4y = 0;

            int index = 0;
            convertValuesCommon();
            minVal = intArray[0];

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

            for (int i = 0; i < intArray.length; i++) {
                if (minVal > intArray[i]) {
                    minVal = intArray[i];
                    index = i;
                }
            }

            if (firstCount == 0) {
                previousPoint = index;
                count = 0;
                firstCount = 1;
                return;
            }
            /*
            if (previousPoint == (index + 1) || previousPoint == (index - 1) || previousPoint == (index + 3) || previousPoint == (index - 3) || previousPoint == index) {
                previousPoint = index;
            } else if ((index % 2 == 0) && (previousPoint == index + 2 || previousPoint == index - 2)) {
                previousPoint = index;
            } else if ((index % 4 == 0) && (previousPoint == index + 4 || previousPoint == index - 4)) {
                previousPoint = index;
            } else if ((index % 2 != 0) && (previousPoint == index + 2 || previousPoint == index - 2)) {
                previousPoint = index;
            } else {
                return;
            }*/
            if ((previousPoint == 0) && (index == 1 || index == 3 || index == 4)) {
                previousPoint = index;
            } else if ((previousPoint == 1) && (index == 0 || index == 2 || index == 3 || index == 4 || index == 5)) {
                previousPoint = index;
            } else if ((previousPoint == 2) && (index == 4 || index == 1 || index == 5)) {
                previousPoint = index;
            } else if ((previousPoint == 3) && (index == 0 || index == 1 || index == 4 || index == 6 || index == 7)) {
                previousPoint = index;
            } else if ((previousPoint == 4) && (index == 0 || index == 1 || index == 2 || index == 3 || index == 5 || index == 6 || index == 7 || index == 8)) {
                previousPoint = index;
            } else if ((previousPoint == 5) && (index == 1 || index == 2 || index == 4 || index == 7 || index == 8)) {
                previousPoint = index;
            } else if ((previousPoint == 6) && (index == 3 || index == 4 || index == 7)) {
                previousPoint = index;
            } else if ((previousPoint == 7) && (index == 3 || index == 4 || index == 6 || index == 5 || index == 8)) {
                previousPoint = index;
            } else if ((previousPoint == 8) && (index == 7 || index == 4 || index == 5)) {
                previousPoint = index;
            } else if (previousPoint == index) {
                previousPoint = index;
            } else {
                minimumValIndex[minimumValCount] = index;
                minimumValCount++;
                if (minimumValCount == 6) {
                    minimumValCount = 0;
                    for (int i = 0; i < minimumValIndex.length; i++) {
                        if (minimumValIndex[0] == minimumValIndex[i]) {
                            minimumValBoolean = true;
                        } else {
                            minimumValBoolean = false;
                            break;
                        }
                    }
                    if (minimumValBoolean == true) {
                        previousPoint = index;
                    } else {
                        count = 0;
                        return;
                    }
                } else if (minimumValCount != 4) {
                    count = 0;
                    return;
                }
            }

            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] == index) {
                    val1x = 50;
                    val1y = -50;
                }
                sum1 += convertValues[arr1[i]];
            }
            for (int i = 0; i < arr2.length; i++) {
                if (arr2[i] == index) {
                    val2x = -50;
                    val2y = -50;
                }
                sum2 += convertValues[arr2[i]];
            }
            for (int i = 0; i < arr3.length; i++) {
                if (arr3[i] == index) {
                    val3x = 50;
                    val3y = 50;
                }
                sum3 += convertValues[arr3[i]];
            }
            for (int i = 0; i < arr4.length; i++) {
                if (arr4[i] == index) {
                    val4x = -50;
                    val4y = 50;
                }
                sum4 += convertValues[arr4[i]];
            }

            posRectifierX = val1x + val2x + val3x + val4x;
            posRectifierY = val1y + val2y + val3y + val4y;

            sumArray[0] = sum1;
            sumArray[1] = sum2;
            sumArray[2] = sum3;
            sumArray[3] = sum4;

            minSum = sum1;
            sumIndex = 1;
            for (int i = 0; i < sumArray.length; i++) {
                if (minSum > sumArray[i]) {
                    minSum = sumArray[i];
                    sumIndex = i + 1;
                }
            }

            if (Math.abs(posRectifierX) == 0 && Math.abs(posRectifierY) == 0) {
                if (sumIndex == 1) {
                    posRectifierX += -50;
                    posRectifierY += 50;
                } else if (sumIndex == 2) {
                    posRectifierX += 50;
                    posRectifierY += 50;
                } else if (sumIndex == 3) {
                    posRectifierX += -50;
                    posRectifierY += -50;
                } else if (sumIndex == 4) {
                    posRectifierX += 50;
                    posRectifierY += -50;
                }
            } else if (Math.abs(posRectifierX) == 0) {
                if (sumIndex == 1) {
                    posRectifierX += -50;
                } else if (sumIndex == 2) {
                    posRectifierX += 50;
                } else if (sumIndex == 3) {
                    posRectifierX += -50;
                } else if (sumIndex == 4) {
                    posRectifierX += 50;
                }
            } else if (Math.abs(posRectifierY) == 0) {
                if (sumIndex == 1) {
                    posRectifierY += 50;
                } else if (sumIndex == 2) {
                    posRectifierY += 50;
                } else if (sumIndex == 3) {
                    posRectifierY += -50;
                } else if (sumIndex == 4) {
                    posRectifierY += -50;
                }
            }
            if (posRectifierX == -100) {
                posRectifierX += 50;
            } else if (posRectifierX == 100) {
                posRectifierX -= 50;
            } else if (posRectifierY == -100) {
                posRectifierY += 50;
            } else if (posRectifierY == 100) {
                posRectifierY -= 50;
            }

            switch (index) {
                case 0:
                    posX = positionMap[0][0] + posRectifierX;
                    posY = positionMap[0][1] + posRectifierY;
                    posSelector();
                    break;
                case 1:
                    posX = positionMap[1][0] + posRectifierX;
                    posY = positionMap[1][1] + posRectifierY;
                    posSelector();
                    break;
                case 2:
                    posX = positionMap[2][0] + posRectifierX;
                    posY = positionMap[2][1] + posRectifierY;
                    posSelector();
                    break;
                case 3:
                    posX = positionMap[3][0] + posRectifierX;
                    posY = positionMap[3][1] + posRectifierY;
                    posSelector();
                    break;
                case 4:
                    posX = positionMap[4][0] + posRectifierX;
                    posY = positionMap[4][1] + posRectifierY;
                    posSelector();
                    break;
                case 5:
                    posX = positionMap[5][0] + posRectifierX;
                    posY = positionMap[5][1] + posRectifierY;
                    posSelector();
                    break;
                case 6:
                    posX = positionMap[6][0] + posRectifierX;
                    posY = positionMap[6][1] + posRectifierY;
                    posSelector();
                    break;
                case 7:
                    posX = positionMap[7][0] + posRectifierX;
                    posY = positionMap[7][1] + posRectifierY;
                    posSelector();
                    break;
                case 8:
                    posX = positionMap[8][0] + posRectifierX;
                    posY = positionMap[8][1] + posRectifierY;
                    posSelector();
                    break;
            }
            t = new Timer(2, new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
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
            java.util.logging.Logger.getLogger(SelectedMapExtendedMapLines.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectedMapExtendedMapLines.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectedMapExtendedMapLines.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectedMapExtendedMapLines.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelectedMapExtendedMapLines().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JLabel dotPointer;
    private javax.swing.JLabel mapLabel;
    // End of variables declaration//GEN-END:variables
}
