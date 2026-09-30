package calculadora_figuras;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class CalculadoraFigurasUI extends JFrame {

    // Paneles principales
    private JPanel panelContenedor;
    private JPanel panelResultados;
    private JPanel panelPrincipal;

    private JComboBox<String> listaFiguras;

    private JLabel lblArea;
    private JLabel lblPerimetro;

    private JLabel lblBase;
    private JTextField txtBase;
    private JLabel lblAltura;
    private JTextField txtAltura;
    private JLabel lblRadio;
    private JTextField txtRadio;
    private JLabel lblLado;
    private JTextField txtLado;

    // === CAMPOS DEL TRAPECIO Y OTRAS FIGURAS ===
    private JLabel lblBaseMayor;
    private JTextField txtBaseMayor;
    private JLabel lblBaseMenor;
    private JTextField txtBaseMenor;

    private JLabel lblDiagonalMayor;
    private JTextField txtDiagonalMayor;
    private JLabel lblDiagonalMenor;
    private JTextField txtDiagonalMenor;

    private JLabel lblNumLados;
    private JTextField txtNumLados;
    private JLabel lblApotema;
    private JTextField txtApotema;

    private JButton btnCalcular;
    private JButton btnLimpiar;

    // Colores del tema
    private static final Color VERDE = new Color(34, 139, 34);
    private static final Color VERDE_OSCURO = new Color(28, 110, 28);
    private static final Color AZUL = new Color(65, 105, 225);
    private static final Color GRIS_OSCURO = new Color(40, 40, 60);
    private static final Color GRIS_CLARO = new Color(245, 245, 250);

    public CalculadoraFigurasUI() {
        aplicarTema();

        setTitle("Calculadora de Áreas y Perímetros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (panelContenedor != null) {
            setContentPane(panelContenedor);
        } else if (panelPrincipal != null) {
            setContentPane(panelPrincipal);
        }

        // Llenar el ComboBox
        if (listaFiguras != null && listaFiguras.getItemCount() == 0) {
            listaFiguras.addItem("Rectángulo");
            listaFiguras.addItem("Cuadrado");
            listaFiguras.addItem("Triángulo");
            listaFiguras.addItem("Círculo");
            listaFiguras.addItem("Trapecio");
            listaFiguras.addItem("Rombo");
            listaFiguras.addItem("Polígono");
            listaFiguras.addItem("Paralelogramo");
        }

        // Evento al cambiar de figura con actualización completa del Layout
        if (listaFiguras != null) {
            listaFiguras.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    limpiar();
                    actualizarVisibilidadCampos();
                    revalidate();
                    repaint();
                    pack();
                }
            });
        }

        // Configuración de botones
        if (btnCalcular != null) {
            btnCalcular.addActionListener(e -> calcular());
            btnCalcular.setBackground(VERDE);
            btnCalcular.setForeground(Color.WHITE);
            btnCalcular.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnCalcular.setFocusPainted(false);
            btnCalcular.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnCalcular.setBackground(VERDE_OSCURO);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnCalcular.setBackground(VERDE);
                }
            });
        }

        if (btnLimpiar != null) {
            btnLimpiar.addActionListener(e -> limpiar());
            btnLimpiar.setBackground(VERDE);
            btnLimpiar.setForeground(Color.WHITE);
            btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnLimpiar.setFocusPainted(false);
            btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnLimpiar.setBackground(VERDE_OSCURO);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnLimpiar.setBackground(VERDE);
                }
            });
        }

        actualizarVisibilidadCampos();
        pack();
        setLocationRelativeTo(null);
    }

    private void aplicarTema() {
        if (panelPrincipal != null) {
            panelPrincipal.setBackground(GRIS_CLARO);
        }

        if (listaFiguras != null) {
            listaFiguras.setBackground(Color.WHITE);
            listaFiguras.setForeground(GRIS_OSCURO);
            listaFiguras.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        aplicarLabel(lblBase);
        aplicarLabel(lblAltura);
        aplicarLabel(lblRadio);
        aplicarLabel(lblLado);
        aplicarLabel(lblBaseMayor);
        aplicarLabel(lblBaseMenor);
        aplicarLabel(lblDiagonalMayor);
        aplicarLabel(lblDiagonalMenor);
        aplicarLabel(lblNumLados);
        aplicarLabel(lblApotema);
        aplicarLabel(lblArea);
        aplicarLabel(lblPerimetro);

        aplicarTextField(txtBase);
        aplicarTextField(txtAltura);
        aplicarTextField(txtRadio);
        aplicarTextField(txtLado);
        aplicarTextField(txtBaseMayor);
        aplicarTextField(txtBaseMenor);
        aplicarTextField(txtDiagonalMayor);
        aplicarTextField(txtDiagonalMenor);
        aplicarTextField(txtNumLados);
        aplicarTextField(txtApotema);
    }

    private void aplicarLabel(JLabel label) {
        if (label != null) {
            label.setForeground(GRIS_OSCURO);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }
    }

    private void aplicarTextField(JTextField field) {
        if (field != null) {
            field.setBackground(Color.WHITE);
            field.setForeground(GRIS_OSCURO);
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 220)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
        }
    }

    private void actualizarVisibilidadCampos() {
        String figura = (listaFiguras != null) ? (String) listaFiguras.getSelectedItem() : "Rectángulo";

        // Ocultar todos los campos
        setVisibilidadCampo(lblBase, txtBase, false);
        setVisibilidadCampo(lblAltura, txtAltura, false);
        setVisibilidadCampo(lblRadio, txtRadio, false);
        setVisibilidadCampo(lblLado, txtLado, false);
        setVisibilidadCampo(lblBaseMayor, txtBaseMayor, false);
        setVisibilidadCampo(lblBaseMenor, txtBaseMenor, false);
        setVisibilidadCampo(lblDiagonalMayor, txtDiagonalMayor, false);
        setVisibilidadCampo(lblDiagonalMenor, txtDiagonalMenor, false);
        setVisibilidadCampo(lblNumLados, txtNumLados, false);
        setVisibilidadCampo(lblApotema, txtApotema, false);

        // Mostrar según figura seleccionada
        if ("Rectángulo".equals(figura) || "Triángulo".equals(figura)) {
            setVisibilidadCampo(lblBase, txtBase, true);
            setVisibilidadCampo(lblAltura, txtAltura, true);
        } else if ("Cuadrado".equals(figura)) {
            setVisibilidadCampo(lblLado, txtLado, true);
        } else if ("Círculo".equals(figura)) {
            setVisibilidadCampo(lblRadio, txtRadio, true);
        } else if ("Trapecio".equals(figura)) {
            setVisibilidadCampo(lblBaseMayor, txtBaseMayor, true);
            setVisibilidadCampo(lblBaseMenor, txtBaseMenor, true);
            setVisibilidadCampo(lblAltura, txtAltura, true);
        } else if ("Rombo".equals(figura)) {
            setVisibilidadCampo(lblDiagonalMayor, txtDiagonalMayor, true);
            setVisibilidadCampo(lblDiagonalMenor, txtDiagonalMenor, true);
        } else if ("Polígono".equals(figura)) {
            setVisibilidadCampo(lblNumLados, txtNumLados, true);
            setVisibilidadCampo(lblLado, txtLado, true);
            setVisibilidadCampo(lblApotema, txtApotema, true);
        } else if ("Paralelogramo".equals(figura)) {
            setVisibilidadCampo(lblBase, txtBase, true);
            setVisibilidadCampo(lblAltura, txtAltura, true);
            setVisibilidadCampo(lblLado, txtLado, true);
        }
    }

    private void setVisibilidadCampo(JLabel label, JTextField textField, boolean visible) {
        if (label != null) label.setVisible(visible);
        if (textField != null) textField.setVisible(visible);
    }

    private void calcular() {
        try {
            String figura = (listaFiguras != null) ? (String) listaFiguras.getSelectedItem() : "Rectángulo";

            if ("Rectángulo".equals(figura)) {
                if (txtBase == null || txtAltura == null) return;
                double base = Double.parseDouble(txtBase.getText().trim());
                double altura = Double.parseDouble(txtAltura.getText().trim());
                actualizarResultados(base * altura, 2 * (base + altura));

            } else if ("Triángulo".equals(figura)) {
                if (txtBase == null || txtAltura == null) return;
                double base = Double.parseDouble(txtBase.getText().trim());
                double altura = Double.parseDouble(txtAltura.getText().trim());
                actualizarResultados((base * altura) / 2.0, base * 3);

            } else if ("Cuadrado".equals(figura)) {
                if (txtLado == null) return;
                double lado = Double.parseDouble(txtLado.getText().trim());
                actualizarResultados(lado * lado, 4 * lado);

            } else if ("Círculo".equals(figura)) {
                if (txtRadio == null) return;
                double radio = Double.parseDouble(txtRadio.getText().trim());
                actualizarResultados(Math.PI * Math.pow(radio, 2), 2 * Math.PI * radio);

            } else if ("Trapecio".equals(figura)) {
                calcularTrapecio();

            } else if ("Rombo".equals(figura)) {
                calcularRombo();

            } else if ("Polígono".equals(figura)) {
                calcularPoligono();

            } else if ("Paralelogramo".equals(figura)) {
                calcularParalelogramo();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingresa números válidos en todos los campos visibles.",
                    "Error de Entrada",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularTrapecio() {
        if (txtBaseMayor == null || txtBaseMenor == null || txtAltura == null) {
            JOptionPane.showMessageDialog(this,
                    "Error: Los componentes del Trapecio no están vinculados en el .form",
                    "Error de Componentes",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String strBaseMayor = txtBaseMayor.getText().trim();
        String strBaseMenor = txtBaseMenor.getText().trim();
        String strAltura = txtAltura.getText().trim();

        if (strBaseMayor.isEmpty() || strBaseMenor.isEmpty() || strAltura.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor llena los 3 campos del Trapecio (Base Mayor, Base Menor y Altura).",
                    "Campos Incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double baseMayor = Double.parseDouble(strBaseMayor);
        double baseMenor = Double.parseDouble(strBaseMenor);
        double altura = Double.parseDouble(strAltura);

        if (baseMayor <= baseMenor) {
            JOptionPane.showMessageDialog(this,
                    "La Base Mayor debe ser más grande que la Base Menor.",
                    "Error Geométrico",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double area = ((baseMayor + baseMenor) * altura) / 2.0;
        double cateto = (baseMayor - baseMenor) / 2.0;
        double ladoOblicuo = Math.sqrt(Math.pow(cateto, 2) + Math.pow(altura, 2));
        double perimetro = baseMayor + baseMenor + (2 * ladoOblicuo);

        actualizarResultados(area, perimetro);
    }

    private void calcularRombo() {
        if (txtDiagonalMayor == null || txtDiagonalMenor == null) return;
        double dMayor = Double.parseDouble(txtDiagonalMayor.getText().trim());
        double dMenor = Double.parseDouble(txtDiagonalMenor.getText().trim());
        double area = (dMayor * dMenor) / 2.0;
        double perimetro = 4 * Math.sqrt(Math.pow(dMayor / 2.0, 2) + Math.pow(dMenor / 2.0, 2));
        actualizarResultados(area, perimetro);
    }

    private void calcularPoligono() {
        if (txtNumLados == null || txtLado == null || txtApotema == null) return;
        double numLados = Double.parseDouble(txtNumLados.getText().trim());
        double lado = Double.parseDouble(txtLado.getText().trim());
        double apotema = Double.parseDouble(txtApotema.getText().trim());
        double perimetro = numLados * lado;
        double area = (perimetro * apotema) / 2.0;
        actualizarResultados(area, perimetro);
    }

    private void calcularParalelogramo() {
        if (txtBase == null || txtAltura == null || txtLado == null) return;
        double base = Double.parseDouble(txtBase.getText().trim());
        double altura = Double.parseDouble(txtAltura.getText().trim());
        double lado = Double.parseDouble(txtLado.getText().trim());
        double perimetro = 2 * (base + lado);
        double area = base * altura;
        actualizarResultados(area, perimetro);
    }

    private void actualizarResultados(double area, double perimetro) {
        if (lblArea != null) {
            lblArea.setText(String.format("Área: %.2f", area));
            lblArea.setForeground(VERDE);
            lblArea.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
        if (lblPerimetro != null) {
            lblPerimetro.setText(String.format("Perímetro: %.2f", perimetro));
            lblPerimetro.setForeground(AZUL);
            lblPerimetro.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
    }

    private void limpiar() {
        if (txtBase != null) txtBase.setText("");
        if (txtAltura != null) txtAltura.setText("");
        if (txtRadio != null) txtRadio.setText("");
        if (txtLado != null) txtLado.setText("");
        if (txtBaseMayor != null) txtBaseMayor.setText("");
        if (txtBaseMenor != null) txtBaseMenor.setText("");
        if (txtDiagonalMayor != null) txtDiagonalMayor.setText("");
        if (txtDiagonalMenor != null) txtDiagonalMenor.setText("");
        if (txtNumLados != null) txtNumLados.setText("");
        if (txtApotema != null) txtApotema.setText("");

        if (lblArea != null) {
            lblArea.setText("Área:");
            lblArea.setForeground(GRIS_OSCURO);
            lblArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }
        if (lblPerimetro != null) {
            lblPerimetro.setText("Perímetro:");
            lblPerimetro.setForeground(GRIS_OSCURO);
            lblPerimetro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraFigurasUI frame = new CalculadoraFigurasUI();
            frame.setVisible(true);
        });
    }
}