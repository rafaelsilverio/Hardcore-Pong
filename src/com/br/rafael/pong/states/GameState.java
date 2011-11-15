package com.br.rafael.pong.states;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

public class GameState {

	//Declara os possiveis valores dos botoes de movimento e acao
	public static final int ACAO_INVALIDA = 0;
	public static final int ACAO_SUBIR = 1;
	public static final int ACAO_DESCER = 2;
	public static final int ACAO_USAR = 3;
	
	//Recebem a altura e largura da tela do celular 
	private int _screenWidth;
	private int _screenHeight;
	
	//Declara variaveis que configura o player 1
	private Integer p1Largura;
	private Integer p1Altura;
	private Integer p1PosX;
	private Integer p1PosY;
	
	//Declara variaveis que configuram o player 2
	private Integer p2Largura;
	private Integer p2Altura;
	private Integer p2PosX;
	private Integer p2PosY;	
	
	//Declara os atributos da bolinha ativa
	private Integer bolaRaio;
	private Integer bolaX;
	private Integer bolaY;
	
	//Declara espelhos da posicao inicial da bola, para resetala
	private Integer bolaXReset;
	private Integer bolaYReset;	
	
	//Define a velocidade de movimento das barras
	private int velocidadeMovimento = 7;

	//Declara os atributos dos botoes de movimento
	private Integer btDownX;
	private Integer btDownY;
	private Integer btDownLargura;
	private Integer btDownAltura;
	private Integer btUpX;
	private Integer btUpY;
	private Integer btUpLargura;
	private Integer btUpAltura;

	//Declara objetos de retas para desenhos
	private Rect p1 = new Rect();
	private Rect p2 = new Rect();
	private Rect btUp = new Rect();
	private Rect btDown = new Rect();
	
	//Declara atributos que facilitam o calculo da trajetoria da bola
	private int direcaoBolaX = -1; //< 0 = em direcao a p1. > 0 = em direcao a p2
	private int direcaoBolaY = 1; //< 0 = em direcao a p1. > 0 = em direcao a p2
	private int velocidadeBola = 7; //Velocida em que a bola vai viajar, em px
	private int velocidadeBolaReset; //Mante espelho para resetar o valor futuramente
	private int aceleraBola = 1; //Fator de aceleracao da velocidade da bola, ao bater em algum objeto
	private int escalarBolaY = 0; //Escalar que deve ser acrescentado a posicao Y da bola
	
	public GameState(Context context) {
		
		//Inicia os valores de acordo com a dimensao da tela
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		_screenWidth = display.getWidth();
		_screenHeight = display.getHeight();
		
		/* Define a posicao inicial dos players */
		//Define a posicao inicial do player 1
		p1Largura = (int) (_screenHeight * 0.05);
		p1Altura = (int) (_screenWidth * 0.2);
		p1PosX = 1;
		p1PosY = (_screenHeight/2) - (p1Altura / 2);
		
		//Define a posicao do player 2
		p2Largura = (int) (_screenHeight * 0.05);
		p2Altura = (int) (_screenWidth * 0.2);
		p2PosX = _screenWidth - p2Largura;
		p2PosY = (_screenHeight/2) - (p1Altura / 2);		
		
		/* Define a posicao inicial dos botoes */
		//Define atributos do botao de descer a barra
		btDownLargura = (int) (_screenHeight * 0.15);
		btDownAltura = (int) (_screenWidth * 0.1);
		btDownX = 10;
		btDownY = _screenHeight - btDownAltura;
		
		//Define atributos para botao de subir a barra
		btUpLargura = (int) (_screenHeight * 0.15);
		btUpAltura = (int) (_screenWidth * 0.1);
		btUpX = 10;
		btUpY = 1;
		
		//Define os atributos da bolinha inicial
		bolaRaio = (int) (_screenHeight * 0.02);
		bolaX = (int) _screenWidth / 2;
		bolaY = (int) _screenHeight / 2;
		
		//Espelha os valores, para resetar futuramente
		bolaXReset = bolaX;
		bolaYReset = bolaY;
		velocidadeBolaReset = velocidadeBola;
	}

	public int proximaPosicaoBolaX(){
		return bolaX + (velocidadeBola * direcaoBolaX);
	}
	
	public int proximaPosicaoBolaY(){
		return bolaY + (escalarBolaY * direcaoBolaY);
	}	
	
