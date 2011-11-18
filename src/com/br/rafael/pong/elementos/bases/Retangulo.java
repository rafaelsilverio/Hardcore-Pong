package com.br.rafael.pong.elementos.bases;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Representa elementos do tipo retangulos.
 * Um retangulo é determinado por um X e Y, que representam
 * o vértice superior esquerdo, e uma altura e largura. 
 * 
 * @author Rafael
 *
 */
public class Retangulo extends Elemento{

	//Define os atributos de dimensao
	private float altura;
	private float largura;
	
	//Define a representacao de retangulo, utilizando biblioteca do android
	private RectF retanguloAndroid;
	
	/**
	 * Constroi um elemento do tipo retangulo, alimentando
	 * seus atributos de acordo com o construtor
	 * 
	 * @param X posição (X) do vértice superior superior a direita
	 * @param Y posição (Y) do vértice superior superior a direita
	 * @param altura Valor associado a coordenada Y para determinar altura
	 * @param largura Valor associado a coordenada X para determinar largura
	 */
	public Retangulo(float X, float Y, float altura, float largura) {
		super(X, Y);
		
		//Alimenta os atributos da classe
		this.altura  = altura;
		this.largura = largura;
		
		//Inicia o retangulo da classe, de acordo com os parametros
		retanguloAndroid = new RectF(X, Y, X + largura, Y + altura);
	}

	/**
	 * Desenha o retangulo no canvas enviado como parametro,
	 * configurando o paint recebido.
	 * 
	 * @param canvas
	 * @param paint
	 */
	@Override
	public void desenha(Canvas canvas, Paint paint){
		
		//Define define a cor do elemento
		configuraCorPaint(paint);
		
		//Desenha o retangulo
		retanguloAndroid.set(getPosX(), getPosY(), getPosX() + getLargura(), getPosY() + getAltura());
		canvas.drawRect(retanguloAndroid, paint);		
	}
	
	/**
	 * Checa se o ponto se encontra dentro do retangulo
	 * 
	 * @param coordenada X
	 * @param coordenada Y
	 * @return
	 */
	public boolean verificaColisao(float X, float Y){
		
		//Recebe a posicao x2 e y2 do retangulo (baseado em suas medidas)
		float posX2 = getPosX() + getLargura();
		float posY2 = getPosY() + getAltura();
		
		//Checa se a posicao X a compararar esta entre a posicao x do elemento
		boolean colisaoX = (X >= getPosX() && X <= posX2);
		
		//Checa se a posicao Y a comparar esta entre a posicao Y do elemento
		boolean colisaoY = (Y >= getPosY() && Y <= posY2);		
		
		//Checa se o circulo se encontra dentro do retangulo
		if(colisaoX && colisaoY){
			return true;
		}
		
		//Nesse ponto, indica que nao houve colisao
		return false;
	}	
	
	/**
	 * Checa se o retangulo do parametro se encontra dentro ou nas proximidades
	 * do retangulo atual
	 * 
	 * @param retangulo
	 * @return
	 */
	public boolean verificaColisao(Retangulo retangulo){
		
		//Checa se a posicao X do retangulo a compararar esta entre a posicao x do elemento
		boolean colisaoX = (retangulo.getPosX() >= getPosX() && retangulo.getPosX() <= (getPosX() + getLargura()));
		
		//Checa se a posicao Y do retangulo a comparar esta entre a posicao Y do elemento
		boolean colisaoY = (retangulo.getPosY() >= getPosY() && retangulo.getPosY() <= (getPosY() + getAltura()));
		
		//Colisao é caracterizada quando á colisao tanto em X quanto em Y
		return (colisaoX && colisaoY);
	}
	
	/**
	 * Checa se o circulo so parametro se encontra dentro ou nas proximidades
	 * do retangulo atual
	 * 
	 * @param retangulo
	 * @return
	 */
	public boolean verificaColisao(Circulo circulo){

		//Recebe a posicao x2 e y2 do retangulo (baseado em suas medidas)
		float posX2 = getPosX() + getLargura();
		float posY2 = getPosY() + getAltura();
		
		//Checa se a posicao X do circulo a compararar esta entre a posicao x do elemento
		boolean colisaoX = (circulo.getPosX() >= getPosX() && circulo.getPosX() <= posX2);
		
		//Checa se a posicao Y do circulo a comparar esta entre a posicao Y do elemento
		boolean colisaoY = (circulo.getPosY() >= getPosY() && circulo.getPosY() <= posY2);		
		
		//Checa se o circulo se encontra dentro do retangulo
		if(colisaoX && colisaoY){
			return true;
		}
		else{
			
			//Checa colisao com Y acima do retangulo
			boolean colisaoAcima = (circulo.getPosY() + circulo.getRaio() >= getPosY() && circulo.getPosY() + circulo.getRaio() <= posY2);	
			boolean colisaoAbaixo = (circulo.getPosY() - circulo.getRaio() >= getPosY() && circulo.getPosY() - circulo.getRaio() <= posY2);
			
			//Checa colisao com X ao redor do retangulo
			boolean colisaoEsquerda = (circulo.getPosX() + circulo.getRaio() >= getPosX() && circulo.getPosX() + circulo.getRaio() <= posX2);	
			boolean colisaoDireita = (circulo.getPosX() - circulo.getRaio() >= getPosX() && circulo.getPosX() - circulo.getRaio() <= posX2);			
			
			//Estando acima ou abaixo do retangulo (mas entre X), checa se Y do circulo colide com Y do retangulo
			if(colisaoX && (colisaoAcima || colisaoAbaixo)){
				return true;
			}
			
			//Estando ao redor do retangulo (mas entre Y), checa se X do circulo colide com X do retangulo
			if(colisaoY && (colisaoEsquerda || colisaoDireita)){
				return true;
			}
			
			//Recebe o ponto central de X e Y do retangulo 
			float centroX = ((posX2 - getPosX()) / 2) + getPosX();
			float centroY = ((posY2 - getPosY()) / 2) + getPosY();
			
			//Define ponto que deve ser utilizado para calcular colisão nos vertices do retangulo
			float calculoX = (circulo.getPosX() < centroX) ? getPosX() : posX2;
			float calculoY = (circulo.getPosY() < centroY) ? getPosY() : posY2;
			
			//Calcula a distancia entre o ponto definido do retangulo com o circulo
			float equacaoEsq = ((circulo.getPosX() - calculoX) * (circulo.getPosX() - calculoX)) + ((circulo.getPosY() - calculoY) * (circulo.getPosY() - calculoY));
			float equacaoDir = (circulo.getRaio() * circulo.getRaio());
			
			//Valida ultimo possivel caso de colisao, nas bordas [(X - a)^2 + (Y - b)^2 < r^2]
			if(equacaoEsq <= equacaoDir){
				return true;
			}
		}
		
		//Retorna que não houve colisao
		return false;
	}	
	
	/**
	 * Retorna o ponto médio da coordenada Y, na posical atual do retangulo
	 */
	public float pontoMedioY(){
		return getPosY() + (altura / 2);
	}
	
	/* Getters e setters default */
	public float getAltura() {
		return altura;
	}
	public void setAltura(float altura) {
		this.altura = altura;
	}
	public float getLargura() {
		return largura;
	}
	public void setLargura(float largura) {
		this.largura = largura;
	}
	public RectF getRetanguloAndroid() {
		return retanguloAndroid;
	}
	public void setRetanguloAndroid(RectF retanguloAndroid) {
		this.retanguloAndroid = retanguloAndroid;
	}
}
