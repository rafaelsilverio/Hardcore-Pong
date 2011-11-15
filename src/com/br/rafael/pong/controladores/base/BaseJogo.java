package com.br.rafael.pong.controladores.base;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.elementos.pong.Bola;
import com.br.rafael.pong.elementos.pong.BotaoAcao;
import com.br.rafael.pong.elementos.pong.Jogador;

public class BaseJogo implements Jogo {

	//Mantem flag que configura jogo multiplayer ou single
	private boolean multiplayer = false;
	
	//Mantem flag que define se o jogo ja acabou
	private boolean fimJogo = false;
	
	//Mantem como atributo os elementos do jogo
	private Jogador player1;
	private Jogador player2;
	private Bola bolaInicial;
	private BotaoAcao acaoSubir;
	private BotaoAcao acaoDescer;
	
	//Mantem como atributo as dimensoes maximas do jogo
	private int alturaJogo;
	private int larguraJogo;
	
	//Declara as proporções dos elementos 
	private float proporcaoAlturaPlayer = (float) 0.27;
	private float proporcaoLarguraPlayer = (float) 0.05;
	private float proporcaoLadoBotao = (float) 0.48;
	private float proporcaoRaioBola = (float) 0.02;
	private float proporcaoTamanhoFonte = (float) 0.1;
	private float proporcaoPlayersLaterais = (float) 0.083;
	private float proporcaoVelicidadeJogadores = (float) 0.018;
	private float proporcaoVelicidadeBola = (float) 0.00875;
	private float proporcaoAceleradorBola = (float) 0.00032;
	
	//Declara os valores brutos, após calculo de proporcao com o tamanho da tela do celular
	private float distanciaPlayersLaterais;
	private float velicidadeJogadores;
	private float velicidadeBola;
	private float aceleradorBola;
	private float lateralBotoes;
	private float alturaPlayer;
	private float larguraPlayer;
	private float posicaoYPlayers;
	private float raioBola;
	
	/**
	 * Inicia e instancia o player 1
	 */
	public void instanciaP1(){
		player1 = new Jogador(1 + distanciaPlayersLaterais, posicaoYPlayers, alturaPlayer, larguraPlayer, velicidadeJogadores);
		player1.iniciaPlacar(((larguraJogo/2) - 2*(alturaJogo * proporcaoTamanhoFonte)), (alturaJogo * proporcaoTamanhoFonte), (alturaJogo * proporcaoTamanhoFonte));
		player1.defineCores(255, 0, 255, 0);
	}
	
	/**
	 * Inicia e instancia o player 2
	 */
	public void instanciaP2(){
		player2 = new Jogador((larguraJogo - larguraPlayer - distanciaPlayersLaterais), posicaoYPlayers, alturaPlayer, larguraPlayer, velicidadeJogadores);
		player2.iniciaPlacar(((larguraJogo/2) + 2*(alturaJogo * proporcaoTamanhoFonte)), (alturaJogo * proporcaoTamanhoFonte), (alturaJogo * proporcaoTamanhoFonte));
		player2.defineCores(255, 0, 255, 0);
	}	
	
	/**
	 * Inicia e instancia os botoes de acao
	 */
	public void instanciaBotoesAcao(){
		
		//Cria os botões de acao
		acaoSubir  = new BotaoAcao(1, 1, lateralBotoes, lateralBotoes);
		acaoDescer = new BotaoAcao(1, (alturaJogo - lateralBotoes), lateralBotoes, lateralBotoes);		
		
		//Define mensagem para os botoes
		acaoSubir.setMensagem(" Touch here_         to_  move up");
		acaoDescer.setMensagem(" Touch here_         to_ move down");		
	}
	
	/**
	 * Inicia a bola do jogo
	 */
	public void instanciaBolaPrincipal(){
		
		//Cria a bola do jogo (no centro da tela)
		bolaInicial = new Bola((larguraJogo /2), (alturaJogo / 2), raioBola, null, null, velicidadeBola, aceleradorBola);		
	}

	/**
	 * Construtor da classe, inicia todos os valores e personagens
	 * relacionados ao jogo, instanciando as classes e posicionando 
	 * os elementos.
	 * 
	 * @param alturaJogo 
	 * @param larguraJogo
	 */
	public BaseJogo(int alturaJogo, int larguraJogo){
		
		//Declara as proporcoes de movimento
		distanciaPlayersLaterais = larguraJogo * proporcaoPlayersLaterais;
		velicidadeJogadores = alturaJogo * proporcaoVelicidadeJogadores;
		velicidadeBola = larguraJogo * proporcaoVelicidadeBola;
		aceleradorBola = larguraJogo * proporcaoAceleradorBola;
		
		//Recebe as dimensoes do jogo
		this.alturaJogo = alturaJogo;
		this.larguraJogo = larguraJogo;		
		
		//Define a lateral dos botoes de acao
		lateralBotoes = alturaJogo * proporcaoLadoBotao;
		
		//Define largura e altura dos players
		alturaPlayer = alturaJogo * proporcaoAlturaPlayer;
		larguraPlayer = larguraJogo * proporcaoLarguraPlayer;
		
		//Define posicao Y dos players (centro)
		posicaoYPlayers = ((alturaJogo / 2) - (alturaPlayer/2));
		
		//Define raio da bola
		raioBola = larguraJogo * proporcaoRaioBola;
	}

	
	/**
	 * Desenha todas os elementos do jogo, utiliza o metodo de desenho de cada um deles
	 * em sequencia.
	 * 
	 * @param canvas
	 * @param paint
	 */
	public void desenha(Canvas canvas, Paint paint){
		
		//Limpa a tela para realizar novo desenho
		canvas.drawRGB(20, 20, 20);
		
		//Chama o metodo "desenha" de todos elementos envolvidos
		player1.desenha(canvas, paint);
		player2.desenha(canvas, paint);
		bolaInicial.desenha(canvas, paint);
		acaoSubir.desenha(canvas, paint);
		acaoDescer.desenha(canvas, paint);
		player1.getPlacar().desenha(canvas, paint);
		player2.getPlacar().desenha(canvas, paint);
	}	
	
