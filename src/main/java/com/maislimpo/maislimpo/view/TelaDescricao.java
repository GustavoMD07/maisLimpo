package com.maislimpo.maislimpo.view;

import java.awt.Color;
import java.net.URL; 
import javax.swing.ImageIcon; 

public class TelaDescricao extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;

    private TelaLogin telaLogin; 

    public TelaDescricao(TelaLogin telaLogin) {
        this.telaLogin = telaLogin;
        initComponents(); 
        setTitle("Projeto Mais Limpo - Descrição do Projeto"); 
        setResizable(false); 
        setLocationRelativeTo(null); 
        getContentPane().setBackground(Color.WHITE);
    }

    public TelaDescricao() {
        initComponents();
        setTitle("Projeto Mais Limpo - Descrição do Projeto");
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        if (botaoVoltar != null) { 
             botaoVoltar.setEnabled(false); 
        }
    }

    private void initComponents() {

        LogoMaisLimpo = new javax.swing.JLabel();
        NossoProjeto = new javax.swing.JLabel();
        QuemSomos = new javax.swing.JLabel();
        Linha1 = new javax.swing.JLabel();
        Linha2 = new javax.swing.JLabel();
        Linha3 = new javax.swing.JLabel();
        Linha4 = new javax.swing.JLabel();
        NossaIdeia = new javax.swing.JLabel();
        Linha5 = new javax.swing.JLabel();
        Linha6 = new javax.swing.JLabel();
        Linha7 = new javax.swing.JLabel();
        Linha8 = new javax.swing.JLabel();
        ComoFunciona = new javax.swing.JLabel();
        botaoVoltar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Projeto Mais Limpo - Descrição do Projeto"); 
        setBackground(new java.awt.Color(255, 255, 255));

        LogoMaisLimpo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        try {
            URL imgUrl = getClass().getResource("/imagens/Logo200x63.png");
            if (imgUrl != null) {
                LogoMaisLimpo.setIcon(new ImageIcon(imgUrl));
            } else {
                LogoMaisLimpo.setText("Logo não encontrada");
                System.err.println("Erro ao carregar imagem: /imagens/Logo200x63.png não encontrada no classpath.");
            }
        } catch (Exception e) {
            LogoMaisLimpo.setText("Erro ao carregar logo");
            System.err.println("Exceção ao carregar logo: " + e.getMessage());
            e.printStackTrace();
        }

        NossoProjeto.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        NossoProjeto.setForeground(new java.awt.Color(0, 102, 102));
        NossoProjeto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NossoProjeto.setText("Nosso Projeto");

        QuemSomos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        QuemSomos.setForeground(new java.awt.Color(0, 102, 102));
        QuemSomos.setText("Quem somos?");

        Linha1.setForeground(new java.awt.Color(0, 204, 204));
        Linha1.setText(" Somos a + Limpo, um projeto dedicado a preservar as praias de Santos, litoral paulista.");

        Linha2.setForeground(new java.awt.Color(0, 204, 204));
        Linha2.setText("Através de uma página, monitoramos a presença de resíduos sólidos nas praias, facilitando a");

        Linha3.setForeground(new java.awt.Color(0, 204, 204));
        Linha3.setText("comunicação entre voluntários, comunidade e ONGs. Nosso objetivo é fortalecer o combate");

        Linha4.setForeground(new java.awt.Color(0, 204, 204));
        Linha4.setText("aos resíduos e às mudanças climáticas.");

        NossaIdeia.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        NossaIdeia.setForeground(new java.awt.Color(0, 102, 102));
        NossaIdeia.setText("Nossa ideia");

        Linha5.setForeground(new java.awt.Color(0, 204, 204));
        Linha5.setText(" Pensando na importância da educação ambiental e na necessidade de ações mais eficientes,");

        Linha6.setForeground(new java.awt.Color(0, 204, 204));
        Linha6.setText("criamos uma API que funciona como uma rede de dados alimentada por voluntários.");

        Linha7.setForeground(new java.awt.Color(0, 204, 204));
        Linha7.setText("Essas informações ajudam a identificar áreas com maior quantidade de resíduos, permitindo");

        Linha8.setForeground(new java.awt.Color(0, 204, 204));
        Linha8.setText("que ONGs e voluntários direcionem seus esforços de forma mais estratégica e eficaz.");

        ComoFunciona.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ComoFunciona.setForeground(new java.awt.Color(0, 102, 102));
        ComoFunciona.setText("Como Funciona");

        botaoVoltar.setBackground(new java.awt.Color(0, 102, 102));
        botaoVoltar.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        botaoVoltar.setForeground(new java.awt.Color(0, 204, 204));
        botaoVoltar.setText("VOLTAR");
        botaoVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoVoltarActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText(" Nosso sistema oferece cadastro de voluntários e ONGs, além de mapas interativos onde é");

        jLabel2.setForeground(new java.awt.Color(0, 204, 204));
        jLabel2.setText("possível registrar e detalhar avistamentos de resíduos. Com esses registros, elaboramos");

        jLabel3.setForeground(new java.awt.Color(0, 204, 204));
        jLabel3.setText("relatórios estatísticos que são disponibilizados para as ONGs na plataforma, promovendo maior");

        jLabel4.setForeground(new java.awt.Color(0, 204, 204));
        jLabel4.setText("engajamento na preservação das nossas praias.");

        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Juntos, podemos fazer a diferença e transformar nossas praias em ambientes mais limpos e sustentáveis!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LogoMaisLimpo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(NossoProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoVoltar)
                .addGap(41, 41, 41))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(ComoFunciona)
                    .addComponent(Linha8)
                    .addComponent(Linha7)
                    .addComponent(Linha6)
                    .addComponent(Linha5)
                    .addComponent(NossaIdeia)
                    .addComponent(Linha4)
                    .addComponent(QuemSomos, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Linha2, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                    .addComponent(Linha3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Linha1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(LogoMaisLimpo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NossoProjeto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QuemSomos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Linha1)
                .addGap(0, 0, 0)
                .addComponent(Linha2)
                .addGap(0, 0, 0)
                .addComponent(Linha3)
                .addGap(0, 0, 0)
                .addComponent(Linha4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NossaIdeia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Linha5)
                .addGap(0, 0, 0)
                .addComponent(Linha6)
                .addGap(0, 0, 0)
                .addComponent(Linha7)
                .addGap(0, 0, 0)
                .addComponent(Linha8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ComoFunciona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(botaoVoltar)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoVoltarActionPerformed
        this.setVisible(false); 
        
        if (this.telaLogin != null) {
            this.telaLogin.setVisible(true);
        }
        
//        this.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ComoFunciona;
    private javax.swing.JLabel Linha1;
    private javax.swing.JLabel Linha2;
    private javax.swing.JLabel Linha3;
    private javax.swing.JLabel Linha4;
    private javax.swing.JLabel Linha5;
    private javax.swing.JLabel Linha6;
    private javax.swing.JLabel Linha7;
    private javax.swing.JLabel Linha8;
    private javax.swing.JLabel LogoMaisLimpo;
    private javax.swing.JLabel NossaIdeia;
    private javax.swing.JLabel NossoProjeto;
    private javax.swing.JLabel QuemSomos;
    private javax.swing.JButton botaoVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}