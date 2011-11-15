package com.br.rafael.pong.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.br.rafael.pong.controladores.base.BaseJogo;
import com.br.rafael.pong.elementos.pong.Jogador;
import com.br.rafael.pong.threads.GameThread;

public class PongSingle extends SurfaceView implements SurfaceHolder.Callback {

	//Mantem uma inst�ncia da thread que gerencia o jogo
	private GameThread threadJogo;
	
    //Mantem flag que diz se a tela esta sendo pressionada pelo usu�rio do telefone
    private Boolean telaPressionada = false;	
	
	public PongSingle(Context context, AttributeSet attrs) {
		super(context, attrs);
		
    	//Declara holder para ter a capacidade de escutar eventos
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true); 		
		
		//Instancia a thread do jogo
		threadJogo = new GameThread(holder, context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		
		//Se o evento for de tela pressionada, sinaliza seu status inicial como botao pressionado
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			
			//Sinaliza flag 
			telaPressionada = true;
		}
		//O flag so ser� definido como falso se o usu�rio largar a tela
		else if(event.getAction() == MotionEvent.ACTION_UP){
			
			//Sinaliza flag 
			telaPressionada = false;			
		}

		//Sinaliza para a thread o estado atual da tela
		((BaseJogo) threadJogo.getGameState()).getPlayer1().setMovimento(telaPressionada);
		
		//Se a tela esta sendo pressionada
		if(telaPressionada){
			
			//Move para cima, se for o caso
			if(((BaseJogo) threadJogo.getGameState()).getAcaoSubir().verificaColisao(event.getX(), event.getY())){
				((BaseJogo) threadJogo.getGameState()).getPlayer1().setMovimentoAtual(Jogador.MOVIMENTO_ACIMA);
				((BaseJogo) threadJogo.getGameState()).getAcaoSubir().setDesaparecer(true);
			}
			else if(((BaseJogo) threadJogo.getGameState()).getAcaoDescer().verificaColisao(event.getX(), event.getY())){
				((BaseJogo) threadJogo.getGameState()).getPlayer1().setMovimentoAtual(Jogador.MOVIMENTO_ABAIXO);
				((BaseJogo) threadJogo.getGameState()).getAcaoDescer().setDesaparecer(true);
			}
			else
				((BaseJogo) threadJogo.getGameState()).getPlayer1().setMovimentoAtual(Jogador.MOVIMENTO_NULO);
		}
		
		//Retorno que define que o metodo esta interessado em saber todos os eventos ocorridos
		return true;
	}	
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		threadJogo.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		threadJogo.pararJogo();
	}

	/* Getters e Setters default */
	public GameThread getThreadJogo() {
		return threadJogo;
	}

	public void setThreadJogo(GameThread threadJogo) {
		this.threadJogo = threadJogo;
	}

	public Boolean getTelaPressionada() {
		return telaPressionada;
	}

	public void setTelaPressionada(Boolean telaPressionada) {
		this.telaPressionada = telaPressionada;
	}

}