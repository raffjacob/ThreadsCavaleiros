/***
 * 		Exercício Threads com Semáforos - Sistemas Operacionais 1 - Fatec ZL 
 * 		Prof. M.Sc. Leandro Colevati dos Santos
 * 		Corredor com Cavaleiros
 * 		
 * 		Aluno: Rafael Jacob Bastos
 * 		
 * 		
 * 		Data: 16/05/2020
 */

package view;

import java.util.concurrent.Semaphore;

import controller.Knights;

public class Castelo {
	public static void main(String[] args) {
		int permissoes = 1;
		Semaphore semaforo = new Semaphore(permissoes); //Definido semaforo principal
		Semaphore porta1 = new Semaphore(permissoes);   //Definido semaforo da porta 1
		Semaphore porta2 = new Semaphore(permissoes);   //Definido semaforo da porta 2
		Semaphore porta3 = new Semaphore(permissoes);	//Definido semaforo da porta 3
		Semaphore porta4 = new Semaphore(permissoes);	//Definido semaforo da porta 4
		
		for (int cavaleiro = 0; cavaleiro <4; cavaleiro++) {   //Enviando numero de threads
			Thread tknight = new Knights(cavaleiro, semaforo, porta1, porta2, porta3, porta4);
			tknight.start();
		}
	}
}
