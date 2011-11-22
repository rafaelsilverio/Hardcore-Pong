package com.br.rafael.pong.elementos.pong;

import com.br.rafael.pong.elementos.bases.Circulo;

/**
 * Classe que representa uma bola ativa do jogo
 * deadzone = area em que a bola morre 
 * 
 * @author Rafael
 *
 */
public class Bola extends Circulo {

	//Declara os atributos que determinam a movimentacao da bola
	private float velocidadeMovimento; //Determina a velocidade do movimento da bola
	private float aceleradorMovimento; //Determina a que passo a velocidade da bola é aumentada
	private float escalarY = 0; //Determina o valor que deve ser multiplicado a Y, para inclinar o movimento da bola
	private int direcaoBolaX = 1; //Determina a direcao da coordenada X da bola
	private int direcaoBolaY = 1; //Determina a direcao da coordenada Y da bola
	private int numeroMaximoAceleracoes = 40; //Define quantas vezes que a bola pode ser acelerada

	//Atributos que copiam o estado inicial da bola, para que ela possa se reiniciar
	private float copiaX;
	private float copiaY;
	private float copiaRaio;
	private float copiaVelocidadeMovimento; 
	private float copiaAceleradorMovimento;
	private float copiaEscalarY; 
	private int copiaDirecaoBolaX;
	private int copiaDirecaoBolaY;	
	private int copiaNumeroMaximoAceleracoes;
	private int copiaVermelho;
	private int copiaVede;
	
	/**
	 * Construtor que inicia uma bola sem atributos definidos
	 */
	public Bola(){
		super(0.0f, 0.0f, 0.0f);
	}
	
	/**
	 * Alimenta os atributos da classe
	 * 
	 * @param X
	 * @param Y
	 * @param raio
	 * @param direcaoBolaX
	 * @param direcaoBolaY
	 */
	public Bola(float X, float Y, float raio, Integer direcaoBolaX, Integer direcaoBolaY, float velocidadeMovimento, float aceleradorMovimento) {
		super(X, Y, raio);
		
		//Recebe os atributos
		this.velocidadeMovimento = velocidadeMovimento;
		this.aceleradorMovimento = aceleradorMovimento;
		
		//Atribui valores nao obrigatorios
		if(direcaoBolaX != null)
			this.direcaoBolaX = direcaoBolaX;
		if(direcaoBolaY != null)
			this.direcaoBolaY = direcaoBolaY;
		
		//Determina cor especifica
		defineCores(200, 255, 255, 0);		
		
		//Copia os valores dos movimentos
		copiaAceleradorMovimento = aceleradorMovimento;
		copiaDirecaoBolaX = this.direcaoBolaX;
		copiaDirecaoBolaY = this.direcaoBolaY;
		copiaEscalarY = escalarY;
		copiaRaio = getRaio();
		copiaVelocidadeMovimento = velocidadeMovimento;
		copiaX = getPosX();
		copiaY = getPosY();
		copiaNumeroMaximoAceleracoes = numeroMaximoAceleracoes;
		copiaVermelho = getVermelho();
		copiaVede = getVerde();
	}


	/**
	 * Volta a bola para seu estado inicial
	 */
	public void reiniciaBola(){
		setAceleradorMovimento(copiaAceleradorMovimento);
		setDirecaoBolaX(copiaDirecaoBolaX);
		setDirecaoBolaY(copiaDirecaoBolaY);
		setEscalarY(copiaEscalarY);
		setPosX(copiaX);
		setPosY(copiaY);
		setRaio(copiaRaio);
		setVelocidadeMovimento(copiaVelocidadeMovimento);
		setNumeroMaximoAceleracoes(copiaNumeroMaximoAceleracoes);
		setVermelho(copiaVermelho);
		setVerde(copiaVede);
	}
	
	/**
	 * Define se a bola esbarrou em alguma deadzone
	 * 	
	 * @param limiteInferior
	 * @param limiteSuperior
	 * @return
	 */
	public boolean encostaDeadzone(float limiteInferior, float limiteSuperior){
	
		//Verifica se a bola vai bater em alguma deadzone
		if((getPosX() - getRaio() <= limiteInferior) || (getPosX() + getRaio() >= limiteSuperior)){
			return true;
		}
		
		//Retorna indicativo que esta livre da deadzone
		return false;
	}
	
	/**
	 * Define se a bola esbarrou em alguma parede
	 * 	
	 * @param limiteInferior
	 * @param limiteSuperior
	 * @return
	 */
	public boolean encostaParede(float limiteInferior, float limiteSuperior){
	
		//Verifica se a bola vai bater em alguma deadzone
		if((getPosY() - getRaio() <= limiteInferior) || (getPosY() + getRaio() >= limiteSuperior)){
			return true;
		}
		
		//Retorna indicativo que esta livre da deadzone
		return false;
	}	
	
	/**
	 * Define os próximos valores que devem ser alocados nos atributos da classe.
	 * O método não faz verificações de colisões, que devem ser realizadas externamente.
	 */
	public void movimenta(){
		
		//Define proximo X
		setPosX(getPosX() + (velocidadeMovimento * direcaoBolaX));
		
		//Define proximo Y
		setPosY(getPosY() + (escalarY * direcaoBolaY)); 
	}

	/**
	 * Acrescenta velocidade para a bola, incrementado de acordo com o acelerador
	 */
	public void aumentaVelocidade(){
		
		//Incrementa a velocidade apenas se não foi aumentada no máximo
		if(numeroMaximoAceleracoes > 0){
			velocidadeMovimento = velocidadeMovimento + aceleradorMovimento;
			numeroMaximoAceleracoes--;
			
			//Reduz a coloração verde (ficando cada vez mais vermelho)
			reduzColoracao();
		}
	}
	
	/**
	 * Reduz a coloracao de verde da bola
	 */
	public void reduzColoracao(){
		
		//Impede valores negativos
		if(getVerde() - (copiaVede / copiaNumeroMaximoAceleracoes) > 0){
			
			//Reduz a coloração verde (ficando cada vez mais vermelho)
			setVerde(getVerde() - (copiaVede / copiaNumeroMaximoAceleracoes));			
		}
		else{
			
			//Define como zero
			setVerde(0);
		}
		
	}
	
	/* Getters e setters padrão */
	public float getVelocidadeMovimento() {
		return velocidadeMovimento;
	}

	public void setVelocidadeMovimento(float velocidadeMovimento) {
		this.velocidadeMovimento = velocidadeMovimento;
	}

	public float getAceleradorMovimento() {
		return aceleradorMovimento;
	}

	public void setAceleradorMovimento(float aceleradorMovimento) {
		this.aceleradorMovimento = aceleradorMovimento;
	}

	public float getEscalarY() {
		return escalarY;
	}

	public void setEscalarY(float escalarY) {
		this.escalarY = escalarY;
	}

	public int getDirecaoBolaX() {
		return direcaoBolaX;
	}

	public void setDirecaoBolaX(int direcaoBolaX) {
		this.direcaoBolaX = direcaoBolaX;
	}

	public int getDirecaoBolaY() {
		return direcaoBolaY;
	}

	public void setDirecaoBolaY(int direcaoBolaY) {
		this.direcaoBolaY = direcaoBolaY;
	}

	public int getNumeroMaximoAceleracoes() {
		return numeroMaximoAceleracoes;
	}

	public void setNumeroMaximoAceleracoes(int numeroMaximoAceleracoes) {
		this.numeroMaximoAceleracoes = numeroMaximoAceleracoes;
	}
}
