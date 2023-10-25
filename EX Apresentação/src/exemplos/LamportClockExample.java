package exemplos;

import java.util.concurrent.atomic.AtomicInteger;

class LamportClock {
    private AtomicInteger clock = new AtomicInteger(0);

    public int getTime() {
        return clock.get();
    }
    //getTime(): Retorna o valor atual do relógio.

    public void increment() {
        clock.getAndIncrement();
    }
    //increment(): Incrementa o valor do relógio em 1.

    public void update(int receivedTime) {
        clock.set(Math.max(clock.get(), receivedTime) + 1);
    }
}
    //update(receivedTime): Atualiza o valor do relógio com base no tempo recebido de outra entidade. O tempo do relógio será o máximo entre seu próprio tempo e o tempo recebido mais 1.
    //Process Class: Esta classe estende Thread e simula um processo concorrente. Cada processo é inicializado com um relógio de Lamport.

class Process extends Thread {
    private LamportClock clock;

    public Process(LamportClock clock) {
        this.clock = clock;
    }

    @Override
    //No método run(), o processo registra o tempo de início, dorme por 2 segundos para simular alguma atividade e, em seguida, registra o tempo de término. Durante esses eventos, o relógio de Lamport é usado para marcar o tempo de início e término.
    //Main Class (LamportClockExample): Aqui está o ponto de entrada do programa:
    public void run() {
        int startTime = clock.getTime();
        System.out.println("Process starts at time: " + startTime);

        // Simulando alguma atividade
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int endTime = clock.getTime();
        System.out.println("Process finishes at time: " + endTime);
    }
}

public class LamportClockExample {
    public static void main(String[] args) {
        LamportClock clock = new LamportClock();
        Process process1 = new Process(clock);
        Process process2 = new Process(clock);

        process1.start();
        process2.start();

        try {
            process1.join();
            process2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Lamport Clock value: " + clock.getTime());
    }
}


/*
Um objeto LamportClock é criado para ser compartilhado entre os processos.
Dois objetos Process são criados, cada um com o mesmo relógio de Lamport.
Ambos os processos são iniciados concorrentemente.
A função join() é usada para esperar até que ambos os processos terminem sua execução.
Após a conclusão dos processos, o tempo atual do relógio de Lamport é impresso.
*/