	//The update method
	public void update() {
		
		//Verifica se a bola vai bater em alguma deadzone
		if((bolaX - bolaRaio) <= 0 || (bolaX + bolaRaio) >= _screenWidth){
			
			//Reseta os valores
			bolaX = bolaXReset;
			bolaY = bolaYReset;
			velocidadeBola = velocidadeBolaReset;
			escalarBolaY = 0;
			
			//Inverte a direcao da bola
			direcaoBolaX = direcaoBolaX * -1;
		}
		//Verifica se bateu em alguma parede
		if((bolaY - bolaRaio) <= 0 || (bolaY + bolaRaio) >= _screenHeight){
			
			//Muda direcao de Y e recalcula
			direcaoBolaY = direcaoBolaY * -1;
			bolaX = proximaPosicaoBolaX();
			bolaY = proximaPosicaoBolaY();	
		}
		//Se houve colisao com P1
		else if(checaColisaoPontoArea(bolaX - bolaRaio, bolaY, p1PosX, p1PosY, p1Altura, p1Largura)){
			
			//Inverte a direcao da bola
			direcaoBolaX = direcaoBolaX * -1;	
			
			//Se houve colisao com 1o quadrante, altera a equacao da reta
			if(checaColisaoPontoArea(bolaX - bolaRaio, bolaY,  p1PosX, p1PosY, (int) (p1Altura * 0.4), p1Largura)){
				
				//Altera escalar de Y
				escalarBolaY = 10;
				aceleraBola++;
				velocidadeBola = velocidadeBola + aceleraBola;
			}
				
			//Calcula proxima posicao
			bolaX = proximaPosicaoBolaX();
			bolaY = proximaPosicaoBolaY();				
		}
		
		//Se houve colisao com p2
		else if(checaColisaoPontoArea(bolaX + bolaRaio, bolaY, p2PosX, p2PosY, p2Altura, p2Largura)){
			
			//Inverte a direcao da bola
			direcaoBolaX = direcaoBolaX * -1;	
			
			//Calcula proxima posicao
			bolaX = proximaPosicaoBolaX();
			bolaY = proximaPosicaoBolaY();				
		}		
		
		//Nesse ponto a bola pode se movimentar de acordo com os valores definidos
		else{
			bolaX = proximaPosicaoBolaX();
			bolaY = proximaPosicaoBolaY();			
		}
	}

	public void acaoPressionada(int acao){
		
		//Descobre qual ação foi disparada
		switch(acao){
		
			case ACAO_SUBIR:

				//Valida o movimento
				if((p1PosY - velocidadeMovimento) >= 0){
					
					//"Move" para cima 
					p1PosY -= velocidadeMovimento;						
				}
				break;
				
			case ACAO_DESCER:

				//Valida o movimento
				if((p1PosY + p1Altura + velocidadeMovimento) <= _screenHeight){
					
					//"Move" para baixo
					p1PosY += velocidadeMovimento;						
				}
				break;
				
			case ACAO_USAR:
				break;
		}
	}

	public void dimensionaRetas(){

		//Redimensiona cada reta utilizada
		p1.set(p1PosX, p1PosY, p1PosX + p1Largura, p1PosY + p1Altura);
		p2.set(p2PosX, p2PosY, p2PosX + p2Largura, p2PosY + p2Altura);
		btUp.set(btUpX, btUpY, btUpX + btUpLargura, btUpY + btUpAltura);
		btDown.set(btDownX, btDownY, btDownX + btDownLargura, btDownY + btDownAltura);
	}
	
	//the draw method
	public void draw(Canvas canvas, Paint paint) {
	
		//Limpa a tela
		canvas.drawRGB(20, 20, 20);
		
		//Define a color para os elementos
		paint.setARGB(200, 0, 200, 0);
		
		//Reconfigura as retas
		dimensionaRetas();
		
		//Desenha os players
		canvas.drawRect(p1, paint);
		canvas.drawRect(p2, paint);
		
		//Configura paint para os botoes de acao
		paint.setARGB(150, 0, 0, 200);
		
		//Desenha os botoes
		canvas.drawRect(btUp, paint);		
		canvas.drawRect(btDown, paint);		
		
		//Configura paint para desenhar bolas
		paint.setARGB(200, 200, 0, 0);
		
		//Desenha a bola
		canvas.drawCircle(bolaX, bolaY, bolaRaio, paint);
	}
	
	public int retornaAcaoPressionada(int CoordX, int CoordY){
		
		//Inicia valor com possivel retorno de acao, inicia com ACAO_INVALIDA, que é uma acao inexistente
		int acaoTraduzida = ACAO_INVALIDA;
		
		//Checa se é acao de subir
		if( (CoordX >= btUpX && CoordX <= btUpX + btUpLargura && CoordY >= btUpY && CoordY <= btUpY + btUpAltura) ){
			acaoTraduzida = ACAO_SUBIR;
		}
		//Checa se é acao de descer
		else if( (CoordX >= btDownX && CoordX <= btDownX + btDownLargura && CoordY >= btDownY && CoordY <= btDownY + btDownAltura) ) {
			acaoTraduzida = ACAO_DESCER;
		}
		
		//Retorna a ação que deve ser tomada
		return acaoTraduzida;
	}
	
	public boolean checaColisaoPontoArea(int PX, int PY, int AX, int AY, int AA, int AL){
		return (PX >= AX && PX <= AX + AL && PY >= AY && PY <= AY + AA);
	}
	public String ax(int X){
		return (new Integer(X)).toString();
	}
	
	public int get_screenWidth() {
		return _screenWidth;
	}

	public int get_screenHeight() {
		return _screenHeight;
	}	
}