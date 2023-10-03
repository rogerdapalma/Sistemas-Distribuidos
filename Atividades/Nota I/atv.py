# -*- coding: utf-8 -*-
"""atv.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/17XsqX_5dsyB_UyURv9rAXXDxqfR9IYrq
"""

#EX001

import threading

class ThreadMostraNome(threading.Thread):
    def __init__(self, nome):
        super().__init__()
        self.nome = nome

    def run(self):
        while True:
            print(self.nome)

def main():
    threads = []

    for i in range(1, 11):
        thread = ThreadMostraNome(f'Thread {i}')
        thread.start()
        threads.append(thread)

    # Espera indefinidamente (você pode interromper o programa manualmente)
    for thread in threads:
        thread.join()

if __name__ == "__main__":
    main()

#EX002

import threading

class Contador(threading.Thread):
    def run(self):
        for i in range(11):
            print(i)

def main():
    threads = []

    # Crie duas ou mais instâncias da classe Contador
    for _ in range(2):
        thread = Contador()
        threads.append(thread)

    # Inicie a execução das threads
    for thread in threads:
        thread.start()

    # Espere até que todas as threads tenham terminado
    for thread in threads:
        thread.join()

if __name__ == "__main__":
    main()

#EX003

import threading
import random

def somar_linha(matriz, linha, resultado):
    soma = sum(matriz[linha])
    resultado[linha] = soma

def main():
    # Leitura das dimensões da matriz
    L = int(input("Digite o número de linhas: "))
    C = int(input("Digite o número de colunas: "))

    # Inicialização da matriz com valores aleatórios
    matriz = [[random.randint(1, 10) for _ in range(C)] for _ in range(L)]
    print("Matriz:")
    for linha in matriz:
        print(linha)

    # Inicialização da lista de resultados
    resultados = [0] * L

    # Criação e inicialização das threads
    threads = []
    for linha in range(L):
        thread = threading.Thread(target=somar_linha, args=(matriz, linha, resultados))
        threads.append(thread)

    # Início das threads
    for thread in threads:
        thread.start()

    # Aguarda o término de todas as threads
    for thread in threads:
        thread.join()

    # Exibe os resultados
    for i, soma in enumerate(resultados):
        print(f"Soma da linha {i}: {soma}")

if __name__ == "__main__":
    main()

#EX004

import threading
import random
import time

class MinhaThread(threading.Thread):
    def __init__(self, thread_id, numero_aleatorio):
        super().__init__()
        self.thread_id = thread_id
        self.numero_aleatorio = numero_aleatorio

    def run(self):
        print(f"Thread {self.thread_id} criada")
        for i in range(self.numero_aleatorio + 1):
            print(f"Thread {self.thread_id}: {i}")
            if i == self.numero_aleatorio:
                print(f"Thread {self.thread_id} entrando em espera")
                tempo_de_espera = random.randint(0, 5)
                time.sleep(tempo_de_espera)
                print(f"Thread {self.thread_id} saindo da espera")
        print(f"Thread {self.thread_id} execução finalizada")

def main():
    num_threads = int(input("Digite a quantidade de threads a serem criadas: "))

    threads = []
    for i in range(num_threads):
        numero_aleatorio = random.randint(0, 100000)
        thread = MinhaThread(i, numero_aleatorio)
        threads.append(thread)
        thread.start()

    for thread in threads:
        thread.join()

if __name__ == "__main__":
    main()

#EX005


import threading
import random
import time

# Semáforo para controlar o número máximo de threads em execução
max_threads = threading.Semaphore(10)

class MinhaThread(threading.Thread):
    def run(self):
        # Suspender a execução por um tempo aleatório entre 0 e 5 segundos
        tempo_de_espera = random.randint(0, 5)
        time.sleep(tempo_de_espera)
        # Libera o semáforo para indicar que esta thread terminou
        max_threads.release()

def main():
    while True:
        # Verifica se há menos de 10 threads em execução e cria uma nova thread
        if max_threads.acquire(blocking=False):
            thread = MinhaThread()
            thread.start()
        else:
            # Se já há 10 threads em execução, espera até que uma delas termine
            time.sleep(1)

if __name__ == "__main__":
    main()