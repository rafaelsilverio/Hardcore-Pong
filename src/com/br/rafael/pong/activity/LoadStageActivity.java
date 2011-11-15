package com.br.rafael.pong.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    	
    	//Inicia o single player
        Intent intent = new Intent(this, HardcorePongActivity.class);
        startActivityForResult(intent, ACTIVITY_SINGLEPLAYER);            
    }       
}
