package com.br.rafael.pong.activity;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.WindowManager;

import com.br.rafael.pong.elementos.pong.Jogador;
import com.br.rafael.pong.multiplayer.controladores.JogoMultiplayer;
import com.br.rafael.pong.multiplayer.threads.TransitaDados;
import com.br.rafael.util.TransitaAtributos;

public class MultiplayerActivity extends Activity {
	
	//Declara o socket de conexao atual
	private BluetoothSocket socketConexao;
	
	//Mantem stancia da thread de transicao de dados
	private TransitaDados transitaDados;
	
	//Metodo de criacao
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer);
        
        //Remove fade out da tela
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);        
        
        //Recebe o socket que foi definido
        socketConexao = TransitaAtributos.getSocketConexao();
        
        //Se o socket for null, finaliza a activity
        if(socketConexao == null)
        	finish();
        
        //Caso contrrio, inicia thread de transio de dados
        else{
        	
        	//Instancia e inicia
        	transitaDados = new TransitaDados(socketConexao, this);
        	transitaDados.start();
        }
    }

    //Envia dados da aplicacao para o aparelho conectado
    public void enviaDados(int codigoMovimento){
    	
    	//Define string a ser enviada de acordo com o tipo do moviemnto
    	String mensagem = "";
    	switch(codigoMovimento){
    		case Jogador.MOVIMENTO_ACIMA:
    			mensagem = JogoMultiplayer.MOVIMENTANDO_ACIMA;
    			break;
    		case Jogador.MOVIMENTO_ABAIXO:
    			mensagem = JogoMultiplayer.MOVIMENTANDO_ABAIXO;
    			break;
    		case Jogador.MOVIMENTO_NULO:
    			mensagem = JogoMultiplayer.ESTATICO;
    			break;    			
    	}
    	
    	//Codifica em bytes e envia
    	byte[] envio = mensagem.getBytes();
    	transitaDados.escreve(envio);
    }
    
    //Envia uma string diretamente para o aparelho conectado
    public void enviaDados(String mensagem){
    	
    	//Codifica em bytes e envia
    	byte[] envio = mensagem.getBytes();
    	transitaDados.escreve(envio);
    }
    
    /* Getters e seters automaticos */
	public BluetoothSocket getSocketConexao() {
		return socketConexao;
	}
}
