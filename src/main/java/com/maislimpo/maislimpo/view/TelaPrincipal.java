package com.maislimpo.maislimpo.view;

import javax.swing.JOptionPane;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.maislimpo.maislimpo.entity.Usuario;

@Component
public class TelaPrincipal extends javax.swing.JFrame {
	private static final long serialVersionUID = 1529676205021341485L;

	private final ApplicationContext applicationContext;
	private Usuario usuarioLogado;

	public TelaPrincipal(ApplicationContext applicationContext) {

		this.applicationContext = applicationContext;
		initComponents();
		setLocationRelativeTo(null);

	}

	public void setUsuarioLogado(Usuario usuario) { 
		this.usuarioLogado = usuario;
		if (this.usuarioLogado != null) {
			System.out.println("LOG TelaPrincipal: Usuário " + this.usuarioLogado.getEmail() + " (Tipo: "
					+ this.usuarioLogado.getTipoUsuario() + ") está agora na tela principal.");
			JOptionPane.showMessageDialog(this, "Bem vindo ao sistema usuário " + usuario.getEmail());
			

		} else {
			System.out.println("LOG TelaPrincipal: Nenhum usuário logado foi definido.");
			JOptionPane.showMessageDialog(this, "Usuário não está logado. Por favor, faça o login novamente.",
					"Erro de Sessão", JOptionPane.ERROR_MESSAGE);
			
			if (applicationContext != null) {
	            TelaLogin telaLogin = applicationContext.getBean(TelaLogin.class);
	            telaLogin.setVisible(true);
	            
	        } else {
	            System.err.println("ERRO CRÍTICO: ApplicationContext é null na TelaPrincipal. Não é possível abrir TelaLogin.");
	        }
	        this.dispose(); 
		}
	}

