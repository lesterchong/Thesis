/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bavaria;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import model.Recommendation;
import model.UtilityMatrix;

/**
 *
 * @author Arturo III
 */
public class Tracker extends javax.swing.JFrame {

    /**
     * Creates new form Tracker
     */
    public Tracker(String str) {
        super(str);
        initComponents();
        readFromFile();
    }

    public Tracker()
    {
        initComponents();
    }
    
    public void writeToFile()
    {
        this.addText(" ************** CLOSED PLAYER ***************** ");
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter("userinfo.txt"));
            String str = TextLog.getText();
            String st[] = str.split("\n");
            for (int i = 0;i < st.length;i++)
            {
                out.write(st[i]);
                out.newLine();
            }
            out.close();
        }
        catch (IOException e)
        {
            System.out.println ("Unable to provide a new update to user");
        }
    }
    
    public void readFromFile()
    {
        try
        {
            Scanner s = new Scanner(new FileReader("userinfo.txt"));
            while (s.hasNextLine())
            {
                String str = s.nextLine();
                TextLog.append(str + "\n");
            }
            this.addText(" ************** OPENED PLAYER ***************** ");
        }
        catch (IOException e)
        {
            System.out.println ("No user file exists yet");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        TextLog = new javax.swing.JTextArea();
        recoButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        TextLog.setEditable(false);
        TextLog.setColumns(20);
        TextLog.setRows(5);
        jScrollPane1.setViewportView(TextLog);

        recoButton.setText("Recommend");
        recoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(recoButton)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recoButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void recoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recoButtonActionPerformed
        UtilityMatrix um = new UtilityMatrix();
        Recommendation reco = new Recommendation(um);
        reco.Recommend();
    }//GEN-LAST:event_recoButtonActionPerformed

    public void addText(String str)
    {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        TextLog.append(dateFormat.format(date)+", "+str+"\n");
    }
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
            java.util.logging.Logger.getLogger(Tracker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tracker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tracker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tracker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tracker().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TextLog;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton recoButton;
    // End of variables declaration//GEN-END:variables
}
