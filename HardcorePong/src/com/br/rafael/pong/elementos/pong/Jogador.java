package com.br.rafael.pong.elementos.pong;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.elementos.bases.Retangulo;

/**
 * Representa um jogador do pong
 * 
 * @author Rafael
 *
 */
public class Jogador extends Retangulo{

	//Declara constantes que definem o movimento que deve ser feito
	public static final int MOVIMENTO_NULO = 0;
	public static final int MOVIMENTO_ACIMA = 1;
	public static final int MOVIMENTO_ABAIXO = 2;
	public static final int MOVIMENTO_ACAO = 3;
	
	//Declara atributo que define se o player esta se movendo
	private boolean movimento = false;
	
	//Declara atributo que define o movimento atual do player
	private int movimentoAtual = MOVIMENTO_NULO;
	
	//Declara velodicade do movimento do jogador atual
	private float velocidadeMovimento;
	
	//Mantem placar do jogador
	private Placar placar;
	
	//Define que uma colisao ocorreu
	private boolean colidiu = false;
	
	//Define contagem de frames que o player vai piscar
	private int framesPiscando = 0;
	
	//Define quantos frames devem ficar piscando
	private final int numFramesPiscando = 6;
	
	/**
	 * Construtor, alimenta os atributos da tela
	 * 
	 * @param X
	 * @param Y
	 * @param altura
	 * @param largura
	 */
	public Jogador(float X, float Y, float altura, float largura, float velocidadeMovimento) {
		super(X, Y, altura, largura);
		
		//Recebe atributos
		this.velocidadeMovimento = velocidadeMovimento;
	}
	
	/**
	 * Inicia o placar do jogador, recebendo suas coordenadas e
	 * tamanho de fonte
	 * 
	 * @param x
	 * @param y
	 * @param tamanhoFonte
	 */
	public void iniciaPlacar(float x, float y, float tamanhoFonte){
		placar = new Placar(x, y, tamanhoFonte);
	}
	
	/**
	 * Realiza o pr�ximo movimento do jogador, caso ele deva se movimentar
	 * (como definido pelo atributo movimento), validando se esta ou n�o
	 * passando dos limites da tela
	 * 
	 * @param float Altura inicial limite 
	 * @param float Algura final limite
	 */
	public void movimentar(float alturaMinima, float alturaMaxima){
		
		//Se movimento estiver definido
		if(movimento){

			//Define qual a a��o a ser tomada
			switch(movimentoAtual){
			
				case MOVIMENTO_ACIMA:
					
					//Valida o movimento
					if((getPosY() - velocidadeMovimento) >= alturaMinima){

						//"Move" para cima
						setPosY(getPosY() - velocidadeMovimento); 
					}					
					break;
					
				case MOVIMENTO_ABAIXO:

					//Valida o movimento
					if((getPosY() + getAltura() + velocidadeMovimento) <= alturaMaxima){

						//"Move" para baixo
						setPosY(getPosY() + velocidadeMovimento); 
					}
					break;	
					
				case MOVIMENTO_ACAO:
					break;								
			}
		}
	}
	
	/**
	 * Chama metodo pai para desenhar na tela, mas realizando acoes a mais
	 */
	public void desenha(Canvas canvas, Paint paint){
		
		//Define acoes de colisao
		acoesColisao();		
		
		//Realiza o desenho na tela
		super.desenha(canvas, paint);		
	}
	
	/**
	 * Chama o metodo pai para desenhar na tela, porem j� verificando movimentos 
	 * a serem feitos.
	 * 
	 * @param canvas
	 * @param paint
	 * @param alturaMinima
	 * @param alturaMaxima
	 */
	public void desenha(Canvas canvas, Paint paint, float alturaMinima, float alturaMaxima){
		
		//Define acoes de colisao
		acoesColisao();
		
		//Checa e realiza movimento dos players
		movimentar(alturaMinima, alturaMaxima);
		
		//Realiza o desenho na tela
		super.desenha(canvas, paint);
	}
	
	/**
	 * Realiza a��es ao ocorrer uma colisao
	 */
	public void acoesColisao(){
		
		//Se houve colisao, pisca o jogador
		if(framesPiscando > 0){
			defineCores(200, 255, 255, 255);
			framesPiscando--;
		}
		else{
			defineCores(200, 0, 200, 0);
		}		
	}
	
	/* Getters e setters default */
	public boolean isMovimento() {
		return movimento;
	}
	public void setMovimento(boolean movimento) {
		this.movimento = movimento;
	}
	public int getMovimentoAtual() {
		return movimentoAtual;
	}
	public void setMovimentoAtual(int movimentoAtual) {
		this.movimentoAtual = movimentoAtual;
	}
	public float getVelocidadeMovimento() {
		return velocidadeMovimento;
	}
	public void setVelocidadeMovimento(float velocidadeMovimento) {
		this.velocidadeMovimento = velocidadeMovimento;
	}

	public Placar getPlacar() {
		return placar;
	}

	public void setPlacar(Placar placar) {
		this.placar = placar;
	}

	public boolean isColidiu() {
		return colidiu;
	}

	public void setColidiu(boolean colidiu) {
		this.colidiu = colidiu;
		
		//Define janela de frames
		framesPiscando = numFramesPiscando;
	}
}