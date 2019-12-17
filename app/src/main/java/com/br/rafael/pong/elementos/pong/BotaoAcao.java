package com.br.rafael.pong.elementos.pong;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.elementos.bases.Retangulo;

/**
 * Define um botão de ação, que é utilizado pelo usuário para interagir com 
 * o jogo
 * 
 * @author Rafael
 *
 */
public class BotaoAcao extends Retangulo {

	//Define identificadores estaticos para os botoes de acao
	public static int ACAO_SUBIR = 0;
	public static int ACAO_DESCER = 1;
	public static int ACAO_USAR = 1;
	
	//Define o texto a ser exibido no botao
	private String mensagem;
	
	//Define que o botao deva desaparecer
	private boolean desaparecer = false;
	
	//Define numero de frames utilizados para desaparecer o botao
	private float taxaDesaparecimento = 2f;
	private float alphaReduzido;
	
	//Recebe uma copia do alpha original
	private int aplphaOriginal;
	
	/**
	 * Alimenta os atributos da classe
	 * 
	 * @param X
	 * @param Y
	 * @param altura
	 * @param largura
	 */
	public BotaoAcao(float X, float Y, float altura, float largura) {
		super(X, Y, altura, largura);
		
		//Determina cor especifica
		defineCores(255, 255, 255, 200);
		
		//Copia atributos
		aplphaOriginal = getAlpha();
		alphaReduzido = getAlpha();
	}
	
	/**
	 * Reescreve o desenha, inserindo texto na area do botao, caso exista
	 * 
	 * @param canvas
	 * @param paint
	 */
	@Override
	public void desenha(Canvas canvas, Paint paint){
		
		//Define o rgb de acordo com a taxa de desaparecimento
		if(desaparecer && (getAlpha() > 0)){
			
			//Define novo aplha
			alphaReduzido = alphaReduzido - taxaDesaparecimento;
			setAlpha((int) alphaReduzido);
		}
		
		//Determina booleano se deve exibir o botao
		boolean exibeBotao = (!desaparecer || (desaparecer && (getAlpha() > 0))); 

		//Se é para exibir o botao
		if(exibeBotao){
			
			//Se há mensagem, desenha na tela
			if(mensagem != null){
				
				//Recebe as linhas da mensagem
				String[] linhas = mensagem.split("_");
				
				//Percorre as linhas, escrevendo no espaço
				int numeroLinha = 1;
				float tamanhoTexto = getLargura()/6;
				float margemTextoY = getLargura()/5;
				for(String linha : linhas){
					
					//Define paint
					paint.setARGB(getAlpha(), 255, 255, 255);
					paint.setTextSize(tamanhoTexto);
					
					//Desenha
					canvas.drawText(linha, getPosX(), getPosY() + (tamanhoTexto * numeroLinha) + margemTextoY, paint);
					
					//Incrementa a linha
					numeroLinha++;
				}
			}	
			
			//Chama a configuracao de cor
			configuraCorPaint(paint);
			
			//Desenha apenas as bordas do botao
			canvas.drawLine(getPosX(), getPosY(), getPosX() + getLargura(), getPosY(), paint);
			canvas.drawLine(getPosX(), getPosY(), getPosX(), getPosY() + getLargura(), paint);
			canvas.drawLine(getPosX() + getLargura(), getPosY() + getLargura(), getPosX(), getPosY() + getLargura(), paint);
			canvas.drawLine(getPosX() + getLargura(), getPosY() + getLargura(), getPosX() + getLargura(), getPosY(), paint);			
		}
	}

	/* Getters e setters gerados */
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isDesaparecer() {
		return desaparecer;
	}

	public void setDesaparecer(boolean desaparecer) {
		this.desaparecer = desaparecer;
	}
}
