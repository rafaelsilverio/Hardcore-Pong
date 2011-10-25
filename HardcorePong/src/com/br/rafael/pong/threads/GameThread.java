package com.br.rafael.pong.threads;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.br.rafael.pong.controladores.JogoPrincipal;

public class GameThread extends Thread {

	//Declara flag que define se o botão foi pressionado, sinalizando para o estado do jogo mover a peça
	private Boolean movimentoPressionado = false;
	
	//Mantem qual movimento deve ser feito, para os casos em que há botão pressionado
	private int tipoAcao;
	
	//Mantem objetos relativos a manipulacao do jogo
	private SurfaceHolder superficie;
	private Paint paint;
	private JogoPrincipal jogoPrincipal;
	
	//Atributo que define que o jogo esta rodando
	private boolean jogoFinalizado = false;
	
	//Mantem a quantidade de milisegundos para desenhar um frame 
	private static final int milisegundos = 16;  //(por volta de 60 FPS)	
	
	/**
	 * Inicia os atributos da thread
	 * 
	 * @param surfaceHolder
	 * @param context
	 */
	public GameThread(SurfaceHolder surfaceHolder, Context context){
		superficie = surfaceHolder;
		paint = new Paint();
		
		//Cria uma instancia de jogo, enviando as dimensoes da tela
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		jogoPrincipal = new JogoPrincipal(display.getHeight(), display.getWidth());
	}

	/**
	 * Método executado ao ser chamado o start,
	 * atualiza os estados do jogo
	 */
	@Override
	public void run() {
		while(!jogoFinalizado){

			//Recebe o inicio da contagem
			final long tempoInicio = SystemClock.uptimeMillis();
			
			//Cria um objeto canvas representando a tela (travando a superficie)
			Canvas canvas = superficie.lockCanvas();

			//Atualiza os passos do jogo
			jogoPrincipal.atualiza();
			
			//Desenha na tela
			jogoPrincipal.desenha(canvas, paint);
						
			//Destrava a superficie
			superficie.unlockCanvasAndPost(canvas);
			
			//Se gastou menos que MS da taxa de frames, deixa a thread em repouso no resto dos frames
			final long tempoGasto = SystemClock.uptimeMillis() - tempoInicio;
			if(tempoGasto < milisegundos){
				try {
					Thread.sleep(tempoGasto);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void pararJogo(){
		jogoFinalizado = true;
	} 
	
	public JogoPrincipal getGameState() {
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
}