package com.br.rafael.util;

import android.bluetooth.BluetoothSocket;

import com.br.rafael.pong.controladores.base.Jogo;

/**
 * Classe que tem o objetivo de transitar atributos entre Activitys
 * do android. Pois não há uma maneira de enviar tipos Object.
 * 
 * @author Rafael
 *
 */
public class TransitaAtributos {
	
	//Declara atributo de socket
	private static BluetoothSocket socketConexao;
	
	//Declara qual deve ser a fase atual
	private static int faseAtual;
	private static Jogo instanciaProximaFase;
	
	//Declara atributo que armazena se o celular é cliente ou servidor
	private static int papelMultiplayer = 0;
	
	/* Getters e setters gerados */
	public static BluetoothSocket getSocketConexao() {
		return socketConexao;
	}
	public static void setSocketConexao(BluetoothSocket socketConexao) {
		TransitaAtributos.socketConexao = socketConexao;
	}
	public static int getPapelMultiplayer() {
		return papelMultiplayer;
	}
	public static void setPapelMultiplayer(int papelMultiplayer) {
		TransitaAtributos.papelMultiplayer = papelMultiplayer;
	}
	public static int getFaseAtual() {
		return faseAtual;
	}
	public static void setFaseAtual(int faseAtual) {
		TransitaAtributos.faseAtual = faseAtual;
	}
	public static Jogo getInstanciaProximaFase() {
		return instanciaProximaFase;
	}
	public static void setInstanciaProximaFase(Jogo instanciaProximaFase) {
		TransitaAtributos.instanciaProximaFase = instanciaProximaFase;
	}
}