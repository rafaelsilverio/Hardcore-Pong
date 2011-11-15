package com.br.rafael.pong.controladores.fases;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.controladores.base.BaseJogo;

public class Fase01 extends BaseJogo {

	public Fase01(int alturaJogo, int larguraJogo) {
		super(alturaJogo, larguraJogo);
		instanciaP1();
		instanciaP2();
		instanciaBotoesAcao();
		instanciaBolaPrincipal();
		
		//Define as cores dos players
		getPlayer2().defineCores(255, 255, 0, 0);
	}

	@Override
	public void desenha(Canvas canvas, Paint paint) {
		
		//Checa se o jogo acabou
		if(!isFimJogo()){
			
			//Verifica se alguem ganhou
			if(getPlayer1().getPlacar().getPontuacao() == 1 || getPlayer2().getPlacar().getPontuacao() == 1){
				setFimJogo(true);
				
				//Se p1 ganhou
				if(getPlayer1().getPlacar().getPontuacao() == 5){
					canvas.drawText("YOU WIN!", getLarguraJogo()/2, getAlturaJogo()/2, paint);
				}
				
				//Se p2 ganhou
				else{
					canvas.drawText("YOU LOSE!", getLarguraJogo()/2, getAlturaJogo()/2, paint);
				}
			}
			//Desenha normalmente apenas se alguem ainda não ganhou
			else{
				super.desenha(canvas, paint);	
			}			
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

}
