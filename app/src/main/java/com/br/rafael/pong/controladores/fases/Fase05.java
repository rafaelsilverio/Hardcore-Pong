package com.br.rafael.pong.controladores.fases;

public class Fase05 extends Fase01 {
    static final int PONTOS_PARTIDA = 5;

    public Fase05(int alturaJogo, int larguraJogo) {
        super(alturaJogo, larguraJogo);
    }

    @Override
    public void instanciaElementos(){
        super.instanciaElementos();
        setPontosPartida(PONTOS_PARTIDA);
    }

    @Override
    public void atualiza(){

        //Se jogo acabou, no faz nada
        if(!isFimJogo()){
            super.atualiza();
            atualizaIAP2(9);
        }
    }
}
