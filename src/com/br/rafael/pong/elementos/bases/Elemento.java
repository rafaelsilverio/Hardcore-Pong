package com.br.rafael.pong.elementos.bases;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Representa os elementos mais básicos de uma tela gráfica,
 * conhecendo atributos básicos, tais como posição X e Y. 
 * 
 * @author Rafael
 *
 */
public class Elemento {

	//Declara atributos de posicao
	private float posX;
	private float posY;
	
	//Declara os atributos de cor [valores de 0 a 255]
	private int alpha = 200; //Define tranparencia do elemento
	private int vermelho = 255;
	private int verde = 255;
	private int azul = 255;
	
	//Mantem um atributo que define se o elemento se encontra ou não ativo
	private boolean ativo = true;
	
	/**
	 * Recebe atributos que alimentam a classe
	 * 
	 * @param X
	 * @param Y
	 */
	public Elemento(float X, float Y){
		
		//Define os valores
		posX = X;
		posY = Y;
	}
	
	/**
	 * Define os valores que configuram a cor do elemento
	 * valores vao de 0 a 255
	 * 
	 * @param alpha
	 * @param vermelho
	 * @param verde
	 * @param azul
	 */
	public void defineCores(int alpha, int vermelho, int verde, int azul){
		
		//Alimenta os atributos
		this.alpha = alpha;
		this.vermelho = vermelho; 
		this.verde = verde;
		this.azul = azul;
	}
	
	/**
	 * Recebe um objeto Paint e o configura com as cores definidas
	 * para o elemento.
	 * 
	 * @return
	 */
	public void configuraCorPaint(Paint paint){
		paint.setARGB(alpha, vermelho, verde, azul);
	}
	
	/**
	 * Método que deve ser implementado por todos que extendem elemento
	 */
	public void desenha(Canvas canvas, Paint paint){
	}
	
	/* Getters e setters default */
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public int getAlpha() {
		return alpha;
	}
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	public int getVermelho() {
		return vermelho;
	}
	public void setVermelho(int vermelho) {
		this.vermelho = vermelho;
	}
	public int getVerde() {
		return verde;
	}
	public void setVerde(int verde) {
		this.verde = verde;
	}
	public int getAzul() {
		return azul;
	}
	public void setAzul(int azul) {
		this.azul = azul;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
