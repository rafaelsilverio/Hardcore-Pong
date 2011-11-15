package com.br.rafael.pong.multiplayer.threads;

import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.br.rafael.pong.activity.OpcoesMultiplayerActivity;

/**
 * Classe responsavel por receber uma conexão como servidor
 * de bluetooth. Assim que uma conexão é recebida, uma nova
 * thread é chamada 
 *  
 * @author Rafael
 *
 */
public class ServidorBluetooth extends Thread {

    //Recebe um objeto da abertura de socket como servidor
    private BluetoothServerSocket btSocketServidor;
    
    //Recebe o objeto que representa o BT do aparelho celular
    private BluetoothAdapter btCelular = BluetoothAdapter.getDefaultAdapter();
    
    //Declara NAME, utilizado na descricao do servidor de BT
    private static final String PONG_NAME = "Hardcore Pong Multiplayer";

    //Declara atributo que mantem a instancia da atividade atual
    private OpcoesMultiplayerActivity atividadeAtual;
    
    /**
     * Alimenta os atributos da classe
     */
    public ServidorBluetooth(OpcoesMultiplayerActivity atividade) {
    	
    	//Recebe a atividade
    	atividadeAtual = atividade;
    	
    	//Declara um socket de conexao de servidor, para testar a criacao do socket
        BluetoothServerSocket socketTemporario = null;
        
        //Tenta realizar a criacao do socket de servidor
        try {
        	socketTemporario = btCelular.listenUsingRfcommWithServiceRecord(PONG_NAME, OpcoesMultiplayerActivity.getPongUuid());
        } 
        catch (IOException e) {
        	atividadeAtual.finalizaModal(true);
        }
        
        //Envia o socket para o atributo da classe
        btSocketServidor = socketTemporario;
    }
    
    /**
     * Chamado ao executar a thread com start
     */
    public void run() {
    	
    	//Inicia um socket de conexao fechada
        BluetoothSocket socketConexao = null;
        
        //Executa enquanto nao ocorrer uma excecao, causada pelo accept ou com o socket sendo retornado
        while (true) {
        	
        	//Bloqueia a execucao, escutando conexoes com o servidor
            try {
            	socketConexao = btSocketServidor.accept();
            } catch (IOException e) {
            	
            	//Finaliza a thread, removendo a modal da activity
            	atividadeAtual.finalizaModal(true);
                break;
            }
            
            //Se uma conexao foi aceita
            if (socketConexao != null) {
            	
            	//Fecha a modal
            	atividadeAtual.finalizaModal(false);
            	
            	//Chama o metodo para gerenciar a conexao
            	atividadeAtual.comecaMultiplayer(socketConexao);
            	
                //Fecha o socket de servidor, pois nao sera mais necessario
                try {
					btSocketServidor.close();
				} catch (IOException e) { }
                
                //Termina o loop, finalizando o RUN da thread
                break;
            }
        }
    }
 
    /**
     * Cancela a escuta do servidor, terminando a thread
     */
    public void cancel() {
        try {
        	btSocketServidor.close();
        } catch (IOException e) { }
    }	
}
