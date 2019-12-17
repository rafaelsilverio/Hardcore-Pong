package com.br.rafael.pong.multiplayer.threads;

import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.br.rafael.pong.activity.ClienteActivity;
import com.br.rafael.pong.activity.OpcoesMultiplayerActivity;

/**
 * Classe responsavel por procurar um aparelho que esteja funcionando como servidor
 * de BT, realizando a thread de conexao, e chamando uma thread no final da conexao 
 * com sucesso
 */
public class ClienteBluetooth extends Thread{
	
	//Mantem uma instancia da atividade que chamou a thread
	private ClienteActivity atividadeAtual;
	
	//Recebe aparelho que esta pareado
	private BluetoothDevice aparelhoConectado;
	
	//Mantem o socket que conectou os 2 aprelhos com sucesso
	private BluetoothSocket socketConexao;

    //Recebe o objeto que representa o BT do aparelho celular
    private BluetoothAdapter btCelular = BluetoothAdapter.getDefaultAdapter();	
    
    //Inicia a thread
	public ClienteBluetooth(ClienteActivity atividadeAtual, BluetoothDevice aparelhoConectado){
		
		//Alimenta os atributos
		this.atividadeAtual = atividadeAtual;
		this.aparelhoConectado = aparelhoConectado;
		
        //Declara socket temporario para testar inicializacao de procura
        BluetoothSocket tmpSocket = null;
 
        //Recebe socket para tentar conectar com o aparelho que foi definido
        try {
        	tmpSocket = this.aparelhoConectado.createRfcommSocketToServiceRecord(OpcoesMultiplayerActivity.getPongUuid());
        } catch (IOException e) { 
        	atividadeAtual.finalizaModal(true);
        }
        
        //Sem problemas, envia o atributo para a classe
        socketConexao = tmpSocket;
	}

	//Metodo de start da thread
    public void run() {
    	
    	//Cancela discovery, pois e inutil para client
    	btCelular.cancelDiscovery();
    	
    	//Tenta conectar os aparelhos
        try {
        	
        	//Conecta os aparelhos com o socket
        	socketConexao.connect();
        } 
        //Em caso de falha
        catch (IOException connectException) {
        	
            //Fecha o socket e finaliza a thread
            try {
            	socketConexao.close();
            } catch (IOException closeException) { 
            	atividadeAtual.finalizaModal(true);
            }
            
            //Finaliza
            atividadeAtual.finalizaModal(true);
            return;
        }
        
        //Finaliza a modal
        atividadeAtual.finalizaModal(false);
        
    	//Chama o metodo para gerenciar a conexao
    	atividadeAtual.comecaMultiplayer(socketConexao);        
    }	
}
