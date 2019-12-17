package com.br.rafael.pong.multiplayer.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.br.rafael.pong.activity.MultiplayerActivity;
import com.br.rafael.pong.elementos.pong.Jogador;
import com.br.rafael.pong.multiplayer.controladores.JogoMultiplayer;
import com.br.rafael.pong.multiplayer.threads.MultiplayerGame;
import com.br.rafael.util.TransitaAtributos;

public class PongMultiple extends SurfaceView implements SurfaceHolder.Callback {

	//Mantem uma instancia da thread que gerencia o jogo
	private static MultiplayerGame threadJogo;
	
    //Mantem flag que diz se a tela esta sendo pressionada pelo usuario do telefone
    private Boolean telaPressionada = false;	
	
    //Mantem instancia da atividade que chama a view
    private MultiplayerActivity atividadeAtual;
    
	public PongMultiple(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//Traduz o contexto
		atividadeAtual = (MultiplayerActivity) context;
		
    	//Declara holder para ter a capacidade de escutar eventos
        SurfaceHolder holder = getHolder();
        holder.addCallback(this); 
        setFocusable(true); 		
		
		//Instancia a thread do jogo
		threadJogo = new MultiplayerGame(holder, context);
	}

	//Define traduz e envia eventos externos
	public static void eventoExterno(byte[] dados, int tamanho){
		
		//Transforma em string 
		String mensagem = new String(dados, 0, tamanho);

		//Verifica se a mensagem contem o caractere de resposta
		if(mensagem.contains(JogoMultiplayer.RESPOSTA_CLIENTE)){
			threadJogo.setPausaThread(false);
			
			//Remove o caractere 
			mensagem.replace(JogoMultiplayer.RESPOSTA_CLIENTE, "");
		}
		
		//Se ainda contem caracteres, envia ao gamestate
		if(mensagem.length() > 0){

			//Se nao tiver underscore e mais de um caractere, define o que fazer com a mensagem
			if(!mensagem.contains(JogoMultiplayer.SEPARADOR_VALORES) && mensagem.length() > 1){
				
				//Se houver mensagem para parar o movimento, define ela como a atual
				if(mensagem.contains(JogoMultiplayer.ESTATICO)){
					
					//Prioridade em parar o jogador
					mensagem = JogoMultiplayer.ESTATICO;
				}
				//Caso contrario recebe o ultimo caractere da mensagem
				else{
					mensagem = mensagem.substring(mensagem.length() - 1);	
				}
			}
			
			//Envia ao gamestate
			threadJogo.getGameState().movePlayer2(mensagem);
			
			//Define que recebeu dados
			threadJogo.setDadosDesenho(true);			
		}
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		
		//Se o evento for de tela pressionada, sinaliza seu status inicial como botao pressionado
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			
			//Sinaliza flag 
			telaPressionada = true;
		}
		//O flag so sera definido como falso se o usuario largar a tela
		else if(event.getAction() == MotionEvent.ACTION_UP){
			
			//Sinaliza flag 
			telaPressionada = false;			
		}

		//Sinaliza para a thread o estado atual da tela, apenas para o servidor
		if(TransitaAtributos.getPapelMultiplayer() == MultiplayerGame.SERVIDOR){
			threadJogo.getGameState().getPlayer1().setMovimento(telaPressionada);
		}		
		
		//Se a tela esta sendo pressionada
		if(telaPressionada){
			
			//Define qual o tipo do movimento, enviando para a thread
			int movimentoRealizado;
			if(threadJogo.getGameState().getAcaoSubir().verificaColisao(event.getX(), event.getY())){
				movimentoRealizado = Jogador.MOVIMENTO_ACIMA;
				threadJogo.getGameState().getAcaoSubir().setDesaparecer(true);
			}
			else if(threadJogo.getGameState().getAcaoDescer().verificaColisao(event.getX(), event.getY())){
				movimentoRealizado = Jogador.MOVIMENTO_ABAIXO;
				threadJogo.getGameState().getAcaoDescer().setDesaparecer(true);
			}
			else{
				movimentoRealizado = Jogador.MOVIMENTO_NULO;
			}
			
			//Define moviemtno do player
			threadJogo.getGameState().getPlayer1().setMovimentoAtual(movimentoRealizado);
			
			//Envia o posicionamento apenas se for cliente
			if(TransitaAtributos.getPapelMultiplayer() == MultiplayerGame.CLIENTE){
				atividadeAtual.enviaDados(movimentoRealizado);	
			}
		}
		//Se a tela nao estiver pressionada, sinaliza (se for cliente)
		else if(TransitaAtributos.getPapelMultiplayer() == MultiplayerGame.CLIENTE){
			atividadeAtual.enviaDados(Jogador.MOVIMENTO_NULO);
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
	public MultiplayerGame getThreadJogo() {
		return threadJogo;
	}

	public Boolean getTelaPressionada() {
		return telaPressionada;
	}

	public void setTelaPressionada(Boolean telaPressionada) {
		this.telaPressionada = telaPressionada;
	}
}