package jogodecartas;

public class Jogador {

    private final String NOME;
    private Carta[] cartas;

    public Jogador(String nome) {
        this.NOME = nome;
    }

    public void setCartas(Carta[] cartas) {
        this.cartas = cartas;
    }

    public void mostrarCartas() {
    	int indice = 0;
        System.out.println("-----------CARTAS DE " + NOME.toUpperCase() + "------------------");
        for (Carta carta : cartas) {
        	
            System.out.println("["+ indice + "] " + carta.toString());
            indice++;
        }
    }
    
    public Carta[] getCartas() {
		return cartas;
	}
    
    

	public String getNOME() {
		return NOME;
	}
}
