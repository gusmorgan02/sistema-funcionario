package principal;

import janelas.TelaConsultaFuncionario;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            TelaConsultaFuncionario tela = new TelaConsultaFuncionario();
            tela.setVisible(true);
        });
    }
}