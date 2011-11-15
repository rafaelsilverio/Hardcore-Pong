package com.br.rafael.pong.controladores.base;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Jogo {
	void desenha(Canvas canvas, Paint paint);
	void atualiza();
}
