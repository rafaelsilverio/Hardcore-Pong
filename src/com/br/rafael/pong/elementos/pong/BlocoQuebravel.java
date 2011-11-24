package com.br.rafael.pong.elementos.pong;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.elementos.bases.Circulo;
import com.br.rafael.pong.elementos.bases.Retangulo;
import com.br.rafael.util.TransitaAtributos;

/**
 * Representa um bloco que aparece em qualquer parte do cenário, podendo ser quebrado
 * pelo jogador.
 * 
 * @author Rafael
 *
 */
public class BlocoQuebravel extends Retangulo{
	
	//Armazena a quantidade de vida(numero de hits que ele defende)
	private int vida = 10;
	
	//Valor que transforma o bloco nao quebravel
	private boolean blocoImortal = false;
	
	//Armazena a quantidade em que o alpha do bloco deve ser reduzido
	private int reducaoAlpha;
	
	//Valores que auxiliam o movimento
	public final static int MOVIMENTO_AUTO_VERTICAL = 0;
	private boolean movimento = false;
	private int movimentoAtual;
	private int direcaoMovimento = 1;
	private float velocidadeMovimento;
	
	
	/**
	 * Instancia o bloco
	 * 
	 * @param X
	 * @param Y
	 * @param altura
	 * @param largura
	 */
	public BlocoQuebravel(float X, float Y, float altura, float largura) {
		super(X, Y, altura, largura);
		
		//Define cores padronizadas
		super.defineCores(255, 201, 201, 201);
		
		//Define taxa de reducao de alpha
		reducaoAlpha = 255/vida;
	}
	
	/**
	 * Ao ocorrer uma colisao, reduz a vida do bloco.
	 * Se acabar a vida dele, alterna sua posicao e tamanho, o negando.
	 */
	@Override
	public boolean verificaColisao(Circulo circulo){

		//Se o bloco for imortal, apenas chama o metodo do pai, caso contrario verifica se ele ainda esta vivo
		if(blocoImortal){
			return super.verificaColisao(circulo);
		}
		else{
			
			//Se o bloco não possui vida, retorna falso
			if(vida == 0){
				return false;
			}			
			
			//Recebe o resultado da colisao
			boolean resultado = super.verificaColisao(circulo);	
			
			//Se houve colisao, reduz a vida
			if(resultado){
				vida--;
				setAlpha(getAlpha() - reducaoAlpha);
			}
			
			//Se acabou a vida, destroi o bloco
			if(vida == 0){
				setPosX(-1);
				setPosY(-1);
				setAltura(0);
				setLargura(0);			
			}

			//Retorna o resultado
			return resultado;			
		}
	}

	/**
	 * Movimenta o bloco, se foi definido, antes de desenhar.
	 * 
	 * @return
	 */
	@Override
	public void desenha(Canvas canvas, Paint paint){
		
		//Se é para movimentar
		if(movimento){
			
			//Realiza acoes de acordo com o movimento
			switch(movimentoAtual){
				case MOVIMENTO_AUTO_VERTICAL:
					
					//Valida o movimento
					if(direcaoMovimento == -1){
						if((getPosY() - velocidadeMovimento) >= 0){
							
							//"Move" para cima
							setPosY(getPosY() - velocidadeMovimento); 
						}
						else{
							direcaoMovimento *= -1;
						}

					}
					else {
						
						//Valida o movimento
						if((getPosY() + getAltura() + velocidadeMovimento) <= TransitaAtributos.getAlturaCelular()){

							//"Move" para baixo
							setPosY(getPosY() + velocidadeMovimento); 
						}	
						else {
							direcaoMovimento *= -1;
						}
					}
					break;
			}
		}
		
		//Chama desenhar do pai
		super.desenha(canvas, paint);
	}
	
	
	/* Getter e setters default */
	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
		
		//Altera a reducao de alpha
		reducaoAlpha = getAlpha() / vida;
	}

	public int getReducaoAlpha() {
		return reducaoAlpha;
	}

	public void setReducaoAlpha(int reducaoAlpha) {
		this.reducaoAlpha = reducaoAlpha;
	}

	public boolean isMovimento() {
		return movimento;
	}

	public void setMovimento(boolean movimento) {
		this.movimento = movimento;
	}

	public float getVelocidadeMovimento() {
		return velocidadeMovimento;
	}

	public void setVelocidadeMovimento(float velocidadeMovimento) {
		this.velocidadeMovimento = velocidadeMovimento;
	}

	public int getMovimentoAtual() {
		return movimentoAtual;
	}

	public void setMovimentoAtual(int movimentoAtual) {
		this.movimentoAtual = movimentoAtual;
	}

	public int getDirecaoMovimento() {
		return direcaoMovimento;
	}

	public void setDirecaoMovimento(int direcaoMovimento) {
		this.direcaoMovimento = direcaoMovimento;
	}

	public boolean isBlocoImortal() {
		return blocoImortal;
	}

	public void setBlocoImortal(boolean blocoImortal) {
		this.blocoImortal = blocoImortal;
	}
	
	
}
