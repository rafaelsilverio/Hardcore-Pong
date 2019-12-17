package com.br.rafael.util;

import android.bluetooth.BluetoothSocket;

import com.br.rafael.pong.controladores.base.BaseJogo;

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
	private static BaseJogo instanciaProximaFase;
	
	//Declara atributo que armazena se o celular é cliente ou servidor
	private static int papelMultiplayer = 0;
	
	//Mantem as dimensoes do celular
	private static int alturaCelular;
	private static int larguraCelular;
	
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
	public static BaseJogo getInstanciaProximaFase() {
		return instanciaProximaFase;
	}
	public static void setInstanciaProximaFase(BaseJogo instanciaProximaFase) {
		TransitaAtributos.instanciaProximaFase = instanciaProximaFase;
	}
	public static int getAlturaCelular() {
		return alturaCelular;
	}
	public static void setAlturaCelular(int alturaCelular) {
		TransitaAtributos.alturaCelular = alturaCelular;
	}
	public static int getLarguraCelular() {
		return larguraCelular;
	}
	public static void setLarguraCelular(int larguraCelular) {
		TransitaAtributos.larguraCelular = larguraCelular;
	}
}