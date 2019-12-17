package com.br.rafael.pong.multiplayer.threads;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.br.rafael.pong.activity.MultiplayerActivity;
import com.br.rafael.pong.multiplayer.controladores.JogoMultiplayer;
import com.br.rafael.util.TransitaAtributos;

public class MultiplayerGame extends Thread{

	//Declara flag que define se o botao foi pressionado, sinalizando para o estado do jogo mover a peca
	private Boolean movimentoPressionado = false;
	
	//Mantem qual movimento deve ser feito, para os casos em que ha botao pressionado
	private int tipoAcao;
	
	//Mantem objetos relativos a manipulacao do jogo
	private SurfaceHolder superficie;
	private Paint paint;
	private JogoMultiplayer jogoPrincipal;
	
	//Mantem atributo da atividade atual
	private MultiplayerActivity atividadeAtual;
	
	//Declara os tipos de multiplayer
	public static final int CLIENTE = 0;
	public static final int SERVIDOR = 1;
	
	//Atributo que define que o jogo esta rodando
	private boolean jogoFinalizado = false;	
	
	//Atributo que define que o cliente tem dados para desenhar
	private boolean dadosDesenho = false;

	//Define atributos de controle da thread de servidor
	private Boolean pausaThread = false;
	private boolean atualizaEEnviaDados = true;
	
	//Mantem a quantidade de milisegundos para desenhar um frame 
	private static final int milisegundos = 16;  //(60 FPS)
	
	/**
	 * Inicia os atributos da thread
	 * 
	 * @param surfaceHolder
	 * @param context
	 */
	public MultiplayerGame(SurfaceHolder surfaceHolder, Context context){
		superficie = surfaceHolder;
		paint = new Paint();
		
		//Recebe a atividade atual
		atividadeAtual = (MultiplayerActivity) context;
		
		//Cria uma instancia de jogo, enviando as dimensoes da tela
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		jogoPrincipal = new JogoMultiplayer(display.getHeight(), display.getWidth());
	}

	/**
	 * Metodo executado ao ser chamado o start,
	 * atualiza os estados do jogo
	 */
	@Override
	public void run() {
		
		//Mantem o valor da ultima vez que pausaThread foi false
		long tempoUltimoFrame = 0;
		
		//Loop principal do jogo
		while(!jogoFinalizado){

			//Define consistencia de pausa da thread, apenas para servidores
			if(TransitaAtributos.getPapelMultiplayer() == MultiplayerGame.SERVIDOR){
				
				//Se a thread esta pausada, e ja se passou alguns frames, libera a thread
				if(pausaThread && (SystemClock.uptimeMillis() - tempoUltimoFrame > (2 * milisegundos))){
					
					//Libera a thread
					setPausaThread(false);
				}
			}

			//Se e para pausar a thread, pula o loop
			if(pausaThread)
				continue;			
			
			//Recebe o tempo em que o valor da pausa foi false
			tempoUltimoFrame = SystemClock.uptimeMillis();
			
			//Recebe o inicio da contagem
			final long tempoInicio = SystemClock.uptimeMillis();
			
			//Realiza as acoes relativa ao papel do celular de servidor
			if(TransitaAtributos.getPapelMultiplayer() == MultiplayerGame.SERVIDOR){

				//Sincroniza a pausa da thread
				synchronized(pausaThread){

					//Se e para atualizar e enviar, realiza a acao e coloca a thread em espera
					if(atualizaEEnviaDados){

						//Sinaliza pausa da thread
						//pausaThread = true;
						setPausaThread(true);
						
						//Atualiza os passos do jogo
						jogoPrincipal.atualiza();
						
						//Envia a situacao atual para o outro aparelho
						atividadeAtual.enviaDados(jogoPrincipal.retornaPosicoesAtuais());		
						
						//Sinaliza desenho de thread
						atualizaEEnviaDados = false;
					}
					
					//Realiza o desenho da tela
					else{
						
						//Cria um objeto canvas representando a tela (travando a superficie)
						Canvas canvas = superficie.lockCanvas();				
						
						//Desenha na tela
						jogoPrincipal.desenha(canvas, paint);	
						
						//Destrava a superficie
						superficie.unlockCanvasAndPost(canvas);			
						
						//Limpa os dados de desenho
						dadosDesenho = false;
						
						//Se gastou menos que MS da taxa de frames, deixa a thread em repouso no resto dos frames, (apenas servidores
						if(TransitaAtributos.getPapelMultiplayer() == MultiplayerGame.SERVIDOR){
							final long tempoGasto = SystemClock.uptimeMillis() - tempoInicio;
							if(tempoGasto < milisegundos){
								try {
									Thread.sleep(tempoGasto);
								} catch (InterruptedException e) {}
							}					
						}	
						
						//Define proximo passo, atualizacao de dados
						atualizaEEnviaDados = true;
					}					
				}
			}
			
			//Se e cliente
			else if(TransitaAtributos.getPapelMultiplayer() == MultiplayerGame.CLIENTE){
				
				//Se tem coisas a desenhar
				if(dadosDesenho){
					
					//Sinaliza ao servidor que os dados ja chegaram
					atividadeAtual.enviaDados(JogoMultiplayer.RESPOSTA_CLIENTE);
					
					//Cria um objeto canvas representando a tela (travando a superficie)
					Canvas canvas = superficie.lockCanvas();				
					
					//Define atualizacao apenas de clientes
					jogoPrincipal.atualizaJogoClient();
					
					//Desenha na tela
					jogoPrincipal.desenha(canvas, paint);	
					
					//Destrava a superficie
					superficie.unlockCanvasAndPost(canvas);			
					
					//Limpa os dados de desenho
					dadosDesenho = false;						
				}
			}
		}
	}

	public void pararJogo(){
		jogoFinalizado = true;
	} 
	
	/* Getters e Setters Gerados */
	public JogoMultiplayer getGameState() {
		return jogoPrincipal;
	}

	public Boolean getMovimentoPressionado() {
		return movimentoPressionado;
	}

	public void setMovimentoPressionado(Boolean movimentoPressionado) {
		this.movimentoPressionado = movimentoPressionado;
	}

	public int getTipoAcao() {
		return tipoAcao;
	}

	public void setTipoAcao(int tipoAcao) {
		this.tipoAcao = tipoAcao;
	}

	public boolean isDadosDesenho() {
		return dadosDesenho;
	}

	public void setDadosDesenho(boolean dadosDesenho) {
		this.dadosDesenho = dadosDesenho;
	}

	public Boolean isPausaThread() {
		return pausaThread;
	}

	public synchronized void setPausaThread(Boolean pausaThread) {
		this.pausaThread = pausaThread;
	}

	public boolean isAtualizaEEnviaDados() {
		return atualizaEEnviaDados;
	}

	public void setAtualizaEEnviaDados(boolean atualizaEEnviaDados) {
		this.atualizaEEnviaDados = atualizaEEnviaDados;
	}	
}
