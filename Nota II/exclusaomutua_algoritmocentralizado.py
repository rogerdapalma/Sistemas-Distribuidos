import threading
import time  # Adicionando a importação do módulo time

#O termo "mutex" é uma abreviação de "mutual exclusion" (exclusão mútua). Em programação concorrente, um mutex é uma estrutura 
#de dados que é usada para garantir que apenas um thread (ou processo) por vez possa acessar uma seção crítica do código. 
#O principal objetivo do uso de mutex é evitar condições de corrida, onde múltiplos threads podem tentar acessar e modificar 
#os mesmos dados simultaneamente, levando a resultados não determinísticos e possíveis corrupções de dados.

#A operação básica de um mutex envolve dois passos:
#Bloqueio (Lock): Um thread que deseja entrar na seção crítica tenta adquirir o mutex. 
#Se o mutex estiver disponível (não bloqueado), o thread o adquire e entra na seção crítica. 
#Se o mutex já estiver bloqueado por outro thread, o thread solicitante aguarda até que o mutex seja liberado.
#Desbloqueio (Unlock): Um thread, ao terminar a execução da seção crítica, libera o mutex. 
#Isso permite que outros threads possam adquiri-lo e entrar na seção crítica.
#A exclusão mútua garantida pelo mutex evita que múltiplos threads executem simultaneamente a seção crítica,
#proporcionando consistência e integridade nos dados compartilhados.

# Definindo a classe do mutex centralizado
class CentralizedMutex:
    # O método __init__ é um construtor chamado quando uma instância da classe é criada
    def __init__(self):
        # Aqui, criamos um objeto de bloqueio (mutex) usando threading.Lock()
        # O mutex será usado para controlar o acesso à seção crítica
        self.lock = threading.Lock()

    # Método para adquirir o mutex (entrar na seção crítica)
    def acquire(self):
        self.lock.acquire()

    # Método para liberar o mutex (sair da seção crítica)
    def release(self):
        self.lock.release()

# Função que representa a seção crítica
def critical_section(mutex, process_id):
    # Adquire o mutex ao entrar na seção crítica
    mutex.acquire()

    # Seção crítica - Simulação de uma operação crítica
    print(f'Processo {process_id} está na seção crítica.')

    # Libera o mutex ao sair da seção crítica
    mutex.release()

# Função principal
def main():
    # Cria uma instância do mutex centralizado
    mutex = CentralizedMutex()

    #Loop para ficar enviando os processos sem parar
    while True:
        # Define funções para dois processos que utilizam a seção crítica
        def process_0():
            critical_section(mutex, 0)

        def process_1():
            critical_section(mutex, 1)

        def process_2():
            critical_section(mutex, 2)


        # Cria threads para os processos
        thread_0 = threading.Thread(target=process_0)
        thread_1 = threading.Thread(target=process_1)
        thread_2 = threading.Thread(target=process_2)

        # Inicia as threads
        thread_0.start()
        thread_1.start()
        thread_2.start()

        # Aguarda o término das threads
        thread_0.join()
        thread_1.join()
        thread_2.join()

        # Adiciona um pequeno atraso entre os loops para evitar criar threads em excesso
        time.sleep(1)

# Verifica se o script está sendo executado como programa principal
if __name__ == "__main__":
    main()

#Se você remover o bloco if __name__ == "__main__":, o código contido na função main será executado sempre que este
#script for importado por outro script. Isso pode não ser desejável, pois a função main será executada automaticamente
#quando o módulo for importado, mesmo que você só queira usar algumas funções ou classes definidas no script sem executar
# a lógica principal.

#Em resumo, a construção if __name__ == "__main__": é uma boa prática para garantir que o código contido
#nesse bloco seja executado apenas quando o script é executado diretamente, e não quando é importado como
#um módulo em outro script. Isso ajuda a modularizar o código e reutilizá-lo em diferentes contextos.