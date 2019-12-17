package com.br.rafael.pong.controladores.fases;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.br.rafael.pong.controladores.base.BaseJogo;
import com.br.rafael.pong.elementos.pong.BlocoQuebravel;

public class Fase03  extends BaseJogo {

	//Define pontuao para terminar o jogo
	static final int PONTOS_PARTIDA = 0;	
	
	//Mantem instancia da barra que se move
	private BlocoQuebravel blocoMovivel;
	
	public Fase03(int alturaJogo, int larguraJogo) {
		super(alturaJogo, larguraJogo);
		instanciaElementos();
	}
	
	/**
	 * Incicia e instancia todos os elementos representados no jogo
	 * 
	 * @param alturaJogo
	 * @param larguraJogo
	 */
	public void instanciaElementos(){
		instanciaP1();
		instanciaP2();
		instanciaBotoesAcao();
		instanciaBolaPrincipal();
		iniciaModalFimJogo();
		setPontosPartida(PONTOS_PARTIDA);
		getElementosAvulsos().clear();
		
		//Define as cores dos players
		getPlayer2().defineCores(255, 255, 0, 0);
		
		//Corta a velocidade da maquina
		getPlayer2().setVelocidadeMovimento(getPlayer2().getVelocidadeMovimento() * 0.5f);
		
		//Cria as barras de defesa para o p2
		BlocoQuebravel defesa = new BlocoQuebravel(getPlayer2().getPosX() - getPlayer2().getLargura() , 
				getAlturaJogo() - getPlayer2().getAltura(),  
				getPlayer2().getAltura(), 
				getPlayer2().getLargura());
		defesa.setVida(defesa.getVida() * 2);
		BlocoQuebravel defesa2 = new BlocoQuebravel(getPlayer2().getPosX() - getPlayer2().getLargura() , 
				0,  
				getPlayer2().getAltura(), 
				getPlayer2().getLargura());
		defesa2.setVida(defesa2.getVida() * 2);
		
		
		//Instancia e registra bloco movivel
		float alturaBloco = getPlayer2().getAltura()/2;
		blocoMovivel = new BlocoQuebravel(defesa.getPosX() - getPlayer2().getLargura(),
				(getAlturaJogo()/2) - alturaBloco, 
				alturaBloco, 
				 getPlayer2().getLargura());
		blocoMovivel.setMovimento(true);
		blocoMovivel.setVelocidadeMovimento(getPlayer1().getVelocidadeMovimento() / 2);
		blocoMovivel.setMovimentoAtual(BlocoQuebravel.MOVIMENTO_AUTO_VERTICAL);
		blocoMovivel.setBlocoImortal(true);
		blocoMovivel.defineCores(blocoMovivel.getAlpha(), 47, 79, 79);
		
		//Registra os elementos
		getElementosAvulsos().add(defesa);
		getElementosAvulsos().add(defesa2);	
		getElementosAvulsos().add(blocoMovivel);
	}

	@Override
	public void desenha(Canvas canvas, Paint paint) {
		super.desenha(canvas, paint);
		
		//Checa se o jogo acabou
		if(!isFimJogo()){			
			
			//Verifica se alguem ganhou
			acoesVitoriaVersus(canvas, paint);
		}
		else {
			
			//Exibe a mensagem de termino da partida
			super.terminaPartida(canvas, paint, (getPlayer1().getPlacar().getPontuacao() == getPontosPartida()));
		}
	}
	
	@Override
	public void atualiza(){
		
		//Se jogo acabou, no faz nada
		if(!isFimJogo()){
			super.atualiza();
			atualizaIAP2(1);			
		}
	}
	
	@Override
	public void acaoModalFinaliza(){
		
		//Reinicia o jogo e fecha a modal
		instanciaElementos();
		setFimJogo(false);
	}
}