	private void initComponents() {

		jLabel2 = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		botaoPontaDaPraia = new javax.swing.JButton();
		botaoAparecida = new javax.swing.JButton();
		botaoEmbare = new javax.swing.JButton();
		botaoBoqueirao = new javax.swing.JButton();
		botaoGonzaga = new javax.swing.JButton();
		botaoPompeia = new javax.swing.JButton();
		botãoJoseMenino = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Projeto Mais Limpo - Tela Principal");
		setBackground(new java.awt.Color(255, 255, 255));
		setResizable(false);

		jLabel2.setFont(new java.awt.Font("Calibri Light", 1, 22)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(0, 204, 153));
		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setText("Aperte no botão da respectiva praia que você está e faça a sua denúncia!");

		jPanel1.setLayout(null);

		botaoPontaDaPraia.setBackground(new java.awt.Color(181, 165, 153));
		botaoPontaDaPraia.setForeground(new java.awt.Color(0, 0, 0));
		botaoPontaDaPraia.setText("Ponta da Praia");
		botaoPontaDaPraia.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoPontaDaPraiaActionPerformed(evt);
			}
		});
		jPanel1.add(botaoPontaDaPraia);
		botaoPontaDaPraia.setBounds(860, 390, 120, 23);

		botaoAparecida.setBackground(new java.awt.Color(181, 165, 153));
		botaoAparecida.setForeground(new java.awt.Color(0, 0, 0));
		botaoAparecida.setText("Aparecida");
		botaoAparecida.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoAparecidaActionPerformed(evt);
			}
		});
		jPanel1.add(botaoAparecida);
		botaoAparecida.setBounds(790, 290, 95, 23);

		botaoEmbare.setBackground(new java.awt.Color(181, 165, 153));
		botaoEmbare.setForeground(new java.awt.Color(0, 0, 0));
		botaoEmbare.setText("Embaré");
		botaoEmbare.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoEmbareActionPerformed(evt);
			}
		});
		jPanel1.add(botaoEmbare);
		botaoEmbare.setBounds(680, 200, 95, 23);

		botaoBoqueirao.setBackground(new java.awt.Color(181, 165, 153));
		botaoBoqueirao.setForeground(new java.awt.Color(0, 0, 0));
		botaoBoqueirao.setText("Boqueirão");
		botaoBoqueirao.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoBoqueiraoActionPerformed(evt);
			}
		});
		jPanel1.add(botaoBoqueirao);
		botaoBoqueirao.setBounds(550, 120, 95, 23);

		botaoGonzaga.setBackground(new java.awt.Color(181, 165, 153));
		botaoGonzaga.setForeground(new java.awt.Color(0, 0, 0));
		botaoGonzaga.setText("Gonzaga");
		botaoGonzaga.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoGonzagaActionPerformed(evt);
			}
		});
		jPanel1.add(botaoGonzaga);
		botaoGonzaga.setBounds(380, 70, 90, 23);

		botaoPompeia.setBackground(new java.awt.Color(181, 165, 153));
		botaoPompeia.setForeground(new java.awt.Color(0, 0, 0));
		botaoPompeia.setText("Pompéia");
		botaoPompeia.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoPompeiaActionPerformed(evt);
			}
		});
		jPanel1.add(botaoPompeia);
		botaoPompeia.setBounds(220, 45, 90, 23);
		//x ,altura, tamanho, transparência

		botãoJoseMenino.setBackground(new java.awt.Color(181, 165, 153));
		botãoJoseMenino.setForeground(new java.awt.Color(0, 0, 0));
		botãoJoseMenino.setText("J.Menino");
		botãoJoseMenino.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botãoJoseMeninoActionPerformed(evt);
			}
		});
		jPanel1.add(botãoJoseMenino);
		botãoJoseMenino.setBounds(100, 30, 95, 23);

		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		try {
			java.net.URL imgUrlPraia = getClass().getResource("/imagens/Praia1000.png");
			if (imgUrlPraia != null) {
				jLabel3.setIcon(new javax.swing.ImageIcon(imgUrlPraia));
			} else {
				jLabel3.setText("Imagem Praia1000.png não encontrada");
				System.err.println("Erro: Imagem /imagens/Praia1000.png não encontrada no classpath.");
			}
		} catch (Exception e) {
			jLabel3.setText("Erro ao carregar imagem da praia");
			e.printStackTrace();
		}
		jPanel1.add(jLabel3);
		jLabel3.setBounds(-10, 0, 1020, 450);

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		try {
            java.net.URL imgUrlLogo = getClass().getResource("/imagens/Logo+Limpo400-removebg-preview.png");
            if (imgUrlLogo != null) {
                 jLabel1.setIcon(new javax.swing.ImageIcon(imgUrlLogo)); 
            } else {
                jLabel1.setText("Imagem LogoMaisLimpo400.png não encontrada");
                System.err.println("Erro: Imagem /imagens/LogoMaisLimpo400.png não encontrada no classpath.");
            }
        } catch (Exception e) {
            jLabel1.setText("Erro ao carregar logo");
            e.printStackTrace();
        } 
																																														
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE))
								.addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel2)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 451,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	private void abrirTelaDenunciaParaPraia(String nomePraia) {
        if (this.usuarioLogado == null) {
            JOptionPane.showMessageDialog(this, 
                "Erro: Usuário não está logado. Por favor, faça o login novamente.", 
                "Erro de Sessão", JOptionPane.ERROR_MESSAGE);
            
            TelaLogin telaLogin = applicationContext.getBean(TelaLogin.class);
            telaLogin.setVisible(true);
            this.dispose();
            return;
        }

        if (applicationContext == null) {
             JOptionPane.showMessageDialog(this, 
                "Erro crítico: ApplicationContext não está disponível.", 
                "Erro Interno", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            TelaDenuncias telaDenuncias = applicationContext.getBean(TelaDenuncias.class);
            telaDenuncias.abrirJanelaParaDenuncia(nomePraia, this.usuarioLogado, this);
            this.setVisible(false); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao tentar abrir a tela de denúncias: " + e.getMessage(),
                "Erro na Aplicação", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void botaoPompeiaActionPerformed(java.awt.event.ActionEvent evt) {
    	 abrirTelaDenunciaParaPraia("Pompéia");
    }

	private void botaoPontaDaPraiaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_botaoPontaDaPraiaActionPerformed
		 abrirTelaDenunciaParaPraia("Ponta da Praia");
	}// GEN-LAST:event_botaoPontaDaPraiaActionPerformed

	private void botaoAparecidaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_botaoAparecidaActionPerformed
		// TODO add your handling code here:
		 abrirTelaDenunciaParaPraia("Aparecida");
	}// GEN-LAST:event_botaoAparecidaActionPerformed

	private void botaoEmbareActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_botaoEmbareActionPerformed
		 abrirTelaDenunciaParaPraia("Embaré");
	}// GEN-LAST:event_botaoEmbareActionPerformed

	private void botaoBoqueiraoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_botaoBoqueiraoActionPerformed
		 abrirTelaDenunciaParaPraia("Boqueirão");
	}// GEN-LAST:event_botaoBoqueiraoActionPerformed

	private void botaoGonzagaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_botaoGonzagaActionPerformed
		 abrirTelaDenunciaParaPraia("Gonzaga");
	}// GEN-LAST:event_botaoGonzagaActionPerformed

	private void botãoJoseMeninoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_botãoJoseMeninoActionPerformed
		 abrirTelaDenunciaParaPraia("José Menino");
	}// GEN-LAST:event_botãoJoseMeninoActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton botaoAparecida;
	private javax.swing.JButton botaoBoqueirao;
	private javax.swing.JButton botaoEmbare;
	private javax.swing.JButton botaoGonzaga;
	private javax.swing.JButton botaoPompeia;
	private javax.swing.JButton botaoPontaDaPraia;
	private javax.swing.JButton botãoJoseMenino;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	// End of variables declaration//GEN-END:variables
}
