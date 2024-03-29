package com.br.rafael.pong.controladores.fases;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.controladores.base.BaseJogo;

public class Fase01 extends BaseJogo {

	//Define pontua��o para terminar o jogo
	static final int PONTOS_PARTIDA = 0;
	
	public Fase01(int alturaJogo, int larguraJogo) {
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
		
		//Se jogo acabou, n�o faz nada
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
