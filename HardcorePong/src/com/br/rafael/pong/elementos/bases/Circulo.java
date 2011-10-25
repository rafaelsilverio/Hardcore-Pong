package com.br.rafael.pong.elementos.bases;


import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Representa elementos do tipo circulo, 
 * necessitando apenas de um raio para existir
 * 
 * @author Rafael
 *
 */
public class Circulo extends Elemento{

	//Define o raio do circulo
	private float raio;
	
	/**
	 * Constroi o circulo, montando o raio inicial
	 * 
	 * @param X
	 * @param Y
	 * @param raio
	 */
	public Circulo(float X, float Y, float raio) {
		super(X, Y);
		
		//Alimenta a classe
		this.raio = raio;
	}

	/**
	 * Desenha o circulo no canvas enviado como parametro,
	 * configurando o paint recebido.
	 * 
	 * @param canvas
	 * @param paint
	 */
	public void desenha(Canvas canvas, Paint paint){
		
		//Define define a cor do elemento
		configuraCorPaint(paint);
		
		//Desenha o retangulo
		canvas.drawCircle(getPosX(), getPosY(), raio, paint);
	}	

	/* Getters e setters padrao */
	public float getRaio() {
		return raio;
	}
	public void setRaio(float raio) {
		this.raio = raio;
	}
}
