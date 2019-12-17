package com.br.rafael.pong.elementos.pong;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.elementos.bases.Retangulo;

public class BlocoEfeito extends Retangulo {

	//Define os tipos possiveis de bloco de efeito
	public static final int ACELERADOR = 0;
	public static final int DESACELERADOR = 1;
	
	//Mantem o tipo do blocos
	private int tipoBloco;
	
	//Mantem uma referencia da bola principal do jogo
	private Bola bola;
	
	//Instancia o pai
	public BlocoEfeito(int tipoBloco, Bola bola, float X, float Y, float altura, float largura) {
		super(X, Y, altura, largura);
		this.tipoBloco = tipoBloco;
		this.bola = bola;
	}

	/**
	 * Se houve colisao com a bola, realiza um efeito sobre ela, dependendo
	 * do tipo do bloco.
	 * 
	 * @return
	 */
	@Override
	public void desenha(Canvas canvas, Paint paint){
		
		//se o elemento nao estiver ativo, finaliza
		if(!isAtivo()){
			return;
		}
		
		//Se houve colisao, altera o estado da bola
		if(isColidiu()){
			switch(tipoBloco){
				case ACELERADOR:
					bola.setVelocidadeMovimento(bola.getVelocidadeMovimento() + (bola.getAceleradorMovimento() * bola.getNumeroMaximoAceleracoes()));
					bola.setVerde(0);
					break;
				case DESACELERADOR:
					float xAnterior = bola.getPosX();
					float yAnterior = bola.getPosY();
					bola.reiniciaBola();
					bola.setPosX(xAnterior);
					bola.setPosY(yAnterior);
					bola.setVerde(255);
					break;					
			}
			
			//Destroi o elemento
			setAtivo(false);
			setPosX(-1);
			setPosY(-1);
			setLargura(0);
			setAltura(0);
		}
		
		//Chama o metodo de desenho
		super.desenha(canvas, paint);		
	}
	
	/* Getters e setters default */
	public int getTipoBloco() {
		return tipoBloco;
	}

	public void setTipoBloco(int tipoBloco) {
		this.tipoBloco = tipoBloco;
	}

	public Bola getBola() {
		return bola;
	}

	public void setBola(Bola bola) {
		this.bola = bola;
	}
}
