package com.br.rafael.pong.controladores.base;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.br.rafael.pong.elementos.bases.Elemento;
import com.br.rafael.pong.elementos.bases.Retangulo;
import com.br.rafael.pong.elementos.pong.Bola;
import com.br.rafael.pong.elementos.pong.BotaoAcao;
import com.br.rafael.pong.elementos.pong.Jogador;

/**
 * Classe base para o jogo, deve ser extendida por suas fases
 * 
 * @author Rafael
 *
 */
public class BaseJogo implements Jogo {

	//Mantem flag que configura jogo multiplayer ou single
	private boolean multiplayer = false;
	
	//Mantem flag que define se o jogo ja acabou
	private boolean fimJogo = false;
	
	//Mantem flag que define se a fase deve ser finalizada, e a próxima ser chamada
	private boolean proximaFase = false;
	
	//Mantem como atributo os elementos do jogo
	private Jogador player1;
	private Jogador player2;
	private Bola bolaInicial;
	private BotaoAcao acaoSubir;
	private BotaoAcao acaoDescer;
	private Retangulo modalFimJogo;
	
	//Cria uma lista de elementos avulsos, que podem ser cadastrados nas fases
	private List<Elemento> elementosAvulsos = new ArrayList<Elemento>();
	
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
	private float proporcaoModalFimJogo = (float) 0.80;
	
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
	private float larguraModal;
	private float alturaModal;
	
	//Mantem o numero de pontos que define se alguem ganhou
	private int pontosPartida;
	
	/**
	 * Instancia a modal que exibe mensagem de fim de jogo
	 */
	public void iniciaModalFimJogo(){
		modalFimJogo = new Retangulo(getMetadeTelaX() - larguraModal/2, 
				getMetadeTelaY() - alturaModal/2, 
				alturaModal, larguraModal);
		modalFimJogo.defineCores(255, 65, 105, 225);
	}
	
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
		
		//Define proporcoes de modal
		larguraModal = larguraJogo * proporcaoModalFimJogo;
		alturaModal =  alturaJogo * proporcaoModalFimJogo;
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
		
		//Se há elementos avulsos, percorre desenhando
		if(!elementosAvulsos.isEmpty()){
			for(Elemento elemento : elementosAvulsos){
				elemento.desenha(canvas, paint);
			}
		}
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
			case 9:
				
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
			case 1:
				
