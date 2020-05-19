package controller;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Knights extends Thread {
	private int cavaleiro;
	private Semaphore semaforo;
	private Semaphore porta1;
	private Semaphore porta2;
	private Semaphore porta3;
	private Semaphore porta4;
	private int distanciaTotal = 2000;
	private int distanciaPercorrida = 0;
	int tempo = 50;
	private boolean permit = false;
	boolean permitP; 
	private int vetP[] = VetorPorta();
	private String Porta; // Cor da porta

	public Knights(int cavaleiro, Semaphore semaforo, Semaphore porta1, Semaphore porta2, Semaphore porta3, Semaphore porta4) {
		this.cavaleiro = cavaleiro;
		this.semaforo = semaforo;
		this.porta1 = porta1;
		this.porta2 = porta2;
		this.porta3 = porta3;
		this.porta4 = porta4;
	}

	@Override
	public void run() {
		Andando();
		try {
			permit = semaforo.tryAcquire(3, TimeUnit.SECONDS); // permit recebe booleano ao tentar acessar o recurso, caso o semaforo esteja disponivel ele retorna true, e da um release após 3 segundos.
			if (permit) { // se permit = true, ele toda a funcao AndandoTocha.
				System.out.println(cavaleiro + "º Cavaleiro pegou a Tocha!");
				AndandoTocha();
			} else {   // caso permit = false, ele roda o AndandoMais.
				AndandoMais();
			}
		} catch (Exception e) {

		} finally {
			if (permit) { // após 3 segundos ele vem para o finally e da o release
				semaforo.release();
			}
		}
	}

	private static int[] VetorPorta() {
		int vetP[] = new int[4];
		int a = 0;
		for (int i = 0; i < 4; i++) {
			vetP[i] = a;
			a++;
		}
		return vetP;
	}

	private void Andando() {
		while (distanciaPercorrida < 500) {
			int deslocamento = 2 + (int) (Math.random() * 2); // Define o galope de 2 à 4 metros.
			distanciaPercorrida += deslocamento;
			try {
				sleep(tempo);
			} catch (Exception e) {
			}
			System.out.println(cavaleiro + "º Cavaleiro andou " + distanciaPercorrida + "ms.");
		}

	}

	private void AndandoMais() {
		while (distanciaPercorrida < 1500) {
			int deslocamento = 2 + (int) (Math.random() * 2); // Define o galope de 2 à 4 metros.
			distanciaPercorrida += deslocamento;
			try {
				sleep(tempo);
			} catch (Exception e) {
			}
			System.out.println(cavaleiro + "º Cavaleiro andou " + distanciaPercorrida + "ms.");
		}
		try {
			permit = semaforo.tryAcquire(); //nova validacao do permit
			if (permit) {
				System.out.println(cavaleiro + "º Cavaleiro pegou a Pedra Brilhante!");
				AndandoPedra();
			} else {
				while (distanciaPercorrida < distanciaTotal) {
					int deslocamento = 2 + (int) (Math.random() * 2); // Define o galope de 2 à 4 metros.
					distanciaPercorrida += deslocamento;
					try {
						sleep(tempo);
					} catch (Exception e) {
					}
					System.out.println(cavaleiro + "º Cavaleiro andou " + distanciaPercorrida + "ms.");
				}
				System.out.println(cavaleiro + "º Cavaleiro chegou ao final do corredor.");
				Portas();
			}
		} catch (Exception e) {
		}
	}

	private void AndandoTocha() {
		while (distanciaPercorrida < distanciaTotal) {
			int deslocamento = 2 + (int) (Math.random() * 4); // Define o galope de 4 à 6 metros.
			distanciaPercorrida += deslocamento;
			try {
				sleep(tempo);
			} catch (Exception e) {
			}
			System.out.println(cavaleiro + "º Cavaleiro andou " + distanciaPercorrida + "ms com a Tocha.");
		}
		System.out.println(cavaleiro + "º Cavaleiro chegou ao final do corredor.");
		Portas();
	}

	private void AndandoPedra() {
		while (distanciaPercorrida < distanciaTotal) {
			int deslocamento = 2 + (int) (Math.random() * 4); // Define o galope de 4 à 6 metros.
			distanciaPercorrida += deslocamento;
			try {
				sleep(tempo);
			} catch (Exception e) {
			}
			System.out.println(cavaleiro + "º Cavaleiro andou " + distanciaPercorrida + "ms com a Pedra Brilhante.");
		}
		System.out.println(cavaleiro + "º Cavaleiro chegou ao final do corredor.");
		Portas();
	}

	private void Portas() { // As portas com um semáforo cada
		permitP = false;
		while (permitP == false) {    //Loop até a thread entrar numa porta
			int door = (int) (Math.random() * 3);
			if (vetP[door] == 0) {
				try {
					permitP = porta1.tryAcquire();
					if (permitP) {
						vetP[door] = 5; //Adiciona um novo valor no vetor para pular os ifs.
						Porta = "Branca";
						Mensagens(door, Porta);
					} else {
						door = (int) (Math.random() * 3);
					}
				} catch (Exception e) {

				}
			}
			if (vetP[door] == 1) {
				try {
					permitP = porta2.tryAcquire();
					if (permitP) {
						vetP[door] = 5;
						Porta = "Preta";
						Mensagens(door, Porta);
					} else {
						door = (int) (Math.random() * 3);
					}
				} catch (Exception e) {

				}
			}
			if (vetP[door] == 2) {
				try {
					permitP = porta3.tryAcquire();
					if (permitP) {
						vetP[door] = 5;
						Porta = "Azul";
						Mensagens(door, Porta);
					} else {
						door = (int) (Math.random() * 3);
					}
				} catch (Exception e) {

				}
			}
			if (vetP[door] == 3) {
				try {
					permitP = porta4.tryAcquire();
					if (permitP) {
						vetP[door] = 5;
						Porta = "Vermelha";
						Mensagens(door, Porta);
					} else {
						door = (int) (Math.random() * 3);
					}
				} catch (Exception e) {

				}
			}
		}

	}

	private void Mensagens(int door, String Porta) { // Mensagens para cada porta
		if (door == 3) {
			System.out.println(cavaleiro + "º Cavaleiro entrou na porta " + Porta + " e saiu do corredor!");
		} else {
			System.out.println(cavaleiro + "º Cavaleiro entrou na porta " + Porta + " e foi morto!");
		}
	}
}
