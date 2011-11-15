package com.br.rafael.pong.activity;

import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InicialActivity extends Activity {

	//Cria identificadores para as activitys
	private static final int ACTIVITY_SINGLEPLAYER = 0;
	private static final int ACTIVITY_MULTIPLAYER = 1;
	private static final int ACTIVITY_REQUISITA_LIGAR_BT = 2;
	
	//Declara um adaptador de bluetooth
	private BluetoothAdapter adaptadorBT;
	
	//Define mensagens para erros de BT
	private static final int MSG_ERRO_BT_LIGAR = R.string.bt_not_active;
	private static final int MSG_ERRO_BT_ACEITAR = R.string.bt_turn_on_error;
	
	//Define UUID para conexoes de multiplayer
	private static UUID appUUID = UUID.fromString("fa87c1d1-afac-11de-8a39-0800200c9a66");
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);
        
        //Inicia o adaptador
        adaptadorBT = BluetoothAdapter.getDefaultAdapter();
        
        //Se o aparelho não tiver suporte a BT, sinaliza e bloqueia botao de multiplayer
        if(adaptadorBT == null){
        	
        	//Avisa ao usuário o problema
        	exibeToastBt(MSG_ERRO_BT_LIGAR);        	
        }
        
        //Define listeners
        Button multiplayer = (Button) findViewById(R.id.bt_multi_play_start);
        Button singlePlayer = (Button) findViewById(R.id.bt_single_play_start);
        multiplayer.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		
        		//Valida adaptador de bluetooth
        		if(adaptadorBT == null){
        			
                	//Avisa ao usuário o problema
        			exibeToastBt(MSG_ERRO_BT_LIGAR);         			
        		}
        		else{
        			
        			//Aciona o modo para ligar o bt
        			ligaBt();
        		}
        	}
        });     
        singlePlayer.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		
        		//Inicia single player
        		comecaSinglePlayer();
        	}
        });
        
        //Define as fonts customizadas para os inputs
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/hachicro.TTF");
        ((TextView) findViewById(R.id.bt_single_play_start)).setTypeface(tf);    
        ((TextView) findViewById(R.id.bt_multi_play_start)).setTypeface(tf);    
    }
    
    //Exibe toast de erro de bt para usuario
    public void exibeToastBt(int mensagem){
    	Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();        	
    }
    
    //Envia para activity de ligar BT
    public void ligaBt(){
    	
    	//Chama activity do proprio android, que habilita o BT
        Intent habilitaBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(habilitaBTIntent, ACTIVITY_REQUISITA_LIGAR_BT);    	
    }
    
    //Inicia atividade de singleplayer
    public void comecaSinglePlayer(){
    	
    	//Inicia o single player
        Intent intent = new Intent(this, LoadStageActivity.class);
        startActivityForResult(intent, ACTIVITY_SINGLEPLAYER);            
    }    
    
    //Inicia atividade de multiplayer
    public void comecaMultiPlayer(){
    	
    	//Inicia as opcoes de multiplayer
        Intent intent = new Intent(this, OpcoesMultiplayerActivity.class);
        startActivityForResult(intent, ACTIVITY_MULTIPLAYER);    	
    }      
    
    //Funcionalidade para retorno das activitys
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        
        	//Retorno da activity de ligar bluetooth
	        case ACTIVITY_REQUISITA_LIGAR_BT:
	        	
	            //Caso o usuário tenha habilitado o bt normalmente
	            if (resultCode == Activity.RESULT_OK) {
	            	
	            	//Envia para tela de escolha de cliente/servidor
	            	comecaMultiPlayer();
	            }
	            
	            //Caso o BT não foi habilitado corretamente
	            else {
	            	
                	//Avisa ao usuário o problema
        			exibeToastBt(MSG_ERRO_BT_ACEITAR);
	            }
	            break;
        }
    }

	public static UUID getAppUUID() {
		return appUUID;
	}     
}
