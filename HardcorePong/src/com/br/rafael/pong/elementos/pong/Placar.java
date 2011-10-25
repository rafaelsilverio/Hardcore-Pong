package com.br.rafael.pong.elementos.pong;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.elementos.bases.Elemento;

public class Placar extends Elemento {

	//Declara pontuação
	private Integer pontuacao = new Integer(0);
	
	//Declara texto da pontuação
	private String textoPlacar;
	
	//Declara tamanho do texto
	private float tamanhoTexto;
	
	/**
	 * Alimenta os dados da classe
	 * 
	 * @param X
	 * @param Y
	 * @param tamanho do texto
	 */
	public Placar(float X, float Y, float tamanhoTexto) {
		super(X, Y);
		this.tamanhoTexto = tamanhoTexto;
	}
	
	/**
	 * Aumenta a pontuação do placar
	 */
	public void aumentaPontos(){
		pontuacao = pontuacao + 1;
	}
	
	/**
	 * Desenha o texto na tela
	 */
	public void desenha(Canvas canvas, Paint paint){
		
		//Configura a cor do paint
		configuraCorPaint(paint);
		
		//Configura tamanho do texto no paint
		paint.setTextSize(tamanhoTexto);
		
		//Desenha o texto
		String textoExibido = textoPlacar == null ? pontuacao.toString() : textoPlacar;
		canvas.drawText(textoExibido, getPosX(), getPosY(), paint);
	}

	/* Getters e Setters gerados automaticamente */
	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getTextoPlacar() {
		return textoPlacar;
	}

	public void setTextoPlacar(String textoPlacar) {
		this.textoPlacar = textoPlacar;
	}

}
