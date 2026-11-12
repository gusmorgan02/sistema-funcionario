package janelas;

import dao.FuncionarioDAO;
import entidade.Funcionario;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TelaCadastroFuncionario extends JFrame {

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtCargo;
    private JTextField txtDepartamento;
    private JTextField txtSalario;
    private JTextField txtCargaHoraria;
    private JTextField txtDataAdmissao;
    private JButton btnSalvar;
    private JButton btnCancelar;

    private Funcionario funcionarioEdicao;
    private Runnable aoSalvar;

    public TelaCadastroFuncionario(Funcionario funcionarioParaEditar, Runnable aoSalvar) {
        this.funcionarioEdicao = funcionarioParaEditar;
        this.aoSalvar = aoSalvar;

        configurarJanela();
        criarComponentes();

        if (funcionarioEdicao != null) {
            carregarDadosEdicao();
        }
    }

    private void configurarJanela() {
        setTitle(funcionarioEdicao == null ? "Cadastro de Funcionário" : "Editar Funcionário");
        setSize(445, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void criarComponentes() {
        JLabel lblId = new JLabel("Matrícula (ID):");
        lblId.setBounds(25, 20, 150, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(180, 20, 220, 25);
        txtId.setEnabled(false); // Chave primária não pode ser alterada
        add(txtId);

        JLabel lblNome = new JLabel("Nome Completo:");
        lblNome.setBounds(25, 60, 150, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(180, 60, 220, 25);
        add(txtNome);

        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setBounds(25, 100, 150, 25);
        add(lblCargo);

        txtCargo = new JTextField();
        txtCargo.setBounds(180, 100, 220, 25);
        add(txtCargo);

        JLabel lblDepartamento = new JLabel("Departamento:");
        lblDepartamento.setBounds(25, 140, 150, 25);
        add(lblDepartamento);

        txtDepartamento = new JTextField();
        txtDepartamento.setBounds(180, 140, 220, 25);
        add(txtDepartamento);

        JLabel lblSalario = new JLabel("Salário (R$):");
        lblSalario.setBounds(25, 180, 150, 25);
        add(lblSalario);

        txtSalario = new JTextField();
        txtSalario.setBounds(180, 180, 220, 25);
        add(txtSalario);

        JLabel lblCargaHoraria = new JLabel("Carga Horária (h):");
        lblCargaHoraria.setBounds(25, 220, 150, 25);
        add(lblCargaHoraria);

        txtCargaHoraria = new JTextField();
        txtCargaHoraria.setBounds(180, 220, 220, 25);
        add(txtCargaHoraria);

        JLabel lblDataAdmissao = new JLabel("Admissão (dd/mm/aaaa):");
        lblDataAdmissao.setBounds(25, 260, 150, 25);
        add(lblDataAdmissao);

        txtDataAdmissao = new JTextField();
        txtDataAdmissao.setBounds(180, 260, 220, 25);
        add(txtDataAdmissao);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(95, 310, 110, 30);
        btnSalvar.addActionListener(e -> salvar());
        add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(225, 310, 110, 30);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void carregarDadosEdicao() {
        txtId.setText(String.valueOf(funcionarioEdicao.getId()));
        txtNome.setText(funcionarioEdicao.getNome());
        txtCargo.setText(funcionarioEdicao.getCargo());
        txtDepartamento.setText(funcionarioEdicao.getDepartamento());
        txtSalario.setText(String.valueOf(funcionarioEdicao.getSalario()));
        txtCargaHoraria.setText(String.valueOf(funcionarioEdicao.getCargaHoraria()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDataAdmissao.setText(sdf.format(funcionarioEdicao.getDataAdmissao()));
    }

    private void salvar() {
        String nome = txtNome.getText().trim();
        String cargo = txtCargo.getText().trim();
        String departamento = txtDepartamento.getText().trim();
        String salarioTexto = txtSalario.getText().trim().replace(",", ".");
        String cargaTexto = txtCargaHoraria.getText().trim();
        String admissaoTexto = txtDataAdmissao.getText().trim();

        if (nome.isBlank() || cargo.isBlank() || departamento.isBlank() || salarioTexto.isBlank() || cargaTexto.isBlank() || admissaoTexto.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double salario = Double.parseDouble(salarioTexto);
            int cargaHoraria = Integer.parseInt(cargaTexto);

            if (salario <= 0 || cargaHoraria <= 0) {
                JOptionPane.showMessageDialog(this, "Salário e Carga Horária devem ser maiores que zero.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            java.util.Date dataConvertida = sdf.parse(admissaoTexto);
            Date dataAdmissao = new Date(dataConvertida.getTime());

            FuncionarioDAO dao = new FuncionarioDAO();

            if (funcionarioEdicao == null) {
                Funcionario novo = new Funcionario(nome, cargo, departamento, salario, cargaHoraria, dataAdmissao);
                if (dao.inserir(novo)) {
                    JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
                    if (aoSalvar != null) aoSalvar.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar funcionário no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                funcionarioEdicao.setNome(nome);
                funcionarioEdicao.setCargo(cargo);
                funcionarioEdicao.setDepartamento(departamento);
                funcionarioEdicao.setSalario(salario);
                funcionarioEdicao.setCargaHoraria(cargaHoraria);
                funcionarioEdicao.setDataAdmissao(dataAdmissao);

                if (dao.atualizar(funcionarioEdicao)) {
                    JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
                    if (aoSalvar != null) aoSalvar.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar funcionário no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Salário e Carga Horária devem ser números válidos.", "Erro de Tipo", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data de admissão inválida. Utilize o formato dd/mm/aaaa.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
        }
    }
}