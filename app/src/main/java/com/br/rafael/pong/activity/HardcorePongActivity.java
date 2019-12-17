package com.br.rafael.pong.activity;

import com.br.rafael.util.TransitaAtributos;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class HardcorePongActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Remove fade out da tela
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    
    /**
     * Metodo que realiza a mudana de fase do jogo
     */
    public void alteraFase(){
    	
    	//Incrementa a fase da transicao e finaliza a activity
    	TransitaAtributos.setFaseAtual(TransitaAtributos.getFaseAtual() + 1);
    	finish();
    }
}