package com.br.rafael.pong.activity;

import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.br.rafael.pong.multiplayer.threads.MultiplayerGame;
import com.br.rafael.pong.multiplayer.threads.ServidorBluetooth;
import com.br.rafael.util.TransitaAtributos;

public class OpcoesMultiplayerActivity extends Activity {
	
	//Define UUID utilizada no aplicativo, para conversas entre cliente e servidor de BT
	private static final UUID PONG_UUID = UUID.fromString("fa87c1d1-afac-11de-8a39-1800200c9a66");
	
	//Declara ids para as atividades chamadas
	private static final int ACT_LIGAR_DESCOBERTA = 1;
	private static final int ACT_INICIA_CLIENTE = 2;
	private static final int ACT_MULTIPLAYER_GAME = 3;
	
	//Mantem instancia do modal
	private ProgressDialog progressDialog;
	
	//Define tempo de descoberta
	private static final int TEMPO_DESCOBERTA = 300;
	
	//Define nome do parametro de socket para enviar intent
	public static final String TRANSITA_SOCKET = "SocketConexao";
	
	//Metodo de criacao
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opcoes_multiplayer);
        
    	//Define os listeners dos botoes
        Button servidor = (Button) findViewById(R.id.bt_server);
        Button cliente = (Button) findViewById(R.id.bt_client);
        servidor.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		iniciaModoDescoberta();  
        	}
        });   
        cliente.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		comecaCliente();  
        	}
        });         
        
        //Define as fonts customizadas para os inputs
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/hachicro.TTF");
        ((TextView) findViewById(R.id.bt_server)).setTypeface(tf);    
        ((TextView) findViewById(R.id.bt_client)).setTypeface(tf);            
    }
    
    //Inicia atividade de cliente de bluetooth
    public void comecaCliente(){
    	
    	//Chama activity do proprio android, que habilita o BT
        Intent intent = new Intent(this, ClienteActivity.class);
        startActivityForResult(intent, ACT_INICIA_CLIENTE);    	
    }      
    
    //Inicia ativiade de transicao de dados
    public void comecaMultiplayer(BluetoothSocket socket){
    	
    	//Insere o socket para a classe de transicao de objetos
    	TransitaAtributos.setSocketConexao(socket);
    	
    	//Define celular como servidor
    	TransitaAtributos.setPapelMultiplayer(MultiplayerGame.SERVIDOR);
    	
    	//Declara intent, insere o socket e envia para a atividade
        Intent intent = new Intent(this, MultiplayerActivity.class);
        startActivityForResult(intent, ACT_MULTIPLAYER_GAME);    	    	
    }
    
    //Inicia atividade para deixar o celular em modo descoberta
    public void iniciaModoDescoberta(){
    
		//Inicia modal e exibe
    	progressDialog = new ProgressDialog(this);
    	progressDialog.setIndeterminate(true);
    	progressDialog.setMessage(getResources().getText(R.string.server_waiting).toString());
    	progressDialog.show();    	
    	
    	//Inicia um intent para activity de deixar o aparelho em modo de descoberta
    	Intent servidorIntent = new	Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
    	
    	//Define o tempo de duracao da descoberta
    	servidorIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, TEMPO_DESCOBERTA);
    	
    	//Inicia atividade de descoberta
    	startActivityForResult(servidorIntent, ACT_LIGAR_DESCOBERTA);	    	
    }    
    
    //Verifica retorno das atividades
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        
        	//Retorno da activity que faz o aparelho ser descoberto
	        case ACT_LIGAR_DESCOBERTA:
	        	
	        	//Verifica se o usuario cancelou a descoberta
	        	if(resultCode == Activity.RESULT_CANCELED){
	        		finalizaModal(false);
	        		Toast.makeText(this, R.string.user_cancel_bt_disover, Toast.LENGTH_LONG).show();      
	        	}
	        	else{
	        		
	        		//Inicia thread de aguardo de conexoes
	        		ServidorBluetooth conexaoThread = new ServidorBluetooth(this);
	        		conexaoThread.start();
	        	}
	        	break;
	        	
	        //Retorno da activity de inicio de cliente
	        case ACT_INICIA_CLIENTE:
	        	
	        	//Se for retorno definido
	            if (resultCode == Activity.RESULT_FIRST_USER) {
	            	
	                //Recebe dados do celular
	                String celular = data.getExtras().getString(ClienteActivity.CONNECT_ERROR);
	                
	                //Exibe erro
	        		new AlertDialog.Builder(this)
	        		.setMessage(getResources().getText(R.string.client_fail).toString() + "\nDevice: [" + celular + "]")
	        		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        			public void onClick(DialogInterface dialog, int whichButton){}
	        		}).show();    	                
	            }
	        	break;
        }
    }

    //Finaliza a modal
    public void finalizaModal(boolean erro){
    	progressDialog.dismiss();
    	
    	//Exibe toast de erro no servidor
    	if(erro){
    		Toast.makeText(this, R.string.server_fail, Toast.LENGTH_LONG).show();        	
    	}
    }
    
	public static UUID getPongUuid() {
		return PONG_UUID;
	}   
}
