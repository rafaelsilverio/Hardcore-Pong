package com.br.rafael.pong.multiplayer.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.br.rafael.pong.multiplayer.views.PongMultiple;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;

public class TransitaDados extends Thread {

	//Mantem o socket de conexao
    private final BluetoothSocket socketConexao;
    
    //Mantem I/O de comunicacao
    private final InputStream entrada;
    private final OutputStream saida;

    //Inicia a classe e alimenta os atributos
    public TransitaDados(BluetoothSocket socket, Activity atividadeAtual) {
    	
    	//Copia o socket e a atividade atual
        socketConexao = socket;
        
        //Cria streans temporarios 
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
 
        //Testa a criacao das streams antes de as assimilar nos atributos
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }
 
        //Recebe as streams criadas
        entrada = tmpIn;
        saida = tmpOut;
    }
 
    //Inicia a thread
    public void run() {
    	
    	//Declara buffer para armazenar dados de recebimento
        byte[] buffer = new byte[1024];
        
        //Numero de bytes de retorno da conexao
        int bytes;
 
        //Fica lendo possiveis recebimentos de dados
        while (true) {
            try {
            	
                //Le os dados em buffer, e recebe o tamanho total em bytes
                bytes = entrada.read(buffer);
                
                //Envia o buffer para a atividade
                PongMultiple.eventoExterno(buffer, bytes);
            } catch (IOException e) {
                break;
            }
        }
    }
 
    //Envia dados para o aparelho conectado
    public void escreve(byte[] bytes) {
        try {
            saida.write(bytes);
        } catch (IOException e) { }
    }
 
    //Fecha a conexao
    public void cancel() {
        try {
            socketConexao.close();
        } catch (IOException e) { }
    }
}
