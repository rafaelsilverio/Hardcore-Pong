package com.br.rafael.pong.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.br.rafael.pong.controladores.base.Jogo;
import com.br.rafael.pong.controladores.fases.Fase01;
import com.br.rafael.pong.controladores.fases.Fase02;
import com.br.rafael.pong.controladores.fases.Fase03;
import com.br.rafael.util.TransitaAtributos;

public class LoadStageActivity  extends Activity {
	
	//Cria identificadores para as activitys
	private static final int ACTIVITY_SINGLEPLAYER = 0;	
	
	//Atribui os elementos da tela
	private TextView tituloFase;
	private TextView descricaoFase;
	private Button iniciarFase;
	
	//Metodo de criacao
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_stage);
        
        //Instancia os elementos da tela
        tituloFase = (TextView) findViewById(R.id.numero_fase);
        descricaoFase = (TextView) findViewById(R.id.descricao_fase);
        iniciarFase = (Button) findViewById(R.id.iniciar_fase);
        
        //Define as fonts customizadas para os inputs
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/hachicro.TTF");
        tituloFase.setTypeface(tf);    
        descricaoFase.setTypeface(tf);         
        iniciarFase.setTypeface(tf);      
        
        //Define textos iniciais
        tituloFase.setText("STAGE 1:");
        descricaoFase.setText("DEFEAT THE RED BAR BEFORE THE TIME RUNS OUT!\nTHE FIRST ONE TO SCORE 5 POINTS WINS!");
        
        //Define os clicks
        iniciarFase.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		comecaSinglePlayer();
        	}
        });        
    }
    
    //Inicia atividade de singleplayer
    public void comecaSinglePlayer(){
    	
    	//Define qual deve ser a fase atual
    	TransitaAtributos.setInstanciaProximaFase(retornaStanciaFaseAtual());
    	
    	//Inicia o single player
        Intent intent = new Intent(this, HardcorePongActivity.class);
        startActivityForResult(intent, ACTIVITY_SINGLEPLAYER);            
    }   
    
    //Define qual deve ser a fase atual, retornando a instancia
    public Jogo retornaStanciaFaseAtual(){
    	
		//Cria uma instancia de jogo, enviando as dimensoes da tela
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	
    	//Determina qual instancia retornar
		switch(TransitaAtributos.getFaseAtual()){
			case 1:
				return new Fase02(display.getHeight(), display.getWidth());
			case 2:
				return new Fase03(display.getHeight(), display.getWidth());					
			default:
				return new Fase01(display.getHeight(), display.getWidth());				
		}
    }
    
    //Verifica retorno das atividades
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        
        	//Retorno da atividade que carrega o jogo
	        case ACTIVITY_SINGLEPLAYER:
	        	
	        	//Determina qual texto definir
	    		switch(TransitaAtributos.getFaseAtual()){
	    			case 1:
	    	            tituloFase.setText("STAGE 2:");
	    	            descricaoFase.setText("DEFEAT THE BLUE  BAR BEFORE THE TIME RUNS OUT!\nTHE FIRST ONE TO SCORE 5 POINTS WINS!");
	    	            break;
	    			case 2:
	    	            tituloFase.setText("STAGE 3:");
	    	            descricaoFase.setText("DEFEAT THE BLUE  BAR BEFORE THE TIME RUNS OUT!\nTHE FIRST ONE TO SCORE 5 POINTS WINS!");
	    	            break;
	    			default:
	    	            tituloFase.setText("STAGE 1:");
	    	            descricaoFase.setText("DEFEAT THE BLUE  BAR BEFORE THE TIME RUNS OUT!\nTHE FIRST ONE TO SCORE 5 POINTS WINS!");			
	    		}	        	
	        	
        }
    }    
}
