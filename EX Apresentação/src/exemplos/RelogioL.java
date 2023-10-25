package exemplos;

import java.util.concurrent.locks.ReentrantLock;

// Classe que representa o relógio lógico
class RelogioLogico {
    private int tempo = 0;

    // Método para obter o tempo atual do relógio
    public synchronized int getTempo() {
        return tempo;
    }

    // Método para atualizar o tempo do relógio
    public synchronized void atualizarTempo(int novoTempo) {
        if (novoTempo > tempo) {
            tempo = novoTempo;
        }
        tempo++; // Garante que cada evento tenha um carimbo de tempo único
    }
}

// Classe que representa um processo
class Processo implements Runnable {
    private int id;
    private RelogioLogico relogio;
    private ReentrantLock lock;

    public Processo(int id, RelogioLogico relogio, ReentrantLock lock) {
        this.id = id;
        this.relogio = relogio;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep((int) (Math.random() * 1000)); // Simula atividades
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.lock(); // Bloqueia o acesso ao relógio lógico

            // Atualiza o tempo do relógio lógico e obtém o tempo local
            relogio.atualizarTempo(relogio.getTempo() + 1);
            int tempoLocal = relogio.getTempo();

            lock.unlock(); // Libera o acesso ao relógio lógico

            // Imprime a ação realizada pelo processo e o tempo associado a ela
            System.out.println("Processo " + id + " realizou acao " + i + " no tempo " + tempoLocal);
        }
    }
}

public class RelogioL {
    public static void main(String[] args) {
        int numProcessos = 3;
        RelogioLogico relogio = new RelogioLogico();
        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < numProcessos; i++) {
            // Cria e inicia threads representando os processos
            Thread processoThread = new Thread(new Processo(i, relogio, lock));
            processoThread.start();
        }
    }
}
