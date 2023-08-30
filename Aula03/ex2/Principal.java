package ex2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class Principal extends javax.swing.JFrame {

    Graphics2D g;

    DatagramSocket socket = null;

    public Principal() {
        initComponents();
        //Configuração da área de desenho no JLabel
        BufferedImage img = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);

        g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setBackground(Color.white);
        g.clearRect(0, 0, 400, 400);

        ImageIcon icon = new ImageIcon(img);
        jLblImagem.setIcon(icon);

        try {
            //cria o scket
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            System.out.println("Erro na criação do socket");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLblImagem = new javax.swing.JLabel();
        jcc = new javax.swing.JColorChooser();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLblImagem.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLblImagemMouseDragged(evt);
            }
        });

        jcc.setColor(java.awt.Color.black);

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton1.setText("\"CONECTAR\"");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblImagem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 381, Short.MAX_VALUE)
                        .addComponent(jcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblImagem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(177, 177, 177))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLblImagemMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblImagemMouseDragged
        Color cor = jcc.getColor();//pega a cor selecionada no JColorChooser

        String mensagem = "PIXEL:" + evt.getX() + "," + evt.getY() + "," + cor.getRed() + "," + cor.getGreen() + "," + cor.getBlue();

        enviarMensagem(mensagem);
    }//GEN-LAST:event_jLblImagemMouseDragged

    public void enviarMensagem(String mensagem) {
        try {
            byte buffer[] = mensagem.getBytes();
            DatagramPacket pacoteEnviar = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 1234);
            socket.send(pacoteEnviar);
        } catch (UnknownHostException ex) {
            System.out.println("Endereço não encontrado");
        } catch (IOException ex) {
            System.out.println("Erro de E/S");
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        enviarMensagem("ME_POE_NA_LISTA");

        //aguarda a lista de pixeis já desenhados
        byte bufferReceber[] = new byte[3200000];
        DatagramPacket pacoteReceber = new DatagramPacket(bufferReceber, bufferReceber.length);
        try {
            socket.receive(pacoteReceber);//bloqueante
            String mensagemRecebida = new String(pacoteReceber.getData());
            if (mensagemRecebida.contains("LISTA_PIXEIS")) {
                String msgPartes[] = mensagemRecebida.split(":");
                String dadosPixeis[] = msgPartes[1].split("_");
                for (int i = 0; i < dadosPixeis.length-1; i++) {
                    String dadosPixel[] = dadosPixeis[i].split(",");
                    int x = Integer.parseInt(dadosPixel[0]);
                    int y = Integer.parseInt(dadosPixel[1]);
                    int red = Integer.parseInt(dadosPixel[2]);
                    int green = Integer.parseInt(dadosPixel[3]);
                    int blue = Integer.parseInt(dadosPixel[4].trim());
                    Color cor = new Color(red, green, blue);
                    //desenha o pixel na imagem do JLabel
                    g.setColor(cor);
                    g.fillOval(x, y, 10, 10);
                }
                repaint();
            }
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //cria a thread que vai ficar recebedo pixeis
        new Thread() {
            public void run() {
                while (true) {
                    //espera chegar uma mensagem
                    byte bufferReceber[] = new byte[100];
                    //pacote sem destinatário, pois é para recber dados
                    DatagramPacket pacoteReceber = new DatagramPacket(bufferReceber, bufferReceber.length);
                    try {
                        socket.receive(pacoteReceber);//bloqueante
                        //Retira os dados recebidos do pacote
                        String mensagemRecebida = new String(pacoteReceber.getData());
                        //verifica o que contem na mensagem
                        if (mensagemRecebida.contains("PIXEL")) {
                            String msgPartes[] = mensagemRecebida.split(":");
                            String dadosPixel[] = msgPartes[1].split(",");
                            int x = Integer.parseInt(dadosPixel[0]);
                            int y = Integer.parseInt(dadosPixel[1]);
                            int red = Integer.parseInt(dadosPixel[2]);
                            int green = Integer.parseInt(dadosPixel[3]);
                            int blue = Integer.parseInt(dadosPixel[4].trim());
                            Color cor = new Color(red, green, blue);
                            //desenha o pixel na imagem do JLabel
                            g.setColor(cor);
                            g.fillOval(x, y, 10, 10);
                            repaint();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLblImagem;
    private javax.swing.JColorChooser jcc;
    // End of variables declaration//GEN-END:variables
}