	/**
	 * Atualiza o estado do jogo, calculando as proximas rotas
	 */
	public void atualiza(){

		//Atualiza a bola
		atualizaBolaInicial();
		
		//Atualiza a posicao dos players (caso eles tenham sido setados)
		if(player1 != null)
			player1.movimentar(0, alturaJogo);
		if(player2 != null)
			player2.movimentar(0, alturaJogo);
	}
	
	/**
	 * Atualiza a posicao do P2, com IA de nivel difi
	 */
	public void atualizaIAP2(int nivel){
		
		//Define a logica para cada nivel
		switch(nivel){
			case 10:
				
				//Move de acordo com a posicao da bolinha e relacao a seu centro, acima sobe e abaixo desce
				player2.setMovimento(true);
				if(bolaInicial.getPosY() > player2.pontoMedioY())
					player2.setMovimentoAtual(Jogador.MOVIMENTO_ABAIXO);
				else if(bolaInicial.getPosY() < player2.pontoMedioY())
					player2.setMovimentoAtual(Jogador.MOVIMENTO_ACIMA);
				else
					player2.setMovimento(false);	
				
				break;
			case 1:
				
				//Se a bola estiver em 60% da tela de distancia do player 2, faz ele seguir a bola
				if(bolaInicial.getPosX() > (larguraJogo * 0.4)){
					
					//Move de acordo com a posicao da bolinha e relacao a seu centro, acima sobe e abaixo desce
					player2.setMovimento(true);
					if(bolaInicial.getPosY() > player2.pontoMedioY())
						player2.setMovimentoAtual(Jogador.MOVIMENTO_ABAIXO);
					else if(bolaInicial.getPosY() < player2.pontoMedioY())
						player2.setMovimentoAtual(Jogador.MOVIMENTO_ACIMA);
					else
						player2.setMovimento(false);						
				}
				else{
					player2.setMovimento(false);
				}

				
				break;				
				
		}
	}
	
	/**
	 * Contem todas as verificacos sobre a atualizacao da bola inicial
	 */
	public void atualizaBolaInicial(){
		
		//Se a bola encosta em uma deadzone
		if(bolaInicial.encostaDeadzone(0, larguraJogo)){

			//Verifica quem deve ganhar o ponto
			if(bolaInicial.getPosX() < (larguraJogo/2))
				player2.getPlacar().aumentaPontos();
			else
				player1.getPlacar().aumentaPontos();
			
			//Recebe a direcao atual da bola
			int direcao = bolaInicial.getDirecaoBolaX();
			
			//Reinicia a bola
			bolaInicial.reiniciaBola();
			
			//Inverte a direcao da bola
			bolaInicial.setDirecaoBolaX(direcao*(-1));
		}
		
		//Se a bola encostou em um dos players
		else{
		
			//Recebe o resultado da verificacao de colisao com player
			boolean colisaoP1 = player1.verificaColisao(bolaInicial);
			boolean colisaoP2 = player2.verificaColisao(bolaInicial);
			
			//Se colidiu com algum player
			if(colisaoP1 || colisaoP2){
				
				//Acelera o movimento
				bolaInicial.aumentaVelocidade();
				
				//Define logica de colisao com a bola
				inclinaBolaColisao(colisaoP1, colisaoP2);
				
				//Define para o player que houve uma colisao
				if(colisaoP1){
					player1.setColidiu(true);
				}
				else{
					player2.setColidiu(true);
				}
				
			}
			//Checa se bateu nas laterais
			else if(bolaInicial.encostaParede(0, alturaJogo)){
				
				//Inverte direcao de Y
				bolaInicial.setDirecaoBolaY(bolaInicial.getDirecaoBolaY() * -1);				
			}
			
			//Atualiza seu movimento
			bolaInicial.movimenta();
			
			//Ao mover a bola, pode ocorrer uma colisão em que a bola entra no elemento, sua posicao deve ser remanejada nesse caso
			remanejaPosicaoBola();
		}		
	}
	
