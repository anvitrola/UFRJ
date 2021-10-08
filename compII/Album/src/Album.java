import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta classe implementa um álbum (de figurinhas, selos, etc.) online, permitindo
 * colecionar itens que possuem uma posição específica no álbum.
 *
 * Itens são acrescentados ao álbum por meio de "pacotinhos" contendo uma quantidade
 * fixa, pré-feterminada de itens.
 *
 * Itens já existentes no álbum e recebidos novamente em pacotinhos posteriores são
 * armazenados para eventuais trocas com outro usuários.
 *
 * @param <T> o tipo de objeto colecionável que o álbum irá armazenar
 */
public class Album<T extends Colecionavel> {

    public int tamanhoDoAlbum;
    public int quantItensPorPacotinho;

    public HashMap<Integer, T> figurinhasColadas;
    public ArrayList<T> figurinhasRepetidas;

    public Album(int tamanhoDoAlbum, int quantItensPorPacotinho) {
        this.quantItensPorPacotinho = quantItensPorPacotinho;
        this.tamanhoDoAlbum = tamanhoDoAlbum;

        this.figurinhasColadas = new HashMap<>();
        this.figurinhasRepetidas = new ArrayList<>();
    }

    public void receberNovoPacotinho(Pacotinho<T> pacotinho) throws PacotinhoInvalidoException {
        int figurinhasNoPacotinho = pacotinho.getFigurinhas().size();
        ArrayList<T> figurinhasDoPacotinho = pacotinho.getFigurinhas();

        if(!(pacotinho.getFigurinhas().size() == quantItensPorPacotinho))
            throw new PacotinhoInvalidoException();

        for(T figurinha : figurinhasDoPacotinho){
            if(figurinha.getPosicao() > tamanhoDoAlbum || figurinha.getPosicao() < 1)
                throw new PacotinhoInvalidoException();
        }

        for(T figurinha : figurinhasDoPacotinho){
            int posicao = figurinha.getPosicao();

            if(figurinhasColadas.containsKey(posicao)){
                figurinhasRepetidas.add(figurinha);
            }

            figurinhasColadas.put(posicao, figurinha);
        }
    }

    public int getTamanho() {
        return this.tamanhoDoAlbum;
    }

    public int getQuantItensColados() {
        return this.figurinhasColadas.size();
    }

    public int getQuantItensRepetidos() {
        return figurinhasRepetidas.size();
    }

    public boolean possuiItemColado(int posicao) {
        return figurinhasColadas.containsKey(posicao);
    }

    public boolean possuiItemRepetido(int posicao) {
        for(T figurinha : figurinhasRepetidas){
           if(figurinha.getPosicao() == posicao)
               return true;
        }
        return false;
    }

    public int getQuantItensFaltantes() {
        return this.tamanhoDoAlbum - getQuantItensColados();
    }

    public T getItemColado(int posicao) {
        return this.figurinhasColadas.get(posicao);
    }
}
