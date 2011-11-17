package com.br.rafael.pong.controladores.fases;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.controladores.base.BaseJogo;

public class Fase02 extends BaseJogo {

	//Define pontuação para terminar o jogo
	static final int PONTOS_PARTIDA = 5;	
	
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
			if(getPlayer1().getPlacar().getPontuacao() == PONTOS_PARTIDA || getPlayer2().getPlacar().getPontuacao() == PONTOS_PARTIDA){
				
				//Define flag que para o jogo
				setFimJogo(true);
				
				//Atualiza mais uma vez a tela
				super.atualiza();
				
				//Exibe saida para o usuario
				super.terminaPartida(canvas, paint, (getPlayer1().getPlacar().getPontuacao() == PONTOS_PARTIDA));
			}
		}
		else {
			
			//Exibe a mensagem de termino da partida
			super.terminaPartida(canvas, paint, (getPlayer1().getPlacar().getPontuacao() == PONTOS_PARTIDA));
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
