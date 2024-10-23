package br.com.bancoaura.internetbanking.auxiliares;

public class AuxiliarValidacao {
    public static boolean validarCpf(String cpf) {
        if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
            cpf.equals("22222222222") || cpf.equals("33333333333") ||
            cpf.equals("44444444444") || cpf.equals("55555555555") ||
            cpf.equals("66666666666") || cpf.equals("77777777777") ||
            cpf.equals("88888888888") || cpf.equals("99999999999") ||
            (cpf.length() != 11)
        ) {
            return false;
        }

        char decimoDigito, decimoPrimeiroDigito;
        int soma, indiceDigito, restoDivisao, num, peso;

        try {
            soma = 0;
            peso = 10;
            for (indiceDigito = 0; indiceDigito < 9; indiceDigito++) {
                // (48 é a posicao de '0' na tabela ASCII)
                num = (int)(cpf.charAt(indiceDigito) - 48);
                soma = soma + (num * peso);
                peso = peso - 1;
            }

            restoDivisao = 11 - (soma % 11);
            if (restoDivisao == 10 || restoDivisao == 11) {
                decimoDigito = '0';
            } else {
                decimoDigito = (char) (restoDivisao + 48);
            }

            soma = 0;
            peso = 11;
            for (indiceDigito = 0; indiceDigito < 10; indiceDigito++) {
                num = (int)(cpf.charAt(indiceDigito) - 48);
                soma = soma + (num * peso);
                peso = peso - 1;
            }

            restoDivisao = 11 - (soma % 11);
            if (restoDivisao == 10 || restoDivisao == 11) {
                decimoPrimeiroDigito = '0';
            } else {
                decimoPrimeiroDigito = (char) (restoDivisao + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if (decimoDigito == cpf.charAt(9) && decimoPrimeiroDigito == cpf.charAt(10)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception erro) {
            return false;
        }
    }
}
