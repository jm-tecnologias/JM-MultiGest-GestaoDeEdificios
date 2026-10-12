/*
 * The MIT License
 *
 * Copyright 2025 JM-Tecnologias.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jmmultigest.utils;

/**
 *
 * @author JM-Tecnologias
 */
import com.fazecast.jSerialComm.SerialPort;

public class LeitorSerialListener {

    public interface CodigoListener {

        void onCodigoLido(String codigo);
    }

    private final SerialPort porta;
    private final CodigoListener listener;

    private volatile boolean activo;
    private Thread threadLeitura;

    public LeitorSerialListener(
            String nomePorta,
            CodigoListener listener) {

        this.listener = listener;
        this.porta = SerialPort.getCommPort(nomePorta);

        porta.setComPortParameters(
                9600,
                8,
                SerialPort.ONE_STOP_BIT,
                SerialPort.NO_PARITY
        );

        porta.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_SEMI_BLOCKING,
                1000,
                0
        );
    }

    public synchronized void iniciar() {
    if (activo) {
        return;
    }

    // Configura a leitura para aguardar dados até 50 ms.
    porta.setComPortTimeouts(
            com.fazecast.jSerialComm.SerialPort.TIMEOUT_READ_SEMI_BLOCKING,
            50,
            0
    );

    if (!porta.openPort()) {
        System.err.println(
                "Não foi possível abrir a porta serial: "
                + porta.getSystemPortName()
        );
        return;
    }

    activo = true;

    threadLeitura = new Thread(() -> {
        StringBuilder codigoBuffer = new StringBuilder();
        byte[] buffer = new byte[256];

        final long TEMPO_INACTIVIDADE_NS = 100_000_000L;

        long ultimoByteRecebido = 0;

        try {
            while (activo && porta.isOpen()) {

                int quantidade = porta.readBytes(
                        buffer,
                        buffer.length
                );

                if (quantidade > 0) {

                    // Regista o momento da última recepção.
                    ultimoByteRecebido = System.nanoTime();

                    for (int i = 0; i < quantidade; i++) {
                        char caractere = (char) (buffer[i] & 0xFF);

                        // Acumula os caracteres recebidos.
                        codigoBuffer.append(caractere);
                    }

                } else if (codigoBuffer.length() > 0
                        && ultimoByteRecebido > 0
                        && System.nanoTime() - ultimoByteRecebido
                        >= TEMPO_INACTIVIDADE_NS) {

                    // Não chegaram novos caracteres durante 300 ms.
                    String codigoCompleto =
                            codigoBuffer.toString().trim();

                    // Prepara o buffer para a próxima leitura.
                    codigoBuffer.setLength(0);
                    ultimoByteRecebido = 0;

                    if (!codigoCompleto.isEmpty()) {
                        System.out.println(
                                "Código completo recebido: "
                                + codigoCompleto
                        );

                        listener.onCodigoLido(codigoCompleto);
                    }
                }
            }

        } catch (Exception ex) {
            if (activo) {
                System.err.println(
                        "Erro na leitura serial: " + ex.getMessage()
                );
            }

        } finally {
            activo = false;

            if (porta.isOpen()) {
                porta.closePort();
            }
        }

    }, "LeitorSerialThread");

    threadLeitura.setDaemon(true);
    threadLeitura.start();

    System.out.println(
            "Porta aberta com sucesso. Aguardando códigos..."
    );
}

    public synchronized void parar() {
        activo = false;

        if (porta.isOpen()) {
            porta.closePort();
        }

        if (threadLeitura != null) {
            threadLeitura.interrupt();
        }

        System.out.println("Leitor serial parado.");
    }
}
