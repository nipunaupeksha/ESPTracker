/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import arduino.Arduino;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;

/**
 *
 * @author Nipuna
 */
public class ViewForm extends javax.swing.JFrame {

    /**
     * Creates new form ViewForm
     */
    private String serialReading = "20000";
    String input = JOptionPane.showInputDialog("Input COM port number");
    Arduino arduino = new Arduino("COM" + input, 9600);
    private int tempvalue;
    private int intArray[] = new int[4];
    private int count = 0;
    private Timer t;
    private int posX = 0;
    private int posY = 0;
    private int minval = Integer.MAX_VALUE;

    public ViewForm() {
        initComponents();
        arduino.openConnection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        arduinoRead();
    }

    public void arduinoRead() {
        System.out.println("Start");
        System.out.println(serialReading);
        System.out.println(arduino.serialRead());
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                while (!serialReading.equals("5000")) {
                    serialReading = arduino.serialRead();
                    serialReading = serialReading.trim();
                    //String[] strArray = serialReading.split(",");
                    System.out.println(serialReading);

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
                    }
                    /*
                    if (isdigit && tempvalue < 9) {
                        System.out.println("Running " + tempvalue);
                        
                    }*/ 
                }
                // Here we can return some object of whatever type
                // we specified for the first template parameter.
                // (in this case we're auto-boxing 'true').
                return true;
            }

            // Can safely update the GUI from this method.
            protected void done() {
                boolean status;
                try {
                    // Retrieve the return value of doInBackground.
                    status = get();
                    //setPosition(status);
                    //pathText.setText("Completed with status: " + status);
                } catch (InterruptedException e) {
                    // This is thrown if the thread's interrupted.
                } catch (ExecutionException e) {
                    // This is thrown if we throw an exception
                    // from doInBackground.
                }
            }

            @Override
            // Can safely update the GUI from this method.
            protected void process(List<Integer> chunks) {
                // Here we receive the values that we publish().
                // They may come grouped in chunks.
                int mostRecentValue = chunks.get(chunks.size() - 1);
                //System.out.println(mostRecentValue);
                setPosition(mostRecentValue);
                //Integer.toString(mostRecentValue)
            }

        };

        worker.execute();

    }

    public void setPosition(int recentValue) {
        intArray[count] = recentValue;
        count++;
        if (count == 4) {
            int a = intArray[0];
            int b = intArray[1];
            int c = intArray[2];
            int d = intArray[3];

            minval = intArray[0];
            int index = 0;
            if (a == b && b == c && c == d && d == a) {
                posX = 200;
                posY = 200;
            } else {
                for (int i = 0; i < 4; i++) {
                    if (minval > intArray[i]) {
                        minval = intArray[i];
                        index = i + 1;
                    }
                }
                switch (index) {
                    case 1:
                        posX = 120;
                        posY = 270;
                        break;
                    case 2:
                        posX = 300;
                        posY = 270;
                        break;
                    case 3:
                        posX = 120;
                        posY = 90;
                        break;
                    case 4:
                        posX = 300;
                        posY = 90;
                        break;
                }

            }
            t = new Timer(2, new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    jLabel5.setBounds(posX, posY, 40, 40);
                }
            });
            t.start();
            t.setRepeats(false);
            count = 0;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("4");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 30, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("3");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 30, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 30, 40));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 30, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reddot16.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ViewForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