				//Se a bola estiver em 30% da tela de distancia do player 2, faz ele seguir a bola
				if(bolaInicial.getPosX() > (larguraJogo * 0.7)){
					
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
	 * Checa se a bola bate em alguma deadzone, realizando as ações necessárias
	 */
	public void checagemBolaInicialDeadzone(){
		
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
	}
	
	/**
	 * Checa se a bola bate em alguma parede limitadora, realizando as ações necessárias
	 */
	public void checagemBolaInicialParedes(){
		
		//Checa se bateu nas laterais
		if(bolaInicial.encostaParede(0, alturaJogo)){
			
			//Inverte direcao de Y
			bolaInicial.setDirecaoBolaY(bolaInicial.getDirecaoBolaY() * -1);				
		}		
	}
		
	/**
	 * Em caso de colisão prévia, remaneja as posicoes da bola
	 */
	public void remanejaPosicaoBola(Retangulo comparador, Bola bolaRemanejada){
		
		//Verifica a colisao
		if(comparador != null && comparador.verificaColisao(bolaInicial)){
			
			//Recebe os dados de quem colidiu
			float colisaoX = comparador.getPosX();
			float colisaoY = comparador.getPosY();
			float colisaoLargura = comparador.getLargura();
			float colisaoAltura  = comparador.getAltura();
			
			//Se bolinha estiver na lateral, altera seu X
			if((bolaInicial.getPosY() <= colisaoY + colisaoAltura) && (bolaInicial.getPosY() >= colisaoY)){
				
				//Altera a posicao depentendo de o circulo estiver a direita ou esquerda do elemento
				if(bolaInicial.getPosX() <= colisaoX){
					bolaRemanejada.setPosX(colisaoX - bolaInicial.getRaio());
				}
				else{
					bolaRemanejada.setPosX(colisaoX + colisaoLargura  + bolaInicial.getRaio());
				}
			}
			//Se a bolinha estiver acima ou abaixo, altera apenas seu Y
			else if((bolaInicial.getPosX() <= colisaoX + colisaoLargura) && (bolaInicial.getPosX() >= colisaoX)){
			
				//Altera a posicao depentendo de o circulo estiver acima ou abaixo
				if(bolaInicial.getPosY() <= colisaoY){
					bolaRemanejada.setPosY(colisaoY - bolaInicial.getRaio());
				}
				else{
					bolaRemanejada.setPosY(colisaoY + colisaoAltura  + bolaInicial.getRaio());
				}					
			}			
		}
	}
	
	/**
	 * Em caso de colisão prévia com as laterais, remaneja as posicoes da bola
	 */
	public void remanejaPosicaoBolaLaterais(Bola bolaRemanejada){
		
		//Checa se bateu nas laterais
		if(bolaInicial.encostaParede(0, alturaJogo)){
			
			//Remaneja posicao Y da bolinha de acordo com o local da colisao
			if(bolaInicial.getPosY() < (alturaJogo / 2)){
				bolaRemanejada.setPosY(bolaInicial.getRaio());
			}
			else{
				bolaRemanejada.setPosY(alturaJogo - bolaInicial.getRaio());
			}
		}
	}	
	
	/**
	 * Checa se a bola bateu em algum elemento, atualizando as posicoes
	 */
	public void checagemColisaoElemento(Retangulo comparador, Bola bolaRemanejada){
		
		//Verifica a colisao
		if(comparador != null && comparador.verificaColisao(bolaInicial)){
			
			//Define que houve colisao
			comparador.setColidiu(true);
			
			//Se for um jogador, aumenta a velocidade da bola
			if(comparador instanceof Jogador){
				bolaInicial.aumentaVelocidade();
			}

			//Inicia distancia do centro dos players com a bola
			float distanciaCentro = comparador.pontoMedioY() - bolaInicial.getPosY();			
			
			//Inverte direcao de X
			bolaInicial.setDirecaoBolaX(bolaInicial.getDirecaoBolaX() * -1);				
			
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
	}
	
	/**
	 * Contem todas as verificacos sobre a atualizacao da bola inicial
	 */
	public void atualizaBolaInicial(){

		//Inicia uma instancia de bola, que vai receber o remanejamento da bola principal, para suavizar a animação de bater nas extremidades de elementos
		Bola bolaTemporaria = new Bola();
		bolaTemporaria.setPosX(-1f);
		bolaTemporaria.setPosY(-1f);
		
		//Se a bola encosta em uma deadzone
		checagemBolaInicialDeadzone();
		
		//Recebe o resultado da verificacao de colisao com player
		checagemColisaoElemento(player1, bolaTemporaria);
		checagemColisaoElemento(player2, bolaTemporaria);

		//Verifica colisao com todos elementos da lista
		if(!elementosAvulsos.isEmpty()){
			for(Elemento elemento : elementosAvulsos){
				
				//Se o elemento for um retangulo, verifica colisao
				if(elemento instanceof Retangulo){
					checagemColisaoElemento((Retangulo) elemento, bolaTemporaria);
				}
			}
		}
		
		//Checa se bateu em alguma parede
		checagemBolaInicialParedes();
		
		//Atualiza seu movimento
		bolaInicial.movimenta();
		
		//Tenta inserir dados na bola temporaria
		remanejaPosicaoBolaLaterais(bolaTemporaria);
		remanejaPosicaoBola(player1, bolaTemporaria);
		remanejaPosicaoBola(player2, bolaTemporaria);
		if(!elementosAvulsos.isEmpty()){
			for(Elemento elemento : elementosAvulsos){
				if(elemento instanceof Retangulo){
					remanejaPosicaoBola((Retangulo) elemento, bolaTemporaria);
				}
			}
		}		
		
		//Ao mover a bola, pode ocorrer uma colisão em que a bola entra no elemento, sua posicao deve ser remanejada nesse caso
		if(bolaTemporaria.getPosX() >= 0.0f){
			bolaInicial.setPosX(bolaTemporaria.getPosX());
		}
		else if(bolaTemporaria.getPosY() >= 0.0f){
			bolaInicial.setPosY(bolaTemporaria.getPosY());
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

	/**
	 * Método que exibe finalização de partida
	 * 
	 * @param canvas
	 * @param paint
	 * @param vencedor false para perdeu, true para ganhou
	 */
	public void terminaPartida(Canvas canvas, Paint paint, boolean venceu){
		
		//Define a mensagem de vitoria ou derrota
		String mensagem = venceu ? "YOU WIN!" : "YOU LOSE!";
		
		//Exibe efeito de desabilitacao da tela
		paint.setARGB(150, 47, 79, 79);
		canvas.drawRect(0, 0, larguraJogo, alturaJogo, paint);
		
		//Recebe o objeto de retangulo que engloba o texto a ser exibido
		Rect textoHolder = new Rect();
		paint.getTextBounds(mensagem, 0, mensagem.length(), textoHolder);
		
		//Exibe um retangulo que envolve a mensagem
		modalFimJogo.desenha(canvas, paint);
		
		//Exibe texto na tela
		paint.setARGB(200, 255, 255, 255);
		canvas.drawText(mensagem, (getLarguraJogo()/2) - (textoHolder.width()/2) , (getAlturaJogo()/2) + (textoHolder.height()/2), paint);
	}
	
	/**
	 * Define o que fazer quando o usuário tocar no modal, deve ser 
	 * implementado por cada fase
	 */
	public void acaoModalFinaliza(){
		
	}	
	
	/* Getters e Setters padrão */
	public float getMetadeTelaX(){
		return (getLarguraJogo()/2);
	}
	public float getMetadeTelaY(){
		return (getAlturaJogo()/2);
	}	
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
	public Retangulo getModalFimJogo() {
		return modalFimJogo;
	}

	public boolean isProximaFase() {
		return proximaFase;
	}

	public void setProximaFase(boolean proximaFase) {
		this.proximaFase = proximaFase;
	}

	public List<Elemento> getElementosAvulsos() {
		return elementosAvulsos;
	}

	public void setElementosAvulsos(List<Elemento> elementosAvulsos) {
		this.elementosAvulsos = elementosAvulsos;
	}

	public int getPontosPartida() {
		return pontosPartida;
	}

	public void setPontosPartida(int pontosPartida) {
		this.pontosPartida = pontosPartida;
	}	
}
