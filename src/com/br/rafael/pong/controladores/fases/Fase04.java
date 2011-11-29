package com.br.rafael.pong.controladores.fases;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.controladores.base.BaseJogo;
import com.br.rafael.pong.elementos.pong.BlocoEfeito;
import com.br.rafael.pong.elementos.pong.BlocoQuebravel;

public class Fase04  extends BaseJogo {

	//Define pontuação para terminar o jogo
	static final int PONTOS_PARTIDA = 5;	
	
	public Fase04(int alturaJogo, int larguraJogo) {
		super(alturaJogo, larguraJogo);
		instanciaElementos();
	}
	
	/**
	 * Incicia e instancia todos os elementos representados no jogo
	 * 
	 * @param alturaJogo
	 * @param larguraJogo
	 */
	public void instanciaElementos(){
		instanciaP1();
		instanciaP2();
		instanciaBotoesAcao();
		instanciaBolaPrincipal();
		iniciaModalFimJogo();
		setPontosPartida(PONTOS_PARTIDA);
		getElementosAvulsos().clear();
		
		//Define as cores dos players
		getPlayer2().defineCores(255, 255, 0, 0);
		
		//Corta a velocidade da maquina
		getPlayer2().setVelocidadeMovimento(getPlayer2().getVelocidadeMovimento() * 0.5f);
		
		//Cria as barras de defesa para o p2
		BlocoQuebravel defesa = new BlocoQuebravel(getPlayer2().getPosX() - getPlayer2().getLargura() , 
				getAlturaJogo() - getPlayer2().getAltura(),  
				getPlayer2().getAltura(), 
				getPlayer2().getLargura());
		defesa.setVida(defesa.getVida() * 2);
		BlocoQuebravel defesa2 = new BlocoQuebravel(getPlayer2().getPosX() - getPlayer2().getLargura() , 
				0,  
				getPlayer2().getAltura(), 
				getPlayer2().getLargura());
		defesa2.setVida(defesa2.getVida() * 2);
		
		
		//Instancia e registra bloco movivel
		float alturaBloco = getPlayer2().getAltura()/2;
		BlocoQuebravel blocoMovivel = new BlocoQuebravel(defesa.getPosX() - getPlayer2().getLargura(),
				(getAlturaJogo()/2) - alturaBloco, 
				alturaBloco, 
				 getPlayer2().getLargura());
		blocoMovivel.setMovimento(true);
		blocoMovivel.setVelocidadeMovimento(getPlayer1().getVelocidadeMovimento() / 2);
		blocoMovivel.setMovimentoAtual(BlocoQuebravel.MOVIMENTO_AUTO_VERTICAL);
		blocoMovivel.setBlocoImortal(true);
		blocoMovivel.defineCores(blocoMovivel.getAlpha(), 47, 79, 79);
		
		//Registra os elementos
		getElementosAvulsos().add(defesa);
		getElementosAvulsos().add(defesa2);	
		getElementosAvulsos().add(blocoMovivel);
		
		
		//Define os valores de bola de efeito
		float posx = blocoMovivel.getPosX() - getPlayer1().getLargura();
		float posy = getAlturaJogo() / 8;
		float largura = getPlayer1().getLargura();
		
		//Cria os blocos de efeito
		BlocoEfeito efeito1 = new BlocoEfeito(BlocoEfeito.ACELERADOR, getBolaInicial(), 
				posx, 
				0 * posy, 
				posy, 
				largura);
		efeito1.defineCores(255, 255, 0, 0);
		BlocoEfeito efeito2 = new BlocoEfeito(BlocoEfeito.DESACELERADOR, getBolaInicial(), 
				posx, 
				1 * posy, 
				posy, 
				largura);
		efeito2.defineCores(255, 0, 0, 255);		
		BlocoEfeito efeito3 = new BlocoEfeito(BlocoEfeito.ACELERADOR, getBolaInicial(), 
				posx, 
				2 * posy, 
				posy, 
				largura);
		efeito3.defineCores(255, 255, 0, 0);		
		BlocoEfeito efeito4 = new BlocoEfeito(BlocoEfeito.DESACELERADOR, getBolaInicial(), 
				posx, 
				3 * posy, 
				posy, 
				largura);
		efeito4.defineCores(255, 0, 0, 255);		
		BlocoEfeito efeito5 = new BlocoEfeito(BlocoEfeito.ACELERADOR, getBolaInicial(), 
				posx, 
				4 * posy, 
				posy, 
				largura);
		efeito5.defineCores(255, 255, 0, 0);
		BlocoEfeito efeito6 = new BlocoEfeito(BlocoEfeito.DESACELERADOR, getBolaInicial(), 
				posx, 
				5 * posy, 
				posy, 
				largura);
		efeito6.defineCores(255, 0, 0, 255);			
		BlocoEfeito efeito7 = new BlocoEfeito(BlocoEfeito.ACELERADOR, getBolaInicial(), 
				posx, 
				6 * posy, 
				posy, 
				largura);
		efeito7.defineCores(255, 255, 0, 0);		
		BlocoEfeito efeito8 = new BlocoEfeito(BlocoEfeito.DESACELERADOR, getBolaInicial(), 
				posx, 
				7 * posy, 
				posy, 
				largura);
		efeito8.defineCores(255, 0, 0, 255);			
		
		//Registra os blocos
		getElementosAvulsos().add(efeito1);
		getElementosAvulsos().add(efeito2);
		getElementosAvulsos().add(efeito3);
		getElementosAvulsos().add(efeito4);
		getElementosAvulsos().add(efeito5);
		getElementosAvulsos().add(efeito6);
		getElementosAvulsos().add(efeito7);
		getElementosAvulsos().add(efeito8);
	}

	@Override
	public void desenha(Canvas canvas, Paint paint) {
		super.desenha(canvas, paint);
		
		//Checa se o jogo acabou
		if(!isFimJogo()){			
			
			//Verifica se alguem ganhou
			acoesVitoriaVersus(canvas, paint);
		}
		else {
			
			//Exibe a mensagem de termino da partida
			super.terminaPartida(canvas, paint, (getPlayer1().getPlacar().getPontuacao() == getPontosPartida()));
		}
	}
	
	@Override
	public void atualiza(){
		
		//Se jogo acabou, não faz nada
		if(!isFimJogo()){
			super.atualiza();
			atualizaIAP2(1);			
		}
	}
	
	@Override
	public void acaoModalFinaliza(){
		
		//Reinicia o jogo e fecha a modal
		instanciaElementos();
		setFimJogo(false);
	}	

}
