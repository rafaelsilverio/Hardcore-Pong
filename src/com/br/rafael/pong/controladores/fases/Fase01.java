package com.br.rafael.pong.controladores.fases;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.controladores.base.BaseJogo;

public class Fase01 extends BaseJogo {

	//Define pontua��o para terminar o jogo
	static final int PONTOS_PARTIDA = 1;
	
	public Fase01(int alturaJogo, int larguraJogo) {
		super(alturaJogo, larguraJogo);
		instanciaP1();
		instanciaP2();
		instanciaBotoesAcao();
		instanciaBolaPrincipal();
		iniciaModalFimJogo();
		
		//Define as cores dos players
		getPlayer2().defineCores(255, 255, 0, 0);
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
		
		//Se jogo acabou, n�o faz nada
		if(!isFimJogo()){
			super.atualiza();
			atualizaIAP2(1);			
		}
	}
}
