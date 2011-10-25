package com.br.rafael.util;

import android.bluetooth.BluetoothSocket;

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
	
	//Declara atributo que armazena se o celular é cliente ou servidor
	private static int papelMultiplayer;
	
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
}