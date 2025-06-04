package com.maislimpo.maislimpo.view;


import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.maislimpo.maislimpo.entity.Usuario; // Para guardar o usuário logado
import com.maislimpo.maislimpo.service.DenunciaService;

@Component 
public class TelaDenuncias extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L; 
	private String nomePraia; 
	private Usuario usuarioLogado; 
	private TelaPrincipal telaOrigem; 
	private final ApplicationContext applicationContext; 
	private final DenunciaService denunciaService;


	private javax.swing.JButton botaoEnviar;
	private javax.swing.JToggleButton botaoVoltar;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTextAreaDenuncia;
	private javax.swing.JLabel labelPraia;

	@Autowired 
	public TelaDenuncias(ApplicationContext applicationContext, DenunciaService denunciaService) {
		this.applicationContext = applicationContext; 
		this.denunciaService = denunciaService;
		initComponents();
	}

	public void abrirJanelaParaDenuncia(String nomePraia, Usuario usuarioLogado, TelaPrincipal telaOrigem) { 																								// 4
		this.nomePraia = nomePraia;
		this.usuarioLogado = usuarioLogado;
		this.telaOrigem = telaOrigem;

		if (this.nomePraia != null) {
			labelPraia.setText("Praia selecionada: " + this.nomePraia);
		} else {
			labelPraia.setText("Praia não especificada");
		}

		if (this.usuarioLogado != null) {
			System.out.println("LOG TelaDenuncias: Abrindo para denúncia na praia '" + this.nomePraia
					+ "' pelo usuário: " + this.usuarioLogado.getEmail() + " (ID: " + this.usuarioLogado.getId() + ")");
		} else {

			System.err
			.println("ALERTA TelaDenuncias: Usuário logado não foi fornecido ao abrir a janela de denúncias.");
			JOptionPane.showMessageDialog(this,
			"Erro crítico: Informações do usuário não disponíveis.\nPor favor, tente novamente a partir da tela principal.",
					"Erro de Sessão", JOptionPane.ERROR_MESSAGE);
		}

		jTextAreaDenuncia.setText("");

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void initComponents() {

		labelPraia = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextAreaDenuncia = new javax.swing.JTextArea();
		botaoEnviar = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		botaoVoltar = new javax.swing.JToggleButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // fecha só a janela, não o app inteiro
		setTitle("Projeto Mais Limpo - Registrar Denúncia");
		setBackground(new java.awt.Color(255, 255, 255));

		labelPraia.setFont(new java.awt.Font("Arial Black", 1, 18)); 
		labelPraia.setForeground(new java.awt.Color(0, 0, 255));
		labelPraia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelPraia.setText("Praia: [Nome da Praia]"); 

		jTextAreaDenuncia.setBackground(new java.awt.Color(153, 255, 255));
		jTextAreaDenuncia.setColumns(20);
		jTextAreaDenuncia.setFont(new java.awt.Font("Arial", 0, 12));
		jTextAreaDenuncia.setForeground(new java.awt.Color(0, 0, 0));
		jTextAreaDenuncia.setRows(5);
		jScrollPane1.setViewportView(jTextAreaDenuncia);

		botaoEnviar.setBackground(new java.awt.Color(102, 204, 255));
		botaoEnviar.setFont(new java.awt.Font("Arial Black", 1, 12));
		botaoEnviar.setForeground(new java.awt.Color(0, 0, 255));
		botaoEnviar.setText("Enviar denúncia");
		botaoEnviar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoEnviarActionPerformed(evt);
			}
		});

		jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); 
		jLabel1.setForeground(new java.awt.Color(102, 153, 255));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("Digite a sua denúncia abaixo:");

		botaoVoltar.setBackground(new java.awt.Color(102, 204, 255));
		botaoVoltar.setFont(new java.awt.Font("Arial Black", 1, 12));
		botaoVoltar.setForeground(new java.awt.Color(0, 0, 255));
		botaoVoltar.setText("Voltar à Tela Principal");
		botaoVoltar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoVoltarActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(labelPraia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGap(0, 83, Short.MAX_VALUE)
														
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jScrollPane1) 
								.addComponent(botaoVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE) 
								.addComponent(botaoEnviar, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(83, 83, 83)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(37, 37, 37).addComponent(labelPraia)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel1)
						.addGap(18, 18, 18) 
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
								javax.swing.GroupLayout.PREFERRED_SIZE) 
						.addGap(18, 18, 18) 
						.addComponent(botaoEnviar).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(botaoVoltar).addContainerGap(42, Short.MAX_VALUE)));

		pack(); 
	}// </editor-fold>//GEN-END:initComponents

	private void botaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {
		String denunciaTexto = jTextAreaDenuncia.getText().trim();
		if (denunciaTexto.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Digite sua denúncia antes de enviar.", "Denúncia Vazia",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (this.usuarioLogado == null) {
			JOptionPane.showMessageDialog(this,
					"Não foi possível identificar o usuário. Por favor, tente logar novamente.", "Erro de Usuário",
					JOptionPane.ERROR_MESSAGE);
			
			 if (telaOrigem != null) { telaOrigem.setVisible(true); } this.dispose();
			 return;
		}

		if (this.nomePraia == null || this.nomePraia.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Não foi possível identificar a praia. Por favor, tente novamente a partir da tela principal.",
					"Erro de Praia", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (this.denunciaService == null) {
			JOptionPane.showMessageDialog(this,
					"Erro interno: Serviço de denúncias não está disponível. Contate o suporte.",
					"Erro Crítico de Configuração", JOptionPane.ERROR_MESSAGE);
			System.err.println("ALERTA TelaDenuncias: denunciaService é null no botaoEnviarActionPerformed.");
			return;
		}

		try {
			this.denunciaService.registrarNovaDenuncia(this.usuarioLogado, this.nomePraia, denunciaTexto);

			JOptionPane.showMessageDialog(this,
					"Denúncia registrada com sucesso!\nMuito obrigado pela sua colaboração em manter nossas praias limpas!",
					"Denúncia Enviada!", JOptionPane.INFORMATION_MESSAGE);

			jTextAreaDenuncia.setText(""); 

//			 int resposta = JOptionPane.showConfirmDialog(this, "Deseja fazer outra denúncia nesta praia?",
//					"Continuar?", JOptionPane.YES_NO_OPTION);
//			 if (resposta == JOptionPane.NO_OPTION) {
//			 botaoVoltarActionPerformed(null); 
//			 }

		} catch (IllegalArgumentException iae) {
			
			JOptionPane.showMessageDialog(this, "Não foi possível registrar a denúncia:\n" + iae.getMessage(),
					"Erro de Validação", JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Ocorreu um erro inesperado ao tentar registrar sua denúncia.\nPor favor, tente novamente mais tarde.\nDetalhe: "
							+ e.getMessage(),
					"Erro no Sistema", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(); 
		}
	}

	private void botaoVoltarActionPerformed(java.awt.event.ActionEvent evt) { 
		if (this.telaOrigem != null) {
			this.telaOrigem.setVisible(true); 
		} else {
			System.err.println("ALERTA TelaDenuncias: telaOrigem é null ao tentar voltar. Abrindo nova TelaPrincipal.");
			if (applicationContext != null) {
				TelaPrincipal novaTelaPrincipal = applicationContext.getBean(TelaPrincipal.class);
				novaTelaPrincipal.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Erro ao tentar voltar. Feche e abra o aplicativo.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		this.dispose(); 
	}

}