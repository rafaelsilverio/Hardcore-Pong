package com.br.rafael.pong.controladores.fases;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.controladores.base.BaseJogo;
import com.br.rafael.pong.elementos.pong.BlocoQuebravel;

public class Fase02 extends BaseJogo {

	//Define pontuação para terminar o jogo
	static final int PONTOS_PARTIDA = 0;	
	
	public Fase02(int alturaJogo, int larguraJogo) {
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
		
		//Define as cores dos players
		getPlayer2().defineCores(255, 255, 0, 0);
		
		//Corta a velocidade da maquina
		getPlayer2().setVelocidadeMovimento(getPlayer2().getVelocidadeMovimento() * 0.5f);
		
		//Cria as barras de defesa para o p2
		BlocoQuebravel defesa = new BlocoQuebravel(getPlayer2().getPosX() - getPlayer2().getLargura() , 
				getAlturaJogo() - getPlayer2().getAltura(),  
				getPlayer2().getAltura(), 
				getPlayer2().getLargura());
		getElementosAvulsos().add(defesa);
		BlocoQuebravel defesa2 = new BlocoQuebravel(getPlayer2().getPosX() - getPlayer2().getLargura() , 
				0,  
				getPlayer2().getAltura(), 
				getPlayer2().getLargura());
		getElementosAvulsos().add(defesa2);		
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
