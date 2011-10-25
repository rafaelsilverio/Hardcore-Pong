package com.br.rafael.pong.multiplayer.controladores;

import com.br.rafael.pong.controladores.JogoPrincipal;
import com.br.rafael.pong.elementos.pong.Jogador;
import com.br.rafael.pong.multiplayer.threads.MultiplayerGame;
import com.br.rafael.util.TransitaAtributos;

public class JogoMultiplayer extends JogoPrincipal {

	//Declara os tipos de mensagens que devem ser enviadas
	public final static String MOVIMENTANDO_ACIMA = "U";
	public final static String MOVIMENTANDO_ABAIXO = "D";
	public final static String ESTATICO = "S";
	public final static String RESPOSTA_CLIENTE = "R";
	
	/**
	 * Inicia e instancia classe pai
	 * 
	 * @param alturaJogo
	 * @param larguraJogo
	 */
	public JogoMultiplayer(int alturaJogo, int larguraJogo) {
		super(alturaJogo, larguraJogo);
		
		//Seta multiplayer como true
		setMultiplayer(true);
		
		//Se for cliente, reposiciona os botoes de ação
		if(TransitaAtributos.getPapelMultiplayer() == MultiplayerGame.CLIENTE){
			getAcaoDescer().setPosX(larguraJogo - getAcaoDescer().getLargura());
			getAcaoSubir().setPosX(larguraJogo - getAcaoSubir().getLargura());
		}
	}
	
	/**
	 * Move o player 2 de acordo com os eventos recebidos
	 */
	public void movePlayer2(String mensagem){
		
		//Decodifica movimento que deve ser feito
		if(mensagem.equals(MOVIMENTANDO_ACIMA)){
			getPlayer2().setMovimento(true);
			getPlayer2().setMovimentoAtual(Jogador.MOVIMENTO_ACIMA);
		}
		else if(mensagem.equals(MOVIMENTANDO_ABAIXO)){
			getPlayer2().setMovimento(true);
			getPlayer2().setMovimentoAtual(Jogador.MOVIMENTO_ABAIXO);			
		}
		else if(mensagem.equals(ESTATICO)){
			getPlayer2().setMovimento(false);
			getPlayer2().setMovimentoAtual(Jogador.MOVIMENTO_NULO);				
		}		
		
		//O caso final é quando o aparelho é um cliente e recebe string de atualizacao de posicoes
		else{
			atualizaPosicoesClient(mensagem);
		}
	}
	
	/**
	 * Monta uma string com a figura atual do jogo, com as posições de todos os elementos chave.
	 * Utilizado para sincronizar servidor - cliente
	 */
	public String retornaPosicoesAtuais(){
		
		//Monta a string com as posicoes dos players e da bola, sendo que as posicoes sao frações da dimensao do celular
		String snapShot = "";
		snapShot += getPlayer1().getPosX() / getLarguraJogo() + "_" + getPlayer1().getPosY() / getAlturaJogo() + "_";
		snapShot += getPlayer2().getPosX() / getLarguraJogo() + "_" + getPlayer2().getPosY() / getAlturaJogo() + "_";
		snapShot += getBolaInicial().getPosX() / getLarguraJogo() + "_" + getBolaInicial().getPosY() / getAlturaJogo() + "_";
		snapShot += getPlayer1().getPlacar().getPontuacao()  + "_" + getPlayer2().getPlacar().getPontuacao() + "_";
		
		//Retorna a string final
		return snapShot;
	}
	
	/**
	 * Atualiza as posicoes de acordo com a string de snapshot do servidor
	 */
	public void atualizaPosicoesClient(String snapsShotServidor){
		
		//Recebe a lista de posicoes
		String[] posicoes = snapsShotServidor.split("_");
		
		//Tenta receber os valores
		try{
			
			//Posiciona os elementos
			getPlayer1().setPosX((new Float(posicoes[0])) * getLarguraJogo());
			getPlayer1().setPosY((new Float(posicoes[1])) * getAlturaJogo());
			getPlayer2().setPosX((new Float(posicoes[2])) * getLarguraJogo());
			getPlayer2().setPosY((new Float(posicoes[3])) * getAlturaJogo());
			getBolaInicial().setPosX((new Float(posicoes[4])) * getLarguraJogo());
			getBolaInicial().setPosY((new Float(posicoes[5])) * getAlturaJogo());			
			getPlayer1().getPlacar().setPontuacao((new Integer(posicoes[6])));			
			getPlayer2().getPlacar().setPontuacao((new Integer(posicoes[7])));			
		}
		catch(NumberFormatException e){
			
		}
	}
}