	//Em caso de colisão prévia, remaneja as posicoes da bola
	public void remanejaPosicaoBola(){
		
		//Recebe o resultado da verificacao de colisao com player
		boolean colisaoP1 = player1.verificaColisao(bolaInicial);
		boolean colisaoP2 = player2.verificaColisao(bolaInicial);
		
		//Se colidiu com algum player
		if(colisaoP1 || colisaoP2){
			
			//Recebe os dados de quem colidiu
			float colisaoX = colisaoP1 ? player1.getPosX() : player2.getPosX();
			float colisaoY = colisaoP1 ? player1.getPosY() : player2.getPosY();
			float colisaoLargura = colisaoP1 ? player1.getLargura() : player2.getLargura();
			float colisaoAltura  = colisaoP1 ? player1.getAltura() : player2.getAltura();
			
			//Se bolinha estiver na lateral, altera seu X
			if((bolaInicial.getPosY() <= colisaoY + colisaoAltura) && (bolaInicial.getPosY() >= colisaoY)){
				
				//Altera a posicao depentendo de o circulo estiver a direita ou esquerda do elemento
				if(bolaInicial.getPosX() <= colisaoX){
					bolaInicial.setPosX(colisaoX - bolaInicial.getRaio());
				}
				else{
					bolaInicial.setPosX(colisaoX + colisaoLargura  + bolaInicial.getRaio());
				}
			}
			//Se a bolinha estiver acima ou abaixo, altera apenas seu Y
			else if((bolaInicial.getPosX() <= colisaoX + colisaoLargura) && (bolaInicial.getPosX() >= colisaoX)){
			
				//Altera a posicao depentendo de o circulo estiver acima ou abaixo
				if(bolaInicial.getPosY() <= colisaoY){
					bolaInicial.setPosY(colisaoY - bolaInicial.getRaio());
				}
				else{
					bolaInicial.setPosY(colisaoY + colisaoAltura  + bolaInicial.getRaio());
				}					
			}
		}
		//Checa se bateu nas laterais
		else if(bolaInicial.encostaParede(0, alturaJogo)){
			
			//Remaneja posicao Y da bolinha de acordo com o local da colisao
			if(bolaInicial.getPosY() < (alturaJogo / 2)){
				bolaInicial.setPosY(bolaInicial.getRaio());
			}
			else{
				bolaInicial.setPosY(alturaJogo - bolaInicial.getRaio());
			}
		}
	}	
	
	//Realiza logica de inclinação da bola, quando ocorre colisao com players
	public void inclinaBolaColisao(boolean colisaoP1, boolean colisaoP2){
		
		//Inicia distancia do centro dos players com a bola
		float distanciaCentro = 0;				
		
		//Define o que fazer com colisao em p1
		if(colisaoP1){
			bolaInicial.setDirecaoBolaX(1);
			
			//Define a distancia da bola com a media
			distanciaCentro = player1.pontoMedioY() - bolaInicial.getPosY();
		}
		
		//Define o que fazer com colisao em p2
		else{
			bolaInicial.setDirecaoBolaX(-1);
			
			//Define a distancia da bola com a media
			distanciaCentro = player2.pontoMedioY() - bolaInicial.getPosY();
		}
		
		//Se não for zero
		if(distanciaCentro != 0){
			
			//Define a direcao do movimento
			bolaInicial.setDirecaoBolaY(distanciaCentro < 0 ? 1 : -1);						
			
			//Recebe o modulo da distancia
			distanciaCentro = distanciaCentro < 0 ? distanciaCentro * -1 : distanciaCentro;
			
			//Recebe o resto com a diferença da velocidade
			distanciaCentro = distanciaCentro % bolaInicial.getVelocidadeMovimento();
			
			//Define o escalar
			bolaInicial.setEscalarY(distanciaCentro);
		}		
	}	
	
	/* Getters e Setters padrão */
	public Jogador getPlayer1() {
		return player1;
	}
	public void setPlayer1(Jogador player1) {
		this.player1 = player1;
	}
	public Jogador getPlayer2() {
		return player2;
	}
	public void setPlayer2(Jogador player2) {
		this.player2 = player2;
	}
	public Bola getBolaInicial() {
		return bolaInicial;
	}
	public void setBolaInicial(Bola bolaInicial) {
		this.bolaInicial = bolaInicial;
	}
	public BotaoAcao getAcaoSubir() {
		return acaoSubir;
	}
	public void setAcaoSubir(BotaoAcao acaoSubir) {
		this.acaoSubir = acaoSubir;
	}
	public BotaoAcao getAcaoDescer() {
		return acaoDescer;
	}
	public void setAcaoDescer(BotaoAcao acaoDescer) {
		this.acaoDescer = acaoDescer;
	}
	public boolean isMultiplayer() {
		return multiplayer;
	}
	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}
	public int getAlturaJogo() {
		return alturaJogo;
	}
	public int getLarguraJogo() {
		return larguraJogo;
	}
	public boolean isFimJogo() {
		return fimJogo;
	}
	public void setFimJogo(boolean fimJogo) {
		this.fimJogo = fimJogo;
	}	
}
