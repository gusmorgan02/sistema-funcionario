package janelas;

import dao.FuncionarioDAO;
import entidade.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaConsultaFuncionario extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    private FuncionarioDAO dao = new FuncionarioDAO();
    private List<Funcionario> listaFuncionarios;

    public TelaConsultaFuncionario() {
        setTitle("Gestão de Pessoal - Consulta de Funcionários");
        setSize(780, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"ID", "Nome", "Cargo", "Departamento", "Salário (R$)", "Carga Horária", "Admissão"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnNovo = new JButton("Novo Funcionário");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Lista");

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnNovo.addActionListener(e -> {
            TelaCadastroFuncionario tela = new TelaCadastroFuncionario(null, this::carregarTabela);
            tela.setVisible(true);
        });

        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
        btnAtualizar.addActionListener(e -> carregarTabela());

        carregarTabela();
    }

    public void carregarTabela() {
        modeloTabela.setRowCount(0);
        listaFuncionarios = dao.listar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Funcionario f : listaFuncionarios) {
            modeloTabela.addRow(new Object[]{
                    f.getId(),
                    f.getNome(),
                    f.getCargo(),
                    f.getDepartamento(),
                    String.format("%.2f", f.getSalario()),
                    f.getCargaHoraria() + "h",
                    sdf.format(f.getDataAdmissao())
            });
        }
    }

    private void editarSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Funcionario selecionado = listaFuncionarios.get(linha);
        TelaCadastroFuncionario tela = new TelaCadastroFuncionario(selecionado, this::carregarTabela);
        tela.setVisible(true);
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Funcionario selecionado = listaFuncionarios.get(linha);
        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o funcionário \"" + selecionado.getNome() + "\"?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {
            if (dao.excluir(selecionado.getId())) {
                JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}