package jogodecartas;

import java.util.Scanner;

public class Jogo {

	private final Scanner entrada = new Scanner(System.in);
	private final Baralho BARALHO;
	private Jogador[] jogadores;
	private Carta[] cartasDescartadas;
	private int indiceDescarte;

	public Jogo() {
		BARALHO = new Baralho();
		BARALHO.mostrarBaralho();
		BARALHO.embaralhar();
		BARALHO.mostrarBaralho();
		BARALHO.setUltimaCarta(BARALHO.getCARTAS().length - 1);
		indiceDescarte = 0;
	}

	public void iniciarJogo() {
		int qtdJogadores = 0;

		do {
			System.out.println("Quantos jogadores irao participar? (maximo de 4 jogadores)");
			qtdJogadores = entrada.nextInt();
		} while (qtdJogadores > 4);

		jogadores = new Jogador[qtdJogadores];

		for (int i = 0; i < qtdJogadores; i++) {
			System.out.println("Jogador " + i + ", digite seu nome:");
			jogadores[i] = new Jogador(entrada.next());
		}
	}

	public void distribuirCartas(int qtdCartas) {
		for (Jogador jogadore : jogadores) {
			jogadore.setCartas(BARALHO.distribuirCartas(qtdCartas));
		}
		cartasDescartadas = new Carta[BARALHO.getCARTAS().length - BARALHO.getContador()];

	}

	public void mostrarCartas() {
		for (Jogador jogadore : jogadores) {
			jogadore.mostrarCartas();
		}
	}

	/**
	 * Retira sempre a ultima carta do baralho
	 */
	public Carta obterCartaBaralho() {
		int indice = BARALHO.getUltimaCarta();
		Carta cartaRetirada = BARALHO.getCARTAS()[indice];
		BARALHO.setUltimaCarta(indice - 1);
		return cartaRetirada;
	}

	/**
	 * Retira sempre a ultima carta do resto do baralho
	 */
	public Carta obterCartaRestoBaralho() {
		Carta carta = cartasDescartadas[indiceDescarte - 1];
		indiceDescarte--;
		cartasDescartadas[indiceDescarte] = null;
		return carta;
	}

	/**
	 * Descarta uma carta que esta na "mao" do jogador
	 */
	public void descartarCartaMao(int index, Jogador jogador, Carta c) {
		Carta cartaDescarte = jogador.getCartas()[index];
		jogador.getCartas()[index] = c;
		descartarCartaObtida(cartaDescarte);
	}

	/**
	 * Descarta a carta obtida, seja ela do baralho ou do resto do baralho
	 */
	public void descartarCartaObtida(Carta carta) {
		cartasDescartadas[indiceDescarte] = carta;
		System.out.println(carta.toString().toUpperCase() + " DESCARTADA");
		indiceDescarte++;
	}

	/**
	 * Metodo reponsavel por descartar uma determinada carta de um jogador
	 */
	public void descarte(Carta carta, Jogador jogador) {
		// metodo responsavel por descartar uma carta de um jogador
		// por isso e necessario passar como parametro a carta que deseja descarta
		// e o jogador
		int indice;
		System.out.println("\t\t     [DESCARTAR]\n");
		mostrarCartasJogador(jogador);
		System.out.println("-------------------------------------------------------");
		System.out.println("\t\t[9] " + carta.toString().toUpperCase() + " [CARTA OBTIDA]");
		System.out.println("-------------------------------------------------------");
		indice = entrada.nextInt();
		// se indice for 9, o metodo descarta a carta que acabou de ser
		// puxada/obtida seja ela do baralho ou do resto do baralho e não adicona a
		// mesma na "mao" do jogador
		// caso contrario, ele descarta a carta selecionada de acordo com o indice
		if (indice == 9) {
			descartarCartaObtida(carta);
		} else {
			descartarCartaMao(indice, jogador, carta);
		}

	}

	/**
	 * Mostra as cartas da mão de um determinado jogador
	 */
	public void mostrarCartasJogador(Jogador jogador) {

		int i = 0;
		for (Carta c : jogador.getCartas()) {
			System.out.println("[" + i + "] " + c.toString());
			i++;
		}
	}

	/**
	 * Metodo responsavel por obter as cartas de acordo com opçao selecionada
	 */
	private Carta obterCarta() {
		int op;
		Carta carta;
		// se a primeira posicao do resto do baralho for diferente de nulo
		// e possivel retirar as cartas ou do baralho ou do resto do baralho
		// caso contrario, só é possível retirar do baralho
		if (cartasDescartadas[0] != null) {

			System.out.println("1 - Obter carta do baralho");
			System.out.println("2 - Obter carta do resto do baralho");
			op = entrada.nextInt();
			System.out.println("-------------------------------------------------------");

			switch (op) {
			case 1:
				carta = obterCartaBaralho();
				return carta;
			case 2:
				carta = obterCartaRestoBaralho();
				return carta;
			default:
				break;
			}
		}
		System.out.println("1 - Obter carta do baralho");
		op = entrada.nextInt();
		System.out.println("-------------------------------------------------------\n");

		carta = obterCartaBaralho();
		return carta;

	}

	public void jogar() {
		int i = 1;
		int numeroPartidas = (cartasDescartadas.length / jogadores.length);
		// a quantidade de jogada só termina quando ultrapassar o tamanho do
		// resto do baralho ou quando alguem vencer(nao esta implementado)
		System.out.println("\n-------------------------------------------------------");

		System.out.println("\t\t[TOTAL DE JOGADAS " + numeroPartidas + "]\n");

		while (i <= numeroPartidas) {
			System.out.println("\t\t     [JOGADA " + i + "]");
			for (Jogador jogador : jogadores) {
				System.out.println("-------------------------------------------------------");
				System.out.println("\t\t" + jogador.getNOME().toUpperCase() + " SUA VEZ DE JOGAR");
				System.out.println("-------------------------------------------------------\n");
				Carta c = obterCarta();
				descarte(c, jogador);
				System.out.println("\n");
			}
			i++;
		}

		System.out.println(cartasDescartadas);
	}

	public Jogador[] getJogadores() {
		return jogadores;
	}

	public static void main(String[] args) {
		Jogo executar = new Jogo();
		executar.iniciarJogo();
		executar.distribuirCartas(9);
		executar.mostrarCartas();
		executar.jogar();

	}

}